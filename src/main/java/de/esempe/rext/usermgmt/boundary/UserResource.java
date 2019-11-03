package de.esempe.rext.usermgmt.boundary;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.google.common.base.Strings;

import de.esempe.rext.usermgmt.boundary.exceptionhandling.ExceptionHandlingInterceptor;
import de.esempe.rext.usermgmt.domain.User;

@Stateless(description = "REST-Interface für User")
@Path("/usermgmt/users")
@Interceptors({ ExceptionHandlingInterceptor.class })
public class UserResource
{
	@PersistenceContext(name = "userdb")
	EntityManager em;

	@Context
	UriInfo uriInfo;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers()
	{
		final List<User> users = this.loadAll();
		return Response.ok(users).build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsersByLogin(@QueryParam("login") final String login)
	{
		if (Strings.isNullOrEmpty(login))
		{
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		final Optional<User> searchResult = this.findByLoginId(login);

		if (searchResult.isPresent())
		{
			return Response.ok(searchResult.get()).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResourceById(@PathParam("id") final String resourceId) throws Exception
	{
		final UUID objid = this.convert2UUID(resourceId);
		final Optional<User> searchResult = this.findByObjId(objid);

		if (searchResult.isPresent())
		{
			return Response.ok(searchResult.get()).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	UUID convert2UUID(final String resourceId)
	{
		try
		{
			return UUID.fromString(resourceId);
		}
		catch (final IllegalArgumentException e)
		{
			throw new WebApplicationException("Ungültiger Wert für UUID", Response.Status.BAD_REQUEST);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteResourceById(@PathParam("id") final String resourceId)
	{
		final UUID objid = this.convert2UUID(resourceId);
		final Optional<User> searchResult = this.findByObjId(objid);

		if (searchResult.isPresent())
		{
			this.delete(objid);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createResource(final User user)
	{
		final UUID objid = user.getObjid();

		// prüfen, ob User bereits vorhanden
		Optional<User> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("User mit Objekt-ID bereits vorhanden").build();
		}
		searchResult = this.findByLoginId(user.getLogin());
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("User mit Login bereits vorhanden").build();
		}

		// User ist neu --> persistieren
		this.save(user);
		final URI linkURI = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path(objid.toString()).build();
		final Link link = Link.fromUri(linkURI).rel("self").type(MediaType.APPLICATION_JSON).build();
		return Response.noContent().links(link).build();

	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateResource(@PathParam("id") final String resourceId, final User user)
	{
		final UUID objid = this.convert2UUID(resourceId);

		// REST-Pfad-ID muss mit ID im Objekt übereinstimmen
		if (false == objid.equals(user.getObjid()))
		{
			final String reason = String.format("Request-Id %s ungleich der Object-Id %s", objid, user.getObjid());
			return Response.status(Response.Status.BAD_REQUEST).entity(reason).build();
		}

		final Optional<User> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.save(user);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}

	/****************************************************************************************
	 *
	 * Methoden für Persistierung
	 *
	 *****************************************************************************************/

	List<User> loadAll()
	{
		return this.em.createNamedQuery("all", User.class).getResultList();
	}

	void save(final User user)
	{
		final Optional<User> findResult = this.findByObjId(user.getObjid());

		// vorhandene Entität?
		if (findResult.isPresent())
		{
			// --> Update
			user.setIdFrom(findResult.get());
			this.em.merge(user);
		}
		else
		{
			// --> Insert
			this.em.persist(user);
		}
		this.em.flush();
	}

	void delete(final UUID objid)
	{
		final Optional<User> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.em.remove(searchResult.get());
			this.em.flush();
		}
	}

	void delete(final User user)
	{
		this.delete(user.getObjid());
	}

	Optional<User> findByObjId(final UUID objid)
	{
		return this.findByNamedQuery("byObjId", "objid", objid);
	}

	Optional<User> findByLoginId(final String login)
	{
		return this.findByNamedQuery("byLogin", "login", login);
	}

	Optional<User> findByNamedQuery(final String nameOfQuery, final String nameOfParameter, final Object valueOfParameter)
	{
		Optional<User> result = Optional.empty();

		try
		{
			final TypedQuery<User> qry = this.em.createNamedQuery(nameOfQuery, User.class);
			qry.setParameter(nameOfParameter, valueOfParameter);
			final User user = qry.getSingleResult();
			result = Optional.of(user);
		}
		// kein Ergebnis
		catch (final NoResultException e)
		{
			// nichts zu tun: dann wird "leeres" Optional geliefertS
		}
		// 2-n Ergebnisse --> hier nicht möglich: Id bzw. Login sind unique
		// catch (final NonUniqueResultException e)
		// {
		//
		// }

		return result;

	}

}

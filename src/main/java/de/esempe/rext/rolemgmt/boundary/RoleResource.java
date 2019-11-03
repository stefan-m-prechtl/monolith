package de.esempe.rext.rolemgmt.boundary;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
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

import de.esempe.rext.rolemgmt.domain.Role;

@Stateless(description = "REST-Interface für Role")
@Path(Constants.path)
public class RoleResource
{
	@PersistenceContext(name = Constants.PersistenceContext)
	EntityManager em;

	@Context
	UriInfo uriInfo;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoles()
	{
		final List<Role> Roles = this.loadAll();
		return Response.ok(Roles).build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRolesByLogin(@QueryParam("name") final String name)
	{
		if (Strings.isNullOrEmpty(name))
		{
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		final Optional<Role> searchResult = this.findByName(name);

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
		final Optional<Role> searchResult = this.findByObjId(objid);

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
		final Optional<Role> searchResult = this.findByObjId(objid);

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
	public Response createResource(final Role role)
	{
		final UUID objid = role.getObjId();

		// prüfen, ob Rolle bereits vorhanden
		Optional<Role> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("Rolle mit Objekt-ID bereits vorhanden").build();
		}
		searchResult = this.findByName(role.getName());
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("Geleichnamige Rolle bereits vorhanden").build();
		}

		// Rolle ist neu --> persistieren
		this.save(role);
		final URI linkURI = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path(objid.toString()).build();
		final Link link = Link.fromUri(linkURI).rel("self").type(MediaType.APPLICATION_JSON).build();
		return Response.noContent().links(link).build();

	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateResource(@PathParam("id") final String resourceId, final Role role)
	{
		final UUID objid = this.convert2UUID(resourceId);

		// REST-Pfad-ID muss mit ID im Objekt übereinstimmen
		if (false == objid.equals(role.getObjId()))
		{
			final String reason = String.format("Request-Id %s ungleich der Object-Id %s", objid, role.getObjId());
			return Response.status(Response.Status.BAD_REQUEST).entity(reason).build();
		}

		final Optional<Role> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.save(role);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}

	/****************************************************************************************
	 *
	 * Methoden für Persistierung
	 *
	 *****************************************************************************************/

	List<Role> loadAll()
	{
		return this.em.createNamedQuery(Constants.all, Role.class).getResultList();
	}

	void save(final Role role)
	{
		final Optional<Role> findResult = this.findByObjId(role.getObjId());

		// vorhandene Entität?
		if (findResult.isPresent())
		{
			// --> Update
			role.setId(findResult.get().getId());
			this.em.merge(role);
		}
		else
		{
			// --> Insert
			this.em.persist(role);
		}
		this.em.flush();
	}

	void delete(final UUID objid)
	{
		final Optional<Role> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.em.remove(searchResult.get());
			this.em.flush();
		}
	}

	void delete(final Role role)
	{
		this.delete(role.getObjId());
	}

	Optional<Role> findByObjId(final UUID objid)
	{
		return this.findByNamedQuery(Constants.byObjId, "objid", objid);
	}

	Optional<Role> findByName(final String name)
	{
		return this.findByNamedQuery(Constants.byName, "name", name);
	}

	Optional<Role> findByNamedQuery(final String nameOfQuery, final String nameOfParameter, final Object valueOfParameter)
	{
		Optional<Role> result = Optional.empty();

		try
		{
			final TypedQuery<Role> qry = this.em.createNamedQuery(nameOfQuery, Role.class);
			qry.setParameter(nameOfParameter, valueOfParameter);
			final Role Role = qry.getSingleResult();
			result = Optional.of(Role);
		}
		// kein Ergebnis
		catch (final NoResultException e)
		{
			// nichts zu tun: dann wird "leeres" Optional geliefertS
		}
		// 2-n Ergebnisse --> hier nicht möglich: Id bzw. Name sind unique
		// catch (final NonUniqueResultException e)
		// {
		//
		// }

		return result;

	}
}

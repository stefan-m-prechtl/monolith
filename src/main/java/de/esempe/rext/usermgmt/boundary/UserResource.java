package de.esempe.rext.usermgmt.boundary;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
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
@Path(Constants.path)
@Interceptors({ ExceptionHandlingInterceptor.class })
public class UserResource
{
	@Context
	UriInfo uriInfo;

	@Inject
	UserRepository repository;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers()
	{
		final List<User> users = this.repository.loadAll();
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

		final Optional<User> searchResult = this.repository.findByLoginId(login);

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
		final Optional<User> searchResult = this.repository.findByObjId(objid);

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
		final Optional<User> searchResult = this.repository.findByObjId(objid);

		if (searchResult.isPresent())
		{
			this.repository.delete(objid);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createResource(final User user)
	{
		final UUID objid = user.getObjId();

		// prüfen, ob User bereits vorhanden
		Optional<User> searchResult = this.repository.findByObjId(objid);
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("User mit Objekt-ID bereits vorhanden").build();
		}
		searchResult = this.repository.findByLoginId(user.getLogin());
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("User mit Login bereits vorhanden").build();
		}

		// User ist neu --> persistieren
		this.repository.save(user);
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
		if (false == objid.equals(user.getObjId()))
		{
			final String reason = String.format("Request-Id %s ungleich der Object-Id %s", objid, user.getObjId());
			return Response.status(Response.Status.BAD_REQUEST).entity(reason).build();
		}

		final Optional<User> searchResult = this.repository.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.repository.save(user);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}
}

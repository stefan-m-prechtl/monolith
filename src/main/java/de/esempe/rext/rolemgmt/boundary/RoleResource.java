package de.esempe.rext.rolemgmt.boundary;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
	@Inject
	RoleRepository repository;

	@Context
	UriInfo uriInfo;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoles()
	{
		final List<Role> Roles = this.repository.loadAll();
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

		final Optional<Role> searchResult = this.repository.findByName(name);

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
		final Optional<Role> searchResult = this.repository.findByObjId(objid);

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
		final Optional<Role> searchResult = this.repository.findByObjId(objid);

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
	public Response createResource(final Role role)
	{
		final UUID objid = role.getObjId();

		// prüfen, ob Rolle bereits vorhanden
		Optional<Role> searchResult = this.repository.findByObjId(objid);
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("Rolle mit Objekt-ID bereits vorhanden").build();
		}
		searchResult = this.repository.findByName(role.getName());
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("Geleichnamige Rolle bereits vorhanden").build();
		}

		// Rolle ist neu --> persistieren
		this.repository.save(role);
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

		final Optional<Role> searchResult = this.repository.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.repository.save(role);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}
}

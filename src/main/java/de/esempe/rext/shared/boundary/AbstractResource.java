package de.esempe.rext.shared.boundary;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;

@Stateless
public class AbstractResource<E extends AbstractEntity>
{
	@Context
	protected UriInfo uriInfo;

	// konkrete Klasse wird per Konstruktor gesetzt
	protected IRepository<E> repository;

	protected AbstractResource()
	{
	}

	protected AbstractResource(final IRepository<E> repository)
	{
		this.repository = repository;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResources()
	{
		final List<E> domainObjects = this.repository.loadAll();
		return Response.ok(domainObjects).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResourceById(@PathParam("id") final String resourceId) throws Exception
	{
		final UUID objid = this.convert2UUID(resourceId);
		final Optional<E> searchResult = this.repository.findByObjId(objid);

		if (searchResult.isPresent())
		{
			return Response.ok(searchResult.get()).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteResourceAll(@QueryParam("flag") final String flag)
	{
		if (flag.equals("all"))
		{
			this.repository.deleteAll();
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteResourceById(@PathParam("id") final String resourceId)
	{
		final UUID objid = this.convert2UUID(resourceId);
		final Optional<E> searchResult = this.repository.findByObjId(objid);

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
	public Response createResource(final E entity)
	{
		final UUID objid = entity.getObjId();

		// prüfen, ob Entity bereits vorhanden ist
		Optional<E> searchResult = this.repository.findByObjId(objid);
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("Entität mit Objekt-ID bereits vorhanden").build();
		}
		searchResult = this.repository.findByKey(entity.getKey());
		if (searchResult.isPresent())
		{
			return Response.status(Response.Status.CONFLICT).entity("Resource mit gleichem Key bereits vorhanden").build();
		}

		// Enitity ist neu --> persistieren
		this.repository.save(entity);
		final URI linkURI = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path(objid.toString()).build();
		final Link link = Link.fromUri(linkURI).rel("self").type(MediaType.APPLICATION_JSON).build();
		return Response.noContent().links(link).build();

	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateResource(@PathParam("id") final String resourceId, final E entity)
	{
		final UUID objid = this.convert2UUID(resourceId);

		// REST-Pfad-ID muss mit ID im Objekt übereinstimmen
		if (false == objid.equals(entity.getObjId()))
		{
			final String reason = String.format("Request-Id %s ungleich der Object-Id %s", objid, entity.getObjId());
			return Response.status(Response.Status.BAD_REQUEST).entity(reason).build();
		}

		final Optional<E> searchResult = this.repository.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.repository.save(entity);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}

	@Produces(MediaType.APPLICATION_JSON)
	protected Response getResourceByKey(final Key key)
	{
		if (null == key)
		{
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		final Optional<E> searchResult = this.repository.findByKey(key);

		if (searchResult.isPresent())
		{
			return Response.ok(searchResult.get()).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	protected UUID convert2UUID(final String resourceId)
	{
		return ResourceHelper.convert2UUID(resourceId);
	}

}

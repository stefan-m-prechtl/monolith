package de.esempe.rext.itemmgmt.boundary;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.esempe.rext.itemmgmt.domain.Item;
import de.esempe.rext.shared.boundary.ResourceHelper;

@Stateless(description = "REST-Interface für Items eines Projektes")
@Path(Constants.pathProjectItems)
public class ProjectItemResource
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	ItemRepository repository;

	@Inject
	public ProjectItemResource(final ItemRepository repository)
	{
		this.repository = repository;
	}

	@GET
	@Path("/{id}/items")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemsByProjectId(@PathParam("id") final String projectId) throws Exception
	{
		final UUID projectObjId = ResourceHelper.convert2UUID(projectId);
		final List<Item> searchResult = this.repository.loadAllForProject(projectObjId);

		return Response.ok(searchResult).build();
	}

}

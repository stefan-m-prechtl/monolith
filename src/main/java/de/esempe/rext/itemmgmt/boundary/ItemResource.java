package de.esempe.rext.itemmgmt.boundary;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.esempe.rext.itemmgmt.domain.Item;
import de.esempe.rext.itemmgmt.domain.Priority;
import de.esempe.rext.shared.boundary.AbstractResource;
import de.esempe.rext.shared.domain.Key;

@Stateless(description = "REST-Interface für Items")
@Path(Constants.pathItems)
public class ItemResource extends AbstractResource<Item>
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	ItemRepository repository;
	@Inject
	PriorityRepository repositoryPriority;

	@Inject
	public ItemResource(final ItemRepository repository)
	{
		super(repository);
		this.repository = repository;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemsByTitle(@QueryParam("title") final String title)
	{
		return super.getResourceByKey(new Key("title", title));
	}

	@Override
	public boolean handleReferencedEntities(Item entity)
	{
		boolean result = false;

		final Priority prio = entity.getPriority();
		final Optional<Priority> repoPriority = this.repositoryPriority.findByObjId(prio.getObjId());
		if (repoPriority.isPresent())
		{
			entity.setPriority(repoPriority.get());
			result = true;
		}

		return result;
	}

}

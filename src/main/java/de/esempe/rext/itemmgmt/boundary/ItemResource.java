package de.esempe.rext.itemmgmt.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import de.esempe.rext.itemmgmt.domain.Item;
import de.esempe.rext.shared.boundary.AbstractResource;

@Stateless(description = "REST-Interface für Items")
@Path(Constants.pathItems)
public class ItemResource extends AbstractResource<Item>
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	ItemRepository repository;

	@Inject
	public ItemResource(final ItemRepository repository)
	{
		super(repository);
		this.repository = repository;
	}

}

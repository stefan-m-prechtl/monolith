package de.esempe.rext.itemmgmt.boundary;

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

import de.esempe.rext.itemmgmt.domain.Priority;
import de.esempe.rext.shared.boundary.AbstractResource;
import de.esempe.rext.shared.domain.Key;

@Stateless(description = "REST-Interface für Prioritäten")
@Path(Constants.pathPriorities)
public class PriorityResource extends AbstractResource<Priority>
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	PriorityRepository repository;

	@Inject
	public PriorityResource(final PriorityRepository repository)
	{
		super(repository);
		this.repository = repository;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPriorityByTitle(@QueryParam("caption") final String caption)
	{
		return super.getResourceByKey(new Key("caption", caption));
	}

}

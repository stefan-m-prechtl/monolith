package de.esempe.rext.workflowmgmt.boundary;

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

import de.esempe.rext.shared.boundary.AbstractResource;
import de.esempe.rext.shared.domain.Key;
import de.esempe.rext.workflowmgmt.domain.State;

@Stateless(description = "REST-Interface für WF-Status")
@Path(Constants.pathStatus)
public class StatusResource extends AbstractResource<State>
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	StatusRepository repository;

	@Inject
	public StatusResource(final StatusRepository repository)
	{
		super(repository);
		this.repository = repository;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStautsByName(@QueryParam("name") final String name)
	{
		return super.getResourceByKey(new Key("name", name));
	}
}

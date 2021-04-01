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
import de.esempe.rext.workflowmgmt.domain.Workflow;

@Stateless(description = "REST-Interface für WF")
@Path(Constants.pathWorkflow)
public class WorkflowResource extends AbstractResource<Workflow>
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	WorkflowRepository repository;

	@Inject
	public WorkflowResource(final WorkflowRepository repository)
	{
		super(repository);
		this.repository = repository;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStateByName(@QueryParam("name") final String name)
	{
		return super.getResourceByKey(new Key("name", name));
	}
}

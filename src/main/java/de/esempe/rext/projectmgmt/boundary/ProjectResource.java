package de.esempe.rext.projectmgmt.boundary;

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

import de.esempe.rext.projectmgmt.domain.Project;
import de.esempe.rext.shared.boundary.AbstractResource;
import de.esempe.rext.shared.domain.Key;

@Stateless(description = "REST-Interface für Projekte")
@Path(Constants.path)
public class ProjectResource extends AbstractResource<Project>
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	ProjectRepository repository;

	@Inject
	public ProjectResource(final ProjectRepository repository)
	{
		super(repository);
		this.repository = repository;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjectsByName(@QueryParam("name") final String name)
	{
		return super.getResourceByKey(new Key("name", name));
	}

}

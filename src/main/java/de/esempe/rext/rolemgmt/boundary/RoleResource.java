package de.esempe.rext.rolemgmt.boundary;

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

import de.esempe.rext.rolemgmt.domain.Role;
import de.esempe.rext.shared.boundary.AbstractResource;
import de.esempe.rext.shared.domain.Key;

@Stateless(description = "REST-Interface für Rollen")
@Path(Constants.path)
public class RoleResource extends AbstractResource<Role>
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	RoleRepository repository;

	@Inject
	public RoleResource(final RoleRepository repository)
	{
		super(repository);
		this.repository = repository;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRolesByName(@QueryParam("name") final String name)
	{
		return super.getResourceByKey(new Key("name", name));
	}

}

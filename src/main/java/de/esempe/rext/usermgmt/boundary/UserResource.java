package de.esempe.rext.usermgmt.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
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
import de.esempe.rext.usermgmt.boundary.exceptionhandling.ExceptionHandlingInterceptor;
import de.esempe.rext.usermgmt.domain.User;

@Stateless(description = "REST-Interface für Benutzer")
@Path(Constants.path)
@Interceptors({ ExceptionHandlingInterceptor.class })
public class UserResource extends AbstractResource<User>
{
	@Context
	UriInfo uriInfo;

	// @Inject --> im Konstruktor
	UserRepository repository;

	@Inject
	public UserResource(final UserRepository repository)
	{
		super(repository);
		this.repository = repository;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsersByLogin(@QueryParam("login") final String login)
	{
		return super.getResourceByKey(new Key("login", login));
	}

}

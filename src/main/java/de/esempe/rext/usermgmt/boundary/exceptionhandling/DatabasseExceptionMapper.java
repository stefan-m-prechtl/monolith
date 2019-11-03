package de.esempe.rext.usermgmt.boundary.exceptionhandling;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DatabasseExceptionMapper implements ExceptionMapper<PersistenceException>
{
	@Override
	public Response toResponse(PersistenceException e)
	{
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Datenbankfehler").build();
	}
}

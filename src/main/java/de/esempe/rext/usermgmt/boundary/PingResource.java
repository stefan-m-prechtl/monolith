package de.esempe.rext.usermgmt.boundary;

import java.time.LocalDateTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Stateless(description = "REST-Interface für Ping")
@Path("/usermgmt/ping")
public class PingResource
{
	@Inject
	@ConfigProperty(name = "PINGMSG", defaultValue = "Got Ping at:")
	String pingMsg;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ping()
	{
		final LocalDateTime now = LocalDateTime.now();
		final String result = this.pingMsg + now.toString();

		return Response.ok(result).build();
	}
}

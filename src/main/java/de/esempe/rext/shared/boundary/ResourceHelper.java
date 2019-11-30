package de.esempe.rext.shared.boundary;

import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public final class ResourceHelper
{
	private ResourceHelper()
	{
	}

	public static UUID convert2UUID(final String resourceId)
	{
		try
		{
			return UUID.fromString(resourceId);
		}
		catch (final IllegalArgumentException e)
		{
			throw new WebApplicationException("Ungültiger Wert für UUID", Response.Status.BAD_REQUEST);
		}
	}
}

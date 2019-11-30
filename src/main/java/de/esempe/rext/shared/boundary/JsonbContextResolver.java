package de.esempe.rext.shared.boundary;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import de.esempe.rext.itemmgmt.boundary.jsonhandling.ItemJsonAdapter;
import de.esempe.rext.projectmgmt.boundary.jsonhandling.ProjectJsonAdapter;
import de.esempe.rext.rolemgmt.boundary.jsonhandling.RoleJsonAdapter;
import de.esempe.rext.usermgmt.boundary.jsonhandling.UserJsonAdapter;

@Provider
public class JsonbContextResolver implements ContextResolver<Jsonb>
{
	@Override
	public Jsonb getContext(final Class<?> type)
	{
		// @formatter:off
		final JsonbConfig config = new JsonbConfig()
				.withAdapters(new ItemJsonAdapter())
				.withAdapters(new ProjectJsonAdapter())
				.withAdapters(new RoleJsonAdapter())
				.withAdapters(new UserJsonAdapter());
	   // @formatter:on

		final Jsonb jsonb = JsonbBuilder.create(config);

		return jsonb;
	}

}

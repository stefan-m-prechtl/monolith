package de.esempe.rext.shared.boundary;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import de.esempe.rext.itemmgmt.boundary.jsonhandling.ItemJsonAdapter;
import de.esempe.rext.itemmgmt.boundary.jsonhandling.PriorityJsonAdapter;
import de.esempe.rext.itemmgmt.boundary.jsonhandling.TaggedValueJsonAdapter;
import de.esempe.rext.projectmgmt.boundary.jsonhandling.ProjectJsonAdapter;
import de.esempe.rext.rolemgmt.boundary.jsonhandling.RoleJsonAdapter;
import de.esempe.rext.usermgmt.boundary.jsonhandling.UserJsonAdapter;
import de.esempe.rext.workflowmgmt.boundary.jsonhandling.StateJsonAdapter;
import de.esempe.rext.workflowmgmt.boundary.jsonhandling.WorkflowJsonAdapter;

@Provider
public class JsonbContextResolver implements ContextResolver<Jsonb>
{
	@Override
	public Jsonb getContext(final Class<?> type)
	{
		// @formatter:off
		final var config = new JsonbConfig()
				.withAdapters(new ItemJsonAdapter())
				.withAdapters(new PriorityJsonAdapter())
				.withAdapters(new TaggedValueJsonAdapter())
				.withAdapters(new ProjectJsonAdapter())
				.withAdapters(new RoleJsonAdapter())
				.withAdapters(new UserJsonAdapter())
				.withAdapters(new StateJsonAdapter())
				.withAdapters(new WorkflowJsonAdapter());
	   // @formatter:on

		final var jsonb = JsonbBuilder.create(config);

		return jsonb;
	}

}

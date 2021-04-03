package de.esempe.rext.workflowmgmt.boundary.jsonhandling;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.rext.workflowmgmt.domain.Workflow;

public class WorkflowJsonAdapter implements JsonbAdapter<Workflow, JsonObject>
{
	public final static String field_id = "workflowid";
	public final static String field_name = "name";
	public final static String field_description = "description";
	public final static String field_firststate_id = "firststateid";

	@Override
	public JsonObject adaptToJson(Workflow workflow) throws Exception
	{
		//@formatter:off
		final var result = Json.createObjectBuilder()
						.add(field_id, workflow.getObjId().toString())
						.add(field_name, workflow.getName())
						.add(field_description, workflow.getDescription())
						.add(field_firststate_id, workflow.getFirstStateObjid().toString() )
						.build();
		//@formatter:on
		return result;
	}

	@Override
	public Workflow adaptFromJson(JsonObject jsonObj) throws Exception
	{
		Workflow result;
		final var name = jsonObj.getString(field_name);
		final var description = jsonObj.getString(field_description);
		final var firstStateId = jsonObj.getString(field_firststate_id);

		if (jsonObj.containsKey(field_id))
		{
			final var objid = UUID.fromString(jsonObj.getString(field_id));
			result = new Workflow(name, objid);
		}
		else
		{
			result = new Workflow(name);
		}
		result.setDescription(description);
		result.setFirstStateObjid(null);

		return result;

	}

}

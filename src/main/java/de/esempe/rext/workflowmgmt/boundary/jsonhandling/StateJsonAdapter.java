package de.esempe.rext.workflowmgmt.boundary.jsonhandling;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.rext.workflowmgmt.domain.State;

public class StateJsonAdapter implements JsonbAdapter<State, JsonObject>
{
	public final static String field_id = "stateid";
	public final static String field_name = "name";
	public final static String field_description = "description";

	@Override
	public JsonObject adaptToJson(State state) throws Exception
	{
		//@formatter:off
		final var result = Json.createObjectBuilder()
						.add(field_id, state.getObjId().toString())
						.add(field_name, state.getName())
						.add(field_description, state.getDescription())
						.build();
		//@formatter:on
		return result;
	}

	@Override
	public State adaptFromJson(JsonObject jsonObj) throws Exception
	{
		State result;
		final var name = jsonObj.getString(field_name);
		final var description = jsonObj.getString(field_description);

		if (jsonObj.containsKey(field_id))
		{
			final var objid = UUID.fromString(jsonObj.getString(field_id));
			result = new State(name, objid);
		}
		else
		{
			result = new State(name);
		}
		result.setDescription(description);

		return result;
	}

}

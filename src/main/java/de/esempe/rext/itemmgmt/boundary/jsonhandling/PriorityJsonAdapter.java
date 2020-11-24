package de.esempe.rext.itemmgmt.boundary.jsonhandling;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.rext.itemmgmt.domain.Priority;

/**
 * Adapter muss in JsonbContextResolver registriert werden!
 *
 * @author stefan.m.prechtl
 *
 */
public class PriorityJsonAdapter implements JsonbAdapter<Priority, JsonObject>
{
	public final static String field_id = "priorityid";
	public final static String field_name = "name";
	public final static String field_description = "description";
	public final static String field_value = "value";

	@Override
	public JsonObject adaptToJson(final Priority entity) throws Exception
	{
		//@formatter:off
		final JsonObject result = Json.createObjectBuilder()
				.add(field_id, entity.getObjId().toString())
				.add(field_name, entity.getName())
				.add(field_description, entity.getDescription())
				.add(field_value, entity.getValue())
				.build();
		//@formatter:on
		return result;
	}

	@Override
	public Priority adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		Priority result;
		final String name = jsonObj.getString(field_name);
		final String description = jsonObj.getString(field_description);
		final int value = jsonObj.getInt(field_value);

		if (jsonObj.containsKey(field_id))
		{
			final UUID objid = UUID.fromString(jsonObj.getString(field_id));
			result = new Priority(value, name, description, objid);
		} else
		{
			result = new Priority(value, name, description);
		}

		return result;
	}

}

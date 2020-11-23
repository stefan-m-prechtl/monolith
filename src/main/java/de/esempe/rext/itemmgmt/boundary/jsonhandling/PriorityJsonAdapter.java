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
	public final static String field_id = "valueid";
	public final static String field_caption = "caption";
	public final static String field_value = "value";

	@Override
	public JsonObject adaptToJson(final Priority entity) throws Exception
	{
		//@formatter:off
		final JsonObject result = Json.createObjectBuilder()
				.add(field_id, entity.getObjId().toString())
				.add(field_caption, entity.getCaption())
				.add(field_value, entity.getValue())
				.build();
		//@formatter:on
		return result;
	}

	@Override
	public Priority adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		Priority result;
		final String caption = jsonObj.getString(field_caption);
		final int value = jsonObj.getInt(field_value);

		if (jsonObj.containsKey(field_id))
		{
			final UUID objid = UUID.fromString(jsonObj.getString(field_id));
			result = new Priority(value, caption, objid);
		} else
		{
			result = new Priority(value, caption);
		}

		return result;
	}

}

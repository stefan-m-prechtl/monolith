package de.esempe.rext.itemmgmt.boundary.jsonhandling;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.rext.itemmgmt.domain.TaggedValue;

/**
 * Adapter muss in JsonbContextResolver registriert werden!
 * 
 * @author stefan.m.prechtl
 *
 */
public class TaggedValueJsonAdapter implements JsonbAdapter<TaggedValue, JsonObject>
{
	public final static String field_id = "valueid";
	public final static String field_value = "value";

	@Override
	public JsonObject adaptToJson(final TaggedValue value) throws Exception
	{
		//@formatter:off
		final JsonObject result = Json.createObjectBuilder()
				.add(field_id, value.getObjId().toString())
				.add(field_value, value.getValue())
				.build();
		//@formatter:on
		return result;
	}

	@Override
	public TaggedValue adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		TaggedValue result;
		final String value = jsonObj.getString(field_value);

		if (jsonObj.containsKey(field_id))
		{
			final UUID objid = UUID.fromString(jsonObj.getString(field_id));
			result = new TaggedValue(value, objid);
		} else
		{
			result = new TaggedValue(value);
		}

		return result;
	}

}

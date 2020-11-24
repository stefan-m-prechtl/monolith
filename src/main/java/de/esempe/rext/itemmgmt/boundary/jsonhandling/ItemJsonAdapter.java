package de.esempe.rext.itemmgmt.boundary.jsonhandling;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.rext.itemmgmt.domain.Item;
import de.esempe.rext.itemmgmt.domain.Priority;

/**
 * Adapter muss in JsonbContextResolver registriert werden!
 *
 * @author stefan.m.prechtl
 *
 */
public class ItemJsonAdapter implements JsonbAdapter<Item, JsonObject>
{
	public final static String field_id = "itemid";
	public final static String field_title = "title";
	public final static String field_content = "content";
	public final static String field_project = "projektobjid";
	public final static String field_creator = "creatorobjid";
	public final static String field_priority = "priority";

	@Override
	public JsonObject adaptToJson(final Item item) throws Exception
	{
		//@formatter:off
		final JsonObject result = Json.createObjectBuilder()
				.add(field_id, item.getObjId().toString())
				.add(field_title, item.getTitle())
				.add(field_content, item.getContent())
				.add(field_project, item.getProject().toString())
				.add(field_creator, item.getCreator().toString())
				.add(field_priority, new PriorityJsonAdapter().adaptToJson(item.getPriority()))
				.build();
		//@formatter:on
		return result;
	}

	@Override
	public Item adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		Item result;
		final String title = jsonObj.getString(field_title);
		final String content = jsonObj.getString(field_content);
		final UUID project = UUID.fromString(jsonObj.getString(field_project));
		final UUID creator = UUID.fromString(jsonObj.getString(field_creator));
		final JsonObject jsonPriority = jsonObj.getJsonObject(field_priority);
		final Priority objPriority = new PriorityJsonAdapter().adaptFromJson(jsonPriority);

		if (jsonObj.containsKey(field_id))
		{
			final UUID objid = UUID.fromString(jsonObj.getString(field_id));
			result = new Item(project, creator, title, objid);
		} else
		{
			result = new Item(project, creator, title);
		}
		result.setContent(content);
		result.setCreator(creator);
		result.setPriority(objPriority);

		return result;
	}

}

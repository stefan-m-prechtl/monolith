package de.esempe.rext.rolemgmt.boundary.jsonhandling;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.rext.rolemgmt.domain.Role;

public class RoleJsonAdapter implements JsonbAdapter<Role, JsonObject>
{
	public final static String field_id = "roleid";
	public final static String field_name = "rolename";
	public final static String field_description = "description";

	@Override
	public JsonObject adaptToJson(final Role role) throws Exception
	{
		//@formatter:off
		final JsonObject result = Json.createObjectBuilder()
				.add(field_id, role.getObjId().toString())
				.add(field_name, role.getName())
				.add(field_description, role.getDescription())
				.build();
		//@formatter:on
		return result;
	}

	@Override
	public Role adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		Role result;
		final String name = jsonObj.getString(field_name);
		final String description = jsonObj.getString(field_description);

		if (jsonObj.containsKey(field_id))
		{
			final UUID objid = UUID.fromString(jsonObj.getString(field_id));
			result = new Role(name, objid);
		}
		else
		{
			result = new Role(name);
		}
		result.setDescription(description);

		return result;
	}

}

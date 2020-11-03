package de.esempe.rext.projectmgmt.boundary.jsonhandling;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.rext.projectmgmt.domain.Project;

public class ProjectJsonAdapter implements JsonbAdapter<Project, JsonObject>
{
	public final static String field_id = "projectid";
	public final static String field_name = "projectname";
	public final static String field_description = "description";
	public final static String field_owner = "owner";

	@Override
	public JsonObject adaptToJson(final Project project) throws Exception
	{
		//@formatter:off
		final JsonObject result = Json.createObjectBuilder()
				.add(field_id, project.getObjId().toString())
				.add(field_name, project.getName())
				.add(field_description, project.getDescription())
				.add(field_owner, project.getOwnerUserObjid().toString())
				.build();
		//@formatter:on
		return result;
	}

	@Override
	public Project adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		Project result;
		final String name = jsonObj.getString(field_name);
		final String description = jsonObj.getString(field_description);
		final UUID ownerid = UUID.fromString(jsonObj.getString(field_owner));

		if (jsonObj.containsKey(field_id))
		{
			final UUID objid = UUID.fromString(jsonObj.getString(field_id));
			result = new Project(name, objid);
		} else
		{
			result = new Project(name);
		}
		result.setDescription(description);
		result.setOwnerUserObjid(ownerid);

		return result;
	}

}

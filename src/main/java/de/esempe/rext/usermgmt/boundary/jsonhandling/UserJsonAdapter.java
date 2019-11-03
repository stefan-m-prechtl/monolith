package de.esempe.rext.usermgmt.boundary.jsonhandling;

import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import de.esempe.rext.usermgmt.domain.User;

public class UserJsonAdapter implements JsonbAdapter<User, JsonObject>
{
	public final static String field_id = "userid";
	public final static String field_login = "userlogin";
	public final static String field_firstname = "firstname";
	public final static String field_lastname = "lastname";

	@Override
	public JsonObject adaptToJson(final User user) throws Exception
	{
		//@formatter:off
		final JsonObject result = Json.createObjectBuilder()
				.add(field_id, user.getObjId().toString())
				.add(field_login, user.getLogin())
				.add(field_firstname, user.getFirstname())
				.add(field_lastname, user.getLastname())
				.build();
		//@formatter:on
		return result;
	}

	@Override
	public User adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		User result;
		final String userlogin = jsonObj.getString(field_login);
		final String firstname = jsonObj.getString(field_firstname);
		final String lastname = jsonObj.getString(field_lastname);

		if (jsonObj.containsKey(field_id))
		{
			final UUID objid = UUID.fromString(jsonObj.getString(field_id));
			result = new User(userlogin, objid);
		}
		else
		{
			result = new User(userlogin);
		}
		result.setFirstname(firstname);
		result.setLastname(lastname);

		return result;
	}

}

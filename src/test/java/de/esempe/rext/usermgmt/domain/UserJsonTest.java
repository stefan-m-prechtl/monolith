package de.esempe.rext.usermgmt.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.JsonbContextResolver;
import de.esempe.rext.usermgmt.boundary.jsonhandling.UserJsonAdapter;

@Tag("unit-test")
@DisplayName("Tests für Json-Konvertierung User")
@TestMethodOrder(OrderAnnotation.class)
public class UserJsonTest
{
	JsonbConfig config;
	Jsonb jsonb;

	User generateUser()
	{
		final User userObj = new User("prs");
		userObj.setFirstname("Stefan");
		userObj.setLastname("Prechtl");

		return userObj;
	}

	@BeforeEach
	void initJsonbConfig()
	{
		final JsonbContextResolver jcr = new JsonbContextResolver();
		this.jsonb = jcr.getContext(Json.class);
	}

	@Test
	@Order(1)
	void convertToJson()
	{
		// prepare
		final User userObj = this.generateUser();

		// act
		final String userJson = this.jsonb.toJson(userObj);
		System.out.println(userJson);

		// assert
		//@formatter:off
		assertAll("User As Json",
		  () -> assertThat(userJson).isNotNull(),
		  () -> assertThat(userJson).isNotEmpty(),
		  () -> assertThat(userJson).contains(UserJsonAdapter.field_firstname),
		  () -> assertThat(userJson).contains(UserJsonAdapter.field_lastname),
		  () -> assertThat(userJson).contains(UserJsonAdapter.field_login),
		  () -> assertThat(userJson).contains(UserJsonAdapter.field_id)
		 );
		//@formatter:on

	}

	@Test
	@Order(2)
	void convertFromJson()
	{
		// prepare
		final User userObj = this.generateUser();
		final String userJson = this.jsonb.toJson(userObj);

		// act
		final User userObjFromJson = this.jsonb.fromJson(userJson, User.class);

		// assert
		//@formatter:off
		assertAll("User from Json",
		  () ->	assertThat(userObjFromJson).isNotNull(),
		  () ->	assertThat(userObjFromJson).isEqualTo(userObj),
		  () ->	assertThat(userObjFromJson.getFirstname()).isEqualTo(userObj.getFirstname()),
		  () ->	assertThat(userObjFromJson.getLastname()).isEqualTo(userObj.getLastname())
		);
		//@formatter:off

	}
}

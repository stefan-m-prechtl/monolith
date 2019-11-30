package de.esempe.rext.rolemgmt.domain;

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

import de.esempe.rext.rolemgmt.boundary.jsonhandling.RoleJsonAdapter;
import de.esempe.rext.shared.boundary.JsonbContextResolver;

@Tag("unit-test")
@DisplayName("Tests für Json-Konvertierung Role")
@TestMethodOrder(OrderAnnotation.class)
public class RoleJsonTest
{
	JsonbConfig config;
	Jsonb jsonb;

	Role generateEntity()
	{
		final Role entity = new Role("Leser");
		entity.setDescription("Rolle für Lesezugriff");

		return entity;
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
		final Role entity = this.generateEntity();

		// act
		final String entityAsJsonString = this.jsonb.toJson(entity);
		System.out.println(entityAsJsonString);

		// assert
		//@formatter:off
		assertAll("Role As Json",
		  () -> assertThat(entityAsJsonString).isNotNull(),
		  () -> assertThat(entityAsJsonString).isNotEmpty(),
		  () -> assertThat(entityAsJsonString).contains(RoleJsonAdapter.field_name),
		  () -> assertThat(entityAsJsonString).contains(RoleJsonAdapter.field_description),
		  () -> assertThat(entityAsJsonString).contains(RoleJsonAdapter.field_id)
		 );
		//@formatter:on

	}

	@Test
	@Order(2)
	void convertFromJson()
	{
		// prepare
		final Role entity = this.generateEntity();
		final String entityAsJsonString = this.jsonb.toJson(entity);

		// act
		final Role entityObjFromJsonString = this.jsonb.fromJson(entityAsJsonString, Role.class);

		// assert
		//@formatter:off
		assertAll("User from Json",
		  () ->	assertThat(entityObjFromJsonString).isNotNull(),
		  () ->	assertThat(entityObjFromJsonString).isEqualTo(entity),
		  () ->	assertThat(entityObjFromJsonString.getName()).isEqualTo(entity.getName()),
		  () ->	assertThat(entityObjFromJsonString.getDescription()).isEqualTo(entity.getDescription())
		);
		//@formatter:off

	}
}

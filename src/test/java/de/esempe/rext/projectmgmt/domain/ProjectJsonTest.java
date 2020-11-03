package de.esempe.rext.projectmgmt.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.UUID;

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

import de.esempe.rext.projectmgmt.boundary.jsonhandling.ProjectJsonAdapter;
import de.esempe.rext.shared.boundary.JsonbContextResolver;

@Tag("unit-test")
@DisplayName("Tests für Json-Konvertierung Project")
@TestMethodOrder(OrderAnnotation.class)
public class ProjectJsonTest
{
	JsonbConfig config;
	Jsonb jsonb;

	Project generateEntity()
	{
		final Project entity = new Project("Demo");
		entity.setDescription("Demoprojekt");
		entity.setOwnerUserObjid(UUID.randomUUID());

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
		final Project entity = this.generateEntity();

		// act
		final String entityAsJsonString = this.jsonb.toJson(entity);
		System.out.println(entityAsJsonString);

		// assert
		//@formatter:off
		assertAll("Project As Json",
		  () -> assertThat(entityAsJsonString).isNotNull(),
		  () -> assertThat(entityAsJsonString).isNotEmpty(),
		  () -> assertThat(entityAsJsonString).contains(ProjectJsonAdapter.field_id),
		  () -> assertThat(entityAsJsonString).contains(ProjectJsonAdapter.field_name),
		  () -> assertThat(entityAsJsonString).contains(ProjectJsonAdapter.field_description),
		  () -> assertThat(entityAsJsonString).contains(ProjectJsonAdapter.field_owner)
		 );
		//@formatter:on

	}

	@Test
	@Order(2)
	void convertFromJson()
	{
		// prepare
		final Project entity = this.generateEntity();
		final String entityAsJsonString = this.jsonb.toJson(entity);

		// act
		final Project entityObjFromJsonString = this.jsonb.fromJson(entityAsJsonString, Project.class);

		// assert
		//@formatter:off
		assertAll("User from Json",
		  () ->	assertThat(entityObjFromJsonString).isNotNull(),
		  () ->	assertThat(entityObjFromJsonString).isEqualTo(entity),
		  () ->	assertThat(entityObjFromJsonString.getName()).isEqualTo(entity.getName()),
		  () ->	assertThat(entityObjFromJsonString.getDescription()).isEqualTo(entity.getDescription()),
		  () ->	assertThat(entityObjFromJsonString.getOwnerUserObjid()).isEqualTo(entity.getOwnerUserObjid())
		);
		//@formatter:off

	}
}

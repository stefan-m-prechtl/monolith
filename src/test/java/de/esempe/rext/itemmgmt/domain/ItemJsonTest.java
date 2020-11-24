package de.esempe.rext.itemmgmt.domain;

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

import de.esempe.rext.itemmgmt.boundary.jsonhandling.ItemJsonAdapter;
import de.esempe.rext.shared.boundary.JsonbContextResolver;

@Tag("unit-test")
@DisplayName("Tests für Json-Konvertierung Item")
@TestMethodOrder(OrderAnnotation.class)
public class ItemJsonTest
{
	JsonbConfig config;
	Jsonb jsonb;

	Item generateItem()
	{
		final UUID projectObjId = UUID.randomUUID();
		final UUID userObjId = UUID.randomUUID();
		final Item itemObj = new Item(projectObjId, userObjId, "Testitem");
		final Priority prioObj = new Priority(50, "Normal", "Standard-Prio");
		itemObj.setContent("Inhalt für Testitem");
		itemObj.setPriority(prioObj);

		return itemObj;
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
		final Item itemObj = this.generateItem();

		// act
		final String userJson = this.jsonb.toJson(itemObj);
		System.out.println(userJson);

		// assert
		//@formatter:off
		assertAll("Item As Json",
		  () -> assertThat(userJson).isNotNull(),
		  () -> assertThat(userJson).isNotEmpty(),
		  () -> assertThat(userJson).contains(ItemJsonAdapter.field_title),
		  () -> assertThat(userJson).contains(ItemJsonAdapter.field_content),
		  () -> assertThat(userJson).contains(ItemJsonAdapter.field_project),
		  () -> assertThat(userJson).contains(ItemJsonAdapter.field_creator),
		  () -> assertThat(userJson).contains(ItemJsonAdapter.field_priority)
		 );
		//@formatter:on

	}

	@Test
	@Order(2)
	void convertFromJson()
	{
		// prepare
		final Item itemObj = this.generateItem();
		final String itemJson = this.jsonb.toJson(itemObj);

		// act
		final Item itemObjFromJson = this.jsonb.fromJson(itemJson, Item.class);

		// assert
		//@formatter:off
		assertAll("User from Json",
		  () ->	assertThat(itemObjFromJson).isNotNull(),
		  () ->	assertThat(itemObjFromJson).isEqualTo(itemObj),
		  () ->	assertThat(itemObjFromJson.getTitle()).isEqualTo(itemObj.getTitle()),
		  () ->	assertThat(itemObjFromJson.getProject()).isEqualTo(itemObj.getProject()),
		  () ->	assertThat(itemObjFromJson.getContent()).isEqualTo(itemObj.getContent()),
		  () ->	assertThat(itemObjFromJson.getCreator()).isEqualTo(itemObj.getCreator()),
		  () ->	assertThat(itemObjFromJson.getPriority()).isEqualTo(itemObj.getPriority())
		);
		//@formatter:off

	}
}

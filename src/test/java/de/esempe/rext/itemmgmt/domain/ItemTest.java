package de.esempe.rext.itemmgmt.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.AbstractEntityTest;

@Tag("integration-test")
@DisplayName("Integrationstests Item-Entity/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)

public class ItemTest extends AbstractEntityTest<Item>
{
	private final static String jpaContext = "testitemdb";

	@BeforeAll
	void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM itemdb.t_item");

		super.setUp(jpaContext, initialQueries, Item.class);

	}

	@Override
	protected Item createObjUnderTest()
	{
		final UUID projectObjId = UUID.randomUUID();
		final UUID creatorObjId = UUID.randomUUID();
		final Item objUnderTest = new Item(projectObjId, creatorObjId, "Testitem");

		objUnderTest.setContent("Inhalt vom Testitem");
		final UUID userObjid = UUID.randomUUID();
		objUnderTest.setCreator(userObjid);

		return objUnderTest;
	}

	@Override
	protected Item updateObjUnderTest(final Item entity)
	{
		final Item updatedObject = entity;
		entity.setContent(entity.getContent().toUpperCase());

		return updatedObject;
	}

	@Test
	public void getContentFromNewInstance()
	{
		// prepare
		final UUID projectObjId = UUID.randomUUID();
		final UUID creatorObjId = UUID.randomUUID();
		final Item objUnderTest = new Item(projectObjId, creatorObjId, "Testitem");
		// act
		final String content = objUnderTest.getContent();
		// assert
		assertThat(content).isNotNull();
		assertThat(content).isEmpty();

	}

	@Test()
	public void setContentEmpty()
	{
		// prepare
		final Item objUnderTest = this.createObjUnderTest();
		// act
		final Throwable thrown = catchThrowable(() -> {
			objUnderTest.setContent(null);
		});

		// then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Leeres Argument");
	}

}

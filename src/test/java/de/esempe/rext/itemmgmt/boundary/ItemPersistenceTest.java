package de.esempe.rext.itemmgmt.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.itemmgmt.domain.Item;
import de.esempe.rext.shared.boundary.AbstractPersistenceTest;

@Tag("integration-test")
@DisplayName("Integrationstests ItemRepository/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class ItemPersistenceTest extends AbstractPersistenceTest<Item>
{
	private final static String jpaContext = "testitemdb";

	@BeforeAll
	static void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM itemdb.t_item");
		initialQueries.add(
				"INSERT INTO itemdb.t_item (objid,project_objid,title,content, creator_user_objid) VALUES (UUID_TO_BIN(UUID()),UUID_TO_BIN(UUID()),'Testitem','Beschreibung Testitem',UUID_TO_BIN(UUID()))");

		AbstractPersistenceTest.setUp(jpaContext, initialQueries);

	}

	@Override
	protected ItemRepository createObjUnderTest()
	{
		final ItemRepository repository = new ItemRepository();
		repository.em = em;
		repository.init();

		return repository;
	}

	@Override
	protected Item createTestEntity()
	{
		final UUID projectObjId = UUID.randomUUID();
		final UUID userObjId = UUID.randomUUID();
		final Item entity = new Item(projectObjId, userObjId, "Testitem");
		entity.setContent("Inhalt für Testitem");

		return entity;

	}

	@Override
	protected Item updateTestEntity(final Item entity)
	{
		final Item updatedObject = entity;
		updatedObject.setContent(entity.getContent().toUpperCase());
		return updatedObject;
	}

}

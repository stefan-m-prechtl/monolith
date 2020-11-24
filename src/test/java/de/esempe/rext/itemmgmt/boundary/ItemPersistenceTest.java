package de.esempe.rext.itemmgmt.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.itemmgmt.domain.Item;
import de.esempe.rext.itemmgmt.domain.Priority;
import de.esempe.rext.shared.boundary.AbstractPersistenceTest;

@Tag("integration-test")
@DisplayName("Integrationstests Item-Repository/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class ItemPersistenceTest extends AbstractPersistenceTest<Item>
{
	private final static String jpaContext = "testitemdb";

	@BeforeAll
	static void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM itemdb.t_item");
		initialQueries.add("DELETE FROM itemdb.t_priority");

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
		final Priority prio = this.savePriority(new Priority(10, "Mittel", "Normale Prio"));

		final Item entity = new Item(projectObjId, userObjId, "Testitem");
		entity.setContent("Inhalt für Testitem");
		entity.setPriority(prio);

		return entity;

	}

	private Priority savePriority(Priority prio)
	{
		final PriorityRepository repository = new PriorityRepository();
		repository.em = this.em;
		repository.init();
		final EntityTransaction tx = repository.em.getTransaction();
		tx.begin();
		repository.save(prio);
		tx.commit();

		return prio;
	}

	@Override
	protected Item updateTestEntity(final Item entity)
	{
		final Item updatedObject = entity;
		updatedObject.setContent(entity.getContent().toUpperCase());
		return updatedObject;
	}

}

package de.esempe.rext.itemmgmt.boundary;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.itemmgmt.domain.Priority;
import de.esempe.rext.shared.boundary.AbstractPersistenceTest;

@Tag("integration-test")
@DisplayName("Integrationstests Priority-Repository/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class PriorityPersistenceTest extends AbstractPersistenceTest<Priority>
{
	private final static String jpaContext = "testitemdb";

	@BeforeAll
	static void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM itemdb.t_priority");

		AbstractPersistenceTest.setUp(jpaContext, initialQueries);

	}

	@Override
	protected PriorityRepository createObjUnderTest()
	{
		final PriorityRepository repository = new PriorityRepository();
		repository.em = em;
		repository.init();

		return repository;
	}

	@Override
	protected Priority createTestEntity()
	{
		final Priority entity = new Priority(10, "Mittel");
		return entity;
	}

	@Override
	protected Priority updateTestEntity(final Priority entity)
	{
		final Priority updatedObject = entity;
		updatedObject.setValue(entity.getValue() + 10);
		return updatedObject;
	}

}

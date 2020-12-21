package de.esempe.rext.workflowmgmt.boundary;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.AbstractPersistenceTest;
import de.esempe.rext.workflowmgmt.domain.State;

@Tag("integration-test")
@DisplayName("Integrationstests WorkflowRepository/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class StatusPersistenceTest extends AbstractPersistenceTest<State>
{
	private final static String jpaContext = "testworkflowdb";

	@BeforeAll
	static void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM workflowdb.t_state");
		initialQueries
				.add("INSERT INTO workflowdb.t_state (objid,name,description) VALUES (UUID_TO_BIN(UUID()),'in Bearbeitung', 'Beschreibung für inBearbeitung')");

		AbstractPersistenceTest.setUp(jpaContext, initialQueries);

	}

	@Override
	protected StateRepository createObjUnderTest()
	{
		final StateRepository repository = new StateRepository();
		repository.em = em;
		repository.init();

		return repository;
	}

	@Override
	protected State createTestEntity()
	{

		final State entity = new State("Erledigt");
		entity.setDescription("Beschreibung für 'Erledigt'");
		return entity;

	}

	@Override
	protected State updateTestEntity(final State entity)
	{
		this.entity.setDescription(entity.getDescription().toUpperCase());
		return entity;
	}

}

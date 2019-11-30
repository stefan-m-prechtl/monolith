package de.esempe.rext.workflowmgmt.boundary;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.AbstractPersistenceTest;
import de.esempe.rext.workflowmgmt.domain.Workflow;

@Tag("integration-test")
@DisplayName("Integrationstests WorkflowRepository/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class WorkflowPersistenceTest extends AbstractPersistenceTest<Workflow>
{
	private final static String jpaContext = "testworkflowdb";

	@BeforeAll
	static void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM workflowdb.t_workflow");
		initialQueries.add(
				"INSERT INTO workflowdb.t_workflow (objid,name,description) VALUES (UUID_TO_BIN(UUID()),'Workflow Aufgabe', 'Beschreibung für Workflow Aufgabe')");

		AbstractPersistenceTest.setUp(jpaContext, initialQueries);

	}

	@Override
	protected WorkflowRepository createObjUnderTest()
	{
		final WorkflowRepository repository = new WorkflowRepository();
		repository.em = em;
		repository.init();

		return repository;
	}

	@Override
	protected Workflow createTestEntity()
	{

		final Workflow entity = new Workflow("Workflow II Aufgabe");
		entity.setDescription("Alternativer Workflow Aufgabe");
		return entity;

	}

	@Override
	protected Workflow updateTestEntity(final Workflow entity)
	{
		this.entity.setDescription(entity.getDescription().toUpperCase());
		return entity;
	}

}

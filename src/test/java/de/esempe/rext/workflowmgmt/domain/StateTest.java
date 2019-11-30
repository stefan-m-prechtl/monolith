package de.esempe.rext.workflowmgmt.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.AbstractEntityTest;

@Tag("integration-test")
@DisplayName("Integrationstests Workflow-Entity/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class StateTest extends AbstractEntityTest<State>
{
	private final static String jpaContext = "testworkflowdb";

	@BeforeAll
	void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM workflowdb.t_transition");
		initialQueries.add("DELETE FROM workflowdb.t_state");
		initialQueries.add("DELETE FROM workflowdb.t_workflow");

		super.setUp(jpaContext, initialQueries, State.class);
	}

	@Override
	protected State createObjUnderTest()
	{

		final State objUnderTest = new State("Neu");
		objUnderTest.setDescription("Beschreibung für Status 'Neu'");
		return objUnderTest;
	}

	@Override
	protected State updateObjUnderTest(final State entity)
	{
		final State updatedObject = entity;
		updatedObject.setDescription(entity.getDescription().toUpperCase());
		return updatedObject;
	}

}

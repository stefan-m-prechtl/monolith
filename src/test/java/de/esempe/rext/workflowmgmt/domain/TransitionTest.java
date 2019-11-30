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
@DisplayName("Integrationstests Transition-Entity/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class TransitionTest extends AbstractEntityTest<Transition>
{
	private final static String jpaContext = "testworkflowdb";

	@BeforeAll
	void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM workflowdb.t_transition");
		initialQueries.add("DELETE FROM workflowdb.t_state");

		super.setUp(jpaContext, initialQueries, Transition.class);

	}

	@Override
	protected Transition createObjUnderTest()
	{
		final Workflow wf = new Workflow("Test-Wf");
		final State fromStatus = new State("Neu");
		final State toStatus = new State("in Arbeit");

		final Transition objUnderTest = new Transition(wf.getObjId(), fromStatus, toStatus);
		objUnderTest.setDescription("Start der Bearbeitung");
		return objUnderTest;
	}

	@Override
	protected Transition updateObjUnderTest(final Transition entity)
	{
		final Transition updatedObject = entity;
		updatedObject.setDescription(entity.getDescription().toUpperCase());
		return updatedObject;
	}

}

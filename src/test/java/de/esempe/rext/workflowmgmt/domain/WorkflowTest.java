package de.esempe.rext.workflowmgmt.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.AbstractEntityTest;

@Tag("integration-test")
@DisplayName("Integrationstests Workflow-Entity/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class WorkflowTest extends AbstractEntityTest<Workflow>
{
	private final static String jpaContext = "testworkflowdb";

	State neu;
	State bearbeiten;
	State erledigt;
	Transition neu2bearbeiten;
	Transition bearbeiten2erledigt;

	@BeforeAll
	void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM workflowdb.t_transition");
		initialQueries.add("DELETE FROM workflowdb.t_state");
		initialQueries.add("DELETE FROM workflowdb.t_workflow");

		super.setUp(jpaContext, initialQueries, Workflow.class);

		this.neu = new State("neu");
		this.bearbeiten = new State("bearbeiten");
		this.erledigt = new State("erledigt");

		this.neu2bearbeiten = new Transition(this.objUnderTest.getObjId(), this.neu, this.bearbeiten);
		this.bearbeiten2erledigt = new Transition(this.objUnderTest.getObjId(), this.bearbeiten, this.erledigt);

	}

	@Override
	protected Workflow createObjUnderTest()
	{

		final Workflow objUnderTest = new Workflow("Test-WF");
		objUnderTest.setDescription("Beschreibung für WF");
		return objUnderTest;
	}

	@Override
	protected Workflow updateObjUnderTest(final Workflow entity)
	{
		final Workflow updatedObject = entity;
		updatedObject.setDescription(entity.getDescription().toUpperCase());
		return updatedObject;
	}

	@Test
	@DisplayName("Alle Status von 'leeren' Workflow")
	public void getStatusEmptyWf()
	{
		// prepare
		final Workflow objUnderTest = this.createObjUnderTest();

		// act
		final Set<State> state = objUnderTest.getStates();

		// assert
		assertThat(state).isNotNull();
		assertThat(state.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("Alle Transitionen von 'leeren' Workflow")
	public void getTransistionsEmptyWf()
	{
		// prepare
		final Workflow objUnderTest = this.createObjUnderTest();

		// act
		final Set<Transition> transitions = objUnderTest.getTransitions();

		// assert
		assertThat(transitions).isNotNull();
		assertThat(transitions.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("Einfacher Workflow - Tranisitionen")
	public void transititionsSimpleWorkflow()
	{
		// prepare
		final Workflow objUnderTest = this.createObjUnderTest();
		objUnderTest.addTransition(this.neu2bearbeiten);
		objUnderTest.addTransition(this.bearbeiten2erledigt);

		// act
		final Set<Transition> transitions = objUnderTest.getTransitions();
		// assert
		assertThat(transitions).isNotNull();
		assertThat(transitions.size()).isEqualTo(2);
		assertThat(transitions).contains(this.neu2bearbeiten, this.bearbeiten2erledigt);
	}

	@Test
	@DisplayName("Einfacher Workflow - Status")
	public void statesSimpleWorkflow()
	{
		// prepare
		final Workflow objUnderTest = this.createObjUnderTest();
		objUnderTest.addTransition(this.neu2bearbeiten);
		objUnderTest.addTransition(this.bearbeiten2erledigt);

		// act
		final Set<State> states = objUnderTest.getStates();
		// assert
		assertThat(states).isNotNull();
		assertThat(states.size()).isEqualTo(3);
		assertThat(states).contains(this.neu, this.bearbeiten, this.erledigt);
	}

	@Test
	@DisplayName("Einfacher Workflow - Folgestatus")
	public void nextStateSimpleWorkflow()
	{
		// prepare
		final Workflow objUnderTest = this.createObjUnderTest();
		objUnderTest.addTransition(this.neu2bearbeiten);
		objUnderTest.addTransition(this.bearbeiten2erledigt);

		// act
		final Set<State> states = objUnderTest.getNextStates(this.neu);
		// assert
		assertThat(states).isNotNull();
		assertThat(states.size()).isEqualTo(1);
		assertThat(states).contains(this.bearbeiten);
	}

}

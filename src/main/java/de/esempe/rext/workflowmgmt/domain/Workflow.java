package de.esempe.rext.workflowmgmt.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;
import de.esempe.rext.workflowmgmt.boundary.Constants;

@Entity
@Table(name = Constants.table_workflow, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.selectAllWorkflow, query = "SELECT w FROM Workflow w"),
	@NamedQuery(name = Constants.deleteAllWorkflow, query = "DELETE FROM Workflow"),
	@NamedQuery(name = Constants.byObjIdWorkflow, query = "SELECT w FROM Workflow w WHERE w.objid= :objid"),
	@NamedQuery(name = Constants.byNameWorkflow, query = "SELECT w FROM Workflow w WHERE w.name= :name")
})
//@formatter:on
@JsonbNillable
public class Workflow extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "workflow_objid")
	private Set<Transition> transitions;
	@Column(name = "first_state_objid")
	private UUID firstStateObjid;

	Workflow()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public Workflow(final String name)
	{
		this(name, UUID.randomUUID());
	}

	public Workflow(final String name, final UUID objid)
	{
		this.objid = objid;
		this.name = name;
		this.description = "";
		this.transitions = new HashSet<>();
	}

	// Getter/Setter
	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public Set<Transition> getTransitions()
	{
		return Collections.unmodifiableSet(this.transitions);
	}

	public Optional<UUID> getFirstStateObjid()
	{
		return Optional.fromNullable(this.firstStateObjid);
	}

	public void setFirstStateObjid(final UUID firstStatusObjid)
	{
		final boolean anyMatch = this.transitions.stream().map(t -> t.getFromState()).anyMatch(s -> s.getObjId().equals(firstStatusObjid));
		if (anyMatch)
		{
			this.firstStateObjid = firstStatusObjid;
		} else
		{
			throw new IllegalArgumentException("Status in keiner Transition als From-Status enthalten");
		}
	}

	public void addTransition(final Transition t)
	{
		this.transitions.add(t);
	}

	public void removeTransition(final Transition t)
	{
		this.transitions.remove(t);
	}

	public Set<State> getStates()
	{
		// @formatter:off
		final Set<State> result = this.transitions
				.stream()
				.flatMap(t -> Stream.of(t.getFromState(), t.getToState()))
				.collect(Collectors.toSet());
		// @formatter:on
		return result;
	}

	public Set<State> getNextStates(final State state)
	{
		// Alle Transititionen mit dem Ausgangsstatus
		final Set<Transition> hasGivenStatusAsFromStatus = this.transitions.stream().filter(t -> t.getFromState().equals(state)).collect(Collectors.toSet());
		// Alle Nachfolger-Status 'toState'dieser Tranistionen
		final Set<State> result = hasGivenStatusAsFromStatus.stream().map(t -> t.getToState()).collect(Collectors.toSet());

		return result;

	}

	@Override
	public Key getKey()
	{
		return new Key("name", this.name);
	}

	// Standardmethoden
	@Override
	public String toString()
	{
		//@formatter:off
		final String result = MoreObjects.toStringHelper(this)
				.add("id",this.getId())
				.add("obiId",this.getObjId())
				.add("name",this.name)
				.toString();

		return result;
		//@formatter:on
	}

}

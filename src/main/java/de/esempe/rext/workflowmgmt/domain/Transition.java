package de.esempe.rext.workflowmgmt.domain;

import java.util.UUID;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;
import de.esempe.rext.workflowmgmt.boundary.Constants;

/**
 * Eine Transition, Übergang vom Status s -> Status t, ist immer genau einem
 * Workflow zugeordnet.
 *
 * @author Stefan M. Prechtl (www.esempe.de)
 *
 */
@Entity
@Table(name = Constants.table_transition, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.selectAllTransition, query = "SELECT t FROM Transition t"),
	@NamedQuery(name = Constants.deleteAllTransition, query = "DELETE FROM Transition"),
	@NamedQuery(name = Constants.byObjIdTransition, query = "SELECT t FROM Transition t WHERE t.objid= :objid"),
	@NamedQuery(name = Constants.byWorkflowTransition, query = "SELECT t FROM Transition t WHERE t.workflowObjid= :workflow_objid")
})
@JsonbNillable
public class Transition extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	@Convert(converter = de.esempe.rext.shared.domain.UuidConverter.class)
	@Column(name = "workflow_objid")
	private UUID workflowObjid;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "from_state_id")
	private State fromState;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "to_state_id")
	private State toState;

	private String description;

	Transition()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public Transition(final UUID workflowObjid, final State fromState, final State toState)
	{
		this(workflowObjid,fromState, toState, UUID.randomUUID());
	}

	public Transition(final UUID workflowObjid, final State fromState, final State toState, final UUID objid)
	{
		this.objid = objid;
		this.workflowObjid = workflowObjid;
		this.fromState= fromState;
		this.toState = toState;
	}

	// Getter/Setter
	public State getFromState()
	{
		return this.fromState;
	}

	public State getToState()
	{
		return this.toState;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}


	@Override
	public Key getKey()
	{
		return Key.EMPTY;
	}

	// Standardmethoden
	@Override
	public String toString()
	{
		//@formatter:off
		final String result = MoreObjects.toStringHelper(this)
				.add("id",this.getId())
				.add("obiId",this.getObjId())
				.add("workflowObjId",this.workflowObjid)
				.add("fromState",this.fromState)
				.add("toState",this.toState)
				.toString();

		return result;
		//@formatter:on
	}

}

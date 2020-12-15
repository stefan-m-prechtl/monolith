package de.esempe.rext.workflowmgmt.domain;

import java.util.UUID;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;
import de.esempe.rext.workflowmgmt.boundary.Constants;

@Entity
@Table(name = Constants.table_state, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.selectAllStatus, query = "SELECT s FROM State s"),
	@NamedQuery(name = Constants.deleteAllStatus, query = "DELETE FROM State"),
	@NamedQuery(name = Constants.byObjIdStatus, query = "SELECT s FROM State s WHERE s.objid= :objid"),
	@NamedQuery(name = Constants.byNameStatus, query = "SELECT s FROM State s WHERE s.name= :name")
})
@JsonbNillable
public class State extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;

	State()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public State(final String name)
	{
		this(name, UUID.randomUUID());
	}

	public State(final String name, final UUID objid)
	{
		this.objid = objid;
		this.name = name;
		this.description = "";
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
				.add("id", this.getId())
				.add("obiId", this.getObjId())
				.add("name", this.name)
				.toString();

		return result;
		//@formatter:on
	}

}

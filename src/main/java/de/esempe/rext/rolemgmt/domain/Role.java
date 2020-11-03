package de.esempe.rext.rolemgmt.domain;

import java.util.UUID;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import de.esempe.rext.rolemgmt.boundary.Constants;
import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;

@Entity
@Table(name = Constants.table, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.selectall, query = "SELECT r FROM Role r"),
	@NamedQuery(name = Constants.deleteall, query = "DELETE FROM Role"),
	@NamedQuery(name = Constants.byObjId, query = "SELECT r FROM Role r WHERE r.objid= :objid"),
	@NamedQuery(name = Constants.byName, query = "SELECT r FROM Role r WHERE r.name= :name")
})
//@formatter:on
@JsonbNillable()
public class Role extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;

	Role()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public Role(final String name)
	{
		this(name, UUID.randomUUID());
	}

	public Role(final String name, final UUID objid)
	{
		this.objid = objid;
		this.name = name;
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
				.add("id",this.getId())
				.add("obiId",this.getObjId())
				.add("name",this.name)
				.toString();

		return result;
		//@formatter:on
	}
}
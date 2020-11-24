package de.esempe.rext.itemmgmt.domain;

import java.util.UUID;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import de.esempe.rext.itemmgmt.boundary.Constants;
import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;

@Entity
@Table(name = Constants.table_priority, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.selectallPriority, query = "SELECT p FROM Priority p"),
	@NamedQuery(name = Constants.deleteallPriority, query = "DELETE FROM Priority"),
	@NamedQuery(name = Constants.byObjIdPriority, query = "SELECT p FROM Priority p WHERE p.objid= :objid"),
	@NamedQuery(name = Constants.byNamePriority, query = "SELECT p FROM Priority p WHERE p.name= :name")
})
//@formatter:on
@JsonbNillable()
public class Priority extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private int value;
	private String name;
	private String description;

	Priority()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public Priority(final int value, final String name, final String description)
	{
		this(value, name, description, UUID.randomUUID());
	}

	public Priority(final int value, String name, final String description, final UUID objid)
	{

		this.objid = objid;
		this.value = value;
		this.name = name;
		this.description = description;
	}

	// Getter/Setter
	public int getValue()
	{
		return this.value;
	}

	public void setValue(final int value)
	{
		this.value = value;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
		this.name = name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		Preconditions.checkArgument(!Strings.isNullOrEmpty(description));
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
				.add("obiId", this.getObjId())
				.add("name", this.value)
				.add("description", this.description)
				.add("value", this.value)
				.toString();

		return result;
		//@formatter:on
	}

}

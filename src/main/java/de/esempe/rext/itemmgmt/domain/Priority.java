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
	@NamedQuery(name = Constants.byCaptionPriority, query = "SELECT p FROM Priority p WHERE p.caption= :caption")
})
@JsonbNillable()
public class Priority extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private int value;
	private String caption;


	Priority()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public Priority(final int value, final String caption)
	{
		this(value, caption, UUID.randomUUID());
	}

	public Priority(final int value, String caption, final UUID objid)
	{
		this.objid = objid;
		this.value = value;
		this.caption = caption;		
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


	public String getCaption()
	{
		return this.caption;
	}

	public void setCaption(String caption)
	{
		Preconditions.checkArgument(!Strings.isNullOrEmpty(caption));
		this.caption = caption;
	}

	@Override
	public Key getKey()
	{
		return new Key("caption", this.caption);
	}


	// Standardmethoden
	@Override
	public String toString()
	{
		//@formatter:off
		final String result = MoreObjects.toStringHelper(this)
				.add("id",this.getId())
				.add("obiId",this.getObjId())
				.add("value",this.value)
				.toString();

		return result;
		//@formatter:on
	}

}


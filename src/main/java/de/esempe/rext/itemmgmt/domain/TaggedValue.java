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
@Table(name = Constants.table_taggedvalue, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.selectallTaggedValue, query = "SELECT t FROM TaggedValue t"),
	@NamedQuery(name = Constants.deleteallTaggedValue, query = "DELETE FROM TaggedValue"),
	@NamedQuery(name = Constants.byObjIdTaggedValue, query = "SELECT t FROM TaggedValue t WHERE t.objid= :objid"),
	@NamedQuery(name = Constants.byTitleTaggedValue, query = "SELECT t FROM TaggedValue t WHERE t.value = :value")
})
@JsonbNillable()
public class TaggedValue extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private String value;


	TaggedValue()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public TaggedValue(final String value)
	{
		this(value, UUID.randomUUID());
	}

	public TaggedValue(final String value, final UUID objid)
	{
		this.objid = objid;
		this.value = value;
	}

	// Getter/Setter
	public String getValue()
	{
		return this.value;
	}

	public void setValue(final String value)
	{
		Preconditions.checkArgument(!Strings.isNullOrEmpty(value));
		this.value = value;
	}


	@Override
	public Key getKey()
	{
		return new Key("value", this.value);
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

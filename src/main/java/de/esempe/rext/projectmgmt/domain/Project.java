package de.esempe.rext.projectmgmt.domain;

import java.util.UUID;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import de.esempe.rext.projectmgmt.boundary.Constants;
import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;

/**
 * Domänenklasse für ein Projekt.
 *
 * @author Stefan M. Prechtl (www.esempe.de)
 *
 */
@Entity
@Table(name = Constants.table, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.selectall, query = "SELECT p FROM Project p"),
	@NamedQuery(name = Constants.deleteall, query = "DELETE FROM Project"),
	@NamedQuery(name = Constants.byObjId, query = "SELECT p FROM Project p WHERE p.objid= :objid"),
	@NamedQuery(name = Constants.byName, query = "SELECT p FROM Project p WHERE p.name= :name")
})
//@formatter:on
@JsonbNillable()
public class Project extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	@Convert(converter = de.esempe.rext.shared.domain.UuidConverter.class)
	@Column(name = "owner_user_objid")
	private UUID ownerUserObjid;

	Project()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public Project(final String name)
	{
		this(name, UUID.randomUUID());
	}

	public Project(final String name, final UUID objid)
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

	public UUID getOwnerUserObjid()
	{
		return this.ownerUserObjid;
	}

	public void setOwnerUserObjid(final UUID ownerUserObjid)
	{
		this.ownerUserObjid = ownerUserObjid;
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
		// @formatter:off
		final String result = MoreObjects.toStringHelper(this)
				.add("id", this.getId())
				.add("objId", this.getObjId())
				.add("name", this.name)
				.add("ownerObjId", this.getOwnerUserObjid())
				.toString();

		return result;
		// @formatter:on
	}
}

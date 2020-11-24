package de.esempe.rext.itemmgmt.domain;

import java.util.UUID;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = Constants.table_item, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.selectallItem, query = "SELECT i FROM Item i"),
	@NamedQuery(name = Constants.deleteallItem, query = "DELETE FROM Item"),
	@NamedQuery(name = Constants.byObjIdItem, query = "SELECT i FROM Item i WHERE i.objid= :objid"),
	@NamedQuery(name = Constants.byTitleItem, query = "SELECT i FROM Item i WHERE i.title = :title"),
	@NamedQuery(name = Constants.byProjectObjId, query = "SELECT i FROM Item i WHERE i.projectObjid= :project_objid")
})
//@formatter:on
@JsonbNillable()
public class Item extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private String title;
	private String content;

	@Convert(converter = de.esempe.rext.shared.domain.UuidConverter.class)
	@Column(name = "project_objid")
	private UUID projectObjid;
	@Convert(converter = de.esempe.rext.shared.domain.UuidConverter.class)
	@Column(name = "creator_user_objid")
	private UUID creatorUserObjid;

	@ManyToOne
	@JoinColumn(name = "fk_priorityid")
	private Priority priority;

	Item()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public Item(final UUID projectObjId, final UUID creatorObjId, final String title)
	{
		this(projectObjId, creatorObjId, title, UUID.randomUUID());
	}

	public Item(final UUID projectObjId, final UUID creatorObjId, final String title, final UUID objid)
	{
		this.objid = objid;
		this.projectObjid = projectObjId;
		this.creatorUserObjid = creatorObjId;
		this.title = title;
		this.content = "";
	}

	// Getter/Setter
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(final String title)
	{
		Preconditions.checkArgument(!Strings.isNullOrEmpty(title));
		this.title = title;
	}

	public String getContent()
	{
		return this.content;
	}

	public void setContent(final String content)
	{
		Preconditions.checkArgument(!Strings.isNullOrEmpty(content), "Leeres Argument");
		this.content = content;
	}

	public UUID getProject()
	{
		return this.projectObjid;
	}

	public void setProject(final UUID projectObjid)
	{
		this.projectObjid = projectObjid;
	}

	public UUID getCreator()
	{
		return this.projectObjid;
	}

	public void setCreator(final UUID userObjid)
	{
		this.creatorUserObjid = userObjid;
	}

	public Priority getPriority()
	{
		return this.priority;
	}

	public void setPriority(Priority priority)
	{
		this.priority = priority;
	}

	@Override
	public Key getKey()
	{
		return new Key("title", this.title);
	}

	// Standardmethoden
	@Override
	public String toString()
	{
		//@formatter:off
		final String result = MoreObjects.toStringHelper(this)
				.add("id", this.getId())
				.add("obiId", this.getObjId())
				.add("title", this.title)
				.add("content", this.content)
				.add("priority", this.priority.getName())
				.toString();

		return result;
		//@formatter:on
	}

}

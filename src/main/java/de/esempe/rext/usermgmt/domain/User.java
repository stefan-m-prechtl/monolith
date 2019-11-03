package de.esempe.rext.usermgmt.domain;

import java.util.UUID;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.usermgmt.boundary.Constants;

@Entity
@Table(name = Constants.table, schema = Constants.schema)
//@formatter:off
@NamedQueries({
	@NamedQuery(name = Constants.all, query = "SELECT u FROM User u"),
	@NamedQuery(name = Constants.byObjId, query = "SELECT u FROM User u WHERE u.objid= :objid"),
	@NamedQuery(name = Constants.byLogin, query = "SELECT u FROM User u WHERE u.login= :login")
})
//@formatter:on

@JsonbNillable()
public class User extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	private String login;
	private String firstname;
	private String lastname;

	User()
	{
		// wegen JPA wird Defaultkonstruktor benötigt
	}

	public User(final String login)
	{
		this(login, UUID.randomUUID());
	}

	public User(final String login, final UUID objid)
	{
		this.id = -1L;
		this.objid = objid;
		this.login = login;
	}

	// Getter/Setter
	public String getLogin()
	{
		return this.login;
	}

	public void setLogin(final String login)
	{
		this.login = login;
	}

	public String getFirstname()
	{
		return this.firstname;
	}

	public void setFirstname(final String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return this.lastname;
	}

	public void setLastname(final String lastname)
	{
		this.lastname = lastname;
	}

	// Standardmethoden
	@Override
	public String toString()
	{
		//@formatter:off
		final String result = MoreObjects.toStringHelper(this)
				.add("id",this.getId())
				.add("obiId",this.getObjId())
				.add("login",this.login)
				.toString();

		return result;
		//@formatter:on
	}

	@Override
	public boolean equals(final Object obj)
	{

		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final User other = (User) obj;
		return Objects.equal(this.getId(), other.getId()) && Objects.equal(this.getObjId(), other.getObjId()) && Objects.equal(this.login, other.login);
	}

	@Override
	public int hashCode()
	{

		return Objects.hashCode(this.getId(), this.getObjId(), this.login);
	}

}

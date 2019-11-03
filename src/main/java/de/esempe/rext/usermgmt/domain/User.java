package de.esempe.rext.usermgmt.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Entity
@Table(name = "t_user", schema = "userdb")
//@formatter:off
@NamedQueries({
	@NamedQuery(name = "all", query = "SELECT u FROM User u"),
	@NamedQuery(name = "byObjId", query = "SELECT u FROM User u WHERE u.objid= :objid"),
	@NamedQuery(name = "byLogin", query = "SELECT u FROM User u WHERE u.login= :login")
})
//@formatter:on

@JsonbNillable()
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Convert(converter = de.esempe.rext.shared.domain.UuidConverter.class)
	private UUID objid;

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

	public void setIdFrom(User user)
	{
		this.id = user.id;
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

	public long getId()
	{
		return this.id;
	}

	public UUID getObjid()
	{
		return this.objid;
	}

	public String getFirstname()
	{
		return this.firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return this.lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	// Standardmethoden
	@Override
	public String toString()
	{
		//@formatter:off
		final String result = MoreObjects.toStringHelper(this)
				.add("id",this.id)
				.add("obiId",this.objid)
				.add("login",this.login)
				.toString();

		return result;
		//@formatter:on
	}

	@Override
	public boolean equals(Object obj)
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
		return Objects.equal(this.id, other.id) && Objects.equal(this.objid, other.objid) && Objects.equal(this.login, other.login);
	}

	@Override
	public int hashCode()
	{

		return Objects.hashCode(this.id, this.objid, this.login);
	}

}

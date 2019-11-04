package de.esempe.rext.usermgmt.boundary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.esempe.rext.usermgmt.domain.User;

@Stateless
public class UserRepository
{
	@PersistenceContext(unitName = Constants.PersistenceContext)
	EntityManager em;

	public List<User> loadAll()
	{
		return this.em.createNamedQuery(Constants.all, User.class).getResultList();
	}

	public void save(final User user)
	{
		final Optional<User> findResult = this.findByObjId(user.getObjId());

		// vorhandene Entität?
		if (findResult.isPresent())
		{
			// --> Update
			user.setId(findResult.get().getId());
			this.em.merge(user);
		}
		else
		{
			// --> Insert
			this.em.persist(user);
		}
		this.em.flush();
	}

	public void delete(final UUID objid)
	{
		final Optional<User> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.em.remove(searchResult.get());
			this.em.flush();
		}
	}

	public void delete(final User user)
	{
		this.delete(user.getObjId());
	}

	public Optional<User> findByObjId(final UUID objid)
	{
		return this.findByNamedQuery(Constants.byObjId, "objid", objid);
	}

	public Optional<User> findByLoginId(final String login)
	{
		return this.findByNamedQuery(Constants.byLogin, "login", login);
	}

	private Optional<User> findByNamedQuery(final String nameOfQuery, final String nameOfParameter, final Object valueOfParameter)
	{
		Optional<User> result = Optional.empty();

		try
		{
			final TypedQuery<User> qry = this.em.createNamedQuery(nameOfQuery, User.class);
			qry.setParameter(nameOfParameter, valueOfParameter);
			final User user = qry.getSingleResult();
			result = Optional.of(user);
		}
		// kein Ergebnis
		catch (final NoResultException e)
		{
			// nichts zu tun: dann wird "leeres" Optional geliefertS
		}
		// 2-n Ergebnisse --> hier nicht möglich: Id bzw. Login sind unique
		// catch (final NonUniqueResultException e)
		// {
		//
		// }

		return result;

	}

}

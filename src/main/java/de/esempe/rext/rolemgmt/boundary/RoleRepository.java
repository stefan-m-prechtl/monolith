package de.esempe.rext.rolemgmt.boundary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.esempe.rext.rolemgmt.domain.Role;

@Stateless
public class RoleRepository
{
	@PersistenceContext(unitName = Constants.PersistenceContext)
	EntityManager em;

	public List<Role> loadAll()
	{
		return this.em.createNamedQuery(Constants.all, Role.class).getResultList();
	}

	public void save(final Role role)
	{
		final Optional<Role> findResult = this.findByObjId(role.getObjId());

		// vorhandene Entität?
		if (findResult.isPresent())
		{
			// --> Update
			role.setId(findResult.get().getId());
			this.em.merge(role);
		}
		else
		{
			// --> Insert
			this.em.persist(role);
		}
		this.em.flush();
	}

	public void delete(final UUID objid)
	{
		final Optional<Role> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.em.remove(searchResult.get());
			this.em.flush();
		}
	}

	void delete(final Role role)
	{
		this.delete(role.getObjId());
	}

	public Optional<Role> findByObjId(final UUID objid)
	{
		return this.findByNamedQuery(Constants.byObjId, "objid", objid);
	}

	public Optional<Role> findByName(final String name)
	{
		return this.findByNamedQuery(Constants.byName, "name", name);
	}

	private Optional<Role> findByNamedQuery(final String nameOfQuery, final String nameOfParameter, final Object valueOfParameter)
	{
		Optional<Role> result = Optional.empty();

		try
		{
			final TypedQuery<Role> qry = this.em.createNamedQuery(nameOfQuery, Role.class);
			qry.setParameter(nameOfParameter, valueOfParameter);
			final Role role = qry.getSingleResult();
			result = Optional.of(role);
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

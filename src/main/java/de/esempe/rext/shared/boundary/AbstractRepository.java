package de.esempe.rext.shared.boundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;

@Stateless
public abstract class AbstractRepository<E extends AbstractEntity> implements IRepository<E>
{
	protected EntityManager em;
	protected Map<String, String> mapNamedQueries;

	// konkrete Entitätsklasse - wird per Konstruktor gesetz
	private final Class<E> entityClass;

	protected AbstractRepository(final Class<E> entityClass)
	{
		this.entityClass = entityClass;
		this.mapNamedQueries = new HashMap<String, String>();
	}

	@Override
	public List<E> loadAll()
	{
		final List<E> result = this.em.createNamedQuery(this.mapNamedQueries.get(NamedQueryConstants.SELECT_ALL), this.entityClass).getResultList();
		return result;
	}

	@Override
	public void deleteAll()
	{
		this.em.createNamedQuery(this.mapNamedQueries.get(NamedQueryConstants.DELETE_ALL), this.entityClass).executeUpdate();
	}

	@Override
	public Optional<E> findByObjId(final UUID objid)
	{
		return this.findOneEntityByNamedQuery(this.mapNamedQueries.get(NamedQueryConstants.SELECT_BY_ID), "objid", objid);
	}

	@Override
	public Optional<E> findByKey(final Key key)
	{
		return this.findOneEntityByNamedQuery(this.mapNamedQueries.get(NamedQueryConstants.SELECT_BY_KEY), key.name, key.value);
	}

	@Override
	public void save(final E entity)
	{
		final Optional<E> findResult = this.findByObjId(entity.getObjId());

		// vorhandene Entität?
		if (findResult.isPresent())
		{
			// --> Update
			entity.setId(findResult.get().getId());
			this.em.merge(entity);
		}
		else
		{
			// --> Insert
			this.em.persist(entity);
		}
		this.em.flush();

	}

	@Override
	public void delete(final UUID objid)
	{
		final Optional<E> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.em.remove(searchResult.get());
			this.em.flush();
		}
	}

	@Override
	public void delete(final E entity)
	{
		this.delete(entity.getObjId());
	}

	protected Optional<E> findOneEntityByNamedQuery(final String nameOfQuery, final String nameOfParameter, final Object valueOfParameter)
	{
		Optional<E> result = Optional.empty();

		try
		{
			final TypedQuery<E> qry = this.em.createNamedQuery(nameOfQuery, this.entityClass);
			qry.setParameter(nameOfParameter, valueOfParameter);
			final E entity = qry.getSingleResult();
			result = Optional.of(entity);
		}
		// kein Ergebnis
		catch (final NoResultException e)
		{
			// nichts zu tun: dann wird "leeres" Optional geliefert
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		// 2-n Ergebnisse --> hier nicht möglich: Id bzw. Login sind unique
		// catch (final NonUniqueResultException e)
		// {
		//
		// }

		return result;

	}

	protected List<E> findEntitiesNamedQuery(final String nameOfQuery, final String nameOfParameter, final Object valueOfParameter)
	{
		List<E> result = new ArrayList<E>();

		try
		{
			final TypedQuery<E> qry = this.em.createNamedQuery(nameOfQuery, this.entityClass);
			qry.setParameter(nameOfParameter, valueOfParameter);
			result = qry.getResultList();

		}
		// kein Ergebnis
		catch (final NoResultException e)
		{
			// nichts zu tun: dann wird leere Liste geliefert!
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return result;

	}

}

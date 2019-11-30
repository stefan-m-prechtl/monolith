package de.esempe.rext.shared.boundary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;

public interface IRepository<E extends AbstractEntity>
{
	List<E> loadAll();

	Optional<E> findByObjId(UUID objId);

	Optional<E> findByKey(Key key);

	void save(E entity);

	void delete(UUID objId);

	void delete(E entity);
}

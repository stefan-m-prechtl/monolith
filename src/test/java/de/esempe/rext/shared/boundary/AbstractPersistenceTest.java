package de.esempe.rext.shared.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import de.esempe.rext.shared.domain.AbstractEntity;
import de.esempe.rext.shared.domain.Key;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractPersistenceTest<E extends AbstractEntity>
{
	protected static EntityManager em;
	protected static EntityTransaction tx;
	protected AbstractRepository<E> objUnderTest;

	protected E entity;

	// BeforeAll
	protected static void setUp(final String jpaContext, final List<String> initialQueries)
	{
		// JPA-Umgebung initialisieren
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(jpaContext);
		em = factory.createEntityManager();
		tx = em.getTransaction();

		// Alle Daten in DB löschen
		PersistenceHelper.runSqlQueries(jpaContext, initialQueries);
	}

	@AfterAll
	static void tearDown() throws Exception
	{
		em.clear();
		em.close();
	}

	@BeforeEach
	void setUpEach()
	{
		this.objUnderTest = this.createObjUnderTest();

	}

	@Test
	@Order(1)
	@DisplayName("Create")
	void create()
	{
		// prepare
		final E result = this.createTestEntity();

		// act
		tx.begin();
		this.objUnderTest.save(result);
		tx.commit();

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isGreaterThan(0);

		// für die Weiterverwendung in den nachfolgenden Tests
		this.entity = result;
	}

	@Test
	@Order(2)
	@DisplayName("Read")
	void read()
	{
		// act
		final Optional<E> searchResult = this.objUnderTest.findByObjId(this.entity.getObjId());

		// assert
		assertThat(searchResult).isNotNull();
		assertThat(searchResult.get()).isNotNull();
		assertThat(searchResult.get().getKey()).isEqualTo(this.entity.getKey());

	}

	@Test
	@Order(3)
	@DisplayName("Update")
	void update()
	{
		// prepare
		this.entity = this.updateTestEntity(this.entity);

		final Long id = this.entity.getId();
		final UUID objid = this.entity.getObjId();
		final Key key = this.entity.getKey();

		// act
		tx.begin();
		this.objUnderTest.save(this.entity);
		tx.commit();

		// assert
		assertThat(this.entity).isNotNull();
		assertThat(this.entity.getId()).isEqualTo(id);
		assertThat(this.entity.getObjId()).isEqualTo(objid);
		assertThat(this.entity.getKey()).isEqualTo(key);
	}

	@Test
	@Order(4)
	@DisplayName("Delete")
	void delete()
	{
		// prepare
		final UUID objid = this.entity.getObjId();

		// act
		tx.begin();
		this.objUnderTest.delete(this.entity);
		tx.commit();

		final Optional<E> searchResult = this.objUnderTest.findByObjId(objid);

		// assert
		assertThat(searchResult.isPresent()).isFalse();
	}

	protected abstract AbstractRepository<E> createObjUnderTest();

	protected abstract E createTestEntity();

	protected abstract E updateTestEntity(E entity);

}

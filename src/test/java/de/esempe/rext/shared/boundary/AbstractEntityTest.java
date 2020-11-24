package de.esempe.rext.shared.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import de.esempe.rext.shared.domain.AbstractEntity;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractEntityTest<E extends AbstractEntity>
{
	protected static EntityManager em;
	protected static EntityTransaction tx;

	// konkrete Entitätsklasse - wird per Konstruktor gesetz
	protected Class<E> entityClass;
	protected E objUnderTest;
	protected Long id;

	// BeforeAll
	protected void setUp(final String jpaContext, final List<String> initialQueries, final Class<E> entityClass)
	{
		// JPA-Umgebung initialisieren
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(jpaContext);
		em = factory.createEntityManager();
		tx = em.getTransaction();

		// Alle Daten in DB löschen, evt. Initialdaten erzeugen
		PersistenceHelper.runSqlQueries(jpaContext, initialQueries);

		this.objUnderTest = this.createObjUnderTest();
		this.entityClass = entityClass;
	}

	@AfterAll
	static void tearDown() throws Exception
	{
		em.clear();
		em.close();
	}

	protected <T extends AbstractEntity> T saveReferencedEntity(T refEntity)
	{
		tx.begin();
		em.persist(refEntity);
		tx.commit();
		return refEntity;
	}

	@Test
	@DisplayName("Create entity")
	@Order(1)
	public void create()
	{
		// act
		tx.begin();
		em.persist(this.objUnderTest);
		tx.commit();
		// assert
		assertThat(this.objUnderTest.getId()).isGreaterThan(0);

		this.id = this.objUnderTest.getId();
	}

	@Test
	@DisplayName("Read entity")
	@Order(2)
	public void read()
	{
		// act
		final E readEntity = em.find(this.entityClass, this.id);
		// assert
		assertThat(readEntity).isNotNull();
		assertThat(readEntity.getId()).isGreaterThan(0);
	}

	@Test
	@DisplayName("Update entity")
	@Order(3)
	public void update()
	{
		// act
		tx.begin();
		final E updatedEntity = em.merge(this.updateObjUnderTest(this.objUnderTest));
		tx.commit();
		// assert
		assertThat(updatedEntity).isNotNull();
		assertThat(updatedEntity.getId()).isEqualTo(this.objUnderTest.getId());
		assertThat(updatedEntity.getObjId()).isEqualTo(this.objUnderTest.getObjId());
	}

	@Test
	@DisplayName("Delete entity")
	@Order(4)
	public void delete()
	{
		// act
		tx.begin();
		em.remove(this.objUnderTest);
		tx.commit();

		// assert
		final E readEntity = em.find(this.entityClass, this.id);
		assertThat(readEntity).isNull();

	}

	protected abstract E createObjUnderTest();

	protected abstract E updateObjUnderTest(E entity);

}

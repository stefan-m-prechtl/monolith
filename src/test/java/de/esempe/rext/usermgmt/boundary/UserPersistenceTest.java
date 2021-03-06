package de.esempe.rext.usermgmt.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.PersistenceHelper;
import de.esempe.rext.usermgmt.domain.User;

@Tag("integration-test")
@DisplayName("Integrationstests UserRepository/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
class UserPersistenceTest
{

	private static EntityManager em;
	private static EntityTransaction tx;
	private static final String jpaContext = "testuserdb";

	private UserRepository objUnderTest;
	private static User user;

	@BeforeAll
	static void setUp() throws Exception
	{
		// JPA-Umgebung initialisieren
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(jpaContext);
		em = factory.createEntityManager();
		tx = em.getTransaction();

		// Alle Daten in DB löschen
		final List<String> deleteQueries = Arrays.asList("DELETE FROM userdb.t_user",
				"INSERT INTO userdb.t_user (objid,login, firstname,lastname) VALUES (UUID_TO_BIN(UUID()),'prs','Stefan', 'Prechtl')");
		PersistenceHelper.runSqlQueries(jpaContext, deleteQueries);

	}

	@BeforeEach
	void setUpEach()
	{
		// Testobjekt erzeugen
		this.objUnderTest = new UserRepository();
		this.objUnderTest.em = em;
		this.objUnderTest.init();
	}

	@AfterAll
	static void tearDown() throws Exception
	{
		em.clear();
		em.close();
	}

	@Test
	@Order(1)
	@DisplayName("Create")
	void create()
	{
		// prepare
		final User result = new User("u4711");
		result.setFirstname("Eva");
		result.setLastname("Mustermann");

		// act
		UserPersistenceTest.tx.begin();
		this.objUnderTest.save(result);
		UserPersistenceTest.tx.commit();

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getObjId()).isNotNull();
		assertThat(result.getLogin()).isEqualTo("u4711");

		// für die Weiterverwendung in den nachfolgenden Tests
		UserPersistenceTest.user = result;
	}

	@Test
	@Order(2)
	@DisplayName("Read")
	void read()
	{
		// act
		final Optional<User> searchResult = this.objUnderTest.findByObjId(UserPersistenceTest.user.getObjId());

		// assert
		assertThat(searchResult).isNotNull();
		assertThat(searchResult.get()).isNotNull();
		assertThat(searchResult.get().getLogin()).isEqualTo(UserPersistenceTest.user.getLogin());

	}

	@Test
	@Order(3)
	@DisplayName("Update")
	void update()
	{
		// prepare
		UserPersistenceTest.user.setLogin(UserPersistenceTest.user.getLogin().toUpperCase());

		final UUID objid = UserPersistenceTest.user.getObjId();
		final String login = UserPersistenceTest.user.getLogin();

		// act
		UserPersistenceTest.tx.begin();
		this.objUnderTest.save(UserPersistenceTest.user);
		UserPersistenceTest.tx.commit();

		// assert
		assertThat(UserPersistenceTest.user).isNotNull();

		assertThat(UserPersistenceTest.user.getObjId()).isEqualTo(objid);
		assertThat(UserPersistenceTest.user.getLogin()).isEqualTo(login);
	}

	@Test
	@Order(4)
	@DisplayName("Delete")
	void delete()
	{
		// prepare
		final UUID objid = UserPersistenceTest.user.getObjId();

		// act
		UserPersistenceTest.tx.begin();
		this.objUnderTest.delete(UserPersistenceTest.user);
		UserPersistenceTest.tx.commit();

		final Optional<User> searchResult = this.objUnderTest.findByObjId(objid);

		// assert
		assertThat(searchResult.isPresent()).isFalse();
	}

}

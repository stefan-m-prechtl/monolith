package de.esempe.rext.rolemgmt.boundary;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.rolemgmt.domain.Role;
import de.esempe.rext.shared.boundary.AbstractPersistenceTest;

@Tag("integration-test")
@DisplayName("Integrationstests RoleRepository/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class RolePersistenceTest extends AbstractPersistenceTest<Role>
{
	private final static String jpaContext = "testroledb";

	@BeforeAll
	static void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM roledb.t_role");
		initialQueries.add("INSERT INTO roledb.t_role (objid,name,description) VALUES (UUID_TO_BIN(UUID()),'SysAdm','Rolle für Systemadminstration')");

		AbstractPersistenceTest.setUp(jpaContext, initialQueries);

	}

	@Override
	protected RoleRepository createObjUnderTest()
	{
		final RoleRepository repository = new RoleRepository();
		repository.em = em;
		repository.init();

		return repository;
	}

	@Override
	protected Role createTestEntity()
	{
		final Role result = new Role("Projektadmin");
		result.setDescription("Rolle für Projektadminstration");

		return result;

	}

	@Override
	protected Role updateTestEntity(final Role role)
	{
		role.setDescription(role.getDescription().toUpperCase());
		return role;
	}

}

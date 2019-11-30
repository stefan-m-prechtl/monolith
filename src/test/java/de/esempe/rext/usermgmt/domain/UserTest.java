package de.esempe.rext.usermgmt.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.AbstractEntityTest;

@Tag("integration-test")
@DisplayName("Integrationstests User-Entity/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class UserTest extends AbstractEntityTest<User>
{
	private final static String jpaContext = "testuserdb";

	@BeforeAll
	void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM userdb.t_user");

		super.setUp(jpaContext, initialQueries, User.class);

	}

	@Override
	protected User createObjUnderTest()
	{
		final User objUnderTest = new User("sp");
		objUnderTest.setFirstname("Stefan");
		objUnderTest.setLastname("Prechtl");

		return objUnderTest;
	}

	@Override
	protected User updateObjUnderTest(final User entity)
	{
		final User updatedObject = entity;
		updatedObject.setFirstname(entity.getFirstname().toUpperCase());

		return updatedObject;
	}

}

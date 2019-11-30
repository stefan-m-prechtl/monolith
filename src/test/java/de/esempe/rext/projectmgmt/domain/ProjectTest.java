package de.esempe.rext.projectmgmt.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

import de.esempe.rext.shared.boundary.AbstractEntityTest;

@Tag("integration-test")
@DisplayName("Integrationstests Project-Entity/MySQL-Datenbank")
@TestMethodOrder(OrderAnnotation.class)
public class ProjectTest extends AbstractEntityTest<Project>
{
	private final static String jpaContext = "testprojectdb";

	@BeforeAll
	void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<String>();
		initialQueries.add("DELETE FROM projectdb.t_project");

		super.setUp(jpaContext, initialQueries, Project.class);

	}

	@Override
	protected Project createObjUnderTest()
	{
		final Project objUnderTest = new Project("Testprojekt");
		objUnderTest.setDescription("Beschreibung für Testprojekt");
		final UUID ownerUserObjId = UUID.randomUUID();
		objUnderTest.setOwnerUserObjid(ownerUserObjId);

		return objUnderTest;
	}

	@Override
	protected Project updateObjUnderTest(final Project entity)
	{
		final Project updatedObject = entity;
		entity.setDescription(entity.getDescription().toUpperCase());

		return updatedObject;
	}
}

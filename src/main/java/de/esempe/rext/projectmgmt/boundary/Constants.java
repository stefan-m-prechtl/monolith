package de.esempe.rext.projectmgmt.boundary;

public class Constants
{
	private Constants()
	{
		// keine Instanz notwendig
	}

	// REST Resource
	public final static String path = "/projectmgmt/projects";
	public final static String PersistenceContext = "projectdb";

	// JPA: Schema, Tabelle, Named Queries
	public final static String schema = "projectdb";
	public final static String table = "t_project";
	public final static String selectall = "allProject";
	public final static String deleteall = "deleteAllProjects";
	public final static String byObjId = "ProjectByObjId";
	public final static String byName = "ProjectByName";

}

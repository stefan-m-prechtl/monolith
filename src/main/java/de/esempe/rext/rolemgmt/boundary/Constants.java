package de.esempe.rext.rolemgmt.boundary;

public final class Constants
{
	private Constants()
	{
		// keine Instanz notwendig
	}

	// REST Resource
	public final static String path = "/rolemgmt/roles";
	public final static String PersistenceContext = "roledb";

	// JPA: Schema, Tabelle, Named Queries

	public final static String schema = "roledb";
	public final static String table = "t_role";
	public final static String all = "allRoles";
	public final static String byObjId = "RolebyObjId";
	public final static String byName = "RolebyName";

}

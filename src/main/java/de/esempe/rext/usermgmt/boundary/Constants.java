package de.esempe.rext.usermgmt.boundary;

public final class Constants
{
	private Constants()
	{
		// keine Instanz notwendig
	}

	// REST Resource
	public final static String path = "/usermgmt/users";
	public final static String PersistenceContext = "userdb";

	// JPA: Schema, Tabelle, Named Queries

	public final static String schema = "userdb";
	public final static String table = "t_user";
	public final static String all = "allUsers";
	public final static String byObjId = "UserbyObjId";
	public final static String byLogin = "UserbyLogin";

}

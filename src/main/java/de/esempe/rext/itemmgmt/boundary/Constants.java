package de.esempe.rext.itemmgmt.boundary;

public class Constants
{
	private Constants()
	{
		// keine Instanz notwendig
	}

	// REST Resource
	public final static String pathItems = "/itemmgmt/items";
	public final static String pathProjectItems = "/itemmgmt/project";
	public final static String PersistenceContext = "itemdb";

	// JPA: Schema, Tabelle, Named Queries

	public final static String schema = "itemdb";
	public final static String table = "t_item";
	public final static String all = "allItems";
	public final static String byObjId = "ItembyObjId";
	public final static String byProjectObjId = "ItemsbyProjectObjId";

}

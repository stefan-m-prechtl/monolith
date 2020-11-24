package de.esempe.rext.itemmgmt.boundary;

public class Constants
{
	private Constants()
	{
		// keine Instanz notwendig
	}

	// REST Resource
	public final static String pathItems = "/itemmgmt/items";
	public final static String pathPriorities = "/itemmgmt/priorities";
	public final static String pathProjectItems = "/itemmgmt/project";
	public final static String PersistenceContext = "itemdb";

	// JPA: Schema, Tabelle, Named Queries
	public final static String schema = "itemdb";
	public final static String table_item = "t_item";
	public final static String table_taggedvalue = "t_taggedvalue";
	public final static String table_priority = "t_priority";

	// Item
	public final static String selectallItem = "selectAllItem";
	public final static String deleteallItem = "deleteAllItem";
	public final static String byTitleItem = "byTitleItem";
	public final static String byObjIdItem = "byObjIdItem";
	public final static String byProjectObjId = "byProjectObjIdItem";
	// Priority
	public final static String selectallPriority = "selectAllPriority";
	public final static String deleteallPriority = "deleteAllPriority";
	public final static String byObjIdPriority = "byObjIdPriority";
	public final static String byNamePriority = "byCaptionPriority";
	// TaggedValue
	public final static String selectallTaggedValue = "selectAllTaggedValue";
	public final static String deleteallTaggedValue = "deleteAllTaggedValue";
	public final static String byObjIdTaggedValue = "byObjIdTaggedValue";
	public final static String byTitleTaggedValue = "byTitleTaggedValue";

}

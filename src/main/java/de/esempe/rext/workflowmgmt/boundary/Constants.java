package de.esempe.rext.workflowmgmt.boundary;

public class Constants
{

	// REST Resource
	public final static String PersistenceContext = "workflowdb";

	// JPA: Schema, Tabelle, Named Queries
	public final static String schema = "workflowdb";
	public final static String table_workflow = "t_workflow";
	public final static String table_state = "t_state";
	public final static String table_transition = "t_transition";

	public final static String allWorkflow = "allWorkflow";
	public final static String byObjIdWorkflow = "WorkflowByObjId";
	public final static String byNameWorkflow = "WorkflowByName";

	public final static String allStatus = "allStatus";
	public final static String byObjIdStatus = "StatusByObjId";
	public final static String byNameStatus = "StatusByName";

	public final static String allTransition = "allTransition";
	public final static String byObjIdTransition = "TransitionByObjId";
	public final static String byWorkflowTransition = "TransitionByWorkflowObjId";

}

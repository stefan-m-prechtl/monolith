package de.esempe.rext.workflowmgmt.boundary;

public class Constants
{

	// REST Resource
	public final static String PersistenceContext = "workflowdb";
	public final static String pathStatus = "/workflowmgmt/status";
	public final static String pathTransition = "/workflowmgmt/transitions";
	public final static String pathWorkflow = "/workflowmgmt/workflows";

	// JPA: Schema, Tabelle, Named Queries
	public final static String schema = "workflowdb";
	public final static String table_workflow = "t_workflow";
	public final static String table_state = "t_state";
	public final static String table_transition = "t_transition";

	public final static String selectAllWorkflow = "selectAllWorkflow";
	public final static String deleteAllWorkflow = "deleteAllWorkflow";
	public final static String byObjIdWorkflow = "selectWorkflowByObjId";
	public final static String byNameWorkflow = "selectWorkflowByName";

	public final static String selectAllStatus = "selectAllStatus";
	public final static String deleteAllStatus = "deleteAllStatus";
	public final static String byObjIdStatus = "selectStatusByObjId";
	public final static String byNameStatus = "selectStatusByName";

	public final static String selectAllTransition = "selectAllTransition";
	public final static String deleteAllTransition = "deleteAllTransition";
	public final static String byObjIdTransition = "selectTransitionByObjId";
	public final static String byWorkflowTransition = "selectTransitionByWorkflowObjId";

}

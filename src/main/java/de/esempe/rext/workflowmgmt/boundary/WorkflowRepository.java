package de.esempe.rext.workflowmgmt.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.esempe.rext.shared.boundary.AbstractRepository;
import de.esempe.rext.shared.boundary.NamedQueryConstants;
import de.esempe.rext.workflowmgmt.domain.Workflow;

@Stateless(description = "Respository für Domänenklasse Workflow")
public class WorkflowRepository extends AbstractRepository<Workflow>
{
	@PersistenceContext(unitName = Constants.PersistenceContext)
	EntityManager em;

	public WorkflowRepository()
	{
		super(Workflow.class);
	}

	@PostConstruct
	public void init()
	{
		super.em = this.em;
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_ALL, Constants.allWorkflow);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_ID, Constants.byObjIdWorkflow);
	}

}

package de.esempe.rext.workflowmgmt.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.esempe.rext.shared.boundary.AbstractRepository;
import de.esempe.rext.shared.boundary.NamedQueryConstants;
import de.esempe.rext.workflowmgmt.domain.State;

@Stateless(description = "Respository für Domänenklasse Status")
public class StateRepository extends AbstractRepository<State>
{
	@PersistenceContext(unitName = Constants.PersistenceContext)
	EntityManager em;

	public StateRepository()
	{
		super(State.class);
	}

	@PostConstruct
	public void init()
	{
		super.em = this.em;
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_ALL, Constants.selectAllStatus);
		this.mapNamedQueries.put(NamedQueryConstants.DELETE_ALL, Constants.deleteAllStatus);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_ID, Constants.byObjIdStatus);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_KEY, Constants.byNameStatus);
	}

}

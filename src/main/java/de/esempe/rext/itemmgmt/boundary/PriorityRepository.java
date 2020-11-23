package de.esempe.rext.itemmgmt.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.esempe.rext.itemmgmt.domain.Priority;
import de.esempe.rext.shared.boundary.AbstractRepository;
import de.esempe.rext.shared.boundary.NamedQueryConstants;

@Stateless(description = "Respository für Domänenklasse Priorität")
public class PriorityRepository extends AbstractRepository<Priority>
{
	@PersistenceContext(unitName = Constants.PersistenceContext)
	EntityManager em;

	public PriorityRepository()
	{
		super(Priority.class);
	}

	@PostConstruct
	public void init()
	{
		super.em = this.em;
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_ALL, Constants.selectallPriority);
		this.mapNamedQueries.put(NamedQueryConstants.DELETE_ALL, Constants.deleteallPriority);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_ID, Constants.byObjIdPriority);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_KEY, Constants.byCaptionPriority);
	}

}
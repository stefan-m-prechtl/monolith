package de.esempe.rext.projectmgmt.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.esempe.rext.projectmgmt.domain.Project;
import de.esempe.rext.shared.boundary.AbstractRepository;
import de.esempe.rext.shared.boundary.NamedQueryConstants;

@Stateless(description = "Respository für Domänenklasse Projekt")
public class ProjectRepository extends AbstractRepository<Project>
{
	@PersistenceContext(unitName = Constants.PersistenceContext)
	EntityManager em;

	public ProjectRepository()
	{
		super(Project.class);
	}

	@PostConstruct
	public void init()
	{
		super.em = this.em;
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_ALL, Constants.selectall);
		this.mapNamedQueries.put(NamedQueryConstants.DELETE_ALL, Constants.deleteall);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_ID, Constants.byObjId);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_KEY, Constants.byName);
	}
}

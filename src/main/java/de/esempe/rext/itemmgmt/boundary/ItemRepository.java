package de.esempe.rext.itemmgmt.boundary;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.esempe.rext.itemmgmt.domain.Item;
import de.esempe.rext.shared.boundary.AbstractRepository;
import de.esempe.rext.shared.boundary.NamedQueryConstants;

@Stateless(description = "Respository für Domänenklasse Projekt")
public class ItemRepository extends AbstractRepository<Item>
{
	@PersistenceContext(unitName = Constants.PersistenceContext)
	EntityManager em;

	public ItemRepository()
	{
		super(Item.class);
	}

	@PostConstruct
	public void init()
	{
		super.em = this.em;
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_ALL, Constants.selectall);
		this.mapNamedQueries.put(NamedQueryConstants.DELETE_ALL, Constants.deleteall);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_ID, Constants.byObjId);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_KEY, Constants.byTitle);
	}

	public List<Item> loadAllForProject(final UUID projectObjid)
	{
		final List<Item> result = this.findEntitiesNamedQuery(Constants.byProjectObjId, "project_objid", projectObjid);
		return result;

	}
}
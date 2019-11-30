package de.esempe.rext.usermgmt.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.esempe.rext.shared.boundary.AbstractRepository;
import de.esempe.rext.shared.boundary.NamedQueryConstants;
import de.esempe.rext.usermgmt.domain.User;

@Stateless(description = "Respository für Domänenklasse Benutzer")
public class UserRepository extends AbstractRepository<User>
{
	@PersistenceContext(unitName = Constants.PersistenceContext)
	EntityManager em;

	public UserRepository()
	{
		super(User.class);
	}

	@PostConstruct
	public void init()
	{
		super.em = this.em;
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_ALL, Constants.all);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_ID, Constants.byObjId);
		this.mapNamedQueries.put(NamedQueryConstants.SELECT_BY_KEY, Constants.byLogin);
	}

}

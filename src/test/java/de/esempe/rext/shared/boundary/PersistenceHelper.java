package de.esempe.rext.shared.boundary;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PersistenceHelper
{
	public static void runSqlQueries(final String jpaContext, final List<String> deleteQueries)
	{
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(jpaContext);
		final EntityManager em = factory.createEntityManager();
		final EntityTransaction tx = em.getTransaction();

		for (final String deleteQuery : deleteQueries)
		{
			tx.begin();
			final Query qry = em.createNativeQuery(deleteQuery);
			qry.executeUpdate();
			tx.commit();
		}
	}

}

//INSERT INTO userdb.t_user (objid,login, firstname,lastname) VALUES (UUID_TO_BIN(UUID()),'prs','Stefan', 'Prechtl');

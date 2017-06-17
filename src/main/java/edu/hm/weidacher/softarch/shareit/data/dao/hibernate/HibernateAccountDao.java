package edu.hm.weidacher.softarch.shareit.data.dao.hibernate;

import edu.hm.weidacher.softarch.shareit.data.dao.AccountDao;
import edu.hm.weidacher.softarch.shareit.data.model.Account;

/**
 * AccountDao + Hibernate.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class HibernateAccountDao extends AbstractHibernateDao<Account> implements AccountDao {

    /**
     * Ctor.
     */
    public HibernateAccountDao() {
	super(Account.class);
    }

    /**
     * Find an account by username.
     *
     * @param username username query
     * @return account if present or null
     */
    @Override
    public Account getByUsername(String username) {
	return getByExtractor(Account::getUsername, username);
    }

}

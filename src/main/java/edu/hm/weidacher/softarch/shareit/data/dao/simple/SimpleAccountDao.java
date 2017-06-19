package edu.hm.weidacher.softarch.shareit.data.dao.simple;

import edu.hm.weidacher.softarch.shareit.data.dao.AccountDao;
import edu.hm.weidacher.softarch.shareit.data.model.Account;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class SimpleAccountDao extends SimpleAbstractDao<Account> implements AccountDao {

    /**
     * Ctor.
     */
    public SimpleAccountDao() {
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

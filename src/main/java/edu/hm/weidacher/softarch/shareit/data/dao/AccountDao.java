package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Account;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public interface AccountDao extends Dao<Account> {

    /**
     * Find an account by username.
     *
     * @param username username query
     * @return account if present or null
     */
    Account getByUsername(String username);
}

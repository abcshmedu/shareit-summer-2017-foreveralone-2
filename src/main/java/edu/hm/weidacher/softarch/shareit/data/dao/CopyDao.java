package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Copy;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class CopyDao extends AbstractUpdatableDao<Copy> {
    protected CopyDao() {
	super(Copy.class);
    }
}

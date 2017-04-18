package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Copy;

/**
 * Dao for the Copy model-type.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class CopyDao extends AbstractUpdatableDao<Copy> {

    /**
     * Ctor.
     */
    protected CopyDao() {
	super(Copy.class);
    }
}

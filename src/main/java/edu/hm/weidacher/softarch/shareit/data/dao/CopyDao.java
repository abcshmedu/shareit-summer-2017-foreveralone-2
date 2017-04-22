package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Copy;

/**
 * Dao for the Copy model-type.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class CopyDao extends AbstractUpdatableDao<Copy> {

    /**
     * Ctor.
     */
    public CopyDao() {
	super(Copy.class);
    }
}

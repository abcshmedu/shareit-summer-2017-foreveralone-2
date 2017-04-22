package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Disc;

/**
 * A Dao for the Disc model.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class DiscDao extends AbstractUpdatableDao<Disc> {

    /**
     * Ctor.
     */
    public DiscDao() {
	super(Disc.class);
    }
}

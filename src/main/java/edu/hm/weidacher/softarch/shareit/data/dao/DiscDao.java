package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Disc;

/**
 * A Dao for the Disc model.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DiscDao extends AbstractUpdatableDao<Disc> {

    /**
     * Ctor.
     */
    public DiscDao() {
	super(Disc.class);
    }
}

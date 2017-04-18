package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Disc;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DiscDao extends AbstractUpdatableDao<Disc> {

    protected DiscDao() {
	super(Disc.class);
    }
}

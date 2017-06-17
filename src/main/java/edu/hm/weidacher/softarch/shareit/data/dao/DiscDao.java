package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Disc;

/**
 * Defines all utility methods for discs.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public interface DiscDao extends UpdatableDao<Disc> {

    /**
     * Returns a Disc identified by a barcode.
     * @param barcode identifier
     * @return disc, or null if none could be identified
     */
    Disc getByBarcode(String barcode);
}

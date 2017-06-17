package edu.hm.weidacher.softarch.shareit.data.dao.simple;

import edu.hm.weidacher.softarch.shareit.data.dao.DiscDao;
import edu.hm.weidacher.softarch.shareit.data.model.Disc;

/**
 * A Dao for the Disc model.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class SimpleDiscDao extends SimpleAbstractUpdatableDao<Disc> implements DiscDao {

    /**
     * Ctor.
     */
    public SimpleDiscDao() {
	super(Disc.class);
    }

    /**
     * Returns a Disc identified by a barcode.
     * @param barcode identifier
     * @return disc, or null if none could be identified
     */
    @Override
    public Disc getByBarcode(String barcode) {
        if (barcode == null) {
            throw new NullPointerException("Barcode may not be null.");
	}

        return getByExtractor((Disc::getBarcode), barcode);
    }
}

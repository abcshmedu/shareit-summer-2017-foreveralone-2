package edu.hm.weidacher.softarch.shareit.data.dao.hibernate;

import edu.hm.weidacher.softarch.shareit.data.dao.DiscDao;
import edu.hm.weidacher.softarch.shareit.data.model.Disc;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class HibernateDiscDao extends AbstractHibernateUpdatableDao<Disc> implements DiscDao {

    /**
     * Ctor.
     */
    public HibernateDiscDao() {
	super(Disc.class);
    }

    /**
     * Returns a Disc identified by a barcode.
     *
     * @param barcode identifier
     * @return disc, or null if none could be identified
     */
    @Override
    public Disc getByBarcode(String barcode) {
	return getByExtractor(Disc::getBarcode, barcode);
    }
}

package edu.hm.weidacher.softarch.shareit.data.dao.hibernate;

import edu.hm.weidacher.softarch.shareit.data.dao.CopyDao;
import edu.hm.weidacher.softarch.shareit.data.model.Copy;

/**
 * Dao that handles Copy entities through hibernate.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class HibernateCopyDao extends AbstractHibernateUpdatableDao<Copy> implements CopyDao {

    /**
     * Ctor.
     */
    public HibernateCopyDao() {
	super(Copy.class);
    }

}

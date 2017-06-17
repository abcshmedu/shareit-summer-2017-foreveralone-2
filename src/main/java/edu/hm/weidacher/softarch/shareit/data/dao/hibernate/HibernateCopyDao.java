package edu.hm.weidacher.softarch.shareit.data.dao.hibernate;

import edu.hm.weidacher.softarch.shareit.data.dao.CopyDao;
import edu.hm.weidacher.softarch.shareit.data.model.Copy;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class HibernateCopyDao extends AbstractHibernateUpdatableDao<Copy> implements CopyDao {

    /**
     * Ctor.
     *
     * @param modelClass the class handled through the dao
     */
    public HibernateCopyDao(Class<Copy> modelClass) {
	super(modelClass);
    }

}

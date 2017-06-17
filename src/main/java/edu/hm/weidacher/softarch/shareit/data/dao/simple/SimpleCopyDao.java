package edu.hm.weidacher.softarch.shareit.data.dao.simple;

import edu.hm.weidacher.softarch.shareit.data.dao.AbstractUpdatableDao;
import edu.hm.weidacher.softarch.shareit.data.dao.CopyDao;
import edu.hm.weidacher.softarch.shareit.data.model.Copy;

/**
 * Dao for the Copy model-type.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class SimpleCopyDao extends AbstractUpdatableDao<Copy> implements CopyDao {

    /**
     * Ctor.
     */
    public SimpleCopyDao() {
	super(Copy.class);
    }
}

package edu.hm.weidacher.softarch.shareit.data.database;

/**
 * Factory for Databases.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public final class DatabaseFactory {

    /**
     * The database itself.
     * Contains everything.
     */
    private static Database database;

    /**
     * Ctor.
     */
    private DatabaseFactory() {
        //private
    }

    /**
     * Returns a database instance.
     *
     * @return database
     */
    public static Database getDatabase() {
        if (database == null) {
            database = new SimpleDatabase();
	}

	return database;
    }

}

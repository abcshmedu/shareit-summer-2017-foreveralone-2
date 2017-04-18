package edu.hm.weidacher.softarch.shareit.data.database;

/**
 * Factory for Databases.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DatabaseFactory {

    /**
     * The database itself.
     * Contains everything.
     */
    private static Database database;

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

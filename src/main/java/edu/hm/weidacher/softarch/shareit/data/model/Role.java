package edu.hm.weidacher.softarch.shareit.data.model;

/**
 * All the roles users can have in the ShareIt application.
 *
 * The access-level of the role is defined by the order of appearance in this enum.
 * The method ordinal() is used to determine the access-level.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public enum Role {

    /**
     * Typical user without any privileges.
     */
    USER,

    /**
     * Admin with advanced access rights.
     */
    ADMIN;

    /**
     * Returns whether this role is authorized to access resources on the given role.
     * @param role role protection level of the resource
     * @return isAuthorized
     */
    public boolean authorizesAccessOn(Role role) {
	return ordinal() >= role.ordinal();
    }
}

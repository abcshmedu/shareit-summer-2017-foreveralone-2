package edu.hm.weidacher.softarch.shareit.data.model;

import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Account model.
 *
 * Each user has an account which only stores information about authentication/authorization.
 * Thus, a future standalone authentication-service can have a separate database, which will help in segregating
 *  the application.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class Account extends AbstractUpdatableModel {

    /**
     * Username.
     */
    private String username;

    /**
     * Password hash.
     */
    private String passwordHash;

    /**
     * Users role.
     */
    private Role role;

    /**
     * Id of the relate users.
     * This is not a relation to a user, because the models will eventually be stored in separate databases.
     */
    private UUID userId;

    /**
     * Ctor.
     */
    public Account() {
    }

    /**
     * Merges the data of the other Model with this.
     * <p>
     * All fields of the other model will be written to this one, except
     * for null fields, the original data remains
     *
     * @param other the other model
     * @throws PersistenceException when the type of other does is not compatible to the type of this model
     */
    @Override
    public <T extends AbstractUpdatableModel> void mergeWith(T other) throws PersistenceException {

    }

    /**
     * Checks whether this model has all required fields.
     *
     * @throws PersistenceException when invalid
     */
    @Override
    public void validate() throws PersistenceException {

    }

    /**
     * Returns the username.
     * @return username
     */
    public String getUsername() {
	return username;
    }

    /**
     * Sets the username.
     * @param username username
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * Returns the password-hash.
     * @return password hash
     */
    public String getPasswordHash() {
	return passwordHash;
    }

    /**
     * Sets the password-hash.
     * @param passwordHash pw hash
     */
    public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
    }

    /**
     * Returns the role.
     * @return role
     */
    public Role getRole() {
	return role;
    }

    /**
     * Sets the role.
     * @param role role
     */
    public void setRole(Role role) {
	this.role = role;
    }

    /**
     * Returns the user id.
     * @return user id
     */
    public UUID getUserId() {
	return userId;
    }

    /**
     * Sets the user id.
     * @param userId user id
     */
    public void setUserId(UUID userId) {
	this.userId = userId;
    }
}

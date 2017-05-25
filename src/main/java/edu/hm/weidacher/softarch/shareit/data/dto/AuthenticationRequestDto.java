package edu.hm.weidacher.softarch.shareit.data.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Dto class carrying authentication data.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class AuthenticationRequestDto {

    /**
     * Username.
     */
    private String username;

    /**
     * Hash of a password.
     */
    @SerializedName("password")
    private String passwordHash;

    /**
     * Ctor.
     */
    public AuthenticationRequestDto() {
    }

    /**
     * Returns whether all necessary fields have been set correctly (by syntax).
     * @return isValid
     */
    public boolean isValid() {
        return
	    username != null
		&& passwordHash != null
		&& passwordHash.length() > 0
		&& username.length() > 0;
    }

    /**
     * Returns the contained password.
     * @return password hash
     */
    public String getPasswordHash() {
	return passwordHash;
    }

    /**
     * Sets the pw.
     * @param passwordHash password hash
     */
    public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
    }

    /**
     * Returns the contained username.
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
}

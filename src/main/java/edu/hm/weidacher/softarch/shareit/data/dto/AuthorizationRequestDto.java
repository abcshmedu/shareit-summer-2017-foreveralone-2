package edu.hm.weidacher.softarch.shareit.data.dto;

import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.data.model.Role;

/**
 * Authorization Request DTO.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class AuthorizationRequestDto {

    /**
     * Token.
     */
    private String token;

    /**
     * User.
     */
    private UUID user;

    /**
     * Role.
     */
    private Role role;

    /**
     * Return the token.
     * @return token
     */
    public String getToken() {
	return token;
    }

    /**
     * Set the token.
     * @param token token
     */
    public void setToken(String token) {
	this.token = token;
    }

    /**
     * Get the user.
     * @return user
     */
    public UUID getUser() {
	return user;
    }

    /**
     * Set the user.
     * @param user user
     */
    public void setUser(UUID user) {
	this.user = user;
    }

    /**
     * Get the role.
     * @return role
     */
    public Role getRole() {
	return role;
    }

    /**
     * Set the role.
     * @param role role
     */
    public void setRole(Role role) {
	this.role = role;
    }
}

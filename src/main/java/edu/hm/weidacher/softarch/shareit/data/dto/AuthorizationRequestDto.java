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
    public String token;

    /**
     * User.
     */
    public UUID user;

    /**
     * Role.
     */
    public Role role;

}

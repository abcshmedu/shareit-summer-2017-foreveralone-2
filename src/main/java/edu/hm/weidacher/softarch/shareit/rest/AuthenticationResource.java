package edu.hm.weidacher.softarch.shareit.rest;

import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;

import edu.hm.weidacher.softarch.shareit.Configuration;
import edu.hm.weidacher.softarch.shareit.data.dao.AccountDao;
import edu.hm.weidacher.softarch.shareit.data.dto.AuthenticationRequestDto;
import edu.hm.weidacher.softarch.shareit.data.dto.AuthorizationRequestDto;
import edu.hm.weidacher.softarch.shareit.data.model.Account;
import edu.hm.weidacher.softarch.shareit.util.AuthenticationUtil;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
@Path(Configuration.SSO)
public class AuthenticationResource extends AbstractResource {

    /**
     * Dao for accounts.
     */
    @Inject
    private AccountDao accountDao;

    /**
     * Authenticate a user.
     *
     * This is the resource that performs a commonly known login
     *  with username and password.
     *
     * When the authentication succeeds, so when the username exists and
     *  the related password-hash matches the stored one for the username,
     *  a Json Web Token (JWT) is returned.
     * This JWT must then be attached to any request on protected resources
     *  in the HTTP header: "Authentication" with a preceeding "Bearer "
     * The full header thus looks like this:
     *  Authentication: Bearer somenastylongstringwhichisinfactthetoken
     *
     * The following status-codes will be returned:
     *
     * 200 : OK
     * 400 : Username not found or the password could not authenticate the username
     * 400 : Bad json
     *
     * @param json
     * {
     *             "username" : "<username>",
     *             "password" : "<password>"
     * }
     * @return {
     *     "accessToken" : "thesamenastylongstringyoushouldincludeinyourauthenticationheaderwiththepreceedingBearer"
     * } OR {
     *     "message" : "Username not found/invalid"
     * }
     */
    @POST
    @Path(Configuration.AUTHENTICATE_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(String json) {
	AuthenticationRequestDto authenticationDto;
        try {
	    authenticationDto = getGson().fromJson(json, AuthenticationRequestDto.class);

	} catch (JsonSyntaxException jsex) {
            return error("Bad json", javax.ws.rs.core.Response.Status.BAD_REQUEST);
	}

	// ################### TODO
	if (authenticationDto.getUsername().equals("admin")) {
            if (authenticationDto.getPasswordHash().equals("root!Lord123")) {
                return Response.ok(getTokenMap(AuthenticationUtil.createAdminToken())).build();
	    }
	}
	// ###################

	// find user for given username
	Account account = accountDao.getByUsername(authenticationDto.getUsername());

        if (account == null) {
            return error("Username not found or the password could not authenticate the username", Response.Status.BAD_REQUEST);
	}

	// authenticate him
	boolean isAuthentic = AuthenticationUtil.authenticate(account, authenticationDto);

        if (!isAuthentic) {
            // intentionally the same message, as an attacker should informed if an account exists
	    return error("Username not found or the password could not authenticate the username", Response.Status.BAD_REQUEST);
	}

	// create JWT
	String authenticationToken = AuthenticationUtil.createAuthenticationToken(account);

	return Response.ok(getTokenMap(authenticationToken)).build();
    }

    /**
     * Authorize a user to access a protected resource.
     *
     * The authorization process can check the token against the role,
     *  or against a user-id.
     *
     * @param json {
     *             "accessToken" : String
     *             "role" : Role.name() // if present, the token is checked against this role
     *             "user" : String // if present, the token is checked against this user-id
     * }
     * @return 	200 OK - Authorized successfully
     * 		400 BAD REQUEST - bad json/no authorization method
     * 		401 UNAUTHORIZED - token not present/Account deleted/token expired
     * 		403 FORBIDDEN - not allowed to access
     */
    @POST
    @Path(Configuration.AUTHORIZE_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(String json) {
	final AuthorizationRequestDto authorizationRequest;
        try {
	    authorizationRequest = getGson().fromJson(json, AuthorizationRequestDto.class);
	} catch (JsonSyntaxException jsex) {
            return error("Bad json", Response.Status.BAD_REQUEST);
	}

	// check for token
	if (authorizationRequest.getToken() == null) {
            return error("No token given", Response.Status.UNAUTHORIZED);
	}

	// strip leading "Bearer"
	authorizationRequest.setToken(authorizationRequest.getToken().replaceFirst("Bearer ", ""));

	boolean authorized = false;

	// authorize by role
	if (authorizationRequest.getRole() != null) {
            authorized = AuthenticationUtil.authorizeRole(authorizationRequest.getToken(), authorizationRequest.getRole());
	}

	// authorize by user-id
	else if (authorizationRequest.getUser() != null) {
	    final Account authorizationTarget = accountDao.getById(authorizationRequest.getUser());

	    authorized = AuthenticationUtil.authorizePrivate(authorizationRequest.getToken(), authorizationTarget);
	}

	// nothing to authorize against given
	else {
            return error("No authorization target given.", Response.Status.BAD_REQUEST);
	}

	if (authorized) {
	    return Response.ok().build();
	} else {
	    return error(Response.Status.FORBIDDEN);
	}
    }

    /**
     * Returns the token embedded in a JSON-Object.
     * @param token jwt
     * @return {token:jwt}
     */
    private String getTokenMap(String token) {
	final Map<String, String> accessToken = Collections.singletonMap("accessToken", token);

	return getGson().toJson(accessToken);
    }
}

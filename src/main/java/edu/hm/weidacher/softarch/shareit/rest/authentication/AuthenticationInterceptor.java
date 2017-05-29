package edu.hm.weidacher.softarch.shareit.rest.authentication;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.URLConnectionEngine;

import edu.hm.weidacher.softarch.shareit.Configuration;
import edu.hm.weidacher.softarch.shareit.data.dto.AuthorizationRequestDto;
import edu.hm.weidacher.softarch.shareit.data.model.Role;

/**
 * Interceptor authorizing requests to protected resources.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
@Provider
public class AuthenticationInterceptor implements ContainerRequestFilter {

    /**
     * Contains contextual information about the invoked resource.
     */
    @Context
    private ResourceInfo resourceInfo;

    /**
     * Filters requests by their authorization.
     *
     * @param context context
     */
    @Override
    public void filter(ContainerRequestContext context) throws IOException {
	// check if the requested resource needs authorization
	Authorize authorizationMethod = getAuthorizationMethod();

	if (authorizationMethod == null) {
	    // no authorization required
	    return;
	}

	// by role
	if (authorizationMethod.value() == AuthorizationMethod.BY_ROLE) {
	    final Role minRole = authorizationMethod.minRole();

	    // get authorization header
	    final String authorizationHeader = context.getHeaderString(HttpHeaders.AUTHORIZATION);

	    if (authorizationHeader == null) {
		context.abortWith(Response
		    .status(Response.Status.UNAUTHORIZED)
		    .build()
		);

		return; // UNAUTHORIZED
	    }

	    // authenticate at sso-service
	    Response authResponse = authenticateRole(authorizationHeader, minRole);

	    if (authResponse.getStatus() == Response.Status.OK.getStatusCode()) {

	        return; // AUTHORIZED

	    } else {
		context.abortWith(Response
		    .status(authResponse.getStatus())
		    .entity(authResponse.getEntity())
		    .build()
		);

	        return; // UNAUTHORIZED / FORBIDDEN
	    }
	}


    }

    /**
     * Returns the authorization method required by the invoked artifact.
     *
     * @return required AuthorizationMethod or null, if unprotected
     */
    private Authorize getAuthorizationMethod() {
	Authorize authorizationRequirement = resourceInfo.getResourceMethod().getAnnotation(Authorize.class);

	if (authorizationRequirement == null) {
	    // check the type-level authorization requirement
	    authorizationRequirement = resourceInfo.getResourceClass().getAnnotation(Authorize.class);
	}

	return authorizationRequirement;
    }

    /**
     * Authenticate a request by the client's role.
     *
     * This method triggers a HTTP request to the authentication-service.
     *
     * @param authenticationToken the clients auth-token
     * @param minRole the lowest role allowed to access the resource
     * @return the Response of the authentication-service
     */
    private Response authenticateRole(String authenticationToken, Role minRole) {
	final AuthorizationRequestDto authRequest = new AuthorizationRequestDto();

	authRequest.token = authenticationToken;
	authRequest.role = minRole;

	return new ResteasyClientBuilder().httpEngine(new URLConnectionEngine()).build()
	    .target(Configuration.SSO_AUTHORIZE_PATH)
	    .request()
	    .buildPost(Entity.json(authRequest))
	    .invoke();
    }
}

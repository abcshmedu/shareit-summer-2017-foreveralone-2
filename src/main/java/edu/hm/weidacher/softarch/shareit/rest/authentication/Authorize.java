package edu.hm.weidacher.softarch.shareit.rest.authentication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.hm.weidacher.softarch.shareit.data.model.Role;

/**
 * Annotation declaring a method or class as protected.
 *
 * Access to protected resources must be authorized with an Interceptor.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

    AuthorizationMethod value();

    Role minRole();
}

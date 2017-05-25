package edu.hm.weidacher.softarch.shareit.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import edu.hm.weidacher.softarch.shareit.data.dto.AuthenticationRequestDto;
import edu.hm.weidacher.softarch.shareit.data.model.Account;
import edu.hm.weidacher.softarch.shareit.data.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * Util for authentication business processes.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class AuthenticationUtil {

    /**
     * Secret key for signing JWT tokens.
     */
    private static final Key JWT_SECRET;

    /**
     * The issuer (this service).
     */
    private static final String AUTHENTICATION_SERVICE_ISSUER = "authentication-service";

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtil.class);

    /*
    	Initialize the secret key.
     */
    static {
	final InputStream configStream
	    = AuthenticationUtil.class.getClassLoader().getResourceAsStream("shareit.properties");

	try {
	    Properties config = new Properties();
	    config.load(configStream);

	    JWT_SECRET = new Gson().fromJson(config.getProperty("jwt-secret"), SecretKey.class);

	    configStream.close();
	    config.clear();
	} catch (IOException e) {
	    throw new AssertionError("Error loading jwt config", e);
	}

    }

    /**
     * Creates a JWT authenticating the given account.
     * @param account account to sign
     * @return JSON object containing the JWT-string
     */
    public static String createAuthenticationToken (Account account) {

	final Map<String, Object> claims = Collections.singletonMap("role", account.getRole());

	final String token = Jwts.builder()
	    .setIssuedAt(new Date())
	    .setIssuer(AUTHENTICATION_SERVICE_ISSUER)
	    .setSubject(account.getUserId().toString())
	    .setClaims(claims)
	    .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
	    .compact();

	return new Gson().toJson(new AccessTokenResponse(token));
    }

    /**
     * Authenticates an authenticationRequest against a specific account.
     *
     * @param account the account to test the authRequest on
     * @param authRequest the authenticationRequest as posed by an user.
     * @return whether the given account could authenticate the authentication-request.
     */
    public static boolean authenticate(Account account, AuthenticationRequestDto authRequest) {
	if (!authRequest.isValid()) {
	    return false;
	}

	// check if username matches
	if (!account.getUsername().equals(authRequest.getUsername())) {
	    return false;
	}

	// check if password-hash matches
	return account.getPasswordHash().equals(authRequest.getPasswordHash());
    }

    /**
     * Authorizes a given token against an account.
     *
     * Use this when a users accesses a resource that contains protected data.
     *
     * @param token JWT token string
     * @param role the lowest valid role
     * @return whether the given token legitimates access for the given role
     */
    public static boolean authorizeRole (String token, Role role) {
	try {

	    final Jws<Claims> jws = Jwts.parser()
		.setSigningKey(JWT_SECRET)
		.parseClaimsJws(token);

	    final Claims claims = jws.getBody();

	    if (!claims.containsKey("role")) {
		return false;
	    }

	    Role subjectRole = Role.valueOf(((String) claims.get("role")));

	    return subjectRole.authorizesAccessOn(role);
	} catch (InvalidClaimException iee) {
	    LOGGER.warn("Invalid Claim {}\n{}", token, iee.getStackTrace());
	    return false;
	} catch (SignatureException se) {
	    LOGGER.warn("Illegal signature on private authorization attempt!\n Token: {}", token);
	    return false;
	} catch (ClassCastException cce) {
	    LOGGER.warn("Someone delivered a non-string role: {}", token);
	    return false;
	}
    }

    /**
     * Authorizes a given token against an account.
     *
     * Use this when a users accesses a fully private resource (like changing passwords etc...)
     *
     * @param token JWT token string
     * @param account account to authorize against
     * @return whether the given token legitimates access on the given account
     */
    public static boolean authorizePrivate (String token, Account account) {
	try {

	    Jwts.parser()
		.requireSubject(account.getUserId().toString())
		.setSigningKey(JWT_SECRET)
		.parseClaimsJws(token);

	} catch (InvalidClaimException iee) {
	    return false;
	} catch (SignatureException se) {
	    LOGGER.warn("Illegal signature on private authorization attempt!\n Token: {}", token);
	    return false;
	}

	return true;
    }

    /**
     * Small util class for serialization of access-tokens.
     */
    private static class AccessTokenResponse {
	String accessToken;
	AccessTokenResponse(String t) {accessToken = t;}
    }
}

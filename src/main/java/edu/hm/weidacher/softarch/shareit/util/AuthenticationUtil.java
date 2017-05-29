package edu.hm.weidacher.softarch.shareit.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

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
import io.jsonwebtoken.MalformedJwtException;
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

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtil.class);

    /**
     * Admin account.
     * TODO: put this guy in the database!
     */
    private static final Account ADMIN_ACCOUNT = new Account();

    /*
    	Initialize the secret key.
    	Iniitialize the hardcoded admin account.
     */
    static {
        // secret key
	final InputStream configStream
	    = AuthenticationUtil.class.getClassLoader().getResourceAsStream("shareit.properties");

	try {
	    Properties config = new Properties();
	    config.load(configStream);


	    JWT_SECRET = new Gson().fromJson(config.getProperty("jwt-secret"), KeyLoadProxy.class).getSecretKey();

	    configStream.close();
	    config.clear();
	} catch (IOException e) {
	    throw new AssertionError("Error loading jwt config", e);
	}

	// admin account
	ADMIN_ACCOUNT.setRole(Role.ADMIN);
	ADMIN_ACCOUNT.setUsername("admin");
	ADMIN_ACCOUNT.setPasswordHash("root!Lord123");
	ADMIN_ACCOUNT.setUserId(UUID.randomUUID());
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
	}  catch (MalformedJwtException mje) {
	    LOGGER.warn("Illegal JWT format: {}", token);
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

	    final Jws<Claims> jws = Jwts.parser()
		.setSigningKey(JWT_SECRET)
		.parseClaimsJws(token);

	    final Claims claims = jws.getBody();

	    if (claims.getSubject() == null) {
		return false;
	    }

	    String subject = claims.getSubject();

	    // TODO #########################
	    if (subject.equals(ADMIN_ACCOUNT.getUserId())) {
	        return true;
	    }
	    // ##################

	    return subject.equals(account.getUserId());

	} catch (InvalidClaimException iee) {
	    return false;
	} catch (SignatureException se) {
	    LOGGER.warn("Illegal signature on private authorization attempt!\n Token: {}", token);
	    return false;
	} catch (MalformedJwtException mje) {
	    LOGGER.warn("Illegal JWT format: {}", token);
	    return false;
	}
    }

    /**
     * Create am administrative token.
     * @return nice token :_)
     */
    public static String createAdminToken() {
	return createAuthenticationToken(ADMIN_ACCOUNT);
    }

    /**
     * Small util class for serialization of access-tokens.
     */
    private static class AccessTokenResponse {
	String accessToken;
	AccessTokenResponse(String t) {accessToken = t;}
    }

    /**
     * Utility class to laod the signing key from properties.
     */
    private static class KeyLoadProxy {
	byte[] key;
	String algorithm;

	public Key getSecretKey() {
	    return new SecretKeySpec(key, algorithm);
	}
    }
}

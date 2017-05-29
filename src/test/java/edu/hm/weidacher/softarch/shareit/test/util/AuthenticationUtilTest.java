package edu.hm.weidacher.softarch.shareit.test.util;

import static junit.framework.TestCase.*;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.UUID;

import org.apache.http.auth.AUTH;
import org.eclipse.jetty.server.Authentication;
import org.junit.Before;
import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.data.dto.AuthenticationRequestDto;
import edu.hm.weidacher.softarch.shareit.data.model.Account;
import edu.hm.weidacher.softarch.shareit.data.model.Role;
import edu.hm.weidacher.softarch.shareit.rest.authentication.AuthorizationMethod;
import edu.hm.weidacher.softarch.shareit.util.AuthenticationUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class AuthenticationUtilTest {

    Key key;
    String adminToken;
    Account admin;

    @Before
    public void init () {
	// make any call to perform the static initialization
	adminToken = AuthenticationUtil.createAdminToken();

	// get key
	try {
	    final Field jwtSecretField = AuthenticationUtil.class.getDeclaredField("JWT_SECRET");
	    jwtSecretField.setAccessible(true);
	    key = ((Key) jwtSecretField.get(null));
	} catch (IllegalAccessException | NoSuchFieldException e) {
	    e.printStackTrace();
	}

	// admin account
	admin = new Account();
	admin.setUsername("admin");
	admin.setPasswordHash("root!Lord123");
	admin.setUserId(UUID.fromString((String)parseToken(adminToken).getBody().get("sub")));

    }

    @Test
    public void positiveAuth() {
	AuthenticationRequestDto auth = new AuthenticationRequestDto();

	auth.setPasswordHash("root!Lord123");
	auth.setUsername("admin");

	assertTrue("Authentication impl bad", AuthenticationUtil.authenticate(admin, auth));
    }

    @Test
    public void negativeAuthBadPassword() {
	AuthenticationRequestDto auth = new AuthenticationRequestDto();

	auth.setPasswordHash("r");
	auth.setUsername("admin");

	assertTrue("Authentication impl bad", !AuthenticationUtil.authenticate(admin, auth));
    }

    @Test
    public void negativeAuthBadUserName() {
	AuthenticationRequestDto auth = new AuthenticationRequestDto();

	auth.setPasswordHash("root!Lord123");
	auth.setUsername("adm");

	assertTrue("Authentication impl bad", !AuthenticationUtil.authenticate(admin, auth));
    }

    @Test
    public void negativeAuthInvalid() {
	AuthenticationRequestDto auth = new AuthenticationRequestDto();

	assertTrue("Authentication impl bad", !AuthenticationUtil.authenticate(admin, auth));
    }

    @Test
    public void testAdminTokenCreation() {
	final String adminToken = AuthenticationUtil.createAdminToken();

	assertNotNull(adminToken);

	final Jws<Claims> jws = parseToken(adminToken);

	assertTrue(jws.getBody().containsKey("role"));

	assertEquals(Role.ADMIN.toString(), jws.getBody().get("role"));
    }

    @Test
    public void testAuthorizeRole() {
	final String adminToken = AuthenticationUtil.createAdminToken();

	assertTrue(AuthenticationUtil.authorizeRole(adminToken, Role.ADMIN));
	assertTrue(AuthenticationUtil.authorizeRole(adminToken, Role.USER));
    }

    @Test
    public void testAuthorizePrivate() {
	final String adminToken = AuthenticationUtil.createAdminToken();

	assertTrue(AuthenticationUtil.authorizePrivate(adminToken, admin));
    }

    @Test
    public void authorizeRoleInvalidToken() {
        assertFalse(AuthenticationUtil.authorizeRole("afpgounadsgpusdgu", Role.USER));
    	assertFalse(AuthenticationUtil.authorizeRole("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NWUxYzBkYy0yMTVhLTQ1YWQtOWRkYi02YjBiOWYxZDliMzMiLCJyb2xlIjoiQURNSU4iLCJpc3MiOiJhdXRoZW50aWNhdGlvbi1zZXJ2aWNlIiwiaWFkIjoxNDk2MDc4ODMwODY3fQ.NBWeZ1VnixrdXFukfCzdLhwgSBsv9Gc34cCk_8fqg7f7vA_BIyMpx9-xrBtfJisn-yTuk4GgR2g84jrx4PaWQ",
	    Role.ADMIN));
    }

    @Test
    public void blah() {
	assertEquals(AuthorizationMethod.BY_ROLE, AuthorizationMethod.BY_ROLE); // meh
    }

    private Jws<Claims> parseToken(String token) {
	System.out.println(token);
	return Jwts.parser()
	    .setSigningKey(key)
	    .parseClaimsJws(token);
    }

}

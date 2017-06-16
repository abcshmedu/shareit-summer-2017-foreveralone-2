package edu.hm.weidacher.softarch.shareit;

/**
 * Configuration class for the ShareIt application.
 *
 * TODO refactor to use a .properties file
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public abstract class Configuration {

    public static final String URL_SEPARATOR = "/";
    public static final int PORT = 8080;
    public static final String HOST = "https://shareit-softarch.herokuapp.com/shareit";
    public static final String LOCALHOST = "http://localhost:8080/shareit";

    /* SSO */

    public static final String SSO = "/sso";
    public static final String AUTHORIZE_PATH = "/authorize";
    public static final String AUTHENTICATE_PATH = "/authenticate";

    public static final String SSO_PATH = LOCALHOST + SSO;
    public static final String SSO_AUTHORIZE_PATH = SSO_PATH + AUTHORIZE_PATH;


}

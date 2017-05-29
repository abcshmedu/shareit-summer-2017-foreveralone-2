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
    public static final String HOST = "https://shareit-softarch.herokuapp.com";
    public static final String SSO_PATH = HOST + URL_SEPARATOR + "/sso";
    public static final String SSO_AUTHORIZE_PATH = SSO_PATH + "/authorize";

}

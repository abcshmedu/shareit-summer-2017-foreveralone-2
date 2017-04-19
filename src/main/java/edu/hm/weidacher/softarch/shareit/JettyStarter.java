package edu.hm.weidacher.softarch.shareit;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Start the application without an AppServer like tomcat.
 * @author ab@cs.hm.edu
 */
public final class JettyStarter {

    private static final String APP_URL = "/";
    private static final int PORT = Configuration.PORT;
    private static final String WEBAPP_DIR = "./src/main/webapp/";

    /**
     * Ctor.
     */
    private JettyStarter() {
        // do not use
    }

    /**
     * Entrypoint for the ShareIt backend application.
     * @param args ignored
     * @throws Exception can be thrown at any time
     */
    public static void main(String... args) throws Exception {
        Server jetty = new Server(PORT);
        jetty.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));
        jetty.start();
        System.out.println("Jetty listening on port " + PORT);
        jetty.join();
    }

}

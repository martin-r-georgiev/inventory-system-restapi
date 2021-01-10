package org.martin.inventory;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.tyrus.server.Server;
import org.martin.inventory.security.CORSFilter;
import org.martin.inventory.websockets.ChatEndpoint;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class deploys CustomApplicationConfig on a Grizzly server
 */
class Publisher {

    private static final URI BASE_URI = URI.create("http://0.0.0.0:9090/inventory/");

    public static void main(String[] args) {

        Server wsServer = new Server("0.0.0.0", 9000, "/ws", null, ChatEndpoint.class);

        try {
            CustomApplicationConfig customApplicationConfig = new CustomApplicationConfig();
            customApplicationConfig.register(new CORSFilter());

            // create and start a grizzly server
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, customApplicationConfig, false);

            System.out.println("Hosting resources at " + BASE_URI.toURL());

            // Starting websocket server
            wsServer.start();

            // Starting HTTP server
            server.start();

            System.out.println("Press enter to stop the server...");
            System.in.read();

        } catch (Exception ex) {
            Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
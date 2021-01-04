package org.martin.inventory;

import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketEngine;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.martin.inventory.security.CORSFilter;
import org.martin.inventory.websockets.ChatWebSocket;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class deploys CustomApplicationConfig on a Grizzly server
 */
class Publisher {

    private static final URI BASE_URI = URI.create("http://localhost:9090/inventory/");

    public static void main(String[] args) {

        try {
            CustomApplicationConfig customApplicationConfig = new CustomApplicationConfig();
            customApplicationConfig.register(new CORSFilter());

            // create and start a grizzly server
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, customApplicationConfig, false);

            System.out.println("Hosting resources at " + BASE_URI.toURL());

            System.out.println("Try the following GET operations in your internet browser: ");
            String[] getOperations = {BASE_URI.toURL() + "items", BASE_URI.toURL() + "items/3"};
            for (String getOperation : getOperations) {
                System.out.println(getOperation);
            }

            StaticHttpHandler staticHandler = new StaticHttpHandler("static");
            staticHandler.setFileCacheEnabled(false);
            server.getServerConfiguration().addHttpHandler(staticHandler,"/static/");

            // Create websocket addon
            WebSocketAddOn webSocketAddOn = new WebSocketAddOn();
            server.getListeners().forEach(listener -> { listener.registerAddOn(webSocketAddOn);});

            // register my websocket app
            ChatWebSocket webSocketApp = new ChatWebSocket();
            WebSocketEngine.getEngine().register("/ws", "/chat", webSocketApp);

            // Now start the server
            server.start();

            System.out.println("Press enter to stop the server...");
            System.in.read();

        } catch (IOException ex) {
            Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
package org.martin.inventory;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.martin.inventory.security.AuthenticationFilter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomApplicationConfig extends ResourceConfig
{
    public CustomApplicationConfig()
    {
        packages("org.martin.inventory.endpoints"); // Finding all resource endpoint classes

        register(new ApplicationBinder());
        register(AuthenticationFilter.class);
        
        // Logging Exchanged HTTP Messages
        register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE));
    }
}

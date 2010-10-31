package org.signaut.camelback.start;

import java.io.File;
import java.io.IOException;
import java.util.logging.LogManager;

import org.signaut.camelback.configuration.CamelbackConfig;
import org.signaut.camelback.configuration.ConfigurationLoader;

public class Starter {

    public static void main(String args[]) {
        final ConfigurationLoader configurationLoader = new ConfigurationLoader();
        //Tell hazelcast to use log4j
        System.setProperty("hazelcast.logging.type", "log4j");
        try {
            // Override default JUL properties (Jersey uses JUL for instance)
            LogManager.getLogManager()
                    .readConfiguration(Starter.class.getResourceAsStream("/properties/camelback-logging.properties"));
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final File configFile = new File("camelback.json");
        final JettyInstance jettyInstance = new JettyInstance(
                configurationLoader.loadJsonConfiguration(configFile, CamelbackConfig.class));
        jettyInstance.start();
    }
}

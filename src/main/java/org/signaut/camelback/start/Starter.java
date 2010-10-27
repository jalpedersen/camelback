package org.signaut.camelback.start;

import java.io.File;

import org.signaut.camelback.configuration.CamelbackConfig;
import org.signaut.camelback.configuration.ConfigurationLoader;

public class Starter {

    public static void main(String args[]) {
        final ConfigurationLoader configurationLoader = new ConfigurationLoader();
        final File configFile = new File("camelback.json");
        final JettyInstance jettyInstance = new JettyInstance(configurationLoader.loadJsonConfiguration(configFile, CamelbackConfig.class));
        jettyInstance.start();
    }
}

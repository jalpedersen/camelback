package org.signaut.camelback.start;

import java.io.File;
import java.io.IOException;
import java.util.logging.LogManager;

import org.apache.commons.cli.CommandLine;
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
        final CommandLine options = new CommandLineOptions().getOptions(args);
        final CamelbackConfig config;
        if (options.hasOption(CommandLineOptions.CONFIG_OPTION)) {
            config = configurationLoader.loadJsonConfiguration(new File(options.getOptionValue(CommandLineOptions.CONFIG_OPTION)),
                                                      CamelbackConfig.class);
        } else {
            config = configurationLoader.loadJsonConfiguration(new File("camelback.json"),
                                                      CamelbackConfig.class);
        }
        if (options.hasOption(CommandLineOptions.PORT_OPTION)) {
            config.setPort(Integer.parseInt(options.getOptionValue(CommandLineOptions.PORT_OPTION)));
        }
        if (options.hasOption(CommandLineOptions.SECURE_PORT_OPTION)) {
            config.setSecurePort(Integer.parseInt(options.getOptionValue(CommandLineOptions.SECURE_PORT_OPTION)));
        }
        
        final JettyInstance jettyInstance = new JettyInstance(config);
                
        jettyInstance.start();
    }
}

package org.signaut.camelback.start;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

class CommandLineOptions {
    public static final String PORT_OPTION = "p";
    public static final String SECURE_PORT_OPTION = "s";
    public static final String CONFIG_OPTION = "c";
    
    public CommandLine getOptions(String args[]) {
        final Options options = new Options();
        options.addOption(PORT_OPTION, "port", true, "Http port");
        options.addOption(SECURE_PORT_OPTION, "secure-port", true, "Https port");
        options.addOption(CONFIG_OPTION, "config", true, "Configuration file");
        final CommandLineParser parser = new PosixParser();
        
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Could not parse command line options", e);
        }
    }
}

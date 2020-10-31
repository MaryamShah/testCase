package com.tbagroup;

import java.util.Properties;

import com.tbagroup.domain.Configuration;
import com.tbagroup.service.TrackService;
import com.tbagroup.service.impl.TrackServiceImpl;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    public static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws ParseException, InterruptedException {
        Configuration configuration = getConfiguration(args);
        TrackService trackService = new TrackServiceImpl(configuration);
        trackService.startWork();
        Thread.currentThread().join();
    }

    private static Configuration getConfiguration(String[] args) throws ParseException {
        if (args == null || args.length == 0)
            showHelpMessage();
        return setupConfiguration(args);
    }

    private static void showHelpMessage() {
        Options options = new Options();
        options.addOption("t", true, "track name. use default tba-track")
                .addOption("c", true, "number of crane. use default 1")
                .addOption("l", true, "length of track. use default 10");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("TbaTrack", options);
    }

    private static Configuration setupConfiguration(String... args) throws ParseException {
        Options options = new Options();
        Option propertyOption = Option.builder().longOpt("D").argName("property=value")
                .hasArgs().valueSeparator().numberOfArgs(2)
                .desc("use value for given properties").build();
        options.addOption(propertyOption);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        String trackName = "tba-track";
        int craneCount = 1;
        int length = 10;
        if (cmd.hasOption("D")) {
            Properties properties = cmd.getOptionProperties("D");
            if (properties.containsKey("t")) {
                trackName = properties.getProperty("t");
            }
            if (properties.containsKey("c")) {
                try {
                    craneCount = Integer.valueOf(properties.getProperty("c"));
                } catch (NumberFormatException e) {
                    LOGGER.warn("number format exception is occurred and reset to default 1 : {}"
                            , e.getMessage());
                }
            }
            if (properties.containsKey("l")) {
                try {
                    length = Integer.valueOf(properties.getProperty("l"));
                } catch (NumberFormatException e) {
                    LOGGER.warn("number format exception is occurred and reset to default 10 : {}"
                            , e.getMessage());
                }
            }
        }
        Configuration application = new Configuration(trackName, length, craneCount);
        LOGGER.debug("configuration {} ", application);
        return application;
    }
    
}

package me.olloth.lordned.seasons.util;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import me.olloth.lordned.seasons.Seasons;
import org.bukkit.util.config.Configuration;

public class Config {

    public static Configuration CONFIG;
    public static String WORLD;
    public static int SEASON_LENGTH;
    public static int SEASONS;
    public static int WEEKDAY_COUNT;
    public static List SEASON_STRINGS;
    public static List SEASON_URLS;
    public static List WEEKDAYS;

    public static void load(Configuration config) {
        config.load();
        CONFIG = config;
        SEASON_STRINGS = new ArrayList();
        SEASON_URLS = new ArrayList();
        WEEKDAYS = new ArrayList();

        int seasonsCount = 0;
        int weekdayCount = 0;
        int urlCount = 0;


        for (Object string : config.getList("weekdays")) {
            WEEKDAYS.add(string);
            weekdayCount++;
        }

        for (Object string : config.getList("season_urls")) {
            SEASON_URLS.add(string);
            urlCount++;
        }

        for (Object string : config.getList("seasons")) {
            SEASON_STRINGS.add(string);
            seasonsCount++;
        }

        if (urlCount != seasonsCount) {
            Seasons.log.log(Level.WARNING, "You don't have the same number "
                    + "of URLs as you do seasons");
        }

        SEASONS = seasonsCount;
        WEEKDAY_COUNT = weekdayCount;

        SEASON_LENGTH = config.getInt("season_length", 28);

        WORLD = config.getString("world");

        if (SEASON_LENGTH <= 0) {
            SEASON_LENGTH = 1;
        }

    }

    public static Configuration newConfig(Configuration config) {

        List seasons = new ArrayList();

        // Default season config block
        seasons.add("Djilba");
        seasons.add("Kamba");
        seasons.add("Birak");
        seasons.add("Bunuru");
        seasons.add("Djeran");
        seasons.add("Makuru");

        List seasonUrls = new ArrayList();
        seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Djilba.zip");
        seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Kamba.zip");
        seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Birak.zip");
        seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Bunuru.zip");
        seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Djeran.zip");
        seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Makuru.zip");

        List weekdays = new ArrayList();

        // Default days of the week config block
        weekdays.add("Sunday");
        weekdays.add("Monday");
        weekdays.add("Tuesday");
        weekdays.add("Wednesday");
        weekdays.add("Thursday");
        weekdays.add("Friday");
        weekdays.add("Saturday");

        config.setProperty("weekdays", weekdays);
        config.setProperty("season_urls", seasonUrls);
        config.setProperty("seasons", seasons);
        config.setProperty("season_length", 28);
        config.setProperty("world", "world");

        config.save();

        System.out.println("Created default config.yml - See Seasons/config.yml to edit it");
        return config;
    }

    public static void configSetup(File directory, File configFile) {
        // Make the folder and configuration file if they don't exist.
        if (!directory.exists()) {
            directory.mkdirs();
        }

        configFile = new File(directory, "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Make the new configuration file
            Configuration config = newConfig(new Configuration(new File(directory, "config.yml")));

            // Load the configuration file
            load(config);
        } else {
            // Load the configuration file
            load(new Configuration(new File(directory, "config.yml")));
        }
    }
}

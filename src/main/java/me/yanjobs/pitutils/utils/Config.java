package me.yanjobs.pitutils.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    static String defaultConfig = "quickmaths.enabled=false\nquickmaths.delayRange=1000,3000\nverbose=false";

    static String lunarClientFolder() {
        if (System.getProperty("os.version").contains("Windows")) {
            return "\\.lunarclient\\";
        } else {
            return "/.lunarclient/";
        }
    }

    static Path configPath = new File(System.getProperty("user.home") + lunarClientFolder() + "config-pit-utils.properties").toPath();
    static public void createConfigFile() throws IOException {
        if (Files.notExists(configPath)) {
            Files.createFile(configPath);
            Files.write(configPath, defaultConfig.getBytes());
        }
    }
    public static Properties getConfig() throws IOException {
        createConfigFile();
        FileInputStream configInput = new FileInputStream(configPath.toFile());
        Properties config = new Properties();
        config.load(configInput);
        return config;
    }
}

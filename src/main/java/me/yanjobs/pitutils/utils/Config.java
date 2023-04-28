package me.yanjobs.pitutils.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    String defaultConfig = "answerQuickMaths=false";

    Path configPath = Paths.get(System.getProperty("user.home") + "lunarclient" + "config-pit-utils.properties");
    public void createConfigFile() throws IOException {
        if (Files.notExists(configPath)) {
            Files.createFile(configPath);
            Files.write(configPath, defaultConfig.getBytes());
        }
    }
    public Properties config() throws IOException {
        createConfigFile();
        FileInputStream configInput = new FileInputStream(configPath.toFile());
        Properties config = new Properties();
        config.load(configInput);
        return config;
    }
}

package me.yanjobs.pitutils.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    public String defaultConfig = "quickmaths.enabled=false\nquickmaths.delayRange=1000,3000\nverbose=false";

    public String getLunarClientFolder() {
        if (System.getProperty("os.version").contains("Windows")) {
            return "\\.lunarclient\\";
        } else {
            return "/.lunarclient/";
        }
    }

    private final Path configPath = new File(System.getProperty("user.home") + getLunarClientFolder() + "config-pit-utils.properties").toPath();
    public void createConfigFile() throws IOException {
        if (Files.notExists(configPath)) {
            Files.createFile(configPath);
            Files.write(configPath, defaultConfig.getBytes());
        }
    }
    public Properties getProperties() throws IOException {
        createConfigFile();
        FileInputStream configInput = new FileInputStream(configPath.toFile());
        Properties config = new Properties();
        config.load(configInput);
        return config;
    }

    public void setProperty(@NotNull String key, @NotNull String value) {
        try {
            FileOutputStream out = new FileOutputStream(configPath.toFile());
            getProperties().setProperty(key, value);
            getProperties().store(out, null);
        } catch (IOException e) {
            AddChatMessage.addErrorMessage("An error happened while editing the config. Check the logs for more infos.");
            throw new RuntimeException(e);
        }
    }
}

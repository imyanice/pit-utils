package me.yanjobs.pitutils;

import club.maxstats.weave.loader.api.ModInitializer;
import club.maxstats.weave.loader.api.command.CommandBus;
import club.maxstats.weave.loader.api.event.EventBus;
import me.yanjobs.pitutils.commands.ConfigCommand;
import me.yanjobs.pitutils.events.ChatEvent;
import me.yanjobs.pitutils.events.KSGlint;
import me.yanjobs.pitutils.utils.Config;

import java.io.IOException;

public class PitUtils implements ModInitializer {
    private static Config config;
    @Override
    public void init() {
        System.out.println("Starting hook... | PitQuickMaths");
        EventBus.subscribe(new ChatEvent());
        CommandBus.register(new ConfigCommand());
        EventBus.subscribe(new KSGlint());
        try {
            config = new Config();
            config.createConfigFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config getConfig() {
        return config;
    }
}

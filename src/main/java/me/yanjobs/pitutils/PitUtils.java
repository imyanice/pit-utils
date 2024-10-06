package me.yanjobs.pitutils;

import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import net.weavemc.loader.api.event.EventBus;
import me.yanjobs.pitutils.commands.ConfigCommand;
import me.yanjobs.pitutils.commands.TargetPlayerCommand;
import me.yanjobs.pitutils.events.ChatEvent;
import me.yanjobs.pitutils.events.KSGlint;
import me.yanjobs.pitutils.utils.Config;

import java.io.IOException;

public class PitUtils implements ModInitializer {
    private static Config config;
    @Override
    public void preInit() {
        System.out.println("Registered PitUtils!");
        EventBus.subscribe(new ChatEvent());
        CommandBus.register(new ConfigCommand());
        CommandBus.register(new TargetPlayerCommand());
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

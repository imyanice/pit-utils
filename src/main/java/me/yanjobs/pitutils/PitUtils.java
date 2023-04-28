package me.yanjobs.pitutils;

import club.maxstats.weave.loader.api.ModInitializer;
import club.maxstats.weave.loader.api.command.CommandBus;
import club.maxstats.weave.loader.api.event.EventBus;
import me.yanjobs.pitutils.commands.HelloCommand;
import me.yanjobs.pitutils.events.ChatEvent;

public class PitUtils implements ModInitializer {
    @Override
    public void init() {
        System.out.println("Starting hook... | PitQuickMaths");
        EventBus.subscribe(new ChatEvent());
        CommandBus.register(new HelloCommand());
    }
}

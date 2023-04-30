package me.yanjobs.pitutils.commands;

import club.maxstats.weave.loader.api.command.Command;

import club.maxstats.weave.loader.api.event.EntityListEvent;
import me.yanjobs.pitutils.utils.AddChatMessage;
import me.yanjobs.pitutils.utils.Config;
import me.yanjobs.pitutils.utils.Config.*;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class ConfigCommand extends Command {

    public boolean isQuickMathsRange(String message) {
        String regex = "^(?:[12]?[0-9]{1,3}|3000)(?:,(?:[12]?[0-9]{1,3}|3000))*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        boolean isMatch = matcher.matches();
        if (isMatch) {
            String[] numbers = message.split(",");
            return Integer.parseInt(numbers[0]) < Integer.parseInt(numbers[2]);
        } else {
            return false;
        }
    };

    public ConfigCommand() {
        super("pitconfig", "pconfig", "pconf", "pitc");
    }
    public void handle(String[] args) {
        if (args.length != 2) {
            AddChatMessage.addErrorMessage("Usage: /pitconfig <option> <value>");
        }

        // Quick Maths section
        if (Objects.equals(args[0], "quickmaths.enabled")) {
            if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
                try {
                    Config.getConfig().setProperty("quickmaths.enabled", args[1].toLowerCase());
                    AddChatMessage.addInfoMessage("Successfully set quickmaths.enabled to " + args[1].toLowerCase());
                } catch (IOException e) {
                    AddChatMessage.addErrorMessage("An error happened while editing the config. Please try again later.");
                    throw new RuntimeException(e);
                }
            } else {
                AddChatMessage.addErrorMessage("The value must be either 'true' or 'false'");
            }

        } else if (Objects.equals(args[0], "quickmaths.range")) {
            if (isQuickMathsRange(args[1])) {
                try {
                    Config.getConfig().setProperty("quickmaths.range", args[1]);
                    AddChatMessage.addInfoMessage("Successfully set quickmaths.range to " + args[1].toLowerCase());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                AddChatMessage.addErrorMessage("The value must be: 'int1,int2' and int2 <= 3000, int1 <= 3000. e.g. 3000,3000");
            }
        } else if (Objects.equals(args[0], "verbose")) {
            if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
                try {
                    Config.getConfig().setProperty("verbose", args[1].toLowerCase());
                    AddChatMessage.addInfoMessage("Successfully set verbose to " + args[1].toLowerCase());
                } catch (IOException e) {
                    AddChatMessage.addErrorMessage("An error happened while editing the config. Please try again later.");
                    throw new RuntimeException(e);
                }
            } else {
                AddChatMessage.addErrorMessage("The value must be either 'true' or 'false'");
            }
        }
    }
}

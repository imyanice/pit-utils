package me.yanjobs.pitutils.commands;

// import net.weavemc.loader.api.command.Command;

import me.yanjobs.pitutils.utils.AddChatMessage;
import net.weavemc.api.command.Command;
import me.yanjobs.pitutils.PitUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Objects;

public class ConfigCommand extends Command {

    public boolean isQuickMathsRange(String message) {
        String regex = "^(?:[12]?[0-9]{1,3}|3000)(?:,(?:[12]?[0-9]{1,3}|3000))*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        boolean isMatch = matcher.matches();
        if (isMatch) {
            String[] numbers = message.split(",");
            return Integer.parseInt(numbers[0]) < Integer.parseInt(numbers[1]);
        } else {
            return false;
        }
    }

    public ConfigCommand() {
        super("pitconfig", "pconfig", "pconf", "pitc");
    }

    public void execute(String[] args) {
        AddChatMessage.addInfoMessage("Made by Yanice, discord.gg/lilith");
        if (args.length != 2) {
            AddChatMessage.addErrorMessage("Usage: /pitconfig <option> <value>");
        } else {
            // Quick Maths section
            if (Objects.equals(args[1], "quickmaths.enabled")) {
                if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                    PitUtils.getConfig().setProperty("quickmaths.enabled", args[2].toLowerCase());
                    AddChatMessage.addInfoMessage("Successfully set quickmaths.enabled to " + args[2].toLowerCase());
                } else {
                    AddChatMessage.addErrorMessage("The value must be either 'true' or 'false'");
                }
            } else if (Objects.equals(args[1], "quickmaths.range")) {
                if (isQuickMathsRange(args[2])) {
                    PitUtils.getConfig().setProperty("quickmaths.range", args[2]);
                    AddChatMessage.addInfoMessage("Successfully set quickmaths.range to " + args[2].toLowerCase());
                } else {
                    AddChatMessage.addErrorMessage(
                            "The value must be: 'int1,int2' and int2 <= 3000, int1 <= 3000. e.g. 3000,3000");
                }
            } else if (Objects.equals(args[1], "verbose")) {
                if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                    PitUtils.getConfig().setProperty("verbose", args[2].toLowerCase());
                    AddChatMessage.addInfoMessage("Successfully set verbose to " + args[2].toLowerCase());
                } else {
                    AddChatMessage.addErrorMessage("The value must be either 'true' or 'false'");
                }
            } else if (Objects.equals(args[1], "glint.enabled")) {
                if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                    PitUtils.getConfig().setProperty("glint.enabled", args[2].toLowerCase());
                    AddChatMessage.addInfoMessage("Successfully set glint.enabled to " + args[2].toLowerCase());
                } else {
                    AddChatMessage.addErrorMessage("The value must be either 'true' or 'false'");
                }
            }
        }
    }
}

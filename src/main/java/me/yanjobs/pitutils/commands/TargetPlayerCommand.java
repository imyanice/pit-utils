package me.yanjobs.pitutils.commands;

import net.weavemc.loader.api.command.Command;
import me.yanjobs.pitutils.PitUtils;
import me.yanjobs.pitutils.utils.AddChatMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TargetPlayerCommand extends Command {
    public TargetPlayerCommand() {
        super("target");
    };

    public static boolean checkIfStringIsPlayer(String usernames) {
        String[] usernameArray = usernames.split(",");
        String regex = "^[a-zA-Z0-9_]{1,16}$";

        for (String username: usernameArray) {
            if (!username.matches(regex)) {
                return false;
            }
        }
        return true;
    }

    public void handle(String[] args) {
        if (args.length == 2) {
            if (args[1].equals("remove")) {
                try {
                    String targetedPlayers = PitUtils.getConfig().getProperty("target.players").toLowerCase();
                    System.out.println(args[0].toLowerCase());
                    System.out.println(args[0].toLowerCase());
                    if (targetedPlayers.contains(args[0].toLowerCase())) {
                        String[] players = targetedPlayers.split(",");
                        List<String> playerList = new ArrayList<>(Arrays.asList(players));
                        playerList.remove(args[0]);
                        String newTargetedPlayers = String.join(",", playerList);
                        PitUtils.getConfig().setProperty("target.players", newTargetedPlayers);
                        AddChatMessage.addInfoMessage("Successfully removed: " + args[0] + " from the targeted players.");
                    } else {
                        AddChatMessage.addErrorMessage("The username you provided isn't targeted.");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                AddChatMessage.addErrorMessage("Usage: /target [ign] [remove]");
            }
        } else if (args.length == 1) {
            if (checkIfStringIsPlayer(args[0])) {
                try {
                    PitUtils.getConfig().setProperty("target.players", PitUtils.getConfig().getProperty("target.players") + "," + args[0]);
                    AddChatMessage.addInfoMessage("Successfully targeting: " + args[0]);
                } catch (IOException e) {

                    throw new RuntimeException(e);
                }
            } else {
                AddChatMessage.addErrorMessage("Please enter correct minecraft usernames separated by a ','.");
            }
        } else if (args.length == 0) {
            try {
                boolean oldValue = Boolean.parseBoolean(PitUtils.getConfig().getProperty("target.enabled"));
                PitUtils.getConfig().setProperty("target.enabled", String.valueOf(!oldValue));
                AddChatMessage.addInfoMessage("Target player now on: " + !oldValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
package me.yanjobs.pitutils.commands;

// import net.weavemc.loader.api.command.Command;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yanjobs.pitutils.PitUtils;
import me.yanjobs.pitutils.utils.AddChatMessage;
import net.weavemc.api.command.Command;

public class TargetPlayerCommand extends Command {
    public TargetPlayerCommand() {
        super("target");
    };

    public static boolean checkIfStringIsPlayer(String usernames) {
        String[] usernameArray = usernames.split(",");
        String regex = "^[a-zA-Z0-9_]{1,16}$";

        for (String username : usernameArray) {
            if (!username.matches(regex)) {
                return false;
            }
        }
        return true;
    }
    
    @Override 
    public void execute(String[] args) {
        AddChatMessage.addInfoMessage(String.join(" ", args));
        switch (args.length) {
            case 1:
                 try {
                    boolean oldValue = Boolean.parseBoolean(PitUtils.getConfig().getProperty("target.enabled"));
                    PitUtils.getConfig().setProperty("target.enabled", String.valueOf(!oldValue));
                    AddChatMessage.addInfoMessage("Target player now on: " + !oldValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                if (checkIfStringIsPlayer(args[1])) {
                    try {
                        PitUtils.getConfig().setProperty("target.players",
                                PitUtils.getConfig().getProperty("target.players") + "," + args[1]);
                        AddChatMessage.addInfoMessage("Successfully targeting: " + args[1]);
                    } catch (IOException e) {

                        throw new RuntimeException(e);
                    }
                } else {
                    AddChatMessage.addErrorMessage("Please enter correct minecraft usernames separated by a ','.");
                }
            break;
            
            case 3:
                if (args[2].equals("remove")) {
                    try {
                        String targetedPlayers = PitUtils.getConfig().getProperty("target.players").toLowerCase();
                        System.out.println(args[1].toLowerCase());
                        System.out.println(args[1].toLowerCase());
                        if (targetedPlayers.contains(args[1].toLowerCase())) {
                            String[] players = targetedPlayers.split(",");
                            List<String> playerList = new ArrayList<>(Arrays.asList(players));
                            playerList.remove(args[1]);
                            String newTargetedPlayers = String.join(",", playerList);
                            PitUtils.getConfig().setProperty("target.players", newTargetedPlayers);
                            AddChatMessage
                                    .addInfoMessage("Successfully removed: " + args[1] + " from the targeted players.");
                        } else {
                            AddChatMessage.addErrorMessage("The username you provided isn't targeted.");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    AddChatMessage.addErrorMessage("Usage: /target [ign] [remove]");
                }
                break;
            default:
                AddChatMessage.addErrorMessage("Usage: /target [ign] [remove]");
        }
        if (args.length == 3) {
            
        } else if (args.length == 2) {
           
        } else if (args.length == 1) {
           
        }
    }
}

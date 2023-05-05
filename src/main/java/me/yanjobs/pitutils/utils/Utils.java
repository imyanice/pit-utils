package me.yanjobs.pitutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {
    public static List<String> getOnlinePlayersByName() {
        final ArrayList<String> players = new ArrayList<>();
        final Collection<NetworkPlayerInfo> playerCollection = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        for (final NetworkPlayerInfo networkPlayerInfo : playerCollection) {
            final String playerName = networkPlayerInfo.getGameProfile().getName();
            if (playerName != null) {
                players.add(playerName);
            }
        }
        return players;
    }
    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }
}

package me.yanjobs.pitutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class AddChatMessage {

    public static void addInfoMessage(@NotNull   String message) {
        ChatComponentText infoLevel = new ChatComponentText(EnumChatFormatting.BLACK + "[" + EnumChatFormatting.DARK_BLUE + "PIT UTILS" + EnumChatFormatting.BLACK + "]" + EnumChatFormatting.WHITE + " - ");
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(infoLevel.text + message));
    }
    public static void addErrorMessage(@NotNull String message) {
        ChatComponentText errorLevel = new ChatComponentText(EnumChatFormatting.BLACK + "[" + EnumChatFormatting.DARK_BLUE + "PIT UTILS" + EnumChatFormatting.BLACK + "]" + EnumChatFormatting.RED + " - ");
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(errorLevel.text + message));
    }
    public static void addVerboseMessage(@NotNull String message) throws IOException {
        if (Objects.equals(Config.getConfig().getProperty("verbose"), "true")) {
            ChatComponentText verboseLevel = new ChatComponentText(EnumChatFormatting.BLACK + "[" + EnumChatFormatting.DARK_BLUE + "PIT UTILS" + EnumChatFormatting.BLACK + "]" + EnumChatFormatting.YELLOW + " - ");
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(verboseLevel.text + message));
        }
       }
}

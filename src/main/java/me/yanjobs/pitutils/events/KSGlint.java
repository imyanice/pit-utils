package me.yanjobs.pitutils.events;

import net.weavemc.loader.api.event.RenderWorldEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import me.yanjobs.pitutils.PitUtils;
import me.yanjobs.pitutils.utils.AddChatMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.util.Color;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static me.yanjobs.pitutils.utils.Utils.getOnlinePlayersByName;
import static me.yanjobs.pitutils.utils.Utils.getPlayerName;

public class KSGlint {
    public static void renderFilledHitbox(final Entity entityIn, final Color color, final boolean translucent, final double partialTicks) {
        final Entity render = Minecraft.getMinecraft().getRenderViewEntity();
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        if (entityIn.ticksExisted == 0) {
            entityIn.lastTickPosX = entityIn.posX;
            entityIn.lastTickPosY = entityIn.posY;
            entityIn.lastTickPosZ = entityIn.posZ;
        }
        final double x = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        final double y = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        final double z = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        final double width = entityIn.width / 2.0f;
        final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + entityIn.height, z + width);
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.disableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.tryBlendFuncSeparate(770, translucent ? 1 : 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableCull();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
    @SubscribeEvent
    public void renderPlayers(final RenderWorldEvent event) throws IOException {
        if (Objects.equals(PitUtils.getConfig().getProperty("target.enabled"), "true")) {
            if (!Objects.equals(PitUtils.getConfig().getProperty("target.players"), "")){
                String targetedPlayers = PitUtils.getConfig().getProperty("target.players");
                for (NetworkPlayerInfo p : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
                    if (targetedPlayers.contains(p.getGameProfile().getName())) {
                        final Color color = new Color(255,255,255, 85);
                        final EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(p.getGameProfile().getName());
                        if (player != null) {
                            GlStateManager.disableDepth();
                            renderFilledHitbox(player, color, false, event.getPartialTicks());
                            GlStateManager.enableDepth();
                            AddChatMessage.addVerboseMessage("Glinted targeted player: " + player.getName());
                        } else {
                            System.out.println("What happened?! Player is null");
                        }
                    }
                }
            }
        }
        if (Objects.equals(PitUtils.getConfig().getProperty("glint.enabled"), "true")) {
            final List<String> players = getOnlinePlayersByName();
            for (String s : players) {
                final EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(s);
                if (Minecraft.getMinecraft().getNetHandler().getPlayerInfo(s) != null && player != null) {
                    if (!Objects.equals(player.getName(), Minecraft.getMinecraft().thePlayer.getName())) {
                        final Color color = this.getColorBasedOnStreak(EnumChatFormatting.getTextWithoutFormattingCodes(getPlayerName(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(s))));
                        if (color != null) {
                            GlStateManager.disableDepth();
                            renderFilledHitbox(player, color, false, event.getPartialTicks());
                            GlStateManager.enableDepth();
                            AddChatMessage.addVerboseMessage("Glinted player: " + player.getName());
                        }
                    }
                }
            }
        }

    }

    public Color getColorBasedOnStreak(final String playerName) {
        if (playerName.endsWith(" HELD")) {
            return new Color(255, 255, 80, 85);
        }
        if (playerName.startsWith("OVRDRV ")) {
            return new Color(255, 80, 80, 85);
        }
        if ((playerName.startsWith("BEAST ") || playerName.endsWith(" BEAST"))) {
            return new Color(80, 255, 80, 85);
        }
        if (playerName.startsWith("HIGH ")) {
            return new Color(255, 180, 0, 85);
        }
        if (playerName.startsWith("HERMIT ")) {
            return new Color(80, 80, 255, 85);
        }
        if (playerName.startsWith("MOON ")) {
            return new Color(80, 255, 255, 85);
        }
        if (playerName.startsWith("UBER")) {
            return new Color(255, 80, 255, 85);
        }
        return null;
    }
}
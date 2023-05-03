package me.yanjobs.pitutils.events;

import club.maxstats.weave.loader.api.event.SubscribeEvent;
import me.yanjobs.pitutils.events.custom.PlayerModelRenderEvent;
import net.minecraft.client.renderer.GlStateManager;

public class GlintPlayer {
    @SubscribeEvent
    public void renderModel(PlayerModelRenderEvent event) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.color(255, 255, 255);
        GlStateManager.popMatrix();
    }
}

package me.yanjobs.pitutils.mixin;

import club.maxstats.weave.loader.api.event.EventBus;
import me.yanjobs.pitutils.events.custom.PlayerModelRenderEvent;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public class RendererLivingEntityMixin {
    @Inject(method = "renderModel", at = @At("HEAD"))
    public void renderModel(EntityLivingBase entity, float f1, float f2, float f3, float f4, float f5, float f6, CallbackInfo ci) {
        EventBus.callEvent(new PlayerModelRenderEvent(entity));
    }
}

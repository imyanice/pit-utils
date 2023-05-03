package me.yanjobs.pitutils.events.custom;

import club.maxstats.weave.loader.api.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class PlayerModelRenderEvent extends Event {
    public EntityLivingBase entityLivingBase;
    public PlayerModelRenderEvent(EntityLivingBase entityLivingBase) {
        this.entityLivingBase = entityLivingBase;
    }
}

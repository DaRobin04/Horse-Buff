package net.F53.HorseBuff.mixin.Server;

import net.minecraft.entity.passive.HappyGhastEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HappyGhastEntity.class)
public interface HappyGhastEntityAccessor {
    @Invoker("setStillTimeout")
    void invokeSetStillTimeout(int stillTimeout);
}

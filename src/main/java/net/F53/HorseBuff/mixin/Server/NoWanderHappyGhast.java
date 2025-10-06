package net.F53.HorseBuff.mixin.Server;

import net.F53.HorseBuff.config.ModConfig;
import net.minecraft.entity.passive.HappyGhastEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HappyGhastEntity.class, priority = 960)
public abstract class NoWanderHappyGhast {

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void noWanderHasPlayerOnTop(CallbackInfo ci) {
        HappyGhastEntity ghast = horsebuff$thiz();
        if (!ghast.getEntityWorld().isClient()) {
            if (ModConfig.getInstance().noGhastWander
                    && ghast.isWearingBodyArmor()
                    && !ghast.hasPassengers()
            ) {
                ((HappyGhastEntityAccessor) ghast).invokeSetStillTimeout(10);
            }
        }
    }

    @Unique
    private HappyGhastEntity horsebuff$thiz() {
        return ((HappyGhastEntity)(Object)this);
    }
}

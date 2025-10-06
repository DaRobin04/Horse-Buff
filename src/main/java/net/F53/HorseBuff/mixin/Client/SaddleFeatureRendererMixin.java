package net.F53.HorseBuff.mixin.Client;

import com.llamalad7.mixinextras.sugar.Local;
import net.F53.HorseBuff.render.entity.model.ExtendedRideableEquippableEntityModel;
import net.F53.HorseBuff.render.entity.state.ExtendedRideableEntityRenderState;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SaddleFeatureRenderer.class)
public abstract class SaddleFeatureRendererMixin {

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V",
            at = @At("HEAD"))
    void updatePlayerPassenger(CallbackInfo callbackInfo,
                               @Local(argsOnly = true) LivingEntityRenderState livingEntityRenderState
                              )
    {
        SaddleFeatureRendererAccessor thisObject = (SaddleFeatureRendererAccessor)(Object)this;
        if(livingEntityRenderState instanceof ExtendedRideableEntityRenderState extendedRideableEntityRenderState) {
            boolean isPlayerPassenger = extendedRideableEntityRenderState.horsebuff$isPlayerPassenger();
            EntityModel entityModel = livingEntityRenderState.baby ? thisObject.getBabyModel() : thisObject.getAdultModel();
            ((ExtendedRideableEquippableEntityModel) entityModel).horsebuff$setPlayerPassenger(isPlayerPassenger);
        }
    }
}

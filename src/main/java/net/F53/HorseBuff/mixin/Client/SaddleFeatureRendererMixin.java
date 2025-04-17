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

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V", shift = At.Shift.AFTER))
    void updatePlayerPassenger(CallbackInfo callbackInfo,
                               @Local(argsOnly = true) LivingEntityRenderState horseEntityRenderState,
                               @Local EntityModel horseEntityModel
                              )
    {
        if(horseEntityRenderState instanceof ExtendedRideableEntityRenderState extendedRideableEntityRenderState) {
            boolean isPlayerPassenger = extendedRideableEntityRenderState.horsebuff$isPlayerPassenger();
            ((ExtendedRideableEquippableEntityModel) horseEntityModel).horsebuff$setPlayerPassenger(isPlayerPassenger);
        }
    }
}

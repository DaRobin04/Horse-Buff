package net.F53.HorseBuff.mixin.Client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.F53.HorseBuff.render.entity.state.ExtendedRideableEntityRenderState;
import net.F53.HorseBuff.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.F53.HorseBuff.utils.RenderUtils.isJeb;
import static net.F53.HorseBuff.utils.RenderUtils.isRideableEntityRenderState;

@Mixin(value = LivingEntityRenderer.class, priority = 960)
public abstract class LivingEntityRendererMixin {

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at = @At("TAIL"))
    void setPlayerPassenger(CallbackInfo ci, @Local(argsOnly = true) LivingEntityRenderState livingEntityRenderState, @Local(argsOnly = true) LivingEntity livingEntity) {
        if (isRideableEntityRenderState(livingEntityRenderState)) {
            boolean playerPassenger = livingEntity.hasPassenger(MinecraftClient.getInstance().player);
            ((ExtendedRideableEntityRenderState) livingEntityRenderState).horsebuff$setId(livingEntity.getId());
            ((ExtendedRideableEntityRenderState) livingEntityRenderState).horsebuff$setPlayerPassenger(playerPassenger);
            if (livingEntity.getCustomName() != null) {
                ((ExtendedRideableEntityRenderState) livingEntityRenderState).horsebuff$setCustomName(livingEntity.getCustomName().getString());
            } else {
                ((ExtendedRideableEntityRenderState) livingEntityRenderState).horsebuff$setCustomName("");
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At("HEAD"))
    void setAlpha(CallbackInfo ci, @Local(argsOnly = true) LivingEntityRenderState livingEntityRenderState, @Share("alpha") LocalIntRef alpha) {
        if (livingEntityRenderState instanceof ExtendedRideableEntityRenderState extendedRideableEntityRenderState) {
            alpha.set(RenderUtils.getAlpha(extendedRideableEntityRenderState.horsebuff$isPlayerPassenger()));
        }
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/ColorHelper;mix(II)I"),
            index = 1)
    int setColorForRender(int color, @Local(argsOnly = true) LivingEntityRenderState livingEntityRenderState, @Share("alpha") LocalIntRef alpha)
    {
        if (isRideableEntityRenderState(livingEntityRenderState)) {
            if (isJeb(livingEntityRenderState) && livingEntityRenderState instanceof ExtendedRideableEntityRenderState extendedRideableEntityRenderState) {
                // see net/minecraft/client/render/entity/feature/SheepWoolFeatureRenderer
                int ageId = MathHelper.floor(livingEntityRenderState.age) / 25 + extendedRideableEntityRenderState.horsebuff$getId();
                int numDyes = DyeColor.values().length;
                int currentDye = ageId % numDyes;
                int nextDye = (ageId + 1) % numDyes;
                float dyeTransitionProgress = ((float) (MathHelper.floor(livingEntityRenderState.age) % 25) + MathHelper.fractionalPart(livingEntityRenderState.age)) / 25.0F;

                int rgb1 = DyeColor.byIndex(currentDye).getEntityColor();
                int rgb2 = DyeColor.byIndex(nextDye).getEntityColor();

                float r = ColorHelper.getRed(rgb1) * (1 - dyeTransitionProgress) + ColorHelper.getRed(rgb2) * dyeTransitionProgress;
                float g = ColorHelper.getGreen(rgb1) * (1 - dyeTransitionProgress) + ColorHelper.getGreen(rgb2) * dyeTransitionProgress;
                float b = ColorHelper.getBlue(rgb1) * (1 - dyeTransitionProgress) + ColorHelper.getBlue(rgb2) * dyeTransitionProgress;

                // increase brightness by a bit because the horse texture is a bit dark
                color = ColorHelper.getArgb((int) Math.min(Math.floor(r) * 2, 255),
                        (int) Math.min(Math.floor(g) * 2, 255),
                        (int) Math.min(Math.floor(b) * 2, 255));
            }
            return ColorHelper.withAlpha(alpha.get(), color);
        } else {
            return color;
        }
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getRenderLayer(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;ZZZ)Lnet/minecraft/client/render/RenderLayer;"),
            index = 2)
    boolean makeRenderLayerTranslucent(boolean translucent, @Local(argsOnly = true) LivingEntityRenderState livingEntityRenderState, @Share("alpha") LocalIntRef alphaRef) {
        if (translucent) return true;
        if (isRideableEntityRenderState(livingEntityRenderState))
            return alphaRef.get() != 255;
        return false;
    }
}

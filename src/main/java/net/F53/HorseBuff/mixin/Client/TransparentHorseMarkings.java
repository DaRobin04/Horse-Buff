package net.F53.HorseBuff.mixin.Client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.F53.HorseBuff.render.entity.state.ExtendedRideableEntityRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.HorseMarkingFeatureRenderer;
import net.minecraft.client.render.entity.state.HorseEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static net.F53.HorseBuff.utils.RenderUtils.getAlpha;

@Mixin(value = HorseMarkingFeatureRenderer.class, priority = 960)
public abstract class TransparentHorseMarkings {

    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/HorseEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityTranslucent(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    RenderLayer makeRenderLayerTranslucent(Identifier texture, Operation<RenderLayer> original, @Local(argsOnly = true) HorseEntityRenderState horseEntityRenderState, @Share("alpha") LocalIntRef alpha) {
        alpha.set(getAlpha(((ExtendedRideableEntityRenderState) horseEntityRenderState).horsebuff$isPlayerPassenger()));
        if (alpha.get() == 255) return original.call(texture);
        return RenderLayer.getItemEntityTranslucentCull(texture);
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/HorseEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/RenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"),
            index = 6)
    int setOpacityForRender(int color, @Local(argsOnly = true) HorseEntityRenderState horseEntityRenderState, @Share("alpha") LocalIntRef alpha) {
        if (horseEntityRenderState instanceof ExtendedRideableEntityRenderState) {
            return ColorHelper.withAlpha(Math.min(Math.max(0, ColorHelper.getAlpha(color)), alpha.get()), color);
        }
        return color;
    }
}

package net.F53.HorseBuff.utils;

import net.F53.HorseBuff.config.ModConfig;
import net.F53.HorseBuff.render.entity.state.ExtendedRideableEntityRenderState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.state.*;

public class RenderUtils {
    public static boolean isJeb(LivingEntityRenderState entityRenderState) {
        if (entityRenderState instanceof ExtendedRideableEntityRenderState extendedRideableEntityRenderState)
        {
            return ModConfig.getInstance().jeb_Horses && extendedRideableEntityRenderState.horsebuff$getCustomName() != null && "jeb_".equals(extendedRideableEntityRenderState.horsebuff$getCustomName());
        }
        return(false);
    }


    public static int getAlpha(boolean isPlayerPassenger) {
        ModConfig.FadeConfig pitchFadeConfig = ModConfig.getInstance().pitchFade;
        if (!pitchFadeConfig.enabled) return 255;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.options.getPerspective().isFirstPerson() || !isPlayerPassenger)
            return 255;

        int minAlpha = 255 - Math.round(pitchFadeConfig.maxTransparency * 2.25f);
        int rate = (255 - minAlpha) / (pitchFadeConfig.startAngle - pitchFadeConfig.endAngle);
        int unclampedAlpha = Math.round(rate * (client.player.renderPitch - pitchFadeConfig.endAngle));

        return Math.min(Math.max(unclampedAlpha, minAlpha), 255);
    }

    public static boolean isRideableEntityRenderState(LivingEntityRenderState livingEntityRenderState) {
        return livingEntityRenderState instanceof LivingHorseEntityRenderState ||
                livingEntityRenderState instanceof LlamaEntityRenderState ||
                livingEntityRenderState instanceof CamelEntityRenderState ||
                livingEntityRenderState instanceof PigEntityRenderState ||
                livingEntityRenderState instanceof StriderEntityRenderState ||
                livingEntityRenderState instanceof HappyGhastEntityRenderState;
    }
}

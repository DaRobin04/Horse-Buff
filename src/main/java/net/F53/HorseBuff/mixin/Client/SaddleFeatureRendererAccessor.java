package net.F53.HorseBuff.mixin.Client;

import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SaddleFeatureRenderer.class)
public interface SaddleFeatureRendererAccessor {
    @Accessor
    EntityModel getBabyModel();
    @Accessor
    EntityModel getAdultModel();
}

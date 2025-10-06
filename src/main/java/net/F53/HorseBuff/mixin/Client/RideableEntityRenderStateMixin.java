package net.F53.HorseBuff.mixin.Client;

import net.F53.HorseBuff.render.entity.state.ExtendedRideableEntityRenderState;
import net.minecraft.client.render.entity.state.CamelEntityRenderState;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;
import net.minecraft.client.render.entity.state.LlamaEntityRenderState;
import net.minecraft.client.render.entity.state.PigEntityRenderState;
import net.minecraft.client.render.entity.state.StriderEntityRenderState;
import net.minecraft.client.render.entity.state.HappyGhastEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Additional fields for:
// horses, donkeys, mules, undead horses, skeleton horses (LivingHorseEntityRenderState)
// llamas and camels
@Mixin(value = {LivingHorseEntityRenderState.class, LlamaEntityRenderState.class, CamelEntityRenderState.class, PigEntityRenderState.class, StriderEntityRenderState.class, HappyGhastEntityRenderState.class}, priority = 960)
public abstract class RideableEntityRenderStateMixin implements ExtendedRideableEntityRenderState {

    @Unique
    private int horsebuff$id;

    @Unique
    private boolean horsebuff$playerPassenger;

    @Unique
    private String horsebuff$customName;

    @Override
    public void horsebuff$setId(int id) {
        this.horsebuff$id = id;
    }

    @Override
    public int horsebuff$getId() {
        return this.horsebuff$id;
    }

    @Override
    public void horsebuff$setPlayerPassenger(boolean playerPassenger) {
        this.horsebuff$playerPassenger = playerPassenger;
    }

    @Override
    public boolean horsebuff$isPlayerPassenger() {
        return horsebuff$playerPassenger;
    }

    @Override
    public void horsebuff$setCustomName(String customName) { this.horsebuff$customName = customName; }

    @Override
    public String horsebuff$getCustomName() { return horsebuff$customName; }
}

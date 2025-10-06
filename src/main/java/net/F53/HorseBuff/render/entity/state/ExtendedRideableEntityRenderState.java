package net.F53.HorseBuff.render.entity.state;

public interface ExtendedRideableEntityRenderState {
    void horsebuff$setId(int id);
    int horsebuff$getId();
    void horsebuff$setPlayerPassenger(boolean playerPassenger);
    boolean horsebuff$isPlayerPassenger();
    void horsebuff$setCustomName(String customeName);
    String horsebuff$getCustomName();
}

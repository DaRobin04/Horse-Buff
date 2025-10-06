package net.F53.HorseBuff;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientInit implements ClientModInitializer {
    //private static final KeyBinding.Category HORSEBUFF_CATEGORY = KeyBinding.Category.create(Identifier.of("text", "HorseBuff"));
    //public static KeyBinding horsePlayerInventory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
    //        "text.HorseBuff.keybinding.horsePlayerInventory",
    //        InputUtil.Type.KEYSYM,
    //        GLFW.GLFW_KEY_LEFT_ALT,
    //        HORSEBUFF_CATEGORY
    //));

    @Override
    public void onInitializeClient() {}
}

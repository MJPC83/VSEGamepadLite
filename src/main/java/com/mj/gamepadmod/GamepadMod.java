package com.mj.gamepadmod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// Main mod class
@Mod(GamepadMod.MOD_ID)
public class GamepadMod {
    public static final String MOD_ID = "gamepadmod";

    private static final Logger LOGGER = LogUtils.getLogger();
    private final GamepadHandler gamepadHandler;

    public GamepadMod() {
        // Initialize Gamepad Handler
        gamepadHandler = new GamepadHandler();

        // Register lifecycle event listeners
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves to listen for other game events
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Gamepad Mod is setting up!");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            gamepadHandler.update(); // Update gamepad input
        }
    }
}

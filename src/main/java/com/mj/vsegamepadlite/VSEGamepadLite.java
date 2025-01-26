package com.mj.vsegamepadlite;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(VSEGamepadLite.MOD_ID)
public class VSEGamepadLite {
    public static final String MOD_ID = "vsegamepadlite";

    private static final Logger LOGGER = LogUtils.getLogger();
    private final GamepadHandler gamepadHandler;

    public VSEGamepadLite() {
        // Initialize Gamepad Handler
        gamepadHandler = new GamepadHandler();

        // Register the common config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        // Register lifecycle event listeners
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves to listen for other game events
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("VSEGamepad lite is setting up!");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            gamepadHandler.update(); // Update gamepad input
        }
    }
}

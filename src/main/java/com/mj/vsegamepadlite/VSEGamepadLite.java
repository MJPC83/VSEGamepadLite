package com.mj.vsegamepadlite;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(VSEGamepadLite.MOD_ID)
public class VSEGamepadLite {

    public static final String MOD_ID = "vsegamepadlite";

    private static GamepadHandler gamepadHandler;

    public VSEGamepadLite() {
        if (FMLEnvironment.dist == Dist.CLIENT) {

            gamepadHandler = new GamepadHandler();

            // Register the common config
            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);


            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            gamepadHandler.update(); // Update gamepad input
        }
    }
}

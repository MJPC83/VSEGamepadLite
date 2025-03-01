package com.mj.vsegamepadlite;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {

    public static final ForgeConfigSpec COMMON_SPEC;
    private static final Map<ModConfig.Type, ForgeConfigSpec> CONFIGS = new HashMap<>();

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON_SPEC = buildConfig(builder);
        CONFIGS.put(ModConfig.Type.COMMON, COMMON_SPEC);
    }

    public static void register() {
        for (Map.Entry<ModConfig.Type, ForgeConfigSpec> entry : CONFIGS.entrySet()) {
            ForgeConfigRegistry.INSTANCE.register(VSEGamepadLite.MOD_ID, entry.getKey(), entry.getValue());
        }
    }

    private static ForgeConfigSpec buildConfig(ForgeConfigSpec.Builder builder) {
        builder.push("General");

        ENABLE_GAMEPAD = builder
                .comment("Enable or disable the Gamepad for ship control")
                .define("enableGamepad", true);

        SELECTED_GAMEPAD = builder
                .comment("Select the gamepad number to take input from")
                .defineInRange("selectedGamepad", 1, 1, 4);

        builder.pop();
        return builder.build();
    }

    public static ForgeConfigSpec.BooleanValue ENABLE_GAMEPAD;
    public static ForgeConfigSpec.IntValue SELECTED_GAMEPAD;

}
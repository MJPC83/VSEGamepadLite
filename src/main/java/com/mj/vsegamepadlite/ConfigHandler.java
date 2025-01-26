package com.mj.vsegamepadlite;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new CommonConfig(builder);
        COMMON_SPEC = builder.build();
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.BooleanValue enableGamepad;
        public final ForgeConfigSpec.IntValue selectedGamepad;


        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            enableGamepad = builder
                    .comment("Enable or disable the Gamepad for ship control")
                    .define("enableGamepad", true); // Default is true (enabled)

            // Select which gamepad to use (1 to 4)
            selectedGamepad = builder
                    .comment("Select the gamepad number to take input from (1-4)")
                    .defineInRange("selectedGamepad", 1, 1, 4); // Default: 1, range between 1 and 4

            builder.pop();
        }
    }
}

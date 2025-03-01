package com.mj.vsegamepadlite;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VSEGamepadLite implements ClientModInitializer {

	public static final String MOD_ID = "vsegamepadlite";

	private static GamepadHandler gamepadHandler;

	public static final Logger LOGGER = LoggerFactory.getLogger(VSEGamepadLite.MOD_ID);


	@Override
	public void onInitializeClient() {

		ConfigHandler.register();

		//update gamepad input
		gamepadHandler = new GamepadHandler();
		ClientTickEvents.END_CLIENT_TICK.register(client -> gamepadHandler.update());

		LOGGER.info("VSEGamepad lite is setting up!");
	}
}

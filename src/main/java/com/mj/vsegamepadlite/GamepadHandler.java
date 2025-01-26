package com.mj.vsegamepadlite;

import com.mj.vsegamepadlite.ConfigHandler;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.system.MemoryStack;

public class GamepadHandler {
    private final Minecraft minecraft;

    public GamepadHandler() {
        this.minecraft = Minecraft.getInstance();
    }

    public void update() {
        // Check the config option before running gamepad logic
        if (!ConfigHandler.COMMON.enableGamepad.get()) {
            return; // If the mod is disabled, exit the method early
        }

        long window = minecraft.getWindow().getWindow(); // Get the game window handle
        int selectedGamepad = ConfigHandler.COMMON.selectedGamepad.get() - 1; // Adjust to 0-based index

        for (int i = GLFW.GLFW_JOYSTICK_1; i <= GLFW.GLFW_JOYSTICK_LAST; i++) {
            // Only process the selected gamepad
            if (i == selectedGamepad + GLFW.GLFW_JOYSTICK_1 && GLFW.glfwJoystickPresent(i) && GLFW.glfwJoystickIsGamepad(i)) {
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    GLFWGamepadState state = GLFWGamepadState.mallocStack(stack);

                    if (GLFW.glfwGetGamepadState(i, state)) {
                        // Map left joystick to WASD
                        float xAxis = state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_X); // X-axis
                        float yAxis = state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y); // Y-axis

                        minecraft.options.keyLeft.setDown(xAxis < -0.5f); // Left
                        minecraft.options.keyRight.setDown(xAxis > 0.5f); // Right
                        minecraft.options.keyUp.setDown(yAxis < -0.5f); // Forward
                        minecraft.options.keyDown.setDown(yAxis > 0.5f); // Backward

                        // Map Button A (Button 0) to Jump
                        boolean jump = state.buttons(GLFW.GLFW_GAMEPAD_BUTTON_A) == GLFW.GLFW_PRESS;
                        minecraft.options.keyJump.setDown(jump);
                    }
                }
            }
        }
    }
}

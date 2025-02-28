package com.mj.vsegamepadlite;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.system.MemoryStack;

public class GamepadHandler {

    private final MinecraftClient minecraft;

    public GamepadHandler() {
        this.minecraft = MinecraftClient.getInstance();
    }


    public void update() {

        // Check the config option before running gamepad logic
        if (!ConfigHandler.ENABLE_GAMEPAD.get()){
            return; // If gamepad is disabled, stop execution
        }

        long window = minecraft.getWindow().getHandle();
        int selectedGamepad = ConfigHandler.SELECTED_GAMEPAD.get() - 1;

        for (int i = GLFW.GLFW_JOYSTICK_1; i <= GLFW.GLFW_JOYSTICK_LAST; i++) {
            // Only process the selected gamepad
            if (i == selectedGamepad + GLFW.GLFW_JOYSTICK_1 && GLFW.glfwJoystickPresent(i) && GLFW.glfwJoystickIsGamepad(i)) {
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    GLFWGamepadState state = GLFWGamepadState.mallocStack(stack);

                    if (GLFW.glfwGetGamepadState(i, state)) {
                        // Map left joystick to WASD
                        float xAxis = state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_X); // X-axis
                        float yAxis = state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y); // Y-axis

                        minecraft.options.leftKey.setPressed(xAxis < -0.5f); // Left
                        minecraft.options.rightKey.setPressed(xAxis > 0.5f); // Right
                        minecraft.options.forwardKey.setPressed(yAxis < -0.5f); // Forward
                        minecraft.options.backKey.setPressed(yAxis > 0.5f); // Backward

                        // Map Button A (Button 0) to Jump
                        boolean jump = state.buttons(GLFW.GLFW_GAMEPAD_BUTTON_A) == GLFW.GLFW_PRESS;
                        minecraft.options.jumpKey.setPressed(jump);
                    }
                }
            }
        }
    }
}
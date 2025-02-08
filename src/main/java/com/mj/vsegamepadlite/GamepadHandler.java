package com.mj.vsegamepadlite;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class GamepadHandler {
    private final Minecraft minecraft;

    public GamepadHandler() {
        this.minecraft = Minecraft.getInstance();
    }

    public void update() {
        if (!ConfigHandler.COMMON.enableGamepad.get()) {
            return; // Exit if gamepad is disabled
        }

        int selectedGamepad = ConfigHandler.COMMON.selectedGamepad.get() - 1;
        for (int i = GLFW.GLFW_JOYSTICK_1; i <= GLFW.GLFW_JOYSTICK_LAST; i++) {
            if (i == selectedGamepad + GLFW.GLFW_JOYSTICK_1 && GLFW.glfwJoystickPresent(i)) {
                if (GLFW.glfwJoystickIsGamepad(i)) {
                    handleGamepad(i);
                } else {
                    handleJoystickFallback(i);
                }
            }
        }
    }

    private void handleGamepad(int gamepadId) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            GLFWGamepadState state = GLFWGamepadState.malloc(stack);

            if (GLFW.glfwGetGamepadState(gamepadId, state)) {
                float xAxis = applyDeadZone(state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_X));
                float yAxis = applyDeadZone(state.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y));

                minecraft.options.keyLeft.setDown(xAxis < -0.5f);
                minecraft.options.keyRight.setDown(xAxis > 0.5f);
                minecraft.options.keyUp.setDown(yAxis < -0.5f);
                minecraft.options.keyDown.setDown(yAxis > 0.5f);

                boolean jump = state.buttons(GLFW.GLFW_GAMEPAD_BUTTON_A) == GLFW.GLFW_PRESS;
                minecraft.options.keyJump.setDown(jump);
            }
        }
    }


    // This is required in 1.20.1 due to change in GLFW function

    private void handleJoystickFallback(int joystickId) {
        FloatBuffer axesBuffer = GLFW.glfwGetJoystickAxes(joystickId);
        if (axesBuffer != null && axesBuffer.limit() > 1) {
            float xAxis = applyDeadZone(axesBuffer.get(0));
            float yAxis = applyDeadZone(axesBuffer.get(1));

            minecraft.options.keyLeft.setDown(xAxis < -0.5f);
            minecraft.options.keyRight.setDown(xAxis > 0.5f);
            minecraft.options.keyUp.setDown(yAxis < -0.5f);
            minecraft.options.keyDown.setDown(yAxis > 0.5f);
        }

        ByteBuffer buttons = GLFW.glfwGetJoystickButtons(joystickId);
        if (buttons != null && buttons.limit() > 2) {
            boolean jump = buttons.get(2) == GLFW.GLFW_PRESS;
            minecraft.options.keyJump.setDown(jump);
        }
    }

    private float applyDeadZone(float value) {
        return Math.abs(value) < 0.2f ? 0.0f : value; // Ignore small movements
    }
}

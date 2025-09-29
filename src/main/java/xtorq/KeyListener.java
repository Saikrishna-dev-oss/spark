package xtorq;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    // 1. The single static instance
    private static KeyListener instance;
    private boolean[] keyPressed = new boolean[350];

    // 2. The constructor MUST be private
    private KeyListener() {
        // Constructor is empty and private
    }

    // 3. The get() method creates the instance only once
    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }
        return KeyListener.instance;
    }

    // This is the callback method passed to GLFW
    public static void keyCallBack(long window, int key, int scancode, int action, int mods) {
        // Access methods/fields via the singleton instance
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    // This is the public method to check a key's state
    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }
}
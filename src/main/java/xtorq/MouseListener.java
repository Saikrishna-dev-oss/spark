package xtorq;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    // 1. The single static instance of the MouseListener. Initialized to null.
    private static MouseListener instance;

    // Member variables for mouse state
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private final boolean[] mouseButtonPressed = new boolean[3]; // Max 3 mouse buttons
    private boolean isDragging;

    // 2. The constructor MUST be private.
    //    It should ONLY initialize the member variables, NOT call 'get()'.
    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
        this.isDragging = false;
        // The boolean array 'mouseButtonPressed' is already initialized to false by default
    }

    // 3. The public static 'get()' method is the ONLY way to access the instance.
    //    It creates the instance ONLY if it doesn't already exist.
    public static MouseListener get() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener(); // Instance is created here, and ONLY here.
        }
        return MouseListener.instance;
    }

    // --- Callback Methods (called by GLFW) ---
    public static void mousePosCallback(long window, double xpos, double ypos) {
        // Access the singleton instance to update its state
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;

        // Update isDragging state based on whether any button is held down
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        // Access the singleton instance to update its state
        if (button < get().mouseButtonPressed.length) { // Check for valid button index
            if (action == GLFW_PRESS) {
                get().mouseButtonPressed[button] = true;
            } else if (action == GLFW_RELEASE) {
                get().mouseButtonPressed[button] = false;
                // If all buttons are released, ensure isDragging is false
                if (!(get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2])) {
                    get().isDragging = false;
                }
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        // Access the singleton instance to update its state
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    // --- Public Getter Methods to query mouse state ---
    public static void endFrame() {
        // Reset scroll values at the end of each frame
        get().scrollX = 0;
        get().scrollY = 0;
        // lastX and lastY are updated in mousePosCallback, no need to update here for DX/DY calculation
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    // Calculates the change in X position since the last frame
    public static float getDx() {
        return (float) (get().xPos - get().lastX); // Current minus last for positive movement right
    }

    // Calculates the change in Y position since the last frame
    public static float getDy() {
        return (float) (get().yPos - get().lastY); // Current minus last for positive movement down
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        }
        return false; // Return false for invalid button indices
    }
}
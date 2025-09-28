
package xtorq;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private double scrollX, scrollY;
    private double xPos,yPos;
    private double lastX, lastY;
    private boolean []mousebuttonclicked = new boolean[3];
    private boolean isDragging;

    private static MouseListener instance= new MouseListener();
    public static MouseListener get() {
        return instance;
    }

    public MouseListener() {
        this.scrollX = 0;
        this.scrollY = 0;
        this.xPos = 0;
        this.yPos = 0;
//        get get().mousebclicked[0]||get().mousebclicked[1];
        this.isDragging = false;
    }
    public static void mousePosCallback(long window, double xPos, double yPos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xPos;
        get().yPos = yPos;
        get().isDragging = get().mousebuttonclicked[0]||get().mousebuttonclicked[1]||get().mousebuttonclicked[2];
    }
    public static void mouseButtonCallback(long window,int button,int action,int mod) {
        if(action==GLFW_PRESS) {
            get().mousebuttonclicked[button] = true;
        } else if (action==GLFW_RELEASE) {
            get().mousebuttonclicked[button] = false;
            get().isDragging = false;
        }

    }
    public static void mouseScrollCallback(long window,double Xoffset,double Yoffset) {
        get().scrollX += Xoffset;
        get().scrollY += Yoffset;
    }
    public static void endFrame() {
        get().scrollX =0;
        get().scrollY =0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }
    public static float getX() {
        return (float)get().xPos;
    }
    public static float getY() {
        return (float)get().yPos;
    }
    public static float getDX() {
        return (float)(get().xPos - get().lastX);
    }
    public static float getDY() {
        return (float)(get().yPos - get().lastY);
    }
    public static float getScrollX() {
        return (float)get().scrollX;
    }
    public static float getScrollY() {
        return (float)get().scrollY;
    }

    public static void setInstance(MouseListener instance) {
        MouseListener.instance = instance;
    }

    public boolean isDragging(){
        return this.isDragging;
    }
    public boolean mouseButtonDown(int button) {
        if(button< get().mousebuttonclicked.length) {
            return this.mousebuttonclicked[button];
        }
        else  {
            return false;
        }
    }

}

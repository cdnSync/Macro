package fr.rader.srmacro.inputs;

import java.awt.*;
import java.awt.event.InputEvent;

public class Mouse {

    private Robot robot;

    public static int LEFT_BUTTON = InputEvent.BUTTON1_DOWN_MASK;
    public static int MIDDLE_BUTTON = InputEvent.BUTTON2_DOWN_MASK;
    public static int RIGHT_BUTTON = InputEvent.BUTTON3_DOWN_MASK;

    public Mouse(Robot robot) {
        this.robot = robot;
    }

    /**
     * Move the mouse to a new position
     * @param x New mouse X
     * @param y New mouse Y
     */
    public void move(int x, int y) {
        int newX = verifyPosition(x, getDisplayWidth());
        int newY = verifyPosition(y, getDisplayHeight());

        robot.mouseMove(newX, newY);
    }

    /**
     * Move the mouse relative to it's current position
     * @param dx Direction in the X axis
     * @param dy Direction in the Y axis
     */
    public void moveRelative(int dx, int dy) {
        move(getMouseX() + dx, getMouseY() + dy);
    }

    public void press(int mouseButton) {
        robot.mousePress(mouseButton);
    }

    public void release(int mouseButton) {
        robot.mouseRelease(mouseButton);
    }

    private int verifyPosition(int position, int max) {
        if(position < 0) position = 0;
        if(position > getDisplayWidth()) position = max - 1;

        return position;
    }

    public int getMouseX() {
        return (int) MouseInfo.getPointerInfo().getLocation().getX();
    }

    public int getMouseY() {
        return (int) MouseInfo.getPointerInfo().getLocation().getY();
    }

    private int getDisplayWidth() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
    }

    private int getDisplayHeight() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
    }
}

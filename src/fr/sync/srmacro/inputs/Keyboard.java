package fr.rader.srmacro.inputs;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Keyboard {

    private Robot robot;

    private boolean isHolding = false;

    public Keyboard(Robot robot) {
        this.robot = robot;
    }

    public void write(String string) {
        for(char character : string.toCharArray()) {
            boolean isUpperCase = Character.isUpperCase(character);

            if(isUpperCase) robot.keyPress(KeyEvent.VK_SHIFT);
            write(validateCharacter(character));
            if(isUpperCase) robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }

    /**
     * Holds a {@code character} for a {@code duration} amount of time
     * @param character The character to hold
     * @param duration The duration of the hold (in milliseconds)
     */
    public void hold(char character, int duration) {
        if(duration < 0) throw new IllegalArgumentException("duration must be a positive number");

        int keyCode = validateCharacter(character);

        isHolding = true;
        Thread thread = new Thread(() -> {
            try {
                robot.keyPress(keyCode);
                Thread.sleep(500);

                while(isHolding) {
                    robot.keyPress(keyCode);

                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        thread.start();

        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }

        robot.keyRelease(keyCode);
        isHolding = false;
    }

    /**
     * Write a key based on it's key code
     * @param keyCode Key code of the key to write.
     */
    public void write(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    private static int validateCharacter(char character) {
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);
        if(keyCode == KeyEvent.CHAR_UNDEFINED) {
            throw new RuntimeException("No key code found for character '" + character + "'");
        }

        return keyCode;
    }
}

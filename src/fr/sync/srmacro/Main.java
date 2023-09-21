package fr.sync.srmacro;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {

    private static Main instance;

    private Robot robot;

    private void start() {
        File file = IO.openFilePrompt("Macro file", null, ".txt");

        MacroReader reader = new MacroReader(file, robot);

        try {
            reader.startMacro();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        instance = this;

        try {
            robot = new Robot();

            start();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public Robot getRobot() {
        return robot;
    }
}

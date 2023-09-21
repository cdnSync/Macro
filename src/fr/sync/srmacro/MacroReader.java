package fr.sync.srmacro;

import fr.sync.srmacro.inputs.Keyboard;
import fr.sync.srmacro.inputs.Mouse;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MacroReader {

    private File macroFile;

    private Keyboard keyboard;
    private Mouse mouse;

    private ArrayList<String> doCommandList = new ArrayList<>();

    public MacroReader(File file, Robot robot) {
        this.macroFile = file;
        this.keyboard = new Keyboard(robot);
        this.mouse = new Mouse(robot);
    }

    public void startMacro() throws IOException {
        FileReader fileReader = new FileReader(macroFile);
        BufferedReader reader = new BufferedReader(fileReader);

        int repeat = 0;
        boolean isDo = false;
        String line;
        while((line = reader.readLine()) != null) {
            line = line.trim();

            if(line.isEmpty()) continue;
            if(line.startsWith("//")) continue;

            if(line.startsWith("do")) {
                repeat = Integer.parseInt(line.split(" ")[1]);
                isDo = true;
                continue;
            }

            if(line.equals(";")) {
                isDo = false;

                for(int i = 0; i < repeat; i++) {
                    for(String command : doCommandList) {
                        executeCommand(command);
                    }
                }

                doCommandList.clear();
                continue;
            }

            if(isDo) {
                doCommandList.add(line);
                continue;
            }

            executeCommand(line);
        }

        reader.close();
        fileReader.close();
    }

    private void executeCommand(String line) {
        String[] command = line.split("\"");

        String[] commandWithExecution = command[0].split(" ");
        switch(commandWithExecution[0].toLowerCase()) {
            case "keyboard":
                switch(commandWithExecution[1].toLowerCase()) {
                    case "write":
                        keyboard.write(command[1]);
                        break;
                    case "writeln":
                        keyboard.write(command[1] + "\n");
                        break;
                    case "press":
                        keyboard.write(commandWithExecution[2]);
                        break;
                    case "hold":
                        keyboard.hold(commandWithExecution[2].toCharArray()[0], Integer.parseInt(commandWithExecution[3]));
                        break;
                }
                break;
            case "mouse":
                switch(commandWithExecution[1].toLowerCase()) {
                    case "move":
                        if(commandWithExecution[2].equalsIgnoreCase("relative")) {
                            mouse.moveRelative(Integer.parseInt(commandWithExecution[3]), Integer.parseInt(commandWithExecution[4]));
                        } else {
                            mouse.move(Integer.parseInt(commandWithExecution[2]), Integer.parseInt(commandWithExecution[3]));
                        }
                        break;
                    case "press":
                        switch(commandWithExecution[2].toLowerCase()) {
                            case "left_button":
                                mouse.press(Mouse.LEFT_BUTTON);
                                break;
                            case "middle_button":
                                mouse.press(Mouse.MIDDLE_BUTTON);
                                break;
                            case "right_button":
                                mouse.press(Mouse.RIGHT_BUTTON);
                                break;
                        }
                        break;
                    case "release":
                        if(commandWithExecution[2].equalsIgnoreCase("all")) {
                            mouse.release(Mouse.LEFT_BUTTON);
                            mouse.release(Mouse.MIDDLE_BUTTON);
                            mouse.release(Mouse.RIGHT_BUTTON);
                        } else {
                            switch(commandWithExecution[2].toLowerCase()) {
                                case "left_button":
                                    mouse.release(Mouse.LEFT_BUTTON);
                                    break;
                                case "middle_button":
                                    mouse.release(Mouse.MIDDLE_BUTTON);
                                    break;
                                case "right_button":
                                    mouse.release(Mouse.RIGHT_BUTTON);
                                    break;
                            }
                        }
                        break;
                }
                break;
            case "wait":
                try {
                    Thread.sleep(Integer.parseInt(commandWithExecution[1]));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Unknown command: " + commandWithExecution[0]);
        }
    }
}

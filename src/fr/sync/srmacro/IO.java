package fr.sync.srmacro;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class IO {

    public static File openFilePrompt(String description, String path, String... extensions) {
        JFileChooser fileChooser = new JFileChooser(path);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                for(String extension : extensions) {
                    if(file.getName().endsWith(extension)) return true;
                }

                return file.isDirectory();
            }

            @Override
            public String getDescription() {
                String finalDescription = description + " (";

                for(int i = 0; i < extensions.length; i++) {
                    finalDescription += "*." + extensions[i] + ((i < extensions.length - 1) ? ", " : ")");
                }

                return finalDescription;
            }
        });

        int option = fileChooser.showOpenDialog(null);

        if(option == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }
}

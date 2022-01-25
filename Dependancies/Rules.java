package Dependancies;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
 
public class Rules {
    public static void main(String[] args) {
        displayRules();
    }

    /**
     * A method to display a JFrame showing the rules of gameplay
     * No precodnitions
     * creates a window with the rules
     */
    public static void displayRules() {
        JFrame rulesFrame = new JFrame("How To Play");

        JLabel rulesLabel = new JLabel(getRules(), SwingConstants.CENTER);

        rulesFrame.add(rulesLabel);

        rulesFrame.setPreferredSize(new Dimension(600, 800));
        rulesFrame.setMinimumSize(new Dimension(600, 800));
        // rulesFrame.setResizable(false);
        rulesFrame.setAlwaysOnTop(true);
        rulesFrame.pack();
        rulesFrame.setVisible(true);
    }

    /**
     * A method to read the contents of the rules.html file
     * No preconditions
     * @return a string with the contents of the rules.html file
     */
    public static String getRules() {
        String filePath = "dependancies/rules.html";
 
        String rules = "";
        try {
            rules = readFile(filePath, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
 
        // System.out.println(rules);
        return rules;
    }

    /**
     * A method to read all characters of a file using the readAllBytes method of Files class
     * @param path the path of the file
     * @param encoding the character set used, usually UTF-8
     * @return a string object containing the contents of the file at path
     * @throws IOException
     */
    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
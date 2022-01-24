package Dependancies;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
 
public class Rules {
    public static void main(String[] args) {
        displayRules();
    }
    public static void displayRules() {
        JFrame rulesFrame = new JFrame("How To Play");

        JLabel rulesLabel = new JLabel(getRules(), SwingConstants.CENTER);

        rulesFrame.add(rulesLabel);

        rulesFrame.setPreferredSize(new Dimension(600, 800));
        // rulesFrame.setResizable(false);
        rulesFrame.pack();
        rulesFrame.setVisible(true);
    }

    public static String getRules() {
        String filePath = "dependancies/rules.html";
 
        String rules = "";
        try {
            rules = readFile(filePath, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
 
        System.out.println(rules);
        return rules;
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
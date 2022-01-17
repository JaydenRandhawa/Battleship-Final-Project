import Dependancies.DebugFunctions;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Game implements ActionListener, KeyListener{

    ImageIcon water = new ImageIcon("Assets/Water.png");
    ImageIcon hitWater = new ImageIcon("Assets/HitWater.png");
    static ImageIcon ship = new ImageIcon("Assets/Ship.png");
    ImageIcon hitShip = new ImageIcon("Assets/HitShip.png");

    static int width = 8;
    static int height = 8;

    static JButton[][] userButtons = new JButton[width][height];
    JButton[][] cpuButtons = new JButton[width][height];

    static JFrame userFrame;
    static JFrame opponentFrame;

    JLabel userInstruction;
    JLabel opponentInstruction;
    
    int[] prevSquare = new int[2];

    static int[][] carrier = new int[2][5];
    static int[][] battleship = new int[2][4];
    static int[][] submarine = new int[2][3];
    static int[][] destroyer = new int[2][2];

    static ArrayList<String> unhitUserShips = new ArrayList<String>();
    static ArrayList<String> unhitEnemyShips = new ArrayList<String>();

    ArrayList<String> remainingEnemySquares = new ArrayList<String>(Arrays.asList(
    "0, 0", "0, 1", "0, 2", "0, 3", "0, 4", "0, 5", "0, 6", "0, 7", 
    "1, 0", "1, 1", "1, 2", "1, 3", "1, 4", "1, 5", "1, 6", "1, 7", 
    "2, 0", "2, 1", "2, 2", "2, 3", "2, 4", "2, 5", "2, 6", "2, 7", 
    "3, 0", "3, 1", "3, 2", "3, 3", "3, 4", "3, 5", "3, 6", "3, 7", 
    "4, 0", "4, 1", "4, 2", "4, 3", "4, 4", "4, 5", "4, 6", "4, 7", 
    "5, 0", "5, 1", "5, 2", "5, 3", "5, 4", "5, 5", "5, 6", "5, 7", 
    "6, 0", "6, 1", "6, 2", "6, 3", "6, 4", "6, 5", "6, 6", "6, 7", 
    "7, 0", "7, 1", "7, 2", "7, 3", "7, 4", "7, 5", "7, 6", "7, 7"));

    static File enemyBoard;
    static Scanner inp;

    
    String[][] enemyCarrier = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemyBattleship = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemySubmarine = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemyDestroyer = {inp.nextLine().split(", "), inp.nextLine().split(", ")};

    int currentLength = 5;
    boolean isVertical = true;

    static boolean boatsPlaced;

    boolean gameOver = false;

    int[][] tempPlacement = new int[2][5];


    Random randPos = new Random();
    static Random randFile = new Random();


    public static void start() throws IOException, InterruptedException {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        createEnemyShips();
        boatsPlaced = false;
        File hsFile = new File("Highscore.txt");
        hsFile.createNewFile();
        new Game(Color.BLACK, Color.WHITE);
    }

    JMenuItem saveBoard0, loadBoard0, saveQuit0, showEnemy0, hideEnemy0, printShips0, restart0;
    JMenuItem saveBoard1, loadBoard1, saveQuit1, showEnemy1, hideEnemy1, printShips1, restart1;

    public JMenuBar createMenuBar(int id){
        JMenuBar menuBar = new JMenuBar();
        JMenu game, debug;

        game = new JMenu("Game");
        debug = new JMenu("Debug");

        if(id == 0){
            saveBoard0 = new JMenuItem("Make Preset From Layout");
            loadBoard0 = new JMenuItem("Load Board Preset");
            saveQuit0 = new JMenuItem("Save Game State and Quit");
            restart0 = new JMenuItem("Restart");
            game.add(saveBoard0);
            game.add(loadBoard0);
            // game.add(saveQuit0);
            game.add(restart0);
    
            showEnemy0 = new JMenuItem("Show Enemy Ships");
            hideEnemy0 = new JMenuItem("Hide Enemy Ships");
            printShips0 = new JMenuItem("Print Ships");
            debug.add(showEnemy0);
            debug.add(hideEnemy0);
            debug.add(printShips0);
    
    
            saveBoard0.addActionListener(menuClicks);
            loadBoard0.addActionListener(menuClicks);
            saveQuit0.addActionListener(menuClicks);
            showEnemy0.addActionListener(menuClicks);
            hideEnemy0.addActionListener(menuClicks);
            printShips0.addActionListener(menuClicks);
            restart0.addActionListener(menuClicks);
        }

        else if(id == 1){
            saveBoard1 = new JMenuItem("Make Preset From Layout");
            loadBoard1 = new JMenuItem("Load Board Preset");
            saveQuit1 = new JMenuItem("Save Game State and Quit");
            restart1 = new JMenuItem("Restart");
            game.add(saveBoard1);
            game.add(loadBoard1);
            // game.add(saveQuit1);
            game.add(restart1);
    
            showEnemy1 = new JMenuItem("Show Enemy Ships");
            hideEnemy1 = new JMenuItem("Hide Enemy Ships");
            printShips1 = new JMenuItem("Print Ships");
            debug.add(showEnemy1);
            debug.add(hideEnemy1);
            debug.add(printShips1);
    
    
            saveBoard1.addActionListener(menuClicks);
            loadBoard1.addActionListener(menuClicks);
            saveQuit1.addActionListener(menuClicks);
            showEnemy1.addActionListener(menuClicks);
            hideEnemy1.addActionListener(menuClicks);
            printShips1.addActionListener(menuClicks);
            restart1.addActionListener(menuClicks);
        }


        menuBar.add(game);
        menuBar.add(debug);

        return menuBar;
    }

    ActionListener menuClicks = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == saveBoard0){

                JFrame userInputFrame = new JFrame();
                JPanel userInputPanel = new JPanel();
        
                JLabel label = new JLabel("Enter the desired name for the save file:");
                JTextField userInputField = new JTextField();
                JButton submitButton = new JButton("Submit");
        
                submitButton.addActionListener(new ActionListener(){
        
                    public void actionPerformed(ActionEvent e) {
                        String fileName = userInputField.getText();
                        try {
                            if(saveUserBoard(fileName, carrier, battleship, submarine, destroyer)){
                                userInstruction.setText("Board Preset Saved");
                            }
                            else{
                                userInstruction.setText("Board Preset With That Name Already Exists");
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        userInputFrame.dispose();
                    }
                });
        
                userInputPanel.setLayout(new GridLayout(3, 1));
        
                userInputPanel.add(label);
                userInputPanel.add(userInputField);
                userInputPanel.add(submitButton);
        
                userInputFrame.add(userInputPanel);
        
                userInputFrame.setUndecorated(true);
                userInputFrame.setPreferredSize(new Dimension(300, 80));
                userInputFrame.setResizable(false);
                userInputFrame.pack();
                userInputFrame.setVisible(true);

            }

            else if(e.getSource() == loadBoard0 || e.getSource() == loadBoard1){

                JFrame userInputFrame = new JFrame();
                JPanel userInputPanel = new JPanel();
        
                JLabel label = new JLabel("Enter the name of the desired board preset:");
                JTextField userInputField = new JTextField();
                JButton submitButton = new JButton("Submit");
        
                submitButton.addActionListener(new ActionListener(){
        
                    public void actionPerformed(ActionEvent e) {
                        String fileName = userInputField.getText();
                        try {
                            if(loadPresetLayout(fileName)){
                                userInstruction.setText("Board Preset Loaded");
                            }
                            else{
                                userInstruction.setText("There Is No Board Preset With That Name");
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        userInputFrame.dispose();
                    }
                });
        
                userInputPanel.setLayout(new GridLayout(3, 1));
        
                userInputPanel.add(label);
                userInputPanel.add(userInputField);
                userInputPanel.add(submitButton);
        
                userInputFrame.add(userInputPanel);
        
                userInputFrame.setUndecorated(true);
                userInputFrame.setPreferredSize(new Dimension(300, 80));
                userInputFrame.setResizable(false);
                userInputFrame.pack();
                userInputFrame.setVisible(true);
            }
            // else if(e.getSource() == saveQuit0 || e.getSource() == saveQuit1){
            //     saveQuit();
            // }
            else if(e.getSource() == restart0 || e.getSource() == restart1){
                try {
                    Battleship.restart();
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            else if(e.getSource() == showEnemy0 || e.getSource() == showEnemy1){
                DebugFunctions.showEnemyShips(cpuButtons, enemyCarrier, enemyBattleship, enemySubmarine, enemyDestroyer, ship);
            }
            else if(e.getSource() == hideEnemy0 || e.getSource() == hideEnemy0){
                DebugFunctions.hideEnemyShips(cpuButtons, enemyCarrier, enemyBattleship, enemySubmarine, enemyDestroyer, water);
                
            }
            else if(e.getSource() == printShips0 || e.getSource() == printShips1){
                DebugFunctions.printAllShips(carrier, battleship, submarine, destroyer);
                
            }
            else if(e.getSource() == restart0 || e.getSource() == restart1){
                System.out.println("Restart");
            }
            
        }
    };

    public static void createEnemyShips() throws IOException{
        // enemyBoard = new File("OpponentPresets/" + randFile.nextInt(6) + ".txt");
        enemyBoard = new File("OpponentPresets/1.txt");
        System.out.println(enemyBoard);
        inp = new Scanner(enemyBoard);
    }


    public Game(Color borderColour, Color squareColour) throws IOException {

        // SECTION User panel, footer, frame initialization.
        JPanel userPanel = new JPanel();
        JPanel userFooter = new JPanel();
        userFrame = new JFrame();
        // !SECTION

        // SECTION User panel, footer, frame setup
        userPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        userPanel.setLayout(new GridLayout(height, width));
        // userPanel.setBackground(Color.DARK_GRAY);

        userFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        userFooter.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //ANCHOR Combine game board and footer into 1 pane object
        JSplitPane userPane = new JSplitPane(SwingConstants.HORIZONTAL, userPanel, userFooter);

        userPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        userPane.setDividerLocation(height*100);
        userPane.setDividerSize(0);

        userFrame.setPreferredSize(new Dimension(width*100, (height+1)*100));
        userFrame.setResizable(false);
        userFrame.add(userPane, BorderLayout.CENTER);
        userFrame.setJMenuBar(createMenuBar(0));
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setTitle("Your Board");
        userFrame.pack();
        // !SECTION



        opponentFrame = new JFrame();
        JPanel opponentPanel = new JPanel();
        JPanel opponentFooter = new JPanel();

        opponentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opponentPanel.setLayout(new GridLayout(height, width));

        opponentFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opponentFooter.setLayout(new GridBagLayout());

        JSplitPane opponentPane = new JSplitPane(SwingConstants.HORIZONTAL, opponentPanel, opponentFooter);

        opponentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        opponentPane.setDividerLocation(height*100);
        opponentPane.setDividerSize(0);

        opponentFrame.setPreferredSize(new Dimension(width*100, (height+1)*100));
        opponentFrame.setResizable(false);
        opponentFrame.add(opponentPane, BorderLayout.CENTER);
        opponentFrame.setJMenuBar(createMenuBar(1));
        opponentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        opponentFrame.setTitle("Opponent Board");
        opponentFrame.pack();
        opponentFrame.setLocation(800, 0);


        // SECTION userGrid buttons creation
        for(int i=0;i<height;i++){
            for(int ii=0;ii<width;ii++){
                JButton x =  userButtons[ii][i] = new JButton("" + ii + "," + i + "," + 0);
                x.setForeground(squareColour);
                x.setHorizontalAlignment(SwingConstants.LEFT);
                x.setVerticalAlignment(SwingConstants.TOP);
                x.setIcon(water);
                // x.setBorder(null);
                // x.setBorderPainted(false);
                x.setBackground(squareColour);
                x.setOpaque(true);
                x.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColour));
                x.addActionListener(boardClicks);
                x.addKeyListener(this);
                userPanel.add(x);
            }
        }
        // !SECTION


        for(int i=0;i<height;i++){
            for(int ii=0;ii<width;ii++){
                JButton x =  cpuButtons[ii][i] = new JButton("" + ii + "," + i + "," + 1);
                x.setForeground(squareColour);
                x.setHorizontalAlignment(SwingConstants.LEFT);
                x.setVerticalAlignment(SwingConstants.TOP);
                x.setIcon(water);
                // x.setBorder(null);
                // x.setBorderPainted(false);
                x.setBackground(squareColour);
                x.setOpaque(true);
                x.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColour));
                x.addActionListener(boardClicks);
                x.addKeyListener(this);
                opponentPanel.add(x);
            }
        }

        userInstruction = new JLabel();
        userInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        userInstruction.setText("<html>Select placement for your ship with length : " + currentLength);
        if(isVertical) userInstruction.setText(userInstruction.getText() + "<br/> Current orientation is vertical(press r to change)</html>");
        else userInstruction.setText(userInstruction.getText() + "<br/> Current orientation is horizontal(press r to change)</html>");
        userFooter.add(userInstruction);

        userFrame.setVisible(true);

        opponentInstruction = new JLabel();
        opponentInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        opponentInstruction.setText("Select a coordiante to fire at:");
        opponentFooter.add(opponentInstruction);

    }

    public void createPlayerLists(){
        for(int i=0;i<5;i++){
            unhitUserShips.add(carrier[0][i] + "," + carrier[1][i]);
            unhitEnemyShips.add(enemyCarrier[0][i] + "," + enemyCarrier[1][i]);
        }

        for(int i=0;i<4;i++){
            unhitUserShips.add(battleship[0][i] + "," + battleship[1][i]);
            unhitEnemyShips.add(enemyBattleship[0][i] + "," + enemyBattleship[1][i]);
        }

        for(int i=0;i<3;i++){
            unhitUserShips.add(submarine[0][i] + "," + submarine[1][i]);
            unhitEnemyShips.add(enemySubmarine[0][i] + "," + enemySubmarine[1][i]);
        }

        for(int i=0;i<2;i++){
            unhitUserShips.add(destroyer[0][i] + "," + destroyer[1][i]);
            unhitEnemyShips.add(enemyDestroyer[0][i] + "," + enemyDestroyer[1][i]);
        }
    }

    public static boolean saveUserBoard(String fileName, int[][] carrier, int[][] battleship, int[][] submarine, int[][] destroyer) throws IOException{
        File saveFile = new File("UserPresets/" + fileName + ".txt");

        if(saveFile.createNewFile()){

            FileWriter file = new FileWriter(saveFile, true);

            String currentLine = "";

            for(int i=0;i<2;i++){
                for(int ii=0;ii<5;ii++){
                    currentLine += carrier[i][ii] + ", ";
                }
                file.write(currentLine.substring(0, currentLine.length()-2));
                file.write(System.lineSeparator());
                currentLine = "";
            }
    
            for(int i=0;i<2;i++){
                for(int ii=0;ii<4;ii++){
                    currentLine += battleship[i][ii] + ", ";
                }
                file.write(currentLine.substring(0, currentLine.length()-2));
                file.write(System.lineSeparator());
                currentLine = "";
            }
    
            for(int i=0;i<2;i++){
                for(int ii=0;ii<3;ii++){
                    currentLine += submarine[i][ii] + ", ";
                }
                file.write(currentLine.substring(0, currentLine.length()-2));
                file.write(System.lineSeparator());
                currentLine = "";
            }
    
            for(int i=0;i<2;i++){
                for(int ii=0;ii<2;ii++){
                    currentLine += destroyer[i][ii] + ", ";
                }
                file.write(currentLine.substring(0, currentLine.length()-2));
                file.write(System.lineSeparator());
                currentLine = "";
            }

            file.write("STOP");
            file.close();

            return true;
        }
        return false;
    }

    public boolean loadPresetLayout(String fileName) throws FileNotFoundException{
        File saveFile = new File("UserPresets/" + fileName + ".txt");

        if(saveFile.exists()){
            Scanner sf = new Scanner(saveFile);

            String[] currentLine = sf.nextLine().split(", ");

            for(int i=0;i<2;i++){
                for(int ii=0;ii<5;ii++){
                    carrier[i][ii] = Integer.parseInt(currentLine[ii]);
                }
                currentLine = sf.nextLine().split(", ");
            }

            for(int i=0;i<5;i++){
                userButtons[carrier[0][i]][carrier[1][i]].setIcon(ship);
            }
    
            for(int i=0;i<2;i++){
                for(int ii=0;ii<4;ii++){
                    battleship[i][ii] = Integer.parseInt(currentLine[ii]);
                }
                currentLine = sf.nextLine().split(", ");
            }

            for(int i=0;i<4;i++){
                userButtons[battleship[0][i]][battleship[1][i]].setIcon(ship);
            }
    
            for(int i=0;i<2;i++){
                for(int ii=0;ii<3;ii++){
                    submarine[i][ii] = Integer.parseInt(currentLine[ii]);
                }
                currentLine = sf.nextLine().split(", ");
            }

            for(int i=0;i<3;i++){
                userButtons[submarine[0][i]][submarine[1][i]].setIcon(ship);
            }
    
            for(int i=0;i<2;i++){
                for(int ii=0;ii<2;ii++){
                    destroyer[i][ii] = Integer.parseInt(currentLine[ii]);
                }
                currentLine = sf.nextLine().split(", ");
            }

            for(int i=0;i<2;i++){
                userButtons[destroyer[0][i]][destroyer[1][i]].setIcon(ship);
            }

            boatsPlaced = true;
            createPlayerLists();

            opponentFrame.setVisible(true);
            return true;
        }
        return false;
    }

    ActionListener boardClicks = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            String[] fields = e.getActionCommand().split(",");
            int boardID = Integer.parseInt(fields[2]);
            int coords[] = new int[]{Integer.parseInt(fields[0]), Integer.parseInt(fields[1])};
    
            if(boardID == 0){
                if(!boatsPlaced){
                    if(isVertical){
                        if(coords[1] + currentLength - 1 < width ){
                            switch(currentLength){
                                case 5:
                                    for(int i=0;i<currentLength;i++){
                                        carrier[0][i] = coords[0];
                                        carrier[1][i] = coords[1] + i;
                                        userButtons[carrier[0][i]][carrier[1][i]].setIcon(ship);
                                    }
                                    break;
                                case 4:
                                    for(int i=0;i<currentLength;i++){
                                        battleship[0][i] = coords[0];
                                        battleship[1][i] = coords[1] + i;
                                        userButtons[battleship[0][i]][battleship[1][i]].setIcon(ship);
                                    }
                                    break;
                                case 3:
                                    for(int i=0;i<currentLength;i++){
                                        submarine[0][i] = coords[0];
                                        submarine[1][i] = coords[1] + i;
                                        userButtons[submarine[0][i]][submarine[1][i]].setIcon(ship);
                                    }
                                    break;
                                case 2:
                                    for(int i=0;i<currentLength;i++){
                                        destroyer[0][i] = coords[0];
                                        destroyer[1][i] = coords[1] + i;
                                        userButtons[destroyer[0][i]][destroyer[1][i]].setIcon(ship);
                                    }
                                    break;
                            }
    
                            if(currentLength > 2){
                                currentLength -= 1;
                                userInstruction.setText("<html>Select placement for your ship with length : " + currentLength);
                                if(isVertical) userInstruction.setText(userInstruction.getText() + "<br/> Current orientation is vertical(press r to change)</html>");
                                else userInstruction.setText(userInstruction.getText() + "<br/> Current orientation is horizontal(press r to change)</html>");
                            }
                            else{
                                boatsPlaced = true;
                                userInstruction.setText("All ships placed.");
                                createPlayerLists();
                                // Debug.showEnemyShips(cpuButtons, enemyCarrier, enemyBattleship, enemySubmarine, enemyDestroyer, ship);
                                opponentFrame.setVisible(true);
    
                                // Debug.printAllShips(carrier, battleship, submarine, destroyer);
                            }
                        }
                        else{
                            userInstruction.setText("Placement out of bounds");
                        }
                    }
                    else{
                        if(coords[0] + currentLength - 1 < height ){
                            switch(currentLength){
                                case 5:
                                    for(int i=0;i<currentLength;i++){
                                        carrier[0][i] = coords[0] + i;
                                        carrier[1][i] = coords[1];
                                        userButtons[carrier[0][i]][carrier[1][i]].setIcon(ship);
                                    }
                                    break;
                                case 4:
                                    for(int i=0;i<currentLength;i++){
                                        battleship[0][i] = coords[0] + i;
                                        battleship[1][i] = coords[1];
                                        userButtons[battleship[0][i]][battleship[1][i]].setIcon(ship);
                                    }
                                    break;
                                case 3:
                                    for(int i=0;i<currentLength;i++){
                                        submarine[0][i] = coords[0] + i;
                                        submarine[1][i] = coords[1];
                                        userButtons[submarine[0][i]][submarine[1][i]].setIcon(ship);
                                    }
                                    break;
                                case 2:
                                    for(int i=0;i<currentLength;i++){
                                        destroyer[0][i] = coords[0] + i;
                                        destroyer[1][i] = coords[1];
                                        userButtons[destroyer[0][i]][destroyer[1][i]].setIcon(ship);
                                    }
                                    break;
                            }
    
                            if(currentLength > 2){
                                currentLength -= 1;
                                userInstruction.setText("<html>Select placement for your ship with length : " + currentLength);
                                if(isVertical) userInstruction.setText(userInstruction.getText() + "<br/> Current orientation is vertical(press r to change)</html>");
                                else userInstruction.setText(userInstruction.getText() + "<br/> Current orientation is horizontal(press r to change)</html>");
                            }
                            else{
                                boatsPlaced = true;
                                userInstruction.setText("All ships placed.");
                                createPlayerLists();
                                // Debug.showEnemyShips(cpuButtons, enemyCarrier, enemyBattleship, enemySubmarine, enemyDestroyer, ship);
                                opponentFrame.setVisible(true);
                                // Debug.printAllShips(carrier, battleship, submarine, destroyer);
                            }
                        }
                        else{
                            userInstruction.setText("Placement out of bounds");
                        }
                    }
                }
            }
            else if(boardID == 1){
                try {
                    if(!handleHit(coords, 0)){
                        System.out.println("Miss");
                        String hitPos = remainingEnemySquares.get(randPos.nextInt(remainingEnemySquares.size()));
                        int[] hitCoords= new int[2];
   
                        hitCoords[0] = Integer.parseInt(hitPos.split(", ")[0]);
                        hitCoords[1] = Integer.parseInt(hitPos.split(", ")[1]);
   
                        remainingEnemySquares.remove(hitPos);
   
                        while(handleHit(hitCoords, 1) && !gameOver){
                            hitPos = remainingEnemySquares.get(randPos.nextInt(remainingEnemySquares.size()));
   
                            hitCoords[0] = Integer.parseInt(hitPos.split(", ")[0]);
                            hitCoords[1] = Integer.parseInt(hitPos.split(", ")[1]);
   
                            remainingEnemySquares.remove(hitPos);
                        }
   
                    }
                    else{
                        System.out.println("Hit");
                    }
                } catch (NumberFormatException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    };

    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'r' || e.getKeyChar() == 'R'){
            isVertical = !isVertical;
            userInstruction.setText("<html>Select placement for your ship with length : " + currentLength);
            if(isVertical) userInstruction.setText(userInstruction.getText() + "<br/> Current orientation is vertical(press r to change)</html>");
            else userInstruction.setText(userInstruction.getText() + "<br/> Current orientation is horizontal(press r to change)</html>");
        }
        
    }

    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {
        
    }

    public void actionPerformed(ActionEvent e) {
        
    }

    static void reset(){
        userFrame.setVisible(false);
        opponentFrame.setVisible(false);
    }


    int highScore(boolean setHighscore) throws IOException{
        File hsFile = new File("Highscore.txt");
        Scanner inp = new Scanner(hsFile);

        if(setHighscore){
            try(FileWriter hsWrite = new FileWriter("Highscore.txt", false)){
                if(!inp.hasNextInt() || inp.nextInt() > moveCount){
                    hsWrite.write(String.valueOf(moveCount));
                    return 1;
                }
                return 0;
            }
        }
        else{
            if(inp.hasNextInt()){
                return inp.nextInt();
            }
            return 64;
        }
    }

    int moveCount = 0;

    int userPoints = 0;
    int cpuPoints = 0;

    public boolean handleHit(int[] coords, int boardID) throws IOException{
        boolean value = false;

        if(boardID == 0){
            moveCount ++;
            opponentInstruction.setText("<html>Miss at: " + coords[0] + ", " + coords[1] + "<br/> Hits/Misses: " + userPoints + "/" + (moveCount - userPoints) + "\tHigh Score: " + highScore(false) + "</html>");
            cpuButtons[coords[0]][coords[1]].setIcon(hitWater);
            while(unhitEnemyShips.contains(coords[0] + "," + coords[1])){
                userPoints ++;
                unhitEnemyShips.remove(unhitEnemyShips.indexOf(coords[0] + "," + coords[1]));
                opponentInstruction.setText("<html>Hit at: " + coords[0] + ", " + coords[1] + "<br/> Hits/Misses: " + userPoints + "/" + (moveCount - userPoints) + "\tHigh Score: " + highScore(false) + "</html>");
                cpuButtons[coords[0]][coords[1]].setIcon(hitShip);
                value = true;
            }
        }

        if(boardID == 1){
            userInstruction.setText("Miss at: " + coords[0] + ", " + coords[1]);
            userButtons[coords[0]][coords[1]].setIcon(hitWater);
            while(unhitUserShips.contains(coords[0] + "," + coords[1])){
                cpuPoints ++;
                unhitUserShips.remove(unhitUserShips.indexOf(coords[0] + "," + coords[1]));
                userInstruction.setText("Hit at: " + coords[0] + ", " + coords[1]);
                userButtons[coords[0]][coords[1]].setIcon(hitShip);
                value = true;
            }
        }

        System.out.println("User Points: " + userPoints + "\nComputer Points: " + cpuPoints);

        if(userPoints == 14){
            gameOver = true;
            System.out.println(highScore(true));
            JOptionPane.showMessageDialog(null, "<html>You Won!<br/> High Score: " + highScore(false) + "\tYour Score: " + moveCount + "<br/>Go to Game > Restart in the menu bar to restart.</html>");
        }
        else if(cpuPoints == 14){
            gameOver = true;
            JOptionPane.showMessageDialog(null, "<html>You Lost.<br/>Go to Game > Restart in the menu bar to restart.</html>");
        }

        return value;
    }
}
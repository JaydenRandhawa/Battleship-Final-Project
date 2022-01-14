import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import java.util.Random;

public class Main implements ActionListener, KeyListener{

    ImageIcon water = new ImageIcon("Assets/Water.png");
    ImageIcon hitWater = new ImageIcon("Assets/HitWater.png");
    ImageIcon ship = new ImageIcon("Assets/Ship.png");
    ImageIcon hitShip = new ImageIcon("Assets/HitShip.png");

    int width = 8;
    int height = 8;

    JButton[][] userButtons = new JButton[width][height];
    JButton[][] cpuButtons = new JButton[width][height];

    JLabel userInstruction;
    JLabel opponentInstruction;
    
    int[] prevSquare = new int[2];

    int[][] carrier = new int[2][5];
    int[][] battleship = new int[2][4];
    int[][] submarine = new int[2][3];
    int[][] destroyer = new int[2][2];

    ArrayList<String> unhitUserShips = new ArrayList<String>();
    ArrayList<String> unhitEnemyShips = new ArrayList<String>();

    ArrayList<String> remainingEnemySquares = new ArrayList<String>(Arrays.asList(
    "0, 0", "0, 1", "0, 2", "0, 3", "0, 4", "0, 5", "0, 6", "0, 7", 
    "1, 0", "1, 1", "1, 2", "1, 3", "1, 4", "1, 5", "1, 6", "1, 7", 
    "2, 0", "2, 1", "2, 2", "2, 3", "2, 4", "2, 5", "2, 6", "2, 7", 
    "3, 0", "3, 1", "3, 2", "3, 3", "3, 4", "3, 5", "3, 6", "3, 7", 
    "4, 0", "4, 1", "4, 2", "4, 3", "4, 4", "4, 5", "4, 6", "4, 7", 
    "5, 0", "5, 1", "5, 2", "5, 3", "5, 4", "5, 5", "5, 6", "5, 7", 
    "6, 0", "6, 1", "6, 2", "6, 3", "6, 4", "6, 5", "6, 6", "6, 7", 
    "7, 0", "7, 1", "7, 2", "7, 3", "7, 4", "7, 5", "7, 6", "7, 7"));

    String[][] enemyCarrier = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemyBattleship = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemySubmarine = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemyDestroyer = {inp.nextLine().split(", "), inp.nextLine().split(", ")};

    int currentLength = 5;
    boolean isVertical = true;
    boolean boatsPlaced = false;

    boolean gameOver = false;

    int[][] tempPlacement = new int[2][5];

    JFrame opponentFrame;

    Random randPos = new Random();
    static Random randFile = new Random();
    static File enemyBoard = new File("OpponentPresets/" + randFile.nextInt(6) + ".txt");
    // static File enemyBoard = new File("OpponentPresets/6.txt");
    static Scanner inp;


    public static void main(String[] args) throws FileNotFoundException {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        createEnemyShips();
        new Main(Color.BLACK, Color.WHITE);
    }

    JMenuItem saveBoard, showEnemy, hideEnemy, printShips, restart;

    public JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu game, debug;
        restart = new JMenuItem("Restart");


        game = new JMenu("Game");
        saveBoard = new JMenuItem("Make Preset From Layout");
        game.add(saveBoard);

        debug = new JMenu("Debug");
        showEnemy = new JMenuItem("Show Enemy Ships");
        hideEnemy = new JMenuItem("Hide Enemy Ships");
        printShips = new JMenuItem("Print Ships");
        debug.add(showEnemy);
        debug.add(hideEnemy);
        debug.add(printShips);

        saveBoard.addActionListener(menuClicks);
        showEnemy.addActionListener(menuClicks);
        hideEnemy.addActionListener(menuClicks);
        printShips.addActionListener(menuClicks);
        restart.addActionListener(menuClicks);


        menuBar.add(game);
        menuBar.add(debug);
        menuBar.add(restart);

        return menuBar;
    }

    ActionListener menuClicks = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == saveBoard){
                System.out.println("Board saved");
            }
            else if(e.getSource() == showEnemy){
                Debug.showEnemyShips(cpuButtons, enemyCarrier, enemyBattleship, enemySubmarine, enemyDestroyer, ship);
            }
            else if(e.getSource() == hideEnemy){
                Debug.hideEnemyShips(cpuButtons, enemyCarrier, enemyBattleship, enemySubmarine, enemyDestroyer, water);
                
            }
            else if(e.getSource() == printShips){
                Debug.printAllShips(carrier, battleship, submarine, destroyer);
                
            }
            else if(e.getSource() == restart){
                System.out.println("Restart");
            }
            
        }
    };

    public static void createEnemyShips() throws FileNotFoundException{
        inp = new Scanner(enemyBoard);
    }


    public Main(Color borderColour, Color squareColour) {

        // SECTION User panel, footer, frame initialization.
        JPanel userPanel = new JPanel();
        JPanel userFooter = new JPanel();
        JFrame userFrame = new JFrame();
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
        userFrame.setJMenuBar(createMenuBar());
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

        opponentPane.setDividerLocation(height*100);
        opponentPane.setDividerSize(0);

        opponentFrame.setPreferredSize(new Dimension(width*100, (height+1)*100));
        opponentFrame.setResizable(false);
        opponentFrame.add(opponentPane, BorderLayout.CENTER);
        opponentFrame.setJMenuBar(createMenuBar());
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
        // TODO Auto-generated method stub
        
    }

    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }


    int userPoints = 0;
    int cpuPoints = 0;

    public boolean handleHit(int[] coords, int boardID){
        boolean value = false;

        if(boardID == 0){
            opponentInstruction.setText("Miss at: " + coords[0] + ", " + coords[1]);
            cpuButtons[coords[0]][coords[1]].setIcon(hitWater);
            while(unhitEnemyShips.contains(coords[0] + "," + coords[1])){
                userPoints ++;
                unhitEnemyShips.remove(unhitEnemyShips.indexOf(coords[0] + "," + coords[1]));
                opponentInstruction.setText("Hit at: " + coords[0] + ", " + coords[1]);
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
            JOptionPane.showMessageDialog(null, "You Won");
        }
        else if(cpuPoints == 14){
            gameOver = true;
            JOptionPane.showMessageDialog(null, "You Lost");
        }

        return value;
    }
}
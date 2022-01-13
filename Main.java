import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
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

    public int[] prevSquare = new int[2];

    int[][] carrier = new int[2][5];
    int[][] battleship = new int[2][4];
    int[][] submarine = new int[2][3];
    int[][] destroyer = new int[2][2];

    String[][] enemyCarrier = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemyBattleship = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemySubmarine = {inp.nextLine().split(", "), inp.nextLine().split(", ")};
    String[][] enemyDestroyer = {inp.nextLine().split(", "), inp.nextLine().split(", ")};

    int currentLength = 5;
    boolean isVertical = true;
    boolean boatsPlaced = false;

    int[][] tempPlacement = new int[2][5];

    JFrame opponentFrame;

    static Random rand = new Random();
    static File enemyBoard = new File("OpponentPresets/" + rand.nextInt(6) + ".txt");
    static Scanner inp;


    public static void main(String[] args) throws FileNotFoundException {
        createEnemyShips();
        new Main(Color.BLACK, Color.WHITE);
    }

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

        userPane.setDividerLocation(800);
        userPane.setDividerSize(0);

        userFrame.setPreferredSize(new Dimension(width*100, (height+1)*100));
        userFrame.setResizable(false);
        userFrame.add(userPane, BorderLayout.CENTER);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setTitle("Your Board");
        userFrame.pack();
        // !SECTION



        opponentFrame = new JFrame();
        JPanel opponentPanel = new JPanel();
        JPanel opponentFooter = new JPanel();

        opponentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opponentPanel.setLayout(new GridLayout(height, width));

        opponentFrame.setPreferredSize(new Dimension(800, 800));
        opponentFrame.setResizable(false);
        opponentFrame.add(opponentPanel, BorderLayout.CENTER);
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
                x.addActionListener(this);
                x.addKeyListener(this);
                userPanel.add(x);
            }
        }
        // !SECTION


        for(int i=0;i<height;i++){
            for(int ii=0;ii<width;ii++){
                JButton x =  cpuButtons[ii][i] = new JButton("" + ii + "," + i + "," + 0);
                x.setForeground(squareColour);
                x.setHorizontalAlignment(SwingConstants.LEFT);
                x.setVerticalAlignment(SwingConstants.TOP);
                x.setIcon(water);
                // x.setBorder(null);
                // x.setBorderPainted(false);
                x.setBackground(squareColour);
                x.setOpaque(true);
                x.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColour));
                x.addActionListener(this);
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
    }

    public void printAllShips(){

        for(int i=0;i<2;i++){
            for(int ii=0;ii<5;ii++){
                System.out.print(carrier[i][ii] + ", ");
            }
            System.out.println();
        }

        for(int i=0;i<2;i++){
            for(int ii=0;ii<4;ii++){
                System.out.print(battleship[i][ii] + ", ");
            }
            System.out.println();
        }

        for(int i=0;i<2;i++){
            for(int ii=0;ii<3;ii++){
                System.out.print(submarine[i][ii] + ", ");
            }
            System.out.println();
        }

        for(int i=0;i<2;i++){
            for(int ii=0;ii<2;ii++){
                System.out.print(destroyer[i][ii] + ", ");
            }
            System.out.println();
        }
    }

    public void showEnemyShips(){
        for(int i=0;i<5;i++){
            cpuButtons[Integer.parseInt(enemyCarrier[0][i])][Integer.parseInt(enemyCarrier[1][i])].setIcon(ship);
        }

        for(int i=0;i<4;i++){
            cpuButtons[Integer.parseInt(enemyBattleship[0][i])][Integer.parseInt(enemyBattleship[1][i])].setIcon(ship);
        }

        for(int i=0;i<3;i++){
            cpuButtons[Integer.parseInt(enemySubmarine[0][i])][Integer.parseInt(enemySubmarine[1][i])].setIcon(ship);
        }

        for(int i=0;i<2;i++){
            cpuButtons[Integer.parseInt(enemyDestroyer[0][i])][Integer.parseInt(enemyDestroyer[1][i])].setIcon(ship);
        }
    }


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
                            showEnemyShips();
                            opponentFrame.setVisible(true);
                            printAllShips();
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
                            showEnemyShips();
                            opponentFrame.setVisible(true);
                            printAllShips();
                        }
                    }
                    else{
                        userInstruction.setText("Placement out of bounds");
                    }
                }
            }
        }

        // System.out.println("Button Pressed: " + coords[0] + ", " + coords[1]);
        // userInstruction.setText("Cell Selected: " + coords[0] + ", " + coords[1]);

        // // userButtons[prevSquare[0]][prevSquare[1]].setBackground(Color.WHITE);
        // // userButtons[prevSquare[0]][prevSquare[1]].setForeground(Color.WHITE);

        // userButtons[prevSquare[0]][prevSquare[1]].setIcon(water);

        // // userButtons[coords[0]][coords[1]].setBackground(Color.RED);
        // // userButtons[coords[0]][coords[1]].setForeground(Color.RED);

        // userButtons[coords[0]][coords[1]].setIcon(ship);
        
        // prevSquare[0] = coords[0];
        // prevSquare[1] = coords[1];
    }
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
}
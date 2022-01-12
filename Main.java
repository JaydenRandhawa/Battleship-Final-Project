import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main implements ActionListener{
    public int width = 8;
    public int height = 8;

    JButton[][] userButtons = new JButton[width][height];
    JButton[][] cpuButtons = new JButton[width][height];

    JLabel instruction;

    public int[] prevSquare = new int[2];

    public static void main(String[] args) {
        new Main(Color.BLACK, Color.WHITE);
    }

    public Main(Color borderColour, Color squareColour) {

        // SECTION User panel, footer, frame initialization.
        
        JPanel userPanel = new JPanel();
        JPanel userFooter = new JPanel();
        JFrame userFrame = new JFrame();

        // !SECTION

        // SECTION User panel, footer, frame setup

        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        userPanel.setLayout(new GridLayout(height, width));
        // userPanel.setBackground(Color.DARK_GRAY);

        userFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        userFooter.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //ANCHOR Combine game board and footer into 1 pane object
        JSplitPane userPane = new JSplitPane(SwingConstants.HORIZONTAL, userPanel, userFooter);

        userPane.setDividerLocation(800);
        userPane.setDividerSize(0);

        userFrame.setPreferredSize(new Dimension(width*100, (height+1)*100));
        userFrame.setResizable(false);
        userFrame.add(userPane, BorderLayout.CENTER);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setTitle("Your Board");
        userFrame.pack();

        // !SECTION



        JFrame opponentFrame = new JFrame();
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

        for(int i=0;i<height;i++){
            for(int ii=0;ii<width;ii++){
                JButton x =  userButtons[ii][i] = new JButton("" + ii + "," + i + "," + 0);
                x.setForeground(squareColour);
                // x.setBorder(null);
                // x.setBorderPainted(false);
                x.setBackground(squareColour);
                x.setOpaque(true);
                x.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, borderColour));
                x.addActionListener(this);
                userPanel.add(x);
            }
        }

            for(int i=0;i<height;i++){
                for(int ii=0;ii<width;ii++){
                    JButton x =  cpuButtons[ii][i] = new JButton("" + ii + "," + i + "," + 1);
                    x.setForeground(squareColour);
                    // x.setBorder(null);
                    // x.setBorderPainted(false);
                    x.setBackground(squareColour);
                    x.setOpaque(true);
                    x.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, borderColour));
                    x.addActionListener(this);
                    opponentPanel.add(x);
                }
            }

            instruction = new JLabel("Select a cell");
            userFooter
.add(instruction);

        userFrame.setVisible(true);
        opponentFrame.setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        String[] fields = e.getActionCommand().split(",");
        int boardID = Integer.parseInt(fields[2]);
        int coords[] = new int[]{Integer.parseInt(fields[0]), Integer.parseInt(fields[1])};

        System.out.println("Button Pressed: " + coords[0] + ", " + coords[1]);
        instruction.setText("Cell Selected: " + coords[0] + ", " + coords[1]);

        userButtons[prevSquare[0]][prevSquare[1]].setBackground(Color.WHITE);
        userButtons[prevSquare[0]][prevSquare[1]].setForeground(Color.WHITE);

        userButtons[coords[0]][coords[1]].setBackground(Color.RED);
        userButtons[coords[0]][coords[1]].setForeground(Color.RED);
        
        prevSquare[0] = coords[0];
        prevSquare[1] = coords[1];
    }
}
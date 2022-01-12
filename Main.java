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
        JFrame frame = new JFrame();
        JFrame frame2 = new JFrame();
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel footerPanel = new JPanel();

        JSplitPane view1 = new JSplitPane(SwingConstants.HORIZONTAL, panel, footerPanel);

        view1.setDividerLocation(800);
        view1.setDividerSize(0);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(height, width));
        // panel.setBackground(Color.DARK_GRAY);

        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        footerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel2.setLayout(new GridLayout(height, width));

        frame.setPreferredSize(new Dimension(width*100, (height+1)*100));
        frame.setResizable(false);
        frame.add(view1, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Your Board");
        frame.pack();

        // frame2.setPreferredSize(new Dimension(800, 800));
        // frame2.setResizable(false);
        // frame2.add(panel2, BorderLayout.CENTER);
        // frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame2.setTitle("Opponent Board");
        // frame2.pack();
        // frame2.setLocation(800, 0);

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
                panel.add(x);
            }
        }

            instruction = new JLabel("Select a cell");
            footerPanel.add(instruction);

        // for(int i=0;i<height*width;i++){
        //     JButton x =  buttons2[i] = new JButton("" + i);
        //     x.setForeground(squareColour);
        //     // x.setBorder(null);
        //     // x.setBorderPainted(false);
        //     x.setBackground(squareColour);
        //     x.setOpaque(true);
        //     x.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, borderColour));
        //     panel2.add(x);
        // }

        frame.setVisible(true);
        // frame2.setVisible(true);


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
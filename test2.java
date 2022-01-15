import java.awt.FlowLayout;
 
import javax.swing.JFrame;
import javax.swing.JTextField;
 
public class test2 extends JFrame {
 
    private static final long serialVersionUID = 1L;
 
    public test2() {
 
        // set flow layout for the frame
        this.getContentPane().setLayout(new FlowLayout());
 
        // create JTextField with default text
        JTextField field2 = new JTextField("Java Code Geeks");
        add(field2);

 
    }
 
    private static void createAndShowGUI() {
 
  //Create and set up the window.
 
  JFrame frame = new test2();
 
  //Display the window.
 
  frame.pack();
 
  frame.setVisible(true);
 
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
    }
 
    public static void main(String[] args) {
 
  //Schedule a job for the event-dispatching thread:
 
  //creating and showing this application's GUI.
 
  javax.swing.SwingUtilities.invokeLater(new Runnable() {
 
public void run() {
 
    createAndShowGUI(); 
 
}
 
  });
    }
 
}
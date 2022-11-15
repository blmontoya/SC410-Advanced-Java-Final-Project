// Imports necessary javax.swing packages
import javax.swing.*;

/**
 * This Frame class inherits properties from the JFrame class
 * This Frame class holds the Panel object where the simulation will be taking place
 */
public class Frame extends JFrame{

    // Instantiates Panel object
    Panel panel;

    /**
     * This Frame object holds the Panel where the simulation occurs as well as properties for the JFrame window
     */
    public Frame()
    {
        // Initializes the Panel object
        panel = new Panel();

        // Adds the Panel object to the frame
        this.add(panel);
        // Sets window to be preferred size, which are the Panel dimensions in the Panel object
        this.pack();

        // Sets title of the JFrame and its visibility on
        this.setTitle("Fun at the Chapel");
        this.setVisible(true);

        // Allows the JFrame to exit when the JFrame window is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Locks window size
        setResizable(false);
    }
}
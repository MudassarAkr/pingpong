import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    GamePanel panel;

    GameFrame(){
        panel = new GamePanel();
        this.add(panel); // Add the game panel to the frame
        this.setTitle("Pong Game"); // Set the title of the frame
        this.setResizable(false); // Make the frame non-resizable
        this.setBackground(Color.black); // Set the background color to black
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the frame is closed
        this.pack(); // Size the frame to fit the preferred size and layouts of its subcomponents
        this.setVisible(true); // Make the frame visible
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }

}

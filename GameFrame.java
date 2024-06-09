import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    GamePanel panel;
    GamePanel multipleBallPanel; // Panel for multiple ball mode
    StartMenu menu;

    GameFrame(){
        panel = new GamePanel();
        multipleBallPanel = new GamePanel(true); // Pass a parameter to enable multiple ball mode
        menu = new StartMenu(this); // Pass the GameFrame instance to the StartMenu
        this.add(menu); // Add the start menu to the frame initially
        this.setTitle("Paddle Pro"); // Set the title of the frame
        this.setResizable(false); // Make the frame non-resizable
        this.setBackground(Color.blue); // Set the background color to black
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the frame is closed
        this.pack(); // Size the frame to fit the preferred size and layouts of its subcomponents
        this.setVisible(true); // Make the frame visible
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }

    public void startGame() {
        this.remove(menu); // Remove the menu
        this.add(panel); // Add the game panel
        this.pack(); // Re-pack the frame to fit the game panel
        this.revalidate(); // Revalidate the frame layout
        panel.requestFocusInWindow(); // Give focus to the GamePanel
    }

    public void startMultipleBallGame() {
        this.remove(menu); // Remove the menu
        this.add(multipleBallPanel); // Add the multiple ball game panel
        this.pack(); // Re-pack the frame to fit the game panel
        this.revalidate(); // Revalidate the frame layout
        multipleBallPanel.requestFocusInWindow(); // Give focus to the GamePanel
    }
}

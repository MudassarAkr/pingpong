import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartMenu extends JPanel implements ActionListener {

    JButton startButton;
    JButton exitButton;
    JButton multipleBallButton;
    JLabel titleLabel;
    GameFrame gameFrame;
    Image backgroundImage;

    StartMenu(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT));

        // Load the background image
        backgroundImage = new ImageIcon("C:/Users/PMYLS/Pictures/New.png").getImage();

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false); // Make the button panel transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Start Game button
        startButton = createStyledButton("Start Game", new Color(70, 130, 180));
        buttonPanel.add(startButton, gbc);

        // Multiple Ball Mode button
        multipleBallButton = createStyledButton("Challenge Mode", new Color(70, 130, 180));
        buttonPanel.add(multipleBallButton, gbc);

        // Exit button
        exitButton = createStyledButton("Exit", new Color(70, 130, 180));
        buttonPanel.add(exitButton, gbc);

        this.add(buttonPanel, BorderLayout.CENTER);

        // Add action listeners
        startButton.addActionListener(this);
        multipleBallButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.PLAIN, 30));
        button.setBackground(color);
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 60));
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
                button.setForeground(Color.white);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setForeground(Color.white);
            }
        });

        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            gameFrame.startGame();
        } else if (e.getSource() == multipleBallButton) {
            gameFrame.startMultipleBallGame();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}

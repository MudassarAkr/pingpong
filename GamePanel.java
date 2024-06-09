import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    private boolean multipleBallMode;
    private ArrayList<Ball> balls;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Score score;

    Image backgroundImage; // Background image

    GamePanel() {
        this(false); // Default to single ball mode
    }

    GamePanel(boolean multipleBallMode) {
        this.multipleBallMode = multipleBallMode;
        balls = new ArrayList<>(); // Initialize the ArrayList
        newPaddles(); // Create new paddles
        newBall(); // Create a new ball or multiple balls if in multiple ball mode

        score = new Score(GAME_WIDTH, GAME_HEIGHT); // Initialize the score

        this.setFocusable(true);
        this.addKeyListener(new AL()); // Add key listener for paddle movement
        this.setPreferredSize(SCREEN_SIZE); // Set the preferred size of the panel

        // Load the background image
        backgroundImage = new ImageIcon("C:/Users/PMYLS/Pictures/panelbackground.jpeg").getImage();

        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start(); // Start the game loop thread
    }

    public void newBall() {
        random = new Random();
        balls.clear(); // Clear the existing balls
        if (multipleBallMode) {
            // Create exactly two balls
            balls.add(new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER));
            balls.add(new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER));
        } else {
            balls.add(new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER));
        }
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics); // Draw the game elements
        g.drawImage(image, 0, 0, this);

        // Draw a white border around the panel to look like a ping pong table
        g.setColor(Color.green);

        // Cast Graphics to Graphics2D to use setStroke()
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5)); // Set stroke thickness to 5
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Outer border
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight()); // Middle line
    }

    public void draw(Graphics g) {
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);

        paddle1.draw(g);
        paddle2.draw(g);
        for (Ball ball : balls) {
            ball.draw(g);
        }
        score.drawScore(g);
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        for (Ball ball : balls) {
            ball.move();
        }
    }

    public void checkCollision() {
        for (Ball ball : balls) {
            // Bounce ball off top & bottom window edges
            if (ball.y <= 0) {
                ball.setYDirection(-ball.yVelocity);
            }
            if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
                ball.setYDirection(-ball.yVelocity);
            }

            // Bounce ball off paddles
            if (ball.intersects(paddle1)) {
                ball.xVelocity = Math.abs(ball.xVelocity);
                ball.xVelocity++;
                ball.yVelocity += (ball.yVelocity > 0) ? 1 : -1;
                ball.setXDirection(ball.xVelocity);
                ball.setYDirection(ball.yVelocity);
            }

            if (ball.intersects(paddle2)) {
                ball.xVelocity = Math.abs(ball.xVelocity);
                ball.xVelocity++;
                ball.yVelocity += (ball.yVelocity > 0) ? 1 : -1;
                ball.setXDirection(-ball.xVelocity);
                ball.setYDirection(ball.yVelocity);
            }

            // Stop paddles at window edges
            paddle1.y = Math.max(paddle1.y, 0);
            paddle1.y = Math.min(paddle1.y, GAME_HEIGHT - paddle1.height);
            paddle2.y = Math.max(paddle2.y, 0);
            paddle2.y = Math.min(paddle2.y, GAME_HEIGHT - paddle2.height);

            // Give a player 1 point and create new paddles & ball
            if (ball.x <= 0) { // Player 2 scored
                score.player2++;
                newPaddles();
                newBall();
                break; // Exit the loop to reset the game state
            }
            if (ball.x >= GAME_WIDTH - BALL_DIAMETER) { // Player 1 scored
                score.player1++;
                newPaddles();
                newBall();
                break; // Exit the loop to reset the game state
            }
        }

        // Check if a player has reached 15 points
        if (score.player1 == 15) {
            JOptionPane.showMessageDialog(this, "Player 1 wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else if (score.player2 == 15) {
            JOptionPane.showMessageDialog(this, "Player 2 wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    public void resetGame() {
        score.player1 = 0;
        score.player2 = 0;
        newPaddles();
        newBall();
    }

    public void run() {
        // game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}

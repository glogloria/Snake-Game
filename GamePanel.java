import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; // Defines the size of each grid swaure to 25 pixels
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE); // Calculates the number of grid units which fit on the screen
    static final int DELAY = 75; // Sets the speed of the game; lower delay = faster snake
    final int x[] = new int[GAME_UNITS]; // Holds head of snake and x coordinate body parts
    final int y[] = new int[GAME_UNITS]; // Holds the y coordinatesof the body parts
    int bodyParts = 6;
    int applesEaten;
    // Positoning of the apples
    int appleX;
    int appleY;
    char direction = 'R'; // By default, the snake starts going right
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){ // Calls "this" method on the GamePanel. The "this" keyword is used to avoid unambiguity with which object to use the method on.
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Sets the size of the GamePanel
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple(); // Places the first apple randomly on the grid
        running = true; // Signals that the game has begun
        timer = new Timer(DELAY, this); // Creates a Swing Timer object
        timer.start(); // Starts the timer

        // Generates the initial 6 body parts
        for (int i = 0; i < bodyParts; i++) { 
            x[i] = 100 - i * UNIT_SIZE;
            y[i] = 100;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }
        g.setColor(Color.red); 
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        for(int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(new Color(45, 180, 0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    public void newApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //random.nextInt(24) gives a numebr from 0 to 23 multiplied by 25 (0, ..., 575)
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; // Multiply by UNIT_SIZE to ensure the apple lands exactly on a grid cell. 
    }

    public void move() {
       for(int i = bodyParts; i > 0; i--) {
        x[i] = x[i-1];
        y[i] = y[i-1];
       } 

       switch (direction) {
        case 'U' -> y[0] -= UNIT_SIZE;
        case 'D' -> y[0] += UNIT_SIZE;
        case 'L' -> x[0] -= UNIT_SIZE;
        case 'R' -> x[0] += UNIT_SIZE;
       }    
    }

    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // Checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // Check if head touches left border
        if(x[0] < 0) {
            running = false;
        }

        // Check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }

        // Check if head touches top border
        if (y[0] < 0) {
            running = false;
        }

        // Check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }

    \

    public void gameOver(Graphics g) {

    }

    // A method from ActionListener which is called by Swing every DELAY milliseconds
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }

        }
    }

}

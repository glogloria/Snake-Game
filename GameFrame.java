import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GameFrame() {
        GamePanel panel = new GamePanel();
        this.add(panel); // Adds the game panel to gameframe, essentially adding the content of panel to the JFrame
        this.setTitle("Snake"); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); // Prevents the user from resizing the window
        this.pack(); // Audomatically adjusts the window based on the sizes of the panel components
        this.setVisible(true); // Makes the window visible on the screen
        this.setLocationRelativeTo(null); // Centers the frame relative to the screen
    }
}
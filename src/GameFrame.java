import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GameFrame extends JFrame {
    // TODO set width and height monitor size dependently
    static final int FRAME_WIDTH = 1000;
    static final private float RATIO = 1;
    static final int FRAME_HEIGHT = (int) (FRAME_WIDTH * RATIO);

    public GameFrame() {
        //this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        JPanel panel = new GamePanel();
        // panel.setBorder(new LineBorder(Color.RED, 5));
        this.add(panel);
        this.setTitle("Travelling Salesman");
        this.setResizable(false);
        // TODO why doesn't work? this.setBackground(Color.black);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

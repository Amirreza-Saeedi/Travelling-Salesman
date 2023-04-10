import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class MovePanel extends JPanel {
    private final Rectangle moveBox = new Rectangle();
    private final Rectangle UP = new Rectangle();
    private final Rectangle RIGHT = new Rectangle();
    private final Rectangle DOWN = new Rectangle();
    private final Rectangle LEFT = new Rectangle();

    public MovePanel(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        setBorder(new LineBorder(Color.BLACK, 2));
        setBackground(Color.WHITE);

        // init move box
        moveBox.width = getWidth() / 2;
        moveBox.height = moveBox.width;
        moveBox.x = getWidth() / 2 - moveBox.width / 2;
        moveBox.y = getHeight() / 2 - moveBox.height / 2;
        
        // init directions rects
        int length = moveBox.width / 3;
        UP.setBounds(moveBox.x + length, moveBox.y, length, length);
        RIGHT.setBounds(UP.x + length, moveBox.y + length, length, length);
        DOWN.setBounds(UP.x, RIGHT.y + length, length, length);
        LEFT.setBounds(moveBox.x, RIGHT.y, length, length);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g.setColor(Color.ORANGE);
        g.drawRect(moveBox.x, moveBox.y, moveBox.width, moveBox.height);
        
        g2.fillRect(UP.x, UP.y, UP.width, UP.height);
        g2.fillRect(RIGHT.x, RIGHT.y, RIGHT.width, RIGHT.height);
        g2.fillRect(DOWN.x, DOWN.y, DOWN.width, DOWN.height);
        g2.fillRect(LEFT.x, LEFT.y, LEFT.width, LEFT.height);
    }
}

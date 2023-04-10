import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MoveMap extends Rectangle {
    public MoveMap(int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.BLACK, 2));
    }
}

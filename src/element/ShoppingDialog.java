package element;

import main.GameFrame;
import main.GamePanel;
import scoreboard.ScoreboardPanel;

import javax.swing.*;
import java.awt.*;

public class ShoppingDialog extends JDialog {
    public ShoppingDialog(GameFrame parentFrame, Treasure treasure, Player player, ScoreboardPanel scoreboardPanel) {
        super(parentFrame, ModalityType.APPLICATION_MODAL);
        ShoppingPanel shoppingPanel = new ShoppingPanel(this, treasure, player, scoreboardPanel);
        add(shoppingPanel);
        pack();
        setTitle("Market");
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        setVisible(true);
    }
}

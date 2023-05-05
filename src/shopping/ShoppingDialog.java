package shopping;

import element.Player;
import element.Treasure;
import main.GameFrame;
import main.ScoreboardPanel;

import javax.swing.*;

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

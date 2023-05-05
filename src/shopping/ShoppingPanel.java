package shopping;

import element.Player;
import element.Treasure;
import menu.Theme;
import main.ScoreboardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShoppingPanel extends JPanel  implements ActionListener, KeyListener {
    static int treasurePrice = 500;
    JLabel titleLabel = new JLabel("Today Offers:", SwingConstants.LEFT);
    JLabel[] weaponsLabels = new JLabel[3];
    public JButton[] weaponsButtons = new JButton[3];
//    JLabel returnLabel = new JLabel();
    private final JButton returnButton = new JButton();
    Treasure treasure;
    JLabel treasureLabel = new JLabel();
    private final JButton treasureButton = new JButton();
    private final Weapon[] weapons = Weapon.weapons;
    Player player;
    ShoppingDialog parentDialog;
    ScoreboardPanel scoreboardPanel;

    public ShoppingPanel(ShoppingDialog parentDialog, Treasure treasure, Player player, ScoreboardPanel scoreboardPanel) {
        super(null);
        setPreferredSize(new Dimension(500, 300));
        setSize(500, 300);
        setBackground(Theme.DARK_THEME.backColor);
        setFocusable(true);
        addKeyListener(this);
        this.parentDialog = parentDialog;
        this.scoreboardPanel = scoreboardPanel;

        this.treasure = treasure;
        this.player = player;
        newComponents();

    }

    void newComponents() { // title, items and return components
        int size = 15;
        Font font = new Font("", Font.PLAIN, size);

        JLabel marketLabel = new JLabel("Market");
        marketLabel.setFont(new Font("ink free", Font.BOLD, 50));
        marketLabel.setBounds(0, 10, getWidth(), 60);
        marketLabel.setForeground(Theme.DARK_THEME.foreColor);
        marketLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(marketLabel);

        // title
        titleLabel.setBounds(20, 80, getWidth(), 50);
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setForeground(Theme.DARK_THEME.foreColor);
        titleLabel.setFont(font);
        add(titleLabel);

        // weapons
        int i = 0;
        JLabel label = new JLabel();
        JButton button = new JButton();
        for (; i < weaponsLabels.length; i++) {
            Weapon weapon = weapons[i];
            weaponsLabels[i] = new JLabel(String.format("%d- %-30s Power: %d   Price: %d", i + 1,
                    weapon.title, weapon.power, weapon.price));
            label = weaponsLabels[i];
            label.setBounds(titleLabel.getX() + 10, titleLabel.getY() + 30 * (i + 1),
                    getWidth(), 20);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setForeground(Theme.DARK_THEME.foreColor);
            label.setFont(font);
            add(label);

            weaponsButtons[i] = new JButton((i + 1) + "");
            button = weaponsButtons[i];
            button.setLocation(75 + 70 * i, getHeight() - 50);
            button.setSize(50, 30);
            button.setFocusable(false);
            if (player.getCoin() < weapon.price) // if money is not enough
                button.setEnabled(false);
            button.setBackground(Theme.BRIGHT_THEME.backColor);
            button.addActionListener(this);
            add(button);
        }

        // treasure
        if (treasure != null) {
            treasureLabel.setText((String.format("%d- %-30s Price: %d", i + 1, "Treasure Map", treasurePrice)));
            treasureLabel.setBounds(label.getX(), label.getY() + 30,
                    getWidth(), 20);
            treasureLabel.setForeground(Theme.DARK_THEME.foreColor);
            treasureLabel.setFont(font);
            add(treasureLabel);
            label = treasureLabel;

            treasureButton.setText((i + 1) + "");
            treasureButton.setBounds(button.getX() + 70, button.getY(), button.getWidth(), button.getHeight());
            treasureButton.setFocusable(false);
            if (player.getCoin() < treasurePrice) // if money is not enough
                treasureButton.setEnabled(false);
            add(treasureButton);
            treasureButton.setBackground(Theme.BRIGHT_THEME.backColor);
            treasureButton.addActionListener(this);
            button = treasureButton;
        }

        // return
//        returnLabel.setBounds(label.getX(), label.getY() + 40, getWidth(), 20);
//        returnLabel.setText("ESC to return.");
//        returnLabel.setForeground(Theme.DARK_THEME.foreColor);
//        add(returnLabel);

        returnButton.setBounds(button.getX() + 70, button.getY(), button.getWidth() + 20, button.getHeight());
        returnButton.setText("Exit");
        returnButton.setFocusable(false);
        returnButton.addActionListener(this);
        returnButton.setBackground(Theme.BRIGHT_THEME.backColor);
        add(returnButton);
    }

    void weaponDialog(Weapon weapon) {
        JOptionPane.showMessageDialog(this,
                weapon.price + "coins payed, " + weapon.power + "power added.",
                "Weapon", JOptionPane.INFORMATION_MESSAGE);
    }

    public void buyWeapon(Weapon weapon) {
        player.buyWeapon(weapon);
        this.weaponDialog(weapon);
        checkButtons();


    }

    public void buyTreasure() {
        player.buyTreasureMap(treasure, treasurePrice);
        JOptionPane.showMessageDialog(parentDialog,
                treasure.getTitle() + " map bought!",
                "Treasure Map", JOptionPane.INFORMATION_MESSAGE);
        checkButtons();
    }

    void checkButtons() {
        for (int i = 0; i < weaponsButtons.length; i++) { // weapons
            if (weapons[i].price > player.getCoin())
                weaponsButtons[i].setEnabled(false);
        }
        if (treasure == null || treasure.getValue() > player.getCoin()) // treasure
            treasureButton.setEnabled(false);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < weaponsButtons.length; i++) { // btn 1, 2, 3
            if (e.getSource() == weaponsButtons[i]) {
                buyWeapon(weapons[i]);
                scoreboardPanel.updateState();
                returnButton.doClick();
            }
        }
        if (e.getSource() == treasureButton) { // btn 4
            buyTreasure();
            scoreboardPanel.updateState();
            returnButton.doClick();
        } else if (e.getSource() == returnButton) { // ret btn
            parentDialog.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_1) {
            weaponsButtons[0].doClick();
        } else if (e.getKeyCode() == '2') {
            weaponsButtons[1].doClick();
        } else if (e.getKeyCode() == '3') {
            weaponsButtons[2].doClick();
        } else if (e.getKeyCode() == '4') {
            treasureButton.doClick();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            returnButton.doClick();
        }
    }
}

package element;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Market extends Element {

    public Market(int x, int y, int width, int height, int id, int _x, int _y) {
        super(x, y, width, height, id, _x, _y, "Market");
        this.setColor(new Color(0xFFC002));
        newLabel(getTitle()); // create label


        setVisible(true);
    }



    public static class MarketPanel extends JPanel implements ActionListener, KeyListener {
        static int treasurePrice = 500;
        private boolean running = true;
        JLabel titleLabel = new JLabel("Today Offers:", SwingConstants.LEFT);
        JLabel[] weaponsLabels = new JLabel[3];
        public JButton[] weaponsButtons = new JButton[3];
        JLabel returnLabel = new JLabel();
        public JButton returnButton = new JButton();
        Treasure treasure;
        JLabel treasureLabel = new JLabel();
        public JButton treasureButton = new JButton();
        public Weapon[] weapons = Weapon.weapons;
        Player player;

        public MarketPanel(Rectangle r, Treasure treasure, Player player) {
            super(null);
            setSize(500, 300);
            setLocation(r.width / 2 - getWidth() / 2,
                    r.height / 2 - getHeight() / 2);
            setBackground(Color.WHITE);
            setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK));
            setFocusable(true);
//            addKeyListener(this);
            setToolTipText("");

            this.treasure = treasure;
            this.player = player;
            newComponents();

        }


        public JButton[] getButtons() {
            JButton[] buttons = new JButton[5];
            int i = 0;
            for (; i < weaponsButtons.length; i++) {
                buttons[i] = weaponsButtons[i];
            }
            buttons[i++] = treasureButton;
            buttons[i++] = returnButton;
            return buttons;
        }
        void newComponents() { // title, items and return components
            // title
            titleLabel.setBounds(20, 20, getWidth(), 50);
            titleLabel.setVerticalAlignment(SwingConstants.TOP);
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
                label.setBounds(titleLabel.getX() + 10, titleLabel.getY() + 40 * (i + 1),
                        getWidth(), 20);
                label.setVerticalAlignment(SwingConstants.CENTER);
                add(label);

                weaponsButtons[i] = new JButton((i + 1) + "");
                button = weaponsButtons[i];
                button.setLocation(25 + 70 * i, getHeight() - 50);
                button.setSize(50, 30);
                button.setFocusable(false);
//                button.addActionListener(this);
                if (player.getCoin() < weapon.price) // if money is not enough
                    button.setEnabled(false);
                add(button);
            }

            // treasure
            if (treasure != null) {
                treasureLabel.setText((String.format("%d- %-30s Price: %d", i + 1, "Treasure Map", treasurePrice)));
                treasureLabel.setBounds(label.getX(), label.getY() + 40,
                        getWidth(), 20);
                add(treasureLabel);
                label = treasureLabel;

                treasureButton.setText((i + 1) + "");
                treasureButton.setBounds(button.getX() + 70, button.getY(), button.getWidth(), button.getHeight());
                treasureButton.setFocusable(false);
//                treasureButton.addActionListener(this);
                if (player.getCoin() < treasurePrice) // if money is not enough
                    treasureButton.setEnabled(false);
                add(treasureButton);
                button = treasureButton;
            }

            // return
            returnLabel.setBounds(label.getX(), label.getY() + 40, getWidth(), 20);
            returnLabel.setText("ESC to return.");
            add(returnLabel);

            returnButton.setBounds(button.getX() + 70, button.getY(), button.getWidth() + 20, button.getHeight());
            returnButton.setText("ESC");
            returnButton.addActionListener(this);
            returnButton.setFocusable(false);
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
            checkButtons();
        }

        public void returnToGame() {
            running = false;
        }

        void checkButtons() {
            for (int i = 0; i < weaponsButtons.length; i++) { // weapons
                if (weapons[i].price > player.getCoin())
                    weaponsButtons[i].setEnabled(false);
            }
            if (treasure.getValue() > player.getCoin()) // treasure
                treasureButton.setEnabled(false);

        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean b) {
            running = b;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            if (e.getSource() == weaponsButtons[0]) { // btn 1
//                buyWeapon(weapons[0]);
//                returnButton.doClick();
//            } else if (e.getSource() == weapons[1]) { // btn 2
//                buyWeapon(weapons[1]);
//                returnButton.doClick();
//            } else if (e.getSource() == weapons[2]) { // btn 3
//                buyWeapon(weapons[2]);
//                returnButton.doClick();
//            } else if (e.getSource() == treasureButton) { // btn 4
//                buyTreasure();
//                returnButton.doClick();
//            } else if (e.getSource() == returnButton) { // ret btn
//                System.err.println("RETURN");
//            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
//            if (e.getKeyCode() == '1') { // btn 1
//                weaponsButtons[0].doClick();
//            } else if (e.getKeyCode() == '2') { // btn 2
//                weaponsButtons[1].doClick();
//            } else if (e.getKeyCode() == 3) { // btn 3
//                weaponsButtons[2].doClick();
//            } else if (e.getKeyCode() == '3') { // btn 4
//                treasureButton.doClick();
//            } else if (e.getKeyCode() == 27) {
//                returnButton.doClick();
//            }
        }
    }
}



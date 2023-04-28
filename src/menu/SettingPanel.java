package menu;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel implements ActionListener, ChangeListener {
    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 300;
    private static final Color backColor = new Color(0x424242);
    private static final Color foreColor = new Color(0xF1F1F1);
    private JDialog parentDialog;
    JButton applyButton = new JButton("Apply");
    JButton closeButton = new JButton("Close");
    JButton resetButton = new JButton("Reset");
    JButton[] buttons = {closeButton, applyButton, resetButton};

    // modes
    JLabel modeLabel = new JLabel("Playing Mode:");
    JRadioButton twoPlayersRButton = new JRadioButton("2 Players");
    JRadioButton threePlayersRButton = new JRadioButton("3 Players");
    JRadioButton fourPlayersRButton = new JRadioButton("4 Players");
    JRadioButton[] modeRButtons = {twoPlayersRButton, threePlayersRButton, fourPlayersRButton};

    // difficulty
    JLabel difficultyLabel = new JLabel("Difficulty:");
    JRadioButton easyRButton = new JRadioButton("Easy");
    JRadioButton mediumRButton = new JRadioButton("Medium");
    JRadioButton hardRButton = new JRadioButton("Hard");
    JRadioButton[] difficultyRButtons = {easyRButton, mediumRButton, hardRButton};

    // board size
    JLabel boardSizeLabel = new JLabel("Board Size:");

    JRadioButton[][] radioButtons = {difficultyRButtons, modeRButtons};
    JLabel[] labels = {modeLabel, difficultyLabel, boardSizeLabel};

    JSpinner boardSizeSpinner = new JSpinner();

    SettingPanel(JDialog parentDialog) {
        super();
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setBackground(backColor);
        setLayout(null);
        this.parentDialog = parentDialog;
        Setting setting = Setting.getInstance();

        setLabels(labels);

        setRadioButtons(modeRButtons, modeLabel);
        if (setting.getNumberOfPlayers() == 2) {
            twoPlayersRButton.setSelected(true);
        } else if (setting.getNumberOfPlayers() == 3) {
            threePlayersRButton.setSelected(true);
        } else if (setting.getNumberOfPlayers() == 4) {
            fourPlayersRButton.setSelected(true);
        }

        setRadioButtons(difficultyRButtons, difficultyLabel);
        if (setting.getDifficulty() == Setting.EASY) {
            easyRButton.setSelected(true);
        } else if (setting.getDifficulty() == Setting.MEDIUM) {
            mediumRButton.setSelected(true);
        } else if (setting.getDifficulty() == Setting.HARD) {
            hardRButton.setSelected(true);
        }

        // spinner
        boardSizeSpinner.setBounds(mediumRButton.getX(),
                boardSizeLabel.getY(), 50, 20);
        boardSizeSpinner.addChangeListener(this);
        boardSizeSpinner.setModel(new SpinnerNumberModel(Setting.getInstance().getBoardSize(),
                Setting.MIN_SIZE, Setting.MAX_SIZE, 1));
        add(boardSizeSpinner);

        setButtons(buttons);
        applyButton.setEnabled(false); // set enabled when any action performed
    }

    private void setLabels(JLabel[] labels) {
        int xLabel = 10;
        int y0 = 20;
        int vSpace = 50;
        int labelSize = 15;
        Dimension labelDimension = new Dimension(getWidth(), labelSize);
        Font labelFont = new Font("", Font.BOLD, labelDimension.height);

        for (int i = 0; i < labels.length; i++) { // labels
            labels[i].setBounds(xLabel, i * vSpace + y0, getWidth(), labelDimension.height);
            labels[i].setForeground(foreColor);
            labels[i].setBackground(backColor);
            labels[i].setFont(labelFont);

            add(labels[i]);
        }
    }

    private void setRadioButtons(JRadioButton[] radioButtons, JLabel topLabel) {
        int x0 = 20;
        int y0 = topLabel.getY() + topLabel.getHeight() + 5;
        int hSpace = 110;
        int radioSize = 20;
        ButtonGroup modeBGroup = new ButtonGroup();

        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i].setBounds(i * hSpace + x0, y0, 100, radioSize);
            radioButtons[i].setBackground(backColor);
            radioButtons[i].setForeground(foreColor);
            radioButtons[i].addActionListener(this);

            modeBGroup.add(radioButtons[i]);
            add(radioButtons[i]);
        }
    }

    private void setButtons(JButton[] buttons) {
        Dimension buttonDimension = new Dimension(100, 30);
        int hSpace = 10 + buttonDimension.width;
        int y0 = getHeight() - (buttonDimension.height + 10);
//        int x0 = getXCenter() - buttonDimension.width - hSpace / 2;
        int x0 = 20;

        for (int i = 0; i < buttons.length; ++i) {
            buttons[i].setSize(buttonDimension);
            buttons[i].setLocation(x0 + i * hSpace, y0);
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }
    }

    private int getXCenter() {
        return getWidth() / 2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        applyButton.setEnabled(true);

        if (e.getSource() == applyButton) {
            Setting setting = Setting.getInstance();

            if (easyRButton.isSelected()) { // difficulty
                setting.setDifficulty(Setting.EASY);
            } else if (mediumRButton.isSelected()) {
                setting.setDifficulty(Setting.MEDIUM);
            } else if (hardRButton.isSelected()) {
                setting.setDifficulty(Setting.HARD);
            }

            if (twoPlayersRButton.isSelected()) { // modes
                setting.setNumberOfPlayers(2);
            } else if (threePlayersRButton.isSelected()) {
                setting.setNumberOfPlayers(3);
            } else if (fourPlayersRButton.isSelected()) {
                setting.setNumberOfPlayers(4);
            }

            setting.setBoardSize((Integer) boardSizeSpinner.getValue()); // board size

            applyButton.setEnabled(false);

        } else if (e.getSource() == resetButton) {
            mediumRButton.setSelected(true);
            twoPlayersRButton.setSelected(true);
            boardSizeSpinner.getModel().setValue(10);

        } else if (e.getSource() == closeButton) {
            parentDialog.dispose();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        applyButton.setEnabled(true);
    }
}

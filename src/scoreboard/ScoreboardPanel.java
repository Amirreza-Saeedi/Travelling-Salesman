package scoreboard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreboardPanel extends JPanel {
    final int VERTICAL_UNIT;
    int elapsedTime = 0;
    int second;
    int minute;
    private String secondString = String.format("%02d", second);
    private String minuteString = String.format("%02d", minute);;
    private JLabel timerLabel = new JLabel();
    public Quest quest;

    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime += 1000;
            second = elapsedTime / 1000 % 60;
            minute = elapsedTime / 60000;
            secondString = String.format("%02d", second);
            minuteString = String.format("%02d", minute);
            timerLabel.setText(minuteString + ":" + secondString);
        }
    });

    public ScoreboardPanel(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        setBorder(new LineBorder(Color.BLACK, 2));
        setBackground(Color.WHITE);
        setLayout(null);
        VERTICAL_UNIT = height / 10;

        newTimer();
        newQuest();

    }

    private void newTimer() {
//        int width = this.getWidth() / 2;
//        int height = 40;
//        int x = this.getWidth() / 2 - width / 2;
//        int y = 5;
        timerLabel.setBounds(0, 0, this.getWidth(), VERTICAL_UNIT);
        timerLabel.setText(minuteString + ":" + secondString);
        timerLabel.setFont(new Font("", Font.PLAIN, VERTICAL_UNIT));
        timerLabel.setBorder(new LineBorder(Color.BLACK, 1));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timer.start();
        this.add(timerLabel);
    }

    private void newQuest() {
        quest = new Quest();
        JLabel label = quest.label;
        label.setBounds(0, timerLabel.getY() + VERTICAL_UNIT, this.getWidth(), VERTICAL_UNIT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font("", Font.PLAIN, VERTICAL_UNIT - 20));
        label.setBorder(new LineBorder(Color.BLACK, 1));
        add(label);
    }
}

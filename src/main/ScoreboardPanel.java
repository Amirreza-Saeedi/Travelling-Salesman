package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreboardPanel extends JPanel {
    int elapsedTime = 0;
    int second;
    int minute;
    String secondString = String.format("%02d", second);
    String minuteString = String.format("%02d", minute);;
    JLabel timerLabel = new JLabel();
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

        // timer
        newTimer();
    }

    private void newTimer() {
        int width = this.getWidth() / 2;
        int height = 40;
        int x = this.getWidth() / 2 - width / 2;
        int y = 5;
        timerLabel.setBounds(x, y, width, height);
        timerLabel.setText(minuteString + ":" + secondString);
        timerLabel.setFont(new Font("", Font.PLAIN, height));
        timerLabel.setBorder(new LineBorder(Color.BLACK, 1));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timer.start();
        this.add(timerLabel);
    }
}

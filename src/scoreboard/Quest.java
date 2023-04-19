package scoreboard;

import element.Treasure;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Quest {
    String title = "Test";
    Image image;
    JLabel label = new JLabel("Quest: " + title);
    Quest() {
    }

    public void setQuest(String title) {
        this.title = title;
        label.setText("Quest: " + this.title);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Treasure) {
            Treasure treasure = (Treasure) obj;
            return treasure.getTitle() == title;
        } else {
            return false;
        }
    }

}

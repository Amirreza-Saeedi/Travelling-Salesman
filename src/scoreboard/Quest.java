package scoreboard;

import element.Treasure;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Quest {
    String title = "Test";
    Image image;
    int id;
    JLabel label = new JLabel("Quest: " + title);
    Quest() {
    }

    public void setQuest(Treasure treasure) {
        this.id = treasure.getId();
        this.title = treasure.getTitle();
        label.setText("Quest: " + this.title);
    }

    public boolean equals(int id) {
        return id == this.id;
    }

    public int getId() {
        return id;
    }
}

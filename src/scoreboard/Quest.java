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
    Rectangle rectangle;
    Quest() {
    }

    public void setQuest(Treasure treasure) {
        if (treasure == null) {
            this.id = -1;
            this.title = "";
            label.setText("Quest: " + title);
        }
        this.id = treasure.getId();
        this.title = treasure.getTitle();
        label.setText("Quest: " + this.title);
        rectangle = treasure;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean equals(int id) {
        return id == this.id;
    }

    public int getId() {
        return id;
    }
}

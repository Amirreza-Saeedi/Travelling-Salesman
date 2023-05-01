package main;

import element.Treasure;

public class Quest { // singleton
    private static final Quest instance = new Quest();
    Treasure treasure;
    Quest() {
    }

    public void setQuest(Treasure t) {
        if (t == null) {
            return;
        }
        treasure = t;
    }

    public static Quest getInstance() {
        return instance;
    }

    public Treasure getTreasure() {
        return treasure;
    }
}

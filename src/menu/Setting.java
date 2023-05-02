package menu;

import java.awt.*;

public class Setting {

    // difficulty
    private int difficulty = MEDIUM;
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;

    // modes
    private int numberOfPlayers = 2;
    final static int MIN_PLAYERS = 2;
    final static int MAX_PLAYERS = 4;

    // board
    private int boardSize = 10;
    final static int MIN_SIZE = 6;
    final static int MAX_SIZE = 12;
    private final static Setting instance = new Setting();
    private Setting() {
    }

    public static Setting getInstance() {
        return instance;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        if (EASY <= difficulty && difficulty <= HARD) {
            this.difficulty = difficulty;
        } else {
            System.out.println("Setting.setDifficulty");
            System.err.println("DIFFICULTY");
        }
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        if (MIN_PLAYERS <= numberOfPlayers && numberOfPlayers <= MAX_PLAYERS) {
            this.numberOfPlayers = numberOfPlayers;
        } else {
            System.out.println("Setting.setNumberOfPlayers");
            System.err.println("numberOfPlayers");
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
        if (MIN_SIZE <= boardSize && boardSize <= MAX_SIZE) {
            this.boardSize = boardSize;
        } else {
            System.out.println("Setting.setBoardSize");
            System.err.println("BOARD SIZE");
        }
    }
}

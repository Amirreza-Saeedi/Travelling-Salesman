package main;

import consts.GameConstants;
import element.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static java.lang.Math.ceil;

public class GamePanel extends JPanel implements ActionListener {
    //screen and map sizes
    private static final int FRAME_WIDTH = 1500;
    private static final double FRAME_RATIO = 4.0 / 7;
    private static final int FRAME_HEIGHT = (int) (FRAME_WIDTH * FRAME_RATIO);
    public final Dimension SCREEN_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    private static final double RATIO_OF_MAP_TO_FRAME = 3.0 / 5;
    private static final int MAP_WIDTH = (int) (FRAME_WIDTH * RATIO_OF_MAP_TO_FRAME);
    private static final int MAP_HEIGHT = FRAME_HEIGHT;
    public final Dimension MAP_SIZE = new Dimension(MAP_WIDTH, MAP_HEIGHT);
    public final int UNIT_SIZE;

    // elements (amounts, units ...)
    static final int BOARD_UNITS = 10;
    private static final int NUMBER_OF_UNITS = BOARD_UNITS * BOARD_UNITS;
    private static final double RATIO_OF_MARKETS_TO_UNITS = 5.0 / (10 * 10);
    public static final int NUMBER_OF_MARKETS = (int) (NUMBER_OF_UNITS * RATIO_OF_MARKETS_TO_UNITS);
    private static final double RATIO_OF_LOOTS_TO_UNITS = 13.0 / (10 * 10);
    public static final int NUMBER_OF_LOOTS = (int) (NUMBER_OF_UNITS * RATIO_OF_LOOTS_TO_UNITS);
    public static final int NUMBER_OF_TREASURES = 8; // TODO expand treasures
    public static final int NUMBER_OF_CASTLES = 1; // TODO add other modes
    private static final double RATIO_OF_WALLS_TO_UNITS = 5.0 / (10 * 10);
    public static final int NUMBER_OF_WALLS = (int) (NUMBER_OF_UNITS * RATIO_OF_WALLS_TO_UNITS);
    private static final double RATIO_OF_TRAPS_TO_UNITS = 5.0 / (10 * 10); // TODO add difficulty condition
    public static final int NUMBER_OF_TRAPS = (int) (NUMBER_OF_UNITS * RATIO_OF_TRAPS_TO_UNITS);

    // players
    public final int NUMBER_OF_PLAYERS = 2; // TODO add other modes


    // TODO difficulty
    public int difficulty;

//    JPanel mapPanel; // TODO convert mapRect to panel?
    JPanel scoreboardPanel;
    BoardMap boardMap;
    private final Random random = new Random();
    private Castle castle;
    private Loot[] loots;
    private Market[] markets;
    private Trap[] traps;
    private Treasure[] treasures;
    private Wall[] walls;
    private StartHouse startHouse; // TODO provide more than one house, and let the player try his chance and throw dice to choose one of them

    private Player[] players; // TODO array or not?

    private DiceMap diceMap;
    private JButton diceButton;
    private int diceNumber;

    private MovePanel movePanel;
    private Trace[] traces;
    private int nTrace;

    private int turn;

  /*  enum Turn {
        PLAYER_1,
        PLAYER_2;
    };*/


    enum Status {
        PLAYER_1_WON,
        PLAYER_2_WON,
        DRAW, // TODO draw?
        PLAYING;
    };



    public GamePanel() {
        // create main panel
        super();
        this.setPreferredSize(SCREEN_SIZE);
        this.setBackground(Color.black);
        this.setLayout(null);

        this.turn = GameConstants.PLAYER_1;


        // game settings
        this.difficulty = GameConstants.MEDIUM;

        // create a Map
        newMap();
        UNIT_SIZE = boardMap.UNIT_SIZE;

        // put elements on the board
        newElements();

        newPlayers();
        newDice();
        newMovePanel();
        addMoveButtons();
        newTraces();

    }




    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);


    }

    public void draw(Graphics g) {
        boardMap.draw(g);
        drawElements(g); // TODO turn
        drawDice(g); // TODO define in its own class?
        drawTrace(g);
        drawPlayers(g);

        checkPossibleMoves();


    }

    private void drawTrace(Graphics g) {
        for (int i = 0; i < nTrace; i++) {
            traces[i].draw(g);
        }
    }

    private void drawPlayers(Graphics g) {
        for (Player player : players) {
            player.draw(g);
        }
    }

    private void drawElements(Graphics g) {
        drawCastle(g);
        drawTreasures(g);
        drawMarkets(g);
        drawLoots(g);
        drawTraps(g);
        drawWalls(g);
        drawStartHouse(g);
    }

    private void drawCastle(Graphics g) {
        castle.draw(g);
    }

    private void drawTreasures(Graphics g) {
        for (Treasure treasure : treasures) {
            treasure.draw(g);
        }
    }

    private void drawMarkets(Graphics g) {
        for (Market market : markets) {
            market.draw(g);
        }
    }

    private void drawLoots(Graphics g) {
        for (Loot loot : loots) {
            loot.draw(g);
        }
    }

    private void drawTraps(Graphics g) {
        for (Trap trap : traps) {
            trap.draw(g);
        }
    }

    private void drawWalls(Graphics g) {

        for (Wall wall : walls) {
            wall.draw(g);
        }
    }

    private void drawStartHouse(Graphics g) {
        startHouse.draw(g);
    }

    private void drawDice(Graphics g) {
        diceMap.draw(g);

        if (diceNumber != -1) { // show result

            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString(String.format("Remaining Moves: %d", diceNumber),
                    diceMap.getXNumberPlace() - 110,
                    diceMap.getYNumberPlace() + diceMap.getHeightNumberPlace()
            );
        }
    }



    private void newTraces() {
        traces = new Trace[6];
        for (int i = 0; i < traces.length; i++) {
            traces[i] = new Trace(new Dimension(UNIT_SIZE, UNIT_SIZE));
        }
        nTrace = 0;
    }

    public void newMap() { // create a Map obj
        int deltaWidth = FRAME_WIDTH - MAP_WIDTH;
        boardMap = new BoardMap(deltaWidth / 2, 0, MAP_WIDTH, MAP_HEIGHT, BOARD_UNITS);
    }

    void newElements() { // create and initialize all game houses
        newCastle();
        newTreasures();
        newMarkets();
        newLoots();
        newTraps();
        newWalls();
        newStartHouse();
    }

    private void newCastle() { // do center the castle on the board
        int x = BOARD_UNITS / 2;
        int y = x;

        castle = new Castle(boardMap.xAxis[x], boardMap.yAxis[y], UNIT_SIZE, UNIT_SIZE);
        castle.set_x(x);
        castle.set_y(y);
        boardMap.board[x][y] = GameConstants.CASTLE; // add house
        this.add(castle.getLabel()); // add label
    }

    private void newTreasures() { // put the treasures in random positions TODO improve algorithm
        treasures = new Treasure[NUMBER_OF_TREASURES];
        int j = random.nextInt(4); // determine which area to start
        Point point = new Point();

        for (int i = 0; i < treasures.length; i++) {
            justRandomization(j, point); // determine coordinate

            treasures[i] = new Treasure(boardMap.xAxis[point.x], boardMap.yAxis[point.y], UNIT_SIZE, UNIT_SIZE,
                    i, point.x, point.y, Treasure.namesList[i], Treasure.valueList[i]); // set specific name and value

            boardMap.board[point.x][point.y] = GameConstants.TREASURE; // add house
            this.add(treasures[i].getLabel()); // add label
            j++;
        }

    }

    private void newMarkets() { // put markets in random positions TODO optimize the algorithm
        markets = new Market[NUMBER_OF_MARKETS];
        int j = random.nextInt(4); // determine which area to start
        Point point = new Point();

        for (int i = 0; i < markets.length; i++) {
            justRandomization(j, point); // determine coordinate

            markets[i] = new Market(boardMap.xAxis[point.x], boardMap.yAxis[point.y], UNIT_SIZE, UNIT_SIZE,
                                    i, point.x, point.y);

            boardMap.board[point.x][point.y] = GameConstants.MARKET; // add house
            this.add(markets[i].getLabel()); // add label
            j++;
        }
    }

    private void newLoots() {
        loots = new Loot[NUMBER_OF_LOOTS];
        int     x = 0,
                y = 0;

        for (int i = 0; i < loots.length; i++) {
            do {
                x = random.nextInt(BOARD_UNITS);
                y = random.nextInt(BOARD_UNITS);
            } while (boardMap.board[x][y] != GameConstants.EMPTY);

            loots[i] = new Loot(boardMap.xAxis[x], boardMap.yAxis[y], UNIT_SIZE, UNIT_SIZE,
                                i, x, y);

            boardMap.board[x][y] = GameConstants.LOOT; // add house
            this.add(loots[i].getLabel()); // add label
        }
    }

    private void newTraps() {
        traps = new Trap[NUMBER_OF_TRAPS];
        int x = 0,
                y = 0;

        for (int i = 0; i < traps.length; i++) {
            do {
                x = random.nextInt(BOARD_UNITS);
                y = random.nextInt(BOARD_UNITS);
            } while (boardMap.board[x][y] != GameConstants.EMPTY);

            traps[i] = new Trap(boardMap.xAxis[x], boardMap.yAxis[y], UNIT_SIZE, UNIT_SIZE,
                                i, x, y);

            boardMap.board[x][y] = GameConstants.TRAP; // add house
            this.add(traps[i].getLabel()); // add label
        }
    }

    private void newWalls() {
        walls = new Wall[NUMBER_OF_WALLS];
        int     x = 0,
                y = 0;

        for (int i = 0; i < walls.length; i++) {
            do {
                x = random.nextInt(BOARD_UNITS);
                y = random.nextInt(BOARD_UNITS);
            } while (boardMap.board[x][y] != GameConstants.EMPTY);

            walls[i] = new Wall(boardMap.xAxis[x], boardMap.yAxis[y], UNIT_SIZE, UNIT_SIZE,
                                i, x, y);

            boardMap.board[x][y] = GameConstants.WALL; // add house
            this.add(walls[i].getLabel()); // add label
        }
    }

    void newStartHouse() { // TODO must not be a trap or wall location
        int side = random.nextInt(4) + 1, // one specific id for each side 1-4
                house = 0;
        int x = 0, y = 0;
        Point point = new Point();
        boolean sw = true;

        while (sw) { // check if there is a Wall
            house = random.nextInt(BOARD_UNITS);

            switch (side) { // determine the coordinate
                case GameConstants.UP: // y = 0
                    if (!isWall(house, 0, true)) {
                        sw = false;
                    }
                    point.x = boardMap.xAxis[house];
                    point.y = boardMap.yAxis[0] - UNIT_SIZE;
                    x = house;
                    y = -1;
                    break;
                case GameConstants.RIGHT: // x = max
                    if (!isWall(BOARD_UNITS - 1, house, true)) {
                        sw = false;
                    }
                    point.x = boardMap.xAxis[BOARD_UNITS - 1] + UNIT_SIZE;
                    point.y = boardMap.yAxis[house];
                    x = BOARD_UNITS;
                    y = house;
                    break;
                case GameConstants.DOWN: // y = max
                    if (!isWall(house, BOARD_UNITS - 1, true)) {
                        sw = false;
                    }
                    point.x = boardMap.xAxis[house];
                    point.y = boardMap.yAxis[BOARD_UNITS - 1] + UNIT_SIZE;
                    x = house;
                    y = BOARD_UNITS;
                    break;
                case GameConstants.LEFT: // x = 0
                    if (!isWall(0, house, true)) {
                        sw = false;
                    }
                    point.x = boardMap.xAxis[0] - UNIT_SIZE;
                    point.y = boardMap.yAxis[house];
                    x = -1;
                    y = house;
                    break;
            }

        }

        startHouse = new StartHouse(point.x, point.y, UNIT_SIZE, UNIT_SIZE,
                                    side, x, y);

        this.add(startHouse.getLabel());
    }

    void newPlayers() {
        players = new Player[NUMBER_OF_PLAYERS];

        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            String title = "Player " + (i + 1);
            players[i] = new Player(startHouse.x, startHouse.y, startHouse.width, startHouse.height,
                    i, startHouse._x, startHouse._y, title);
        }
    }

    void newDice() {
        // init dice obj
        int width = boardMap.x;
        int height = FRAME_HEIGHT / 2;
        int x = 0;
        int y = FRAME_HEIGHT / 2;
        diceMap = new DiceMap(x, y, width, height);

        // init dice button
        diceButton = new JButton("Throw!");
        diceButton.setToolTipText("Dice");
        diceButton.setBounds(diceMap.getDICE());
        diceButton.setVisible(true);
        diceButton.setEnabled(true);
        diceButton.addActionListener(this);
        this.add(diceButton);
    }

    private void newMovePanel() {
        int     x = boardMap.x + boardMap.width,
                y = FRAME_HEIGHT / 2;
        int     width = FRAME_WIDTH - x,
                height = FRAME_HEIGHT - y;
        movePanel = new MovePanel(x, y, width, height);
        add(movePanel);
    }

    private void addMoveButtons() {
        movePanel.upButton.addActionListener(this);
        movePanel.rightButton.addActionListener(this);
        movePanel.downButton.addActionListener(this);
        movePanel.leftButton.addActionListener(this);

       /* movePanel.upButton.setEnabled(false);
        movePanel.rightButton.setEnabled(false);
        movePanel.downButton.setEnabled(false);
        movePanel.leftButton.setEnabled(false);*/

        /*switch (startHouse.getId()) {
            case consts.GameConstants.UP:
                movePanel.downButton.setEnabled(true);
                break;
            case consts.GameConstants.RIGHT:
                movePanel.leftButton.setEnabled(true);
                break;
            case consts.GameConstants.DOWN:
                movePanel.upButton.setEnabled(true);
                break;
            case consts.GameConstants.LEFT:
                movePanel.rightButton.setEnabled(true);
                break;

        }*/
    }

    private void newQuest() {

    }



    private boolean isWall(int x, int y, boolean optimized) {
        if (optimized) {
            return (boardMap.board[x][y] == GameConstants.WALL);
        } else {
            for (Wall wall : walls) {
                if (x == wall._x && y == wall._y)
                    return true;
            }
            return false;
        }
    }



    private void justRandomization(int area, Point p) { // todo combine two approaches
        int x = 0,
                y = 0;

        switch (area % 4) {
            case 0:
                do { // x1, y1 upper-left
                    x = random.nextInt(BOARD_UNITS / 2);
                    y = random.nextInt(BOARD_UNITS / 2);
                } while (boardMap.board[x][y] != GameConstants.EMPTY);
                break;
            case 1:
                do { // x1, y2 upper-right
                    x = random.nextInt(BOARD_UNITS / 2);
                    y = (int) (random.nextInt(BOARD_UNITS / 2) + ceil(BOARD_UNITS / 2.0));
                } while (boardMap.board[x][y] != GameConstants.EMPTY);
                break;
            case 2:
                do { // x2, y1 lower-left
                    x = (int) (random.nextInt(BOARD_UNITS / 2) + ceil(BOARD_UNITS / 2.0));
                    y = random.nextInt(BOARD_UNITS / 2);
                } while (boardMap.board[x][y] != GameConstants.EMPTY);
                break;
            case 3:
                do { // x2, y2 lower-right
                    x = (int) (random.nextInt(BOARD_UNITS / 2) + ceil(BOARD_UNITS / 2.0));
                    y = (int) (random.nextInt(BOARD_UNITS / 2) + ceil(BOARD_UNITS / 2.0));
                } while (boardMap.board[x][y] != GameConstants.EMPTY);
                break;

        }

        p.x = x;
        p.y = y;
    }

    private void checkPossibleMoves() {
        int     x = players[turn]._x,
                y = players[turn]._y;

        // start houses:
        if (x == -1) { // only right
            movePanel.upButton.setEnabled(false);
            movePanel.rightButton.setEnabled(true);
            movePanel.downButton.setEnabled(false);
            movePanel.leftButton.setEnabled(false);

        } else if (x == BOARD_UNITS) { // only left
            movePanel.upButton.setEnabled(false);
            movePanel.rightButton.setEnabled(false);
            movePanel.downButton.setEnabled(false);
            movePanel.leftButton.setEnabled(true);

        } else if (y == -1) { // only down
            movePanel.upButton.setEnabled(false);
            movePanel.rightButton.setEnabled(false);
            movePanel.downButton.setEnabled(true);
            movePanel.leftButton.setEnabled(false);

        } else if (y == BOARD_UNITS) { // only up
            movePanel.upButton.setEnabled(true);
            movePanel.rightButton.setEnabled(false);
            movePanel.downButton.setEnabled(false);
            movePanel.leftButton.setEnabled(false);

        } else { // inside the board

            if (y == 0 || isWall(x, y - 1, false) || isTrace(x, y - 1)) { // up dir
                movePanel.upButton.setEnabled(false);
            } else {
                movePanel.upButton.setEnabled(true);
            }

            if (x == BOARD_UNITS - 1 || isWall(x + 1, y, false) || isTrace(x + 1, y)) { // right dir
                movePanel.rightButton.setEnabled(false);
            } else {
                movePanel.rightButton.setEnabled(true);
            }

            if (y == BOARD_UNITS - 1 || isWall(x, y + 1, false) || isTrace(x, y + 1)) { // down dir
                movePanel.downButton.setEnabled(false);
            } else {
                movePanel.downButton.setEnabled(true);
            }

            if (x == 0 || isWall(x - 1, y, false) || isTrace(x - 1, y)) { // left dir
                movePanel.leftButton.setEnabled(false);
            } else {
                movePanel.leftButton.setEnabled(true);
            }

            if (diceNumber != 0) // if player traps himself
                checkMovePenalty();
        }
    }

    void checkMovePenalty() { // todo if all of move buttons were disabled
        if (!movePanel.upButton.isEnabled() && !movePanel.rightButton.isEnabled() &&
                !movePanel.downButton.isEnabled() && !movePanel.leftButton.isEnabled()) {
/*
*
* */
            System.err.println("DON'T TRAP YOURSELF!");
            nextPlayer();
            repaint();
        }
    }

    void checkHouse() { // applies appropriate behavior
        Player curPlayer = players[turn];

        for (int i = turn, j = 0; j < NUMBER_OF_PLAYERS - 1; ++i, ++j) { // first check for fight
            int nxtTurn = (i + 1) % NUMBER_OF_PLAYERS;
            if (curPlayer._x == players[nxtTurn]._x && curPlayer._y == players[nxtTurn]._y)
                applyFight(players[nxtTurn]);
        }

        switch (boardMap.board[curPlayer._x][curPlayer._y]) { // then for houses
            case GameConstants.LOOT:
                applyLoot();
                break;
            case GameConstants.TRAP:
                applyTrap();
                break;
            case GameConstants.TREASURE:
                applyTreasure();
                break;
            case GameConstants.CASTLE:
                applyCastle();
                break;
            case GameConstants.MARKET:
                applyMarket();
                break;
        }



    }

    void applyFight(Player opponent) {
        System.out.println("main.GamePanel.applyFight");
        System.out.println("opponent = " + opponent);
        players[turn].fight(opponent, startHouse); // todo random start house
        System.out.println();

    }

    void applyTreasure() {
        System.out.println("main.GamePanel.applyTreasure");
        System.out.println();
    }

    void applyTrap() {
        System.out.println("main.GamePanel.applyTrap");
        System.out.println();
    }

    void applyCastle() {
        System.out.println("main.GamePanel.applyCastle");
        System.out.println();
    }

    void applyMarket() {
        System.out.println("main.GamePanel.applyMarket");
        System.out.println();
    }

    void applyLoot() {
        System.out.println("main.GamePanel.applyLoot");
        System.out.println();
    }

    private boolean isTrace(int x, int y) {
        for (int i = 0; i < nTrace; i++) {
            if (traces[i]._x == x && traces[i]._y == y)
                return true;
        }
        return false;
    }

    void nextPlayer() { // make things ready for next player
        turn = (turn + 1) % NUMBER_OF_PLAYERS;
        diceNumber = 0;
        nTrace = 0;
        diceButton.setEnabled(true);
        System.out.println("players = " + players[turn]);
        System.out.println();

    }

    void checkGameStatus() { // todo check if current player has won


    }


    
    void moveListener() { // things to do after each move
        diceNumber--;
        nTrace++;
        if (diceNumber == 0) { // trigger at the end of the move
            checkHouse();
            nextPlayer();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // simplify variables:
        Player player = players[turn];

        // dice:
        if (e.getSource() == diceButton) {
            diceNumber = diceMap.throwDice();
            diceButton.setEnabled(false);
            repaint();
        }

        // moves:
        else if (e.getSource() == movePanel.upButton && diceNumber != 0) { // go up
            traces[nTrace].setTrace(player.x, player.y, player._x, player._y);

            player.y -= boardMap.UNIT_SIZE;
            player._y--;

            moveListener();
            repaint();

        } else if (e.getSource() == movePanel.rightButton && diceNumber != 0) { // go right
            traces[nTrace].setTrace(player.x, player.y, player._x, player._y);

            player.x += boardMap.UNIT_SIZE;
            player._x++;

            moveListener();
            repaint();

        } else if (e.getSource() == movePanel.downButton && diceNumber != 0) { // go down
            traces[nTrace].setTrace(player.x, player.y, player._x, player._y);

            player.y += boardMap.UNIT_SIZE;
            player._y++;

            moveListener();
            repaint();

        } else if (e.getSource() == movePanel.leftButton && diceNumber != 0) { // go left
            traces[nTrace].setTrace(player.x, player.y, player._x, player._y);

            player.x -= boardMap.UNIT_SIZE;
            player._x--;

            moveListener();
            repaint();
        }

    }
}

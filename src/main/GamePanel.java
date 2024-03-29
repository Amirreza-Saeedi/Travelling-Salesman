package main;

import consts.GameConstants;
import element.*;
import menu.*;
import shopping.ShoppingDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.ceil;
import static java.lang.Math.max;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final GameFrame frame;
    //screen and map sizes
    private static final int FRAME_WIDTH = 1500;
    private static final double FRAME_RATIO = 4.0 / 7;
    private static final int FRAME_HEIGHT = (int) (FRAME_WIDTH * FRAME_RATIO);
    public final Dimension SCREEN_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    private static final double RATIO_OF_MAP_TO_FRAME = 3.0 / 5;
    private static final int BOARD_WIDTH = (int) (FRAME_WIDTH * RATIO_OF_MAP_TO_FRAME);
    private static final int BOARD_HEIGHT = FRAME_HEIGHT;
//    public final Dimension BOARD_SIZE = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    public final int UNIT_SIZE;


    //    JPanel mapPanel; // TODO convert mapRect to panel?
    ScoreboardPanel scoreboardPanel;
    StatePanel statePanel;
    Board boardPanel;

    private final Random random = new Random();
    private Castle castle;
    private Loot[] loots;
    private Market[] markets;
    private Trap[] traps;
    private Treasure[] treasures;
    private Wall[] walls;
    private StartHouse startHouse; // TODO provide more than one house, and let the player try his chance and throw dice to choose one of them

    private Player[] players; // TODO array or not?
    private DicePanel dicePanel;
    private byte diceNumber;

    private MovePanel movePanel;
    private Trace[] traces;
    private int nTrace;

    public int turn = GameConstants.PLAYER_1;;
    private int curQuest = 0;
    private int[] questList; // shuffled list of treasures id

    Setting setting = Setting.getInstance();


    enum Status {
        END,
        CONTINUE;

    };
    Status status = Status.CONTINUE;
    private boolean isEnded = false;


    public GamePanel(GameFrame frame) {
        // create main panel
        super();
        this.setPreferredSize(SCREEN_SIZE);
        this.setSize(SCREEN_SIZE);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(this);
        this.setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.frame = frame;

        // add board panel to the center
        newBoard();
        UNIT_SIZE = boardPanel.UNIT_SIZE;

        // put elements on the board
        newElements();
        newPlayers();
        newTraces();

        // add side panels
        newDice();
        newMovePanel();
        newScoreboard();
        newSidePanel();

        newQuest();

        // game settings
        this.turn = GameConstants.PLAYER_1;
    }


    public void newBoard() { // panel at center
        int deltaWidth = FRAME_WIDTH - BOARD_WIDTH;
        boardPanel = new Board(new Rectangle(deltaWidth / 2, 0, BOARD_WIDTH, BOARD_HEIGHT),
                new Dimension(getWidth(), getHeight()));
//        boardPanel = new Board(new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }


    void newDice() { // panel at lower-left
        // init dice obj
        int width = boardPanel.x;
        int height = FRAME_HEIGHT / 2;
        int x = 0;
        int y = FRAME_HEIGHT / 2;
        dicePanel = new DicePanel(new Rectangle(x, y, width, height));
        add(dicePanel);

        // init dice button
        dicePanel.getButton().addActionListener(this);
    }

    private void newMovePanel() { // panel in lower-right
        int     x = boardPanel.x + boardPanel.width,
                y = FRAME_HEIGHT / 2;
        int     width = FRAME_WIDTH - x,
                height = FRAME_HEIGHT - y;
        movePanel = new MovePanel(x, y, width, height);
        movePanel.upButton.addActionListener(this);
        movePanel.rightButton.addActionListener(this);
        movePanel.downButton.addActionListener(this);
        movePanel.leftButton.addActionListener(this);
        add(movePanel);

    }

    private void newScoreboard() { // panel at upper-right
        int     x = (int) (boardPanel.getX() + boardPanel.getWidth()),
                y = (int) boardPanel.getY();
        int     width = movePanel.getWidth(),
                height = movePanel.getY() - y;
        scoreboardPanel = new ScoreboardPanel(x, y, width, height, players);
        add(scoreboardPanel);
    }

    private void newSidePanel() {
        int width = boardPanel.x;
        int height = dicePanel.getY();
        statePanel = new StatePanel(new Rectangle(0, 0, width, height), players);
        add(statePanel);
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        // todo temp
        Rectangle r = Quest.getInstance().getTreasure();
        g.setColor(new Color(0x37FFFFFF, true));
        g.fillRect(r.x, r.y, r.width, r.height);

    }

    public void draw(Graphics g) {
        boardPanel.draw(g);
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
        if (!isEnded) {
            players[turn].draw(g);
        } else {
            for (Player player : players) {
                if (player.isPlaying()) {
                    player.draw(g);
                }
            }
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
        if (!isEnded) {
            for (Treasure treasure : treasures) {
                Player player = players[turn];
                int id = treasure.getId();
                if (player.locatedTreasures[id] && !treasure.isLooted()) {
                    treasure.draw(g);
                    treasure.setVisible(true);
                } else {
                    treasure.setVisible(false);
                }
            }
        } else {
            for (Treasure treasure : treasures) {
                treasure.draw(g);
                treasure.setVisible(true);
            }
        }
    }

    private void drawMarkets(Graphics g) { // show always
        for (Market market : markets) {
            market.draw(g);
        }
    }

    private void drawLoots(Graphics g) { // todo find better algorithm
        if (!isEnded) {
            for (Loot loot : loots) {
                if (loot.isVisible()) {
                    loot.draw(g);
                }
            }
        } else {
            for (Loot loot : loots) {
                if (!loot.isLooted()) {
                    loot.draw(g);
                    loot.setVisible(true);
                }
            }
        }
    }

    private void drawTraps(Graphics g) {
        if (!isEnded) {
            if (setting.getDifficulty() == GameConstants.MEDIUM || setting.getDifficulty() == GameConstants.HARD) { // show temporarily
                for (Trap trap : traps) { // todo player trap array
                    if (trap.isVisible()) {
                        trap.draw(g);
                        trap.setVisible(false);
                    }
                }
            } else if (setting.getDifficulty() == GameConstants.EASY) { // show permanently
                Player player = players[turn];
                for (int i = 0; i < setting.getNumberOfTraps(); ++i) {
                    if (player.locatedTraps[i]) {
                        traps[i].draw(g);
                        traps[i].setVisible(true);
                    } else {
                        traps[i].setVisible(false);
                    }

                }
            }
        } else {
            for (Trap trap : traps) {
                trap.draw(g);
                trap.setVisible(true);
            }
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
        dicePanel.update(diceNumber);
    }



    private void newTraces() {
        traces = new Trace[6];
        for (int i = 0; i < traces.length; i++) {
            traces[i] = new Trace(new Dimension(UNIT_SIZE, UNIT_SIZE));
        }
        nTrace = 0;
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
        int x = setting.getBoardSize() / 2;
        int y = x;

        castle = new Castle(boardPanel.xAxis[x], boardPanel.yAxis[y], UNIT_SIZE, UNIT_SIZE);
        castle.set_x(x);
        castle.set_y(y);
        boardPanel.board[x][y] = GameConstants.CASTLE; // add house
        this.add(castle.getLabel()); // add label
    }

    private void newTreasures() { // put the treasures in random positions TODO improve algorithm
        treasures = new Treasure[setting.getNumberOfTreasures()];
        int j = random.nextInt(4); // determine which area to start
        Point point = new Point();

        for (int i = 0; i < treasures.length; i++) {
            justRandomization(j, point); // determine coordinate

            treasures[i] = new Treasure(boardPanel.xAxis[point.x], boardPanel.yAxis[point.y], UNIT_SIZE, UNIT_SIZE,
                    i, point.x, point.y, Treasure.namesList[i], Treasure.valueList[i]); // set specific name and value

            boardPanel.board[point.x][point.y] = GameConstants.TREASURE;
            this.add(treasures[i].getLabel()); // add label
            j++;
        }

    }

    private void newMarkets() { // put markets in random positions TODO optimize the algorithm
        markets = new Market[setting.getNumberOfMarkets()];
        int j = random.nextInt(4); // determine which area to start
        Point point = new Point();

        for (int i = 0; i < markets.length; i++) {
            justRandomization(j, point); // determine coordinate

            markets[i] = new Market(boardPanel.xAxis[point.x], boardPanel.yAxis[point.y], UNIT_SIZE, UNIT_SIZE,
                                    i, point.x, point.y);

            boardPanel.board[point.x][point.y] = GameConstants.MARKET; // add house
            this.add(markets[i].getLabel()); // add label
            j++;
        }
    }

    private void newLoots() {
        loots = new Loot[setting.getNumberOfLoots()];
        int     x = 0,
                y = 0;

        for (int i = 0; i < loots.length; i++) {
            do { // todo add a appropriate func
                x = random.nextInt(setting.getBoardSize());
                y = random.nextInt(setting.getBoardSize());
            } while (boardPanel.board[x][y] != GameConstants.EMPTY);

            int value = Loot.createValue();

            loots[i] = new Loot(boardPanel.xAxis[x], boardPanel.yAxis[y], UNIT_SIZE, UNIT_SIZE,
                                i, x, y, value);

            boardPanel.board[x][y] = GameConstants.LOOT; // add house
            this.add(loots[i].getLabel()); // add label
        }
    }

    private void newTraps() {
        traps = new Trap[setting.getNumberOfTraps()];
        int x = 0,
                y = 0;

        for (int i = 0; i < traps.length; i++) {
            do {
                x = random.nextInt(setting.getBoardSize());
                y = random.nextInt(setting.getBoardSize());
            } while (boardPanel.board[x][y] != GameConstants.EMPTY);

            int physicalDamage = Trap.createPhysicalDamage();
            int financialDamage = Trap.createFinancialDamage();
            traps[i] = new Trap(boardPanel.xAxis[x], boardPanel.yAxis[y], UNIT_SIZE, UNIT_SIZE,
                                i, x, y, physicalDamage, financialDamage);
            traps[i].classify();

            boardPanel.board[x][y] = GameConstants.TRAP; // add house
            this.add(traps[i].getLabel()); // add label
        }
    }

    private void newWalls() {
        walls = new Wall[setting.getNumberOfWalls()];
        int     x = 0,
                y = 0;

        for (int i = 0; i < walls.length; i++) {
            do {
                x = random.nextInt(setting.getBoardSize());
                y = random.nextInt(setting.getBoardSize());
            } while (boardPanel.board[x][y] != GameConstants.EMPTY);

            walls[i] = new Wall(boardPanel.xAxis[x], boardPanel.yAxis[y], UNIT_SIZE, UNIT_SIZE,
                                i, x, y);

            boardPanel.board[x][y] = GameConstants.WALL; // add house
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
            house = random.nextInt(setting.getBoardSize());

            switch (side) { // determine the coordinate
                case GameConstants.UP: // y = 0
                    if (!isWall(house, 0, true)) {
                        sw = false;
                    }
                    point.x = boardPanel.xAxis[house];
                    point.y = boardPanel.yAxis[0] - UNIT_SIZE;
                    x = house;
                    y = -1;
                    break;
                case GameConstants.RIGHT: // x = max
                    if (!isWall(setting.getBoardSize() - 1, house, true)) {
                        sw = false;
                    }
                    point.x = boardPanel.xAxis[setting.getBoardSize() - 1] + UNIT_SIZE;
                    point.y = boardPanel.yAxis[house];
                    x = setting.getBoardSize();
                    y = house;
                    break;
                case GameConstants.DOWN: // y = max
                    if (!isWall(house, setting.getBoardSize() - 1, true)) {
                        sw = false;
                    }
                    point.x = boardPanel.xAxis[house];
                    point.y = boardPanel.yAxis[setting.getBoardSize() - 1] + UNIT_SIZE;
                    x = house;
                    y = setting.getBoardSize();
                    break;
                case GameConstants.LEFT: // x = 0
                    if (!isWall(0, house, true)) {
                        sw = false;
                    }
                    point.x = boardPanel.xAxis[0] - UNIT_SIZE;
                    point.y = boardPanel.yAxis[house];
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
        players = new Player[setting.getNumberOfPlayers()];

        for (int i = 0; i < setting.getNumberOfPlayers(); i++) {
            String title = "Player " + (i + 1);
            players[i] = new Player(startHouse, i, title);
        }

        players[turn].setTurn(true);
    }

    private void newQuest() {
        shuffleQuests();
        nextQuest();
    }

    private void shuffleQuests() { // shuffles array
        questList = new int[setting.getNumberOfTreasures()];
        for (int i = 0; i < questList.length; i++) { // init
            questList[i] = i;
        }
        for (int i = 0; i < questList.length; i++) { // shuffle
            int j = random.nextInt(questList.length);
            int temp = questList[i];
            questList[i] = questList[j];
            questList[j] = temp;
        }

    }

    private void nextQuest() {
        System.out.println("GamePanel.nextQuest");
        if (curQuest < questList.length) {
            Treasure treasure = treasures[questList[curQuest]];
            Quest.getInstance().setQuest(treasure); // set quest
            boardPanel.board[treasure._x][treasure._y] = GameConstants.QUEST; // add location to board

            ++ curQuest;
        } else {
            Quest.getInstance().setQuest(null);
        }
    }


    private boolean isWall(int x, int y, boolean optimized) {
        if (optimized) {
            return (boardPanel.board[x][y] == GameConstants.WALL);
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
                    x = random.nextInt(setting.getBoardSize() / 2);
                    y = random.nextInt(setting.getBoardSize() / 2);
                } while (boardPanel.board[x][y] != GameConstants.EMPTY);
                break;
            case 1:
                do { // x1, y2 upper-right
                    x = random.nextInt(setting.getBoardSize() / 2);
                    y = (int) (random.nextInt(setting.getBoardSize() / 2) + ceil(setting.getBoardSize() / 2.0));
                } while (boardPanel.board[x][y] != GameConstants.EMPTY);
                break;
            case 2:
                do { // x2, y1 lower-left
                    x = (int) (random.nextInt(setting.getBoardSize() / 2) + ceil(setting.getBoardSize() / 2.0));
                    y = random.nextInt(setting.getBoardSize() / 2);
                } while (boardPanel.board[x][y] != GameConstants.EMPTY);
                break;
            case 3:
                do { // x2, y2 lower-right
                    x = (int) (random.nextInt(setting.getBoardSize() / 2) + ceil(setting.getBoardSize() / 2.0));
                    y = (int) (random.nextInt(setting.getBoardSize() / 2) + ceil(setting.getBoardSize() / 2.0));
                } while (boardPanel.board[x][y] != GameConstants.EMPTY);
                break;

        }

        p.x = x;
        p.y = y;
    }

    private void checkPossibleMoves() { // set buttons status & may perform penalty
        int     x = players[turn]._x,
                y = players[turn]._y;

        // start houses:
        if (x == -1) { // only right
            movePanel.upButton.setEnabled(false);
            movePanel.rightButton.setEnabled(true);
            movePanel.downButton.setEnabled(false);
            movePanel.leftButton.setEnabled(false);

        } else if (x == setting.getBoardSize()) { // only left
            movePanel.upButton.setEnabled(false);
            movePanel.rightButton.setEnabled(false);
            movePanel.downButton.setEnabled(false);
            movePanel.leftButton.setEnabled(true);

        } else if (y == -1) { // only down
            movePanel.upButton.setEnabled(false);
            movePanel.rightButton.setEnabled(false);
            movePanel.downButton.setEnabled(true);
            movePanel.leftButton.setEnabled(false);

        } else if (y == setting.getBoardSize()) { // only up
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

            if (x == setting.getBoardSize() - 1 || isWall(x + 1, y, false) || isTrace(x + 1, y)) { // right dir
                movePanel.rightButton.setEnabled(false);
            } else {
                movePanel.rightButton.setEnabled(true);
            }

            if (y == setting.getBoardSize() - 1 || isWall(x, y + 1, false) || isTrace(x, y + 1)) { // down dir
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

    void checkMovePenalty() { // checkHouse mustn't be called in these circumstances
        /*punish player,
        * send him to start house,
        * skip remaining moves*/
        if (!movePanel.upButton.isEnabled() && !movePanel.rightButton.isEnabled() &&
                !movePanel.downButton.isEnabled() && !movePanel.leftButton.isEnabled()) {
            int fine = players[turn].applyPenalty();
            scoreboardPanel.updateState();
            repaint();
            JOptionPane.showMessageDialog(frame, "Don't trap yourself!\n" + fine + " coins lost. Go back to start.",
                    "Penalty", JOptionPane.ERROR_MESSAGE);
            nextPlayer();
        }
    }

    void checkHouse() { // applies appropriate behavior

//        if (checkFight()) { // first check for fight
//            return; // if lost don't check houses
//        }
        byte fightResult = checkFight();
        if (fightResult == -1) { // prevent index out of bounds bug
            return;
        }

        Player curPlayer = players[turn];
        switch (boardPanel.board[curPlayer._x][curPlayer._y]) { // then for houses
            case GameConstants.LOOT:
                applyLoot();
                break;
            case GameConstants.TRAP:
                applyTrap();
                break;
            case GameConstants.QUEST: // just the quest
                applyTreasure();
                break;
            case GameConstants.CASTLE:
                applyCastle();
                break;
            case GameConstants.MARKET:
                applyMarket();
                break;
            default:
                if (fightResult == 0) { // if fight didn't occur
                    JOptionPane.showConfirmDialog(frame, "Next player...", "",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                }
        }


    }

    private byte checkFight() {
        Player curPlayer = players[turn];
        for (int i = turn, j = 0; j < setting.getNumberOfPlayers() - 1; ++i, ++j) { // first check for fight
            int nxtTurn = (i + 1) % setting.getNumberOfPlayers();

            if (curPlayer._x == players[nxtTurn]._x && curPlayer._y == players[nxtTurn]._y) {
                boolean result = applyFight(players[nxtTurn]); // fight
                scoreboardPanel.updateState();
                statePanel.update(players[nxtTurn]);

                if (!result) { // if defeated
                    JOptionPane.showMessageDialog(frame, String.format("\"%s\" beat you!", players[nxtTurn].getTitle()),
                            "Fight", JOptionPane.ERROR_MESSAGE);
                    return -1;
                } else {
                    JOptionPane.showMessageDialog(frame, String.format("You beat \"%s\"!", players[nxtTurn].getTitle()),
                            "Fight", JOptionPane.INFORMATION_MESSAGE);
                    return 1;
                }

            }
        }
        return 0;
    }

    boolean applyFight(Player opponent) {
        return players[turn].fight(opponent); // todo random start house
    }

    void applyTreasure() { // called if and only if treasure been set on quest
        Player player = players[turn];
        Treasure treasure = (Treasure) findElement(new Treasure(), player._x, player._y);
        if (treasure == null) { // check for err
            System.err.println("GamePanel.applyTreasure\nnull");
            return;
        }

        if (!player.locatedTreasures[treasure.getId()]) { // todo find a better arrangement
            System.out.println("main.GamePanel.applyTreasure");
            player.locatedTreasures[treasure.getId()] = true;

            statePanel.update(treasure);
            repaint();

            JOptionPane.showMessageDialog(this,
                    String.format("%s founded! Deliver it to castle to earn its prize.", treasure.getTitle()),
                    "Treasure", JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.println();
    }

    void applyTrap() {
        System.out.println("main.GamePanel.applyTrap");
        Player player = players[turn];

        Trap trap = (Trap) findElement(new Trap(), player._x, player._y);

        if (trap != null) { // todo what?
            trap.setVisible(true);
            player.applyTrap(trap);
            player.locatedTraps[trap.getId()] = true;
            scoreboardPanel.updateState();
            statePanel.update(trap);

            repaint();

            JOptionPane.showMessageDialog(this,
                    String.format("Trap triggered!\n%d coins lost,\n and %d damage taken.",
                            trap.getFinancialDamage(), trap.getPhysicalDamage()),
                    trap.getTitle(),
                    JOptionPane.INFORMATION_MESSAGE);
            trap.setVisible(false);

        } else {
            System.err.println("GamePanel.applyTrap\nnull");
        }
    }

    void applyCastle() { // is called when
        System.out.println("main.GamePanel.applyCastle");
        Player player = players[turn];
        int questID = Quest.getInstance().getTreasure().getId();
        statePanel.update(castle);

        if (player.locatedTreasures[questID]) { // if player has treasure address
            Treasure treasure = treasures[questID];
            player.applyTreasure(treasure);
            treasure.setLooted(true);
            boardPanel.board[treasure._x][treasure._y] = GameConstants.EMPTY; // clear the house
            scoreboardPanel.updateState(); // update scoreboard

            repaint(); // todo cross the treasure image
//            todo send it from board to scoreboard

            JOptionPane.showMessageDialog(this,
                    String.format("%s delivered. %d coins earned.", treasure.getTitle(), treasure.getValue()),
                    "Castle", JOptionPane.INFORMATION_MESSAGE);

            nextQuest(); // start next quest
            checkGameStatus(); // check if game ended

        } else {
            JOptionPane.showMessageDialog(this,
                    "\"Hmm. Find wanted treasure then come back to earn your prize\".",
                    "Castle", JOptionPane.INFORMATION_MESSAGE);
        }

        System.out.println();
    }

    void applyMarket() {
        System.out.println("main.GamePanel.applyMarket");
        Treasure treasure = pickRandomTreasure();
        if (treasure == null) {
            System.err.println("GamePanel.applyMarket");
        }

        statePanel.update(markets[0]);
        new ShoppingDialog(frame, treasure, players[turn], scoreboardPanel); // todo casting is ok?

        System.out.println();
    }

    void applyLoot() {
        System.out.println("main.GamePanel.applyLoot");
        Player player = players[turn];
        Loot loot = (Loot) findElement(new Loot(), player._x, player._y);

        if (loot != null) {
            if (!loot.isLooted()) { // if it is not empty
                loot.setVisible(true);
                loot.setLooted(true); // todo why doesn't work?
                player.applyLoot(loot);
                boardPanel.board[player._x][player._y] = GameConstants.EMPTY; // free the house
                scoreboardPanel.updateState();
                statePanel.update(loot);

                repaint();

                JOptionPane.showMessageDialog(this,
                        String.format("Loot founded! %d coins gained.", loot.getValue()),
                        loot.getTitle(),
                        JOptionPane.INFORMATION_MESSAGE);
                loot.setVisible(false);
            }

        } else {
            System.err.println("GamePanel.applyLoot\nnull");
        }
    }

    private boolean isTrace(int x, int y) {
        for (int i = 0; i < nTrace; i++) {
            if (traces[i]._x == x && traces[i]._y == y)
                return true;
        }
        return false;
    }

    void nextPlayer() { // called after complete moves or wrong moves
        /*freeze the screen,
         *make things ready for next player.*/
        repaint();
        freeze(2000);

        players[turn].setTurn(false);
        System.out.println("player1 name = " + players[turn].getTitle());
        System.out.println("player1 turn = " + players[turn].isTurn());
        do {
            turn = (turn + 1) % setting.getNumberOfPlayers();
        } while (!players[turn].isPlaying());
        players[turn].setTurn(true);
        System.out.println("player2 name = " + players[turn].getTitle());
        System.out.println("player2 turn = " + players[turn].isTurn());

        statePanel.update();
        diceNumber = 0;
        nTrace = 0;
        dicePanel.getButton().setEnabled(true);
        System.out.println("players = " + players[turn]);
        System.out.println();

    }

    private void freeze(int delay) {
//        long startTime = System.currentTimeMillis();
//        while (System.currentTimeMillis() - startTime < delay) {
//            repaint();
//        }

//        java.util.Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                timer.cancel();
//            }
//        }, delay);


    }

    void checkGameStatus() { // called by apply treasure
        /* declare whether a player has won, lost, drawn
        * or should continue*/

        { // first set losers
            final int maxTreasures = getMaxTreasures();
            final int nRemainingTreasures = getNRemainingTreasures();
            // set losers
            System.out.println("looser loop:");
            for (Player player : players) {
                if (player.getNumberOfTreasures() + nRemainingTreasures < maxTreasures && player.isPlaying()) {
                    player.setState(GameConstants.LOST);
                    JOptionPane.showMessageDialog(this, "Player '" + player.getTitle() + "'lost!",
                            "", JOptionPane.ERROR_MESSAGE);
                    player.revive();
                    repaint();
                }
            }
        }

        if (getNAlivePlayers() == 1) { // set winner based on loser
            System.out.println("winner loop:");
            for (Player player : players) {
                if (player.isPlaying()) {
                    player.setState(GameConstants.WON);
                    JOptionPane.showMessageDialog(this, "Player '" + player.getTitle() + "' won!",
                            "", JOptionPane.PLAIN_MESSAGE);

                    endGame();
                    break;
                }
            }
            isEnded = true;

        } else if (getNRemainingTreasures() == 0 && getNAlivePlayers() != 1) { // draw
            System.out.println("drawer loop");
            for (Player player : players) {
                if (player.isPlaying()) {
                    player.setState(GameConstants.DRAWN);
                }
            }
            isEnded = true;
            JOptionPane.showMessageDialog(this, "Draw!");
            endGame();

        }

    }

    private void endGame() {
        /*remove dice and moves buttons' action listeners,
        * show the result,
        * stop timer*/
        for (JButton b : movePanel.getButtons()) {
            b.removeActionListener(this);
        }
        dicePanel.getButton().removeActionListener(this);
        scoreboardPanel.getTimer().stop();
        new ResultDialog(frame, players);
    }

    private int getNRemainingTreasures() { // todo or loop through array
        System.out.println("GamePanel.getNRemainingTreasures");
        int n = 0;
        for (Treasure treasure : treasures) {
            if (!treasure.isLooted())
                n++;
        }
        System.out.println("n = " + n);
        return n;
    }

    private int getMaxTreasures() {
        int max = 0;
        for (Player player : players) {
            if (max < player.getNumberOfTreasures())
                max = player.getNumberOfTreasures();
        }
        return max;
    }

    private int getNAlivePlayers() {
        int n = 0;
        for (Player player : players) {
            if (player.isPlaying()) {
                n++;
            }
        }
        return n;
    }

    private Element findElement(Element element, int x, int y) { // called in apply loot, trap and treasure
        Element[] elements;
        if (element instanceof Loot) {
            elements = loots;
        } else if (element instanceof Trap) {
            elements = traps;
        } else if (element instanceof Treasure) {
            elements = treasures;
        }
        else {
            System.err.println("GamePanel.findElement\nInvalid input");
            return null;
        }

        for (Element value : elements) { // looking for...
            if (x == value._x && y == value._y)
                return value;
        }

        System.err.println("GamePanel.findElement\nNot founded");
        return null;
    }

    Treasure pickRandomTreasure() {
        int i = random.nextInt(setting.getNumberOfTreasures());
        Player player = players[turn];
        for (int j = 0; j < setting.getNumberOfTreasures(); j++) {
            int index = i % setting.getNumberOfTreasures();
            if (!treasures[index].isLooted() && !player.locatedTreasures[index]) {
                return treasures[index];
            } else i++;
        }
        return null;
    }



    private void moveListener() { // things to do after each move
        diceNumber--;
        nTrace++;
        if (diceNumber == 0) { // trigger at the end of the move
            checkHouse();
            nextPlayer();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // main panel
        // simplify variables:
        Player player = players[turn];

        // dice:
        if (e.getSource() == dicePanel.getButton()) {
            diceNumber = (byte) dicePanel.throwDice();
            dicePanel.getButton().setEnabled(false);
            repaint();
        }

        // moves:
        else if (e.getSource() == movePanel.upButton && diceNumber != 0) { // go up
            traces[nTrace].setTrace(player.x, player.y, player._x, player._y);

            player.y -= boardPanel.UNIT_SIZE;
            player._y--;

            repaint();
            moveListener();
            repaint();

        } else if (e.getSource() == movePanel.rightButton && diceNumber != 0) { // go right
            traces[nTrace].setTrace(player.x, player.y, player._x, player._y);

            player.x += boardPanel.UNIT_SIZE;
            player._x++;

            repaint();
            moveListener();
            repaint();

        } else if (e.getSource() == movePanel.downButton && diceNumber != 0) { // go down
            traces[nTrace].setTrace(player.x, player.y, player._x, player._y);

            player.y += boardPanel.UNIT_SIZE;
            player._y++;

            repaint();
            moveListener();
            repaint();

        } else if (e.getSource() == movePanel.leftButton && diceNumber != 0) { // go left
            traces[nTrace].setTrace(player.x, player.y, player._x, player._y);

            player.x -= boardPanel.UNIT_SIZE;
            player._x--;

            repaint();
            moveListener();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("e.getKeyChar() = " + e.getKeyChar());
        System.out.println("e.getKeyLocation() = " + e.getKeyLocation());
        System.out.println("e.getKeyCode() = " + e.getKeyCode());

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }


    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37: // left arrow
                movePanel.leftButton.doClick();
                break;
            case 38: // up arrow
                movePanel.upButton.doClick();
                break;
            case 39: // right arrow
                movePanel.rightButton.doClick();
                break;
            case 40: // down arrow
                movePanel.downButton.doClick();
                break;
            case ' ':
                dicePanel.getButton().doClick();
                break;
            case KeyEvent.VK_ESCAPE:
                new PauseDialog(frame);
                break;
            case '\t':
                new ResultDialog(frame, players); // todo just set it visible?

        }
    }
}

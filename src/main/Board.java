package main;

import menu.Setting;
import menu.Theme;

import java.awt.*;

public class Board extends Rectangle {
    private final int BOARD_UNITS;
    public final int UNIT_SIZE;
    public final static Color BOARD_COLOR = new Color(0xFF7BC2BB);
    private final Rectangle BOARD = new Rectangle(); // the board including start house
//    private final Rectangle UNIT = new Rectangle(); // TODO necessary?
    int[][] board; // saves board houses
    int[] xAxis; // saves x axis coordinates
    int[] yAxis; // saves y axis coordinates
    private final Dimension parentSize;

    public Board(Rectangle r, Dimension parentSize) {
        /*converting to Panel results in bugs,
        * such as misplaced x any y-axis magnitudes
        * used in elements locations.*/
        super(r.x, r.y, r.width, r.height);
        this.parentSize = parentSize;
        BOARD_UNITS = Setting.getInstance().getBoardSize();

        // initialize the board
        int vSpace = 20;
        BOARD.height = r.height - vSpace; // will be modified
        UNIT_SIZE = BOARD.height / (BOARD_UNITS + 2); // start house margin considered

        BOARD.height -= BOARD.height % UNIT_SIZE; // modify; delete margins
        BOARD.width = BOARD.height;
        BOARD.setLocation(getXCenter() - BOARD.width / 2, getYCenter() - BOARD.height / 2);

        // initialize x-axis and y-axis coordinates
        xAxis = new int[BOARD_UNITS];
        yAxis = new int[BOARD_UNITS];

        int x0 = BOARD.x + UNIT_SIZE;
        int y0 = BOARD.y + UNIT_SIZE;
        for (int i = 0; i < BOARD_UNITS; i++) {
            xAxis[i] = x0 + i * UNIT_SIZE;
            yAxis[i] = y0 + i * UNIT_SIZE;
        }

        // initialize board units
        board = new int[BOARD_UNITS][BOARD_UNITS];
    }

    public void draw(Graphics g) {
        drawBackground(g);
        drawBoard(g);
    }

    private void drawBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        g2.setColor(Theme.BRIGHT_THEME.backColor);
        g2.fillRect(this.x, this.y, this.width, this.height);
        g2.setColor(Color.BLACK);
        g2.drawRect(this.x, this.y, this.width, this.height);
    }

    private void drawBoard(Graphics g) {
        // the board backGround
        g.setColor(BOARD_COLOR);
        g.fillRect(BOARD.x + UNIT_SIZE, BOARD.y + UNIT_SIZE,
                BOARD.width - 2 * UNIT_SIZE, BOARD.height - 2 * UNIT_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(BOARD.x + UNIT_SIZE, BOARD.y + UNIT_SIZE,
                BOARD.width - 2 * UNIT_SIZE, BOARD.height - 2 * UNIT_SIZE);

        // the board lines
        for (int i = 1; i < xAxis.length; i++) {
            g.drawLine(BOARD.x + UNIT_SIZE, yAxis[i], // horizontal divider lines
                    BOARD.x + BOARD.width - UNIT_SIZE, yAxis[i]);
            g.drawLine(xAxis[i], BOARD.y + UNIT_SIZE, // vertical divider lines
                    xAxis[i], BOARD.y + BOARD.height - UNIT_SIZE);
        }
    }

    private int getXCenter() {
        return parentSize.width / 2;
    }

    private int getYCenter() {
        return parentSize.height / 2;
    }

}

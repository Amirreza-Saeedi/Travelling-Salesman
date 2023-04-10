import java.awt.*;

public class BoardMap extends Rectangle {
    private final int BOARD_UNITS;
    public final int UNIT_SIZE;
    private final Rectangle BOARD = new Rectangle();
    private final Rectangle UNIT = new Rectangle(); // TODO necessary?
    int[][] board;
    int[] xAxis;
    int[] yAxis;

    public BoardMap(int x, int y, int width, int height, int boardUnits) {
        super(x, y, width, height);
        BOARD_UNITS = boardUnits;

        // initialize board
        int yDistance = this.height / 10; // TODO not a good mechanism
        BOARD.height = this.height - 2 * yDistance; // will be modified

        UNIT_SIZE = BOARD.height / BOARD_UNITS; // declare unit size

        // modify board based on unit size
        BOARD.height -= BOARD.height % UNIT_SIZE; // modify
        BOARD.width = BOARD.height;
        BOARD.y = this.y + yDistance;
        int deltaWidth = this.width - BOARD.width;
        BOARD.x = this.x + deltaWidth / 2;

        // initialize unit dimensions
        UNIT.width = UNIT.height = UNIT_SIZE;

        // initialize x-axis and y-axis coordinates
        xAxis = new int[BOARD_UNITS];
        yAxis = new int[BOARD_UNITS];

        for (int i = 0; i < BOARD_UNITS; i++) {
            xAxis[i] = BOARD.x + i * UNIT_SIZE;
            yAxis[i] = BOARD.y + i * UNIT_SIZE;
        }

        // initialize board units
        board = new int[BOARD_UNITS][BOARD_UNITS];
    }

    public void draw(Graphics g) {
        drawBackground(g);
        drawBoard(g);
        //drawElements(g);

    }

    private void drawBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        g2.setColor(Color.WHITE);
        g2.fillRect(this.x, this.y, this.width, this.height);
        g2.setColor(Color.BLACK);
        g2.drawRect(this.x, this.y, this.width, this.height);
    }

    private void drawBoard(Graphics g) {
        //g.setColor(new Color(0x3AC0AD));
        g.setColor(new Color(0xFF7BC2BB));
        g.fillRect(BOARD.x, BOARD.y, BOARD.width, BOARD.height);
        g.setColor(Color.BLACK);
        g.drawRect(BOARD.x, BOARD.y, BOARD.width, BOARD.height);

        // draw the board lines
        for (int i = 1; i < BOARD_UNITS; i++) {
            g.drawLine(BOARD.x, BOARD.y + i * UNIT_SIZE, // horizontal divider lines
                    BOARD.x + BOARD.width, BOARD.y + i * UNIT_SIZE);
            g.drawLine(BOARD.x + i * UNIT_SIZE, BOARD.y, // vertical divider lines
                    BOARD.x + i * UNIT_SIZE, BOARD.y + BOARD.height);
        }
    }

    private void drawElements(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(xAxis[6], yAxis[4], UNIT.width, UNIT.height);
        g.fillRect(xAxis[0], yAxis[0], UNIT.width, UNIT.height);
        g.fillRect(xAxis[1], yAxis[1], UNIT.width, UNIT.height);
    }
}

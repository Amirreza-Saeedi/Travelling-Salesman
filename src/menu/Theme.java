package menu;

import java.awt.*;

public class Theme {
    public final Color backColor;
    public final Color foreColor;

    Theme(Color backColor, Color foreColor) {
        this.backColor = backColor;
        this.foreColor = foreColor;
    }
    public static final Theme DARK_THEME = new Theme(new Color(0x424242), new Color(0xF1F1F1));
    public static final Theme BRIGHT_THEME = new Theme(new Color(0xF8D980), Color.BLACK);
}

package element;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Market extends Element {

    public Market(int x, int y, int width, int height, int id, int _x, int _y) {
        super(x, y, width, height, id, _x, _y, "Market");
        this.setColor(new Color(0xFFC002));
        newLabel(getTitle()); // create label

        setVisible(true);
    }


}



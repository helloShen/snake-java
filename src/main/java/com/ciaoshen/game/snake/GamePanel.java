package com.ciaoshen.game.snake;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {

    private static final Color DARK_GRAY = new Color(50, 50, 50);

    public GamePanel() {
        this.setBackground(DARK_GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}

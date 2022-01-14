package com.ciaoshen.game.snake;

import javax.swing.JFrame;
import java.awt.*;

public class GameFrame extends JFrame {

    // hard coded parameters
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;

    public GameFrame() throws HeadlessException {
        // frame config
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.setLocationRelativeTo(null);  // to appear at the center of the screen
        // panel config
        GamePanel panel = new GamePanel();
        this.add(panel);
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

    }
}

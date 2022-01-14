package com.ciaoshen.game.snake;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    // hard coded parameters
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 800;

    public GameFrame() throws HeadlessException {
        // panel config (!!make sure add panel before configuring the frame)
        GamePanel panel = new GamePanel();
        this.add(panel);
        // frame config
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.pack(); // let frame fit for its components
        this.setLocationRelativeTo(null);  // to appear at the center of the screen
    }
}

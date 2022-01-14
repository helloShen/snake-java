package com.ciaoshen.game.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    // panel size
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    // colors
    private static final Color BOARD_GRAY = new Color(50, 50, 50);
    private static final Color SNAKE_CYAN = new Color(0, 255, 255);
    private static final Color SNAKE_HEAD = new Color(0, 200, 255);
    private static final Color APPLE_PINK = new Color(255, 105,180);
    // grids
    private static final int GRID_SIZE = 15;
    private static final int GRIDS_X = PANEL_WIDTH / GRID_SIZE;
    private static final int GRIDS_Y = PANEL_HEIGHT / GRID_SIZE;
    // 4 directions
    private static final char RIGHT = 'R';
    private static final char LEFT = 'L';
    private static final char UP = 'U';
    private static final char DOWN = 'D';
    // time frame
    private static final int TIMEFRAME = 100;
    // snake body
    private int snakeSize = 5;
    private int[][] snake = new int[GRIDS_X * GRIDS_Y][2];
    private final int SNAKE_INIT_X = (GRIDS_X / 2 - 1) * GRID_SIZE;
    private final int SNAKE_INIT_Y = (GRIDS_Y / 2 - 1) * GRID_SIZE;
    // snake states
    private static char direction = RIGHT;
    // apple
    private int apple_x;
    private int apple_y;
    // game states
    private boolean running = false;
    // utils
    private Timer timer;
    private Random random;

    public GamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setBackground(BOARD_GRAY);
        this.setFocusable(true);
        random = new Random(System.currentTimeMillis());
        gameStart();
    }

    // start the game
    public void gameStart() {
        // init snake
        for (int i = 0; i < snakeSize; i++) {
            snake[i][0] = SNAKE_INIT_X - i * GRID_SIZE;
            snake[i][1] = SNAKE_INIT_Y;
        }
        // init apple
        newApple();
        // start game
        running = true;
        timer = new Timer(TIMEFRAME, this);
        timer.start();
    }

    // draw the panel for each time frame
    private void draw(Graphics g) {
        // snake
        g.setColor(SNAKE_HEAD); // snake head
        g.fillOval(snake[0][0], snake[0][1], GRID_SIZE, GRID_SIZE);
        for (int i = 1; i < snakeSize; i++) { // snake body
            g.setColor(SNAKE_CYAN);
            g.fillOval(snake[i][0], snake[i][1], GRID_SIZE, GRID_SIZE);
        }
        // apple
        g.setColor(APPLE_PINK);
        g.fillOval(apple_x, apple_y, GRID_SIZE, GRID_SIZE);
    }

    // snake move (modify snake[] array)
    public void move() {
        // right shift snake[] array to cut the tail
        for (int j = 0; j < 2; j++) {
            for (int i = snakeSize - 1; i > 0; i--) {
                snake[i][j] = snake[i - 1][j];
            }
        }
        // repositioning the snake head
        switch (direction) {
            case RIGHT:
                snake[0][0] += GRID_SIZE; break;
            case LEFT:
                snake[0][0] -= GRID_SIZE; break;
            case UP:
                snake[0][1] -= GRID_SIZE; break;
            case DOWN:
                snake[0][1] += GRID_SIZE; break;
        }
    }

    private void newApple() {
        apple_x = random.nextInt(GRIDS_X) * GRID_SIZE;
        apple_y = random.nextInt(GRIDS_Y) * GRID_SIZE;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // timer call this listener method every 75 millisecond
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

}

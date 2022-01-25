package com.ciaoshen.game.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel {

    // panel size
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    // colors
    private static final Color BOARD_GRAY = new Color(50, 50, 50);
    private static final Color SNAKE_CYAN = new Color(0, 230, 230);
    private static final Color APPLE_PINK = new Color(255, 105,180);
    private static final Color TEXT_WHITE = new Color(255, 255,255);
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
    private static final int SNAKE_INIT_SIZE = 5;
    private int snakeSize = 0;
    private final int[][] snake = new int[GRIDS_X * GRIDS_Y][2];
    // snake states
    private char direction = RIGHT;
    // apple
    private final int[] apple = new int[2];
    // game status
    private enum Mode { WELCOME, GAME_RUNNING, GAME_POSED, GAME_OVER }
    private Mode mode;
    private boolean canRedirect = true; // if false, direction can NOT be changed
    private long timeStart = 0; // time consumed for each game
    private long timeEnd = 0;
    // utils
    private final Timer timer;
    private final Random random;

    public GamePanel() {
        // panel config
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setBackground(BOARD_GRAY);
        this.setFocusable(true);
        // utils setting
        random = new Random(System.currentTimeMillis());
        // binding event listeners
        this.addKeyListener(new MyKeyAdapter()); // bind KeyListener to panel
        timer = new Timer(TIMEFRAME, new MyTimerAdapter()); // bind an ActionListener to timer as an ActionListener
        /*
         * !!IMPORTANT!!
         * Make sure to initiate to "Welcome Interface Mode" by default.
         * Cause JFrame.setVisible() will automatically invoke panel.paintComponent() method to draw components.
         */
        mode = Mode.WELCOME;
    }

    /* reinitialize the snake, the apple, and restart the timer. */
    private void gameStart() {
        // init snake
        snakeSize = SNAKE_INIT_SIZE;
        for (int i = 0; i < snakeSize; i++) {
            snake[i][0] = 0;
            snake[i][1] = (GRIDS_Y / 2 - 1) * GRID_SIZE;
        }
        direction = RIGHT;
        // init apple
        newApple();
        // start the game
        mode = Mode.GAME_RUNNING;
        canRedirect = true;
        timeEnd = 0;
        timeStart = System.currentTimeMillis();
        timer.start();
    }

    /* stop timer, and draw "GAME OVER" on the panel */
    private void gameOver() {
        timer.stop();
        timeEnd = System.currentTimeMillis();
        mode = Mode.GAME_OVER;
        // draw "GAME OVER"
        repaint();
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
        // now player is free to change direction
        canRedirect = true;
    }

    /* create next apple at a random position */
    private void newApple() {
        apple[0] = random.nextInt(GRIDS_X) * GRID_SIZE;
        apple[1] = random.nextInt(GRIDS_Y) * GRID_SIZE;
    }

    /* check if snake's head touch the apple */
    private boolean reachApple() {
        return snake[0][0] == apple[0] && snake[0][1] == apple[1];
    }

    /* snake grows up and create a new apple */
    private void eatApple() {
        snake[snakeSize][0] = snake[snakeSize - 1][0];
        snake[snakeSize][1] = snake[snakeSize - 1][1];
        snakeSize++;
        newApple();
    }

    /* snake dies when,
     *  1. head touches the board,
     *  2. head cuts it's own body
     */
    private boolean checkCollisions() {
        if (snake[0][0] < 0 || snake[0][0] >= PANEL_WIDTH) return true;
        if (snake[0][1] < 0 || snake[0][1] >= PANEL_HEIGHT) return true;
        for (int i = 1; i < snakeSize; i++) {
            if (snake[0][0] == snake[i][0] && snake[0][1] == snake[i][1]) return true;
        }
        return false;
    }

    // draw the panel for each time frame
    private void draw(Graphics g) {
        // snake head
        g.setColor(SNAKE_CYAN);
        g.fillRect(snake[0][0], snake[0][1], GRID_SIZE, GRID_SIZE);
        // snake body
        for (int i = 1; i < snakeSize; i++) {
            g.setColor(SNAKE_CYAN);
            g.fillRect(snake[i][0], snake[i][1], GRID_SIZE, GRID_SIZE);
        }
        // apple
        g.setColor(APPLE_PINK);
        g.fillOval(apple[0], apple[1], GRID_SIZE, GRID_SIZE);
    }

    // Add a pause label to the panel
    private void drawPause(Graphics g) {
        draw(g);
        g.setColor(TEXT_WHITE);
        g.fillRect(PANEL_WIDTH / 2 - (GRID_SIZE * 2), PANEL_HEIGHT / 2 - (GRID_SIZE * 2), GRID_SIZE, GRID_SIZE * 4);
        g.fillRect(PANEL_WIDTH / 2 + GRID_SIZE, PANEL_HEIGHT / 2 - (GRID_SIZE * 2), GRID_SIZE, GRID_SIZE * 4);
    }

    /* draw the welcome interface */
    private void drawWelcome(Graphics g) {
        g.setColor(TEXT_WHITE);
        g.setFont(new Font("courier", Font.PLAIN, 50));
        g.drawString("Hungry Snake", 20, 225);
        g.setFont(new Font("courier", Font.PLAIN, 15));
        g.drawString("Using UP, DOWN, RIGHT and LEFT arrow to control the snake.", 20, 275);
        g.drawString("Press SPACE to pause the game.", 20, 325);
        g.setFont(new Font("courier", Font.PLAIN, 20));
        g.drawString("[Press ENTER to start!]", 20, 450);
    }

    /* show GAME OVER and the score */
    private void drawGameOver(Graphics g) {
        g.setColor(TEXT_WHITE);
        g.setFont(new Font("courier", Font.PLAIN, 50));
        g.drawString("GAME OVER", 100, 225);
        g.setFont(new Font("courier", Font.PLAIN, 25));
        String score = String.format("Score: %d", snakeSize);
        double time = ((double)(timeEnd - timeStart)) / 1000;
        String timeStr = String.format("Time: %f\n", time);
        g.drawString(score, 100, 275);
        g.drawString(timeStr, 100, 325);
        g.setFont(new Font("courier", Font.PLAIN, 20));
        g.drawString("[Press ENTER to try again!]", 100, 450);
    }

    /*
     * This method is NOT supposed to be invoked directly by programmer.
     * JPanel.repaint() method will ask panel to invoke this method for you.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(mode) {
            case WELCOME:
                drawWelcome(g);
                break;
            case GAME_RUNNING:
                draw(g);
                break;
            case GAME_OVER:
                drawGameOver(g);
                break;
            case GAME_POSED:
                drawPause(g);
                break;
        }
    }

    /* My implementation of ActionListener interface */
    private class MyTimerAdapter implements ActionListener {
        /*
         * A callback ActionEvent handler method defined by ActionListener interface.
         * Timer thread will periodically callback this method.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mode == Mode.GAME_RUNNING) {
                move();
                if (checkCollisions()) {
                    gameOver();
                    return;
                }
                if (reachApple()) {
                    eatApple();
                }
                repaint();
            }
        }
    }

    /* Local KeyListener implementation. */
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (canRedirect && direction != 'R') {
                        direction = 'L';
                        canRedirect = false;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (canRedirect && direction != 'L') {
                        direction = 'R';
                        canRedirect = false;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (canRedirect && direction != 'D') {
                        direction = 'U';
                        canRedirect = false;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (canRedirect && direction != 'U') {
                        direction = 'D';
                        canRedirect = false;
                    }
                    break;
                case KeyEvent.VK_SPACE: // pause the game
                    if (mode == Mode.GAME_RUNNING) {
                        // In pause mode, timer ActionEvent handler do nothing. repaint() method need to be called manually.
                        mode = Mode.GAME_POSED;
                        repaint();
                    } else if (mode == Mode.GAME_POSED) {
                        mode = Mode.GAME_RUNNING;
                        repaint();
                    }
                    break;
                case KeyEvent.VK_ENTER: // restart the game
                    if (mode == Mode.WELCOME) { // prevent user from restarting game when game is running
                        repaint();
                        gameStart();
                    } else if (mode == Mode.GAME_OVER) {
                        mode = Mode.WELCOME;
                        repaint();
                    }
                    break;
                default:
                    // do nothing
            }
        }
    }
}

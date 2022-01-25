package com.ciaoshen.game.snake;

import javax.swing.*;

public class SnakeGame implements Runnable {

    private GameFrame board;

    public SnakeGame() {
        this.board = new GameFrame();
    }

    @Override
    public void run() {
        // Invoked on the event dispatching thread.
        // Construct and show GUI.
    }

    /*
     * Calls to an application's main method, or methods in Applet,
     * are not invoked on the event dispatching thread. As such, care
     * must be taken to transfer control to the event dispatching
     * thread when constructing
     */
    public static void main(String[] args) {
        // Invoke SnakeGame on the event dispatching thread.
        SwingUtilities.invokeLater(new SnakeGame());
    }

}

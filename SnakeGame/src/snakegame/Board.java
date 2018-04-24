package snakegame;

import java.awt.EventQueue;
import java.awt.KeyEventPostProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Board {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                JFrame frame = new BoardFrame();

                frame.setTitle("Snake");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setVisible(true);

            }
        });
    }

}

class BoardFrame extends JFrame {

    private Snake snk;

    public static final int INTERVAL = Configure.INTERVAL;

    // read the speed of the snake from the config
    public BoardFrame() {

        snk = new Snake();

        snk.setFood(new Food().getSnake(snk.getSnakeBody()));

        final KeyboardFocusManager manager = KeyboardFocusManager
                .getCurrentKeyboardFocusManager();
        // keyboard listener

        new Thread(new Runnable() {

            public void run() {

                while (true) {
                    BoardComponent bc = new BoardComponent();
                    bc.setSnake(snk);
                    add(bc);

                    MyKeyEventPostProcessor mke = new MyKeyEventPostProcessor();
                    mke.setSnk(snk);
                    manager.addKeyEventPostProcessor(mke);

                    try {
                        Thread.sleep(INTERVAL);
                        // stop motion
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    snk.snakeMove();

                    pack();

                }
            }
        }).start();

    }
}

class MyKeyEventPostProcessor implements KeyEventPostProcessor {

    private Snake snk;

    public boolean postProcessKeyEvent(KeyEvent e) {

        Direction dir = null;
        //current location sotrage
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dir = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                dir = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                dir = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                dir = Direction.RIGHT;
                break;
        }

        if (dir != null) {
            snk.setMoveDir(dir);
        }
        // log into the current location storage
        return true;
    }

    public void setSnk(Snake snk) {
        this.snk = snk;
    }

}

class BoardComponent extends JComponent {

    public static final int Width = Configure.WIDTH;
    public static final int Height = Configure.HEIGTH;
    public static final int TileWidth = Configure.TILE_WIDTH;
    public static final int TileHeight = Configure.TILE_HEIGHT;
    public static final int Row = Configure.ROW;
    public static final int Column = Configure.COL;
    private static final int XOffset = (Width - Column * TileWidth) / 2;
    private static final int YOffset = (Height - Row * TileHeight) / 2;
    // get data from config

    private Snake snk;

    public void setSnake(Snake snk) {
        this.snk = snk;
    }

    //print method
    public void paintComponent(Graphics g) {
        drawDecoration(g);
        drawFill(g);
    }

    //draw the snake
    public void drawFill(Graphics g) {

        g.setColor(Color.ORANGE);

        for (SnakePos sp : snk.getSnakeBody()) {
            g.fillRect(sp.col * TileWidth + XOffset, sp.row * TileHeight
                    + YOffset, TileWidth, TileHeight);
        }
        // color the snake 

        Food food = snk.getFood();

        g.setColor(Color.GREEN);

        g.fillRect(food.col * TileWidth + XOffset, food.row * TileHeight
                + YOffset, TileWidth, TileHeight);

    }

    //edge of the board
    public void drawDecoration(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(XOffset, YOffset, Column * TileWidth, Row * TileHeight);
    }

    public Dimension getPreferredSize() {
        return new Dimension(Width, Height);

    }
}

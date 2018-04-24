package snakegame;

import java.util.LinkedList;

public class SFood extends SnakePos {

    public int row;
    public int col;

    public static final int Row = Configure.ROW;
    public static final int Column = Configure.COL;

    SFood() {
        randomPos();
    }

    public SFood getSnake(LinkedList<SnakePos> snakeBody) {
        while (checkSame(snakeBody)) {
            randomPos();
        }
        return this;
    }

    private boolean checkSame(LinkedList<SnakePos> snakeBody) {
        for (SnakePos sp : snakeBody) {
            if (sp.row == this.row && sp.col == this.col) {
                return true;
            }
        }
        return false;
    }

    private void randomPos() {
        this.row = (int) (Math.random() * Row);
        this.col = (int) (Math.random() * Column);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.util.LinkedList;

/**
 *
 * @author joneszhuang
 */
public class Snake {

    private Direction snakeDir;

    private Direction moveDir;
    
    private LinkedList<SnakePos> snakeBody;
    //snake body

    public static final int Row = Configure.ROW;
    public static final int Column = Configure.COL;
    // 从Configure文件中读取的游戏行列

    public Snake() {
        snakeBody = new LinkedList<SnakePos>();
        reset();
        //first snake 
    }

    public Direction getSnakeDir() {
        return snakeDir;
    }

    public void setSnakeDir(Direction snakeDir) {
        this.snakeDir = snakeDir;
    }

    public LinkedList<SnakePos> getSnakeBody() {
        return snakeBody;
    }

    public void setMoveDir(Direction dir) {
        this.moveDir = dir;
    }
    
    
     public void reset() {
        snakeBody.clear();
        // clearn the previous generate snake 
        SnakePos beginPos = null;
        // snake head
        setMoveDir(null);
        // clean the keylistening record
        do {
            beginPos = this.RandomPos();
            // place the head
        } while (beginPos.row + 3 > Row);
        // make sure it does not generate oustide of the frame

        snakeBody.add(beginPos);
        snakeBody.add(new SnakePos(beginPos.row + 1, beginPos.col));
        snakeBody.add(new SnakePos(beginPos.row + 2, beginPos.col));
        // place the snake bodysection into the snake body
        setSnakeDir(Direction.UP);
        // set the snake face up all the time
    }
     //the following methof use to generate the snake positon on the board
     private SnakePos RandomPos() {

        int randomRow = (int) (Math.random() * Row);
        int randomCol = (int) (Math.random() * Column);

        return new SnakePos(randomRow, randomCol);
    }
     
     public void snakeMove() {

        int addRow = snakeBody.getFirst().row;
        int addCol = snakeBody.getFirst().col;
        //make sure the new body part connect and find the original body parts

        Direction up = Direction.UP;
        Direction down = Direction.DOWN;
        Direction left = Direction.LEFT;
        Direction right = Direction.RIGHT;

        if ((moveDir != null)
                && !((snakeDir == up && moveDir == down)
                        || (snakeDir == down && moveDir == up)
                        || (snakeDir == left && moveDir == right) || (snakeDir == right && moveDir == left)))
            snakeDir = moveDir;
        // bind the keyboard key to the new generated

        switch (snakeDir) {
        case UP:
            addRow--;
            break;
        case DOWN:
            addRow++;
            break;
        case LEFT:
            addCol--;
            break;
        case RIGHT:
            addCol++;
            break;
        }
        // change the new head spot

        
    }
}


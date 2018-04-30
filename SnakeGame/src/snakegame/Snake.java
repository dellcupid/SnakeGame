/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author joneszhuang
 */
public class Snake {

    private Direction snakeDir;

    private Direction moveDir;

    private Food food;
    private SFood sFood;

    private LinkedList<SnakePos> snakeBody;
    //snake body
    
    private int score = 0;

    public static final int Row = Configure.ROW;
    public static final int Column = Configure.COL;
    // get location

    public Snake() {
        snakeBody = new LinkedList<SnakePos>();
        reset();
        //initialize the snake 
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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
    
    public SFood getSFood()
    {
        return sFood;
    }
    
    public void setSFood(SFood sFood)
    {
        this.sFood = sFood;
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
        
        setScore(0);
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
                || (snakeDir == left && moveDir == right) || (snakeDir == right && moveDir == left))) {
            snakeDir = moveDir;
        }
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
        SnakePos addPos = new SnakePos(addRow, addCol);

        if (!isFood(addPos) && !isSFood(addPos))
        {
            snakeBody.removeLast();
        }
        else if(isFood(addPos) && !isSFood(addPos))
        {
            setFood(new Food().getSnake(snakeBody));
            setScore(score + 1);
        }
        else if (!isFood(addPos) && isSFood(addPos))
        {
            
            setSFood(new SFood().getSnake(snakeBody));
            setScore(score + 1);
             if(snakeBody.size() < 7)
            {
                JOptionPane.showMessageDialog(new JFrame(), "you are not strong enough and you're poisoned to death");
                System.exit(0);
            }
            snakeBody.removeLast();
            snakeBody.removeLast();
        }

        if (isCollision(addPos)) {
             
                JOptionPane.showMessageDialog(new JFrame(), "Game Over!");
                System.exit(0);
            
        } else {
            snakeBody.addFirst(addPos);
        }
    }

    private boolean isFood(SnakePos addPos) {
        if (food.row == addPos.row && food.col == addPos.col) {
            return true;
        }
        return false;
    }
    
    private boolean isSFood(SnakePos addPos) {
        if (sFood.row == addPos.row && sFood.col == addPos.col) {
            return true;
        }
        return false;
    }

    //collition method
    private boolean isCollision(SnakePos addPos) {
        if (addPos.row < 0 || addPos.row > Row - 1 || addPos.col < 0 || addPos.col > Column - 1) {
            return true;
        }
        for (SnakePos sp : snakeBody) {
            if ((sp.row == addPos.row) && (sp.col == addPos.col)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
}

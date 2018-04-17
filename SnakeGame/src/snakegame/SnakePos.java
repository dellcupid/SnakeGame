/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

/**
 *
 * @author joneszhuang
 */
public class SnakePos {

    public int col;
    public int row;
    // where is the snake on the board
    
    SnakePos(int row, int col) {
        this.col = col;
        this.row = row;
    }
    
    SnakePos()
    {
        col = 0;
        row = 0;
    }

}

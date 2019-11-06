package com.example.maze;

import java.io.Serializable;

class Cell implements Serializable {
    boolean topWall = true;
    boolean bottomWall = true;
    boolean leftWall = true;
    boolean rightWall = true;
    boolean visited = false;
    int row;
    int col;

    Cell(int col, int row)
    {
        this.col = col;
        this.row = row;
    }

    boolean isValidDirection(int direction)
    {
        if (direction == Maze.LEFT)
            return col > 0 && !leftWall;
        if (direction == Maze.RIGHT)
            return col < Maze.COLS - 1 && !rightWall;
        if (direction == Maze.UPWARD)
            return row > 0 && !topWall;
        if (direction == Maze.DOWNWARD)
            return row < Maze.ROWS - 1 && !bottomWall;
        System.err.println("Cell.isValidDirection: invalid input");
        return false;
    }
}
package com.example.maze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

class Maze implements Serializable {
    Cell[][] cells;
    Cell player, exit;

    boolean playerIsDead;
    boolean playerEscapes;

    // COLS : ROWS = 2 : 3
    static final int COLS = 10;
    static final int ROWS = 15;
    static final float WALL_THICKNESS = 10;

    static final int UPWARD = 1;
    static final int DOWNWARD = 2;
    static final int LEFT = 3;
    static final int RIGHT = 4;

    Random random = new Random();

    Maze() {
        createMaze();
    }

    void createMaze() {
        cells = new Cell[COLS][ROWS];
        Stack<Cell> visitedCells = new Stack<>();
        Cell current, next;

        playerIsDead = false;
        playerEscapes = false;

        // Create a 2d array of cells.
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        current = cells[0][0];
        current.visited = true;
        // Procedure:
        // If we find a neighbour of the current cell, remove the wall between the
        // current cell and the neighbour, and then do the procedure again on the neighbour.
        // Otherwise, get the last visited cell and then do the procedure on it.
        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current, next);
                visitedCells.push(current);
                current = next;
                current.visited = true;
            } else {
                current = visitedCells.pop();
            }
        } while (!visitedCells.isEmpty());

        // Top left corner.
        player = cells[0][0];
        // Bottom right corner.
        exit = cells[COLS - 1][ROWS - 1];
    }

    private Cell getNeighbour(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();

        // Check whether the left cell is visited, if there is one.
        if (cell.col > 0 && !cells[cell.col - 1][cell.row].visited)
            neighbours.add(cells[cell.col - 1][cell.row]);

        // Check whether the right cell is visited, if there is one.
        if (cell.col < COLS - 1 && !cells[cell.col + 1][cell.row].visited)
            neighbours.add(cells[cell.col + 1][cell.row]);

        // Check whether the cell above is visited, if there is one.
        if (cell.row > 0 && !cells[cell.col][cell.row - 1].visited)
            neighbours.add(cells[cell.col][cell.row - 1]);

        // Check whether the cell below is visited, if there is one.
        if (cell.row < ROWS - 1 && !cells[cell.col][cell.row + 1].visited)
            neighbours.add(cells[cell.col][cell.row + 1]);

        if (neighbours.size() == 0)
            return null;
        int i = random.nextInt(neighbours.size());
        return neighbours.get(i);
    }

    private void removeWall(Cell cell1, Cell cell2) {
        // If cell2 is at the left side of cell1.
        if (cell1.col - 1 == cell2.col) {
            cell1.leftWall = false;
            cell2.rightWall = false;
        }
        // If cell2 is at the right side of cell1.
        else if (cell1.col + 1 == cell2.col) {
            cell1.rightWall = false;
            cell2.leftWall = false;
        }
        // If cell2 is on top of cell1.
        else if (cell1.row - 1 == cell2.row) {
            cell1.topWall = false;
            cell2.bottomWall = false;
        }
        // If cell1 is on top of cell2.
        else {
            cell1.bottomWall = false;
            cell2.topWall = false;
        }
    }

    void movePlayer(int direction) {
        switch (direction) {
            case Maze.LEFT:
                if (player.isValidDirection(LEFT))
                    player = cells[player.col - 1][player.row];
                break;
            case Maze.RIGHT:
                if (player.isValidDirection(RIGHT))
                    player = cells[player.col + 1][player.row];
                break;
            case Maze.UPWARD:
                if (player.isValidDirection(UPWARD))
                    player = cells[player.col][player.row - 1];
                break;
            case Maze.DOWNWARD:
                if (player.isValidDirection(DOWNWARD))
                    player = cells[player.col][player.row + 1];
                break;
        }
        if (player == exit)
            playerEscapes = true;
    }

    void clearVisitedMarks() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                cells[i][j].visited = false;
            }
        }
    }

    void restartMaze() {
        player = cells[0][0];
        playerIsDead = false;
        playerEscapes = false;
    }
}
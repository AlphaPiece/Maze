package com.example.maze;

public class KillerModeMaze extends Maze {
    Cell killer, detective;

    KillerModeMaze() {
        super();
        createRoles();
    }

    private void createRoles() {
        killer = cells[0][ROWS - 1];
        detective = cells[COLS - 1][0];
    }
}

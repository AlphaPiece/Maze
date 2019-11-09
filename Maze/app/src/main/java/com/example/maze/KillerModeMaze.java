package com.example.maze;


import java.util.ArrayDeque;

public class KillerModeMaze extends Maze {
    Cell killer, detective;
    ArrayDeque<Cell> killerPath, detectivePath;
    int countSteps;

    KillerModeMaze() {
        super();
        createRoles();
        countSteps = 0;
    }

    private void createRoles() {
        killer = cells[0][ROWS - 1];
        detective = cells[COLS - 1][0];
    }

    private void findKillerPath() {
        Cell current, next;
        killerPath = new ArrayDeque<>();
        clearVisitedMarks();
        current = killer;
        current.visited = true;

        while (true) {
            next = nextNotVisitedCell(current);
            if (next == player) {
                killerPath.addLast(current);
                killerPath.addLast(next);
                break;
            } else if (next != null) {
                killerPath.addLast(current);
                current = next;
                current.visited = true;
            } else {
                current = killerPath.pollLast();
            }
        }
        killerPath.pollFirst();
        for (Cell cell : killerPath)
            cell.printCell();
    }

    private Cell nextNotVisitedCell(Cell current) {
        // Check whether the left cell is visited, if there is one.
        if (current.isValidDirection(LEFT) && !cells[current.col - 1][current.row].visited)
            return cells[current.col - 1][current.row];

        // Check whether the right cell is visited, if there is one.
        if (current.isValidDirection(RIGHT) && !cells[current.col + 1][current.row].visited)
            return cells[current.col + 1][current.row];

        // Check whether the cell above is visited, if there is one.
        if (current.isValidDirection(UPWARD) && !cells[current.col][current.row - 1].visited)
            return cells[current.col][current.row - 1];

        // Check whether the cell below is visited, if there is one.
        if (current.isValidDirection(DOWNWARD) && !cells[current.col][current.row + 1].visited)
            return cells[current.col][current.row + 1];

        return null;
    }

    private void moveKiller() {
        if (killerPath == null || killerPath.isEmpty() || countSteps == 5) {
            findKillerPath();
            countSteps = 0;
        }
        killer = killerPath.pollFirst();
        countSteps++;
    }

    void movePlayer(int direction) {
        super.movePlayer(direction);

        if (player == exit) {
            return;
        } else if (player == killer) {
            playerIsDead = true;
            return;
        }

        moveKiller();

        if (killer == player) {
            playerIsDead = true;
            return;
        }
    }
}

package com.example.maze;


import java.util.ArrayDeque;

public class KillerModeMaze extends Maze {
    Cell killer, detective;
    ArrayDeque<Cell> killerPath, detectivePath;
    int killerSteps, detectiveSteps;
    boolean killerIsCatched;

    KillerModeMaze() {
        super();
        createRoles();
        killerIsCatched = false;
    }

    private void createRoles() {
        killer = cells[0][ROWS - 1];
        detective = cells[COLS - 1][0];
    }

    /**
     * Find the path for the killer to catch the player.
     *
     * Precondition: killer != player
     */
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
    }

    /**
     * Find the path for the detective to catch the killer.
     *
     * Precondition: detective != killer
     */
    private void findDetectivePath() {
        Cell current, next;
        detectivePath = new ArrayDeque<>();
        clearVisitedMarks();
        current = detective;
        current.visited = true;

        while (true) {
            next = nextNotVisitedCell(current);
            if (next == killer) {
                detectivePath.addLast(current);
                detectivePath.addLast(next);
                break;
            } else if (next != null) {
                detectivePath.addLast(current);
                current = next;
                current.visited = true;
            } else {
                current = detectivePath.pollLast();
            }
        }
        detectivePath.pollFirst();
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
        if (killerPath == null || killerPath.isEmpty() || killerSteps == 5) {
            findKillerPath();
            killerSteps = 0;
        }
        killer = killerPath.pollFirst();
        killerSteps++;
    }

    private void moveDetective() {
        if (detectivePath == null || detectivePath.isEmpty() || detectiveSteps == 5) {
            findDetectivePath();
            detectiveSteps = 0;
        }
        if (detectivePath.peekFirst() != player) {
            detective = detectivePath.pollFirst();
        }
        detectiveSteps++;
    }

    void movePlayer(int direction) {
        super.movePlayer(direction);

        if (killerIsCatched || player == exit) {
            return;
        } else if (player == killer) {
            playerIsDead = true;
            return;
        }

        moveKiller();

        if (killer == player) {
            playerIsDead = true;
            return;
        } else if (killer == detective) {
            killerIsCatched = true;
            return;
        }

        moveDetective();

        if (detective == killer) {
            killerIsCatched = true;
            return;
        }
    }
}

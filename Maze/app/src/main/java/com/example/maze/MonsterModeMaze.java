package com.example.maze;

import java.util.ArrayList;

class MonsterModeMaze extends Maze {
    int numberOfMonsters;
    Cell[] monsters;

    MonsterModeMaze() {
        super();
        numberOfMonsters = COLS * ROWS / 18;
        createMonsters();
    }

    private void createMonsters() {
        int i, j, k, l;

        monsters = new Cell[numberOfMonsters];
        l = Math.min(COLS, ROWS) / 4;
        for (i = 0; i < numberOfMonsters; i++) {
            j = random.nextInt(COLS - l) + l;
            k = random.nextInt(ROWS - l) + l;
            monsters[i] = cells[j][k];
        }
    }

    void moveMonsters() {
        ArrayList<Cell> validCells;

        for (int i = 0; i < numberOfMonsters; i++) {
            validCells = new ArrayList<>();
            if (monsters[i].isValidDirection(LEFT))
                validCells.add(cells[monsters[i].col - 1][monsters[i].row]);
            if (monsters[i].isValidDirection(RIGHT))
                validCells.add(cells[monsters[i].col + 1][monsters[i].row]);
            if (monsters[i].isValidDirection(UPWARD))
                validCells.add(cells[monsters[i].col][monsters[i].row - 1]);
            if (monsters[i].isValidDirection(DOWNWARD))
                validCells.add(cells[monsters[i].col][monsters[i].row + 1]);
            int j = random.nextInt(validCells.size());
            monsters[i] = validCells.get(j);
        }
    }

    void movePlayer(int direction) {
        super.movePlayer(direction);
        moveMonsters();
        for (int i = 0; i < numberOfMonsters; i++) {
            if (player == monsters[i]) {
                playerIsDead = true;
                return;
            }
        }
    }
}
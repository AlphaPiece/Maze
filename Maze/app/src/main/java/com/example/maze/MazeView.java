package com.example.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import androidx.annotation.Nullable;

import android.graphics.Paint;
import android.media.midi.MidiOutputPort;
import android.util.AttributeSet;
import android.view.View;

public class MazeView extends View {
    Maze maze;
    private Paint wallPaint, playerPaint, exitPaint;
    private Paint monsterPaint;
    private Paint killerPaint, detectivePaint;

    private float cellSize, hMargin, vMargin;

    public MazeView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mazeSetup();
        modeSetup();
    }

    private void mazeSetup() {
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(Maze.WALL_THICKNESS);

        playerPaint = new Paint();
        playerPaint.setColor(Color.BLUE);

        exitPaint = new Paint();
        exitPaint.setColor(Color.BLACK);
    }

    private void modeSetup() {

        if (ModeActivity.modeString.equals("Monster Mode")) {
            maze = new MonsterModeMaze();
            monsterPaint = new Paint();
            monsterPaint.setColor(Color.RED);
        } else if (ModeActivity.modeString.equals("Killer Mode")) {
            maze = new KillerModeMaze();
            killerPaint = new Paint();
            killerPaint.setColor(Color.MAGENTA);
            detectivePaint = new Paint();
            detectivePaint.setColor(Color.YELLOW);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        int width = getWidth();
        int height = getHeight();

        // Decide the size of cells based on the screen.
        if (width / height < Maze.COLS / Maze.ROWS)
            cellSize = width / (Maze.COLS + 1);
        else
            cellSize = height / (Maze.ROWS + 1);

        hMargin = (width - Maze.COLS * cellSize) / 2;
        vMargin = (height - Maze.ROWS * cellSize) / 2;

        // Draw the walls.

        // Translate the origin from (0, 0) to (hMargin, vMargin).
        canvas.translate(hMargin, vMargin);

        for (int x = 0; x < Maze.COLS; x++)
        {
            for (int y = 0; y < Maze.ROWS; y++)
            {
                if (maze.cells[x][y].topWall)
                    canvas.drawLine(x * cellSize, y * cellSize,
                            (x + 1) * cellSize, y * cellSize,
                            wallPaint);
                if (maze.cells[x][y].bottomWall)
                    canvas.drawLine(x * cellSize, (y + 1) * cellSize,
                            (x + 1) * cellSize, (y + 1) * cellSize,
                            wallPaint);
                if (maze.cells[x][y].leftWall)
                    canvas.drawLine(x * cellSize, y * cellSize,
                            x * cellSize, (y + 1) * cellSize,
                            wallPaint);
                if (maze.cells[x][y].rightWall)
                    canvas.drawLine((x + 1) * cellSize, y * cellSize,
                            (x + 1) * cellSize, (y + 1) * cellSize,
                            wallPaint);
            }
        }

        // Draw player and exit squares.

        float margin = cellSize / 7;

        canvas.drawRect(maze.player.col * cellSize + margin,
                maze.player.row * cellSize + margin,
                (maze.player.col + 1) * cellSize - margin,
                (maze.player.row + 1) * cellSize - margin,
                playerPaint);

        canvas.drawRect(maze.exit.col * cellSize + margin,
                maze.exit.row * cellSize + margin,
                (maze.exit.col + 1) * cellSize - margin,
                (maze.exit.row + 1) * cellSize - margin,
                exitPaint);

        if (ModeActivity.modeString.equals("Monster Mode")) {
            monsterModeDraw(canvas, margin);
        } else if (ModeActivity.modeString.equals("Killer Mode")) {
            killerModeDraw(canvas, margin);
        } else {
            System.out.println("Error: No such maze mode");
        }
    }

    private void monsterModeDraw(Canvas canvas, float margin) {
        MonsterModeMaze monsterModeMaze = (MonsterModeMaze) maze;
        for (int i = 0; i < monsterModeMaze.numberOfMonsters; i++) {
            canvas.drawRect(monsterModeMaze.monsters[i].col * cellSize + margin,
                    monsterModeMaze.monsters[i].row * cellSize + margin,
                    (monsterModeMaze.monsters[i].col + 1) * cellSize - margin,
                    (monsterModeMaze.monsters[i].row + 1) * cellSize - margin,
                    monsterPaint);
        }
    }

    private void killerModeDraw(Canvas canvas, float margin) {
        KillerModeMaze killerModeMaze = (KillerModeMaze) maze;
        if (!killerModeMaze.killerIsCatched) {
            canvas.drawRect(killerModeMaze.killer.col * cellSize + margin,
                    killerModeMaze.killer.row * cellSize + margin,
                    (killerModeMaze.killer.col + 1) * cellSize - margin,
                    (killerModeMaze.killer.row + 1) * cellSize - margin,
                    killerPaint);
            canvas.drawRect(killerModeMaze.detective.col * cellSize + margin,
                    killerModeMaze.detective.row * cellSize + margin,
                    (killerModeMaze.detective.col + 1) * cellSize - margin,
                    (killerModeMaze.detective.row + 1) * cellSize - margin,
                    detectivePaint);
        }
    }

    void updateMaze(int direction) {
        maze.movePlayer(direction);
        invalidate();
    }
}

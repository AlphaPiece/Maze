package com.example.maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MazeActivity extends AppCompatActivity {
    private MazeView mazeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        mazeView = findViewById(R.id.mazeView);
    }

    public void clickButton(View view)
    {
        ImageButton button = (ImageButton) view;
        String contentDescription = button.getContentDescription().toString();
        if (contentDescription.equals("LeftButton")) {
            mazeView.updateMaze(Maze.LEFT);
        } else if (contentDescription.equals("RightButton")) {
            mazeView.updateMaze(Maze.RIGHT);
        } else if (contentDescription.equals("UpwardButton")) {
            mazeView.updateMaze(Maze.UPWARD);
        } else if (contentDescription.equals("DownwardButton")) {
            mazeView.updateMaze(Maze.DOWNWARD);
        } else {
            System.out.println("Error: invalid button");
        }

        if (mazeView.maze.adventurerIsDead) {
            Intent intent = new Intent(this, DeadStateActivity.class);
            startActivity(intent);
        }
    }
}

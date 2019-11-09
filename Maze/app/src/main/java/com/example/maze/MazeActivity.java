package com.example.maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MazeActivity extends AppCompatActivity {
    private MazeView mazeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        mazeView = findViewById(R.id.mazeView);
    }

    public void clickDirectionButton(View view) {
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
            System.out.println("Error: invalid direction button");
        }

        if (mazeView.maze.playerIsDead) {
            Intent intent = new Intent(this, DeadStateActivity.class);
            startActivity(intent);
        } else if (mazeView.maze.playerEscapes) {
            Intent intent = new Intent(this, SurvivalStateActivity.class);
            startActivity(intent);
        }
    }

    public void clickFunctionButton(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();
        if (text.equals("Quit")) {
            Intent intent = new Intent(this, ModeActivity.class);
            startActivity(intent);
        } else if (text.equals("Save")) {

        } else if (text.equals("Restart")) {
            mazeView.restartMaze();
        } else {
            System.out.println("Error: invalid function button");
        }
    }
}

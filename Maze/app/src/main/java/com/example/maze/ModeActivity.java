package com.example.maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModeActivity extends AppCompatActivity {
    static String modeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
    }

    public void clickMonsterModeButton(View view) {
        modeString = "MonsterMode";
        Intent intent = new Intent(this, MazeActivity.class);
        startActivity(intent);
    }
}

package com.example.maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class SurvivalStateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survival_state);

        new CountDownTimer(4000, 1000) {

            TextView stateTimer = findViewById(R.id.survivalStateTimer);

            public void onTick(long millisUntilFinished) {
                stateTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                Intent intent = new Intent(SurvivalStateActivity.this,
                        ModeActivity.class);
                startActivity(intent);
            }

        }.start();
    }
}
package com.example.maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        new CountDownTimer(4000, 1000) {

            TextView statusTimer = findViewById(R.id.statusTimer);

            public void onTick(long millisUntilFinished) {
                statusTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                Intent intent = new Intent(StatusActivity.this, ModeActivity.class);
                startActivity(intent);
            }

        }.start();
    }
}

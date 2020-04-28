package com.example.a24game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main menu screen.
 */
public class LaunchActivity extends AppCompatActivity {
    /**
     * create main menu.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launchscreen);

        TextView singlePlayerButton = findViewById(R.id.singleplayer);
        singlePlayerButton.setOnClickListener(v -> {
            singlePlayerButton.setText(R.string.coming_soon);
        });

        TextView multiPlayerButton = findViewById(R.id.multiplayer);
        multiPlayerButton.setOnClickListener(v -> {
            multiPlayerButton.setText(R.string.coming_soon);
        });

        TextView practiceButton = findViewById(R.id.practice);
        practiceButton.setOnClickListener(v -> {
            Intent practiceIntent = new Intent(this, Practice.class);
            startActivity(practiceIntent);
        });
    }
}

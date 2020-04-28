package com.example.a24game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class GamePlay extends AppCompatActivity {
    /**
     * onCreate for practice mode.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solverscreen);
    }
}


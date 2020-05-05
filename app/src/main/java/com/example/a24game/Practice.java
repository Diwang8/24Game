package com.example.a24game;

import android.os.Bundle;

/**
 * Practice mode.
 */
public class Practice extends GamePlay {
    /**
     * onCreate for practice mode.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadProblem() {
        int[] newValues;
        do {
            newValues = new int[4];
            for (int i = 0; i < 4; i++) {
                newValues[i] = rng.nextInt(24) + 1;
            }
            solvable = new Solvable(newValues[0], newValues[1], newValues[2], newValues[3]);
        } while(!solvable.isSolvable());

        for (int i = 0; i < 4; i++) {
            numbers[i].newValue(newValues[i]);
        }

        clear();
    }
}

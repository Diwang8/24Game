package com.example.a24game;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

/**
 * Handles numerical buttons in games.
 */
public class NumButton extends InputButton {
    /** The number of this NumButton. */
    private int value;

    /** Whether or not this button has been used in the solution. */
    private boolean used;

    /**
     * Sets parameters for NumButton object.
     * @param myID ID of this button
     * @param myView TextView of this button
     * @param myGame GamePlay object that this button is a part of.
     */
    public NumButton(final int myID, final TextView myView, final GamePlay myGame) {
        super(myID, myView, myGame);
        used = false;
        buttonView.setOnClickListener(v -> {
            if (!used && game.solnSize() < 16) {
                used = true;
                buttonView.setBackgroundDrawable(ContextCompat.getDrawable(game, R.drawable.used_num_back));
                buttonView.setTextColor(game.getResources().getColor(R.color.grey));
                game.solutionAdd(this);
            }
        });
    }


    /**
     * Changes number value of this button to reset this problem.
     * @param newValue new value of this button.
     */
    public void newValue(final int newValue) {
        value = newValue;
        buttonView.setText("" + newValue);
    }

    /**
     * Returns number of this button.
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns value of this button as a string.
     * @return value as a string.
     */
    public String toString() {
        return "" + value;
    }

    /** Unpresses the button. To be called when the number is deleted from the solution.*/
    public void unPress() {
        used = false;
        buttonView.setBackgroundDrawable(ContextCompat.getDrawable(game, R.drawable.numback));
        buttonView.setTextColor(game.getResources().getColor(R.color.black));
    }
}

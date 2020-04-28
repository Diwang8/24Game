package com.example.a24game;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Handles operation buttons in games.
 */
public class OpButton extends InputButton {
    /** Character for the operation of this button. */
    private char op;

    /**
     * Sets parameters for OpButton object.
     * @param myID ID of this button
     * @param myView TextView of this button
     * @param myGame GamePlay object that this button is a part of.
     * @param myOp character representing the operation for this button.
     */
    public OpButton(final int myID, final TextView myView, final GamePlay myGame, final char myOp) {
        super(myID, myView, myGame);
        op = myOp;
        buttonView.setOnClickListener(v -> {
            if (game.solnSize() < 16) {
                game.solutionAdd(this);
            }
        });
    }

    /**
     * Returns the operation of this button as a string.
     * @return operation as a string.
     */
    @NonNull
    public String toString() {
        return "" + op;
    }
}

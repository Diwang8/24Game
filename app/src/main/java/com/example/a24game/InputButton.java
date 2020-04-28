package com.example.a24game;

import android.widget.TextView;

/**
 * Abstract class for all buttons in game.
 */
public abstract class InputButton {
    /** ID number of this button. */
    protected int buttonID;

    /** TextView for this button. */
    protected TextView buttonView;

    /** GamePlay instance that this button is a part of. */
    protected GamePlay game;

    /**
     * Sets parameters for GameButton object.
     * @param myID ID of this button
     * @param myView TextView of this button
     * @param myGame GamePlay object that this button is a part of.
     */
    public InputButton(final int myID, final TextView myView, final GamePlay myGame) {
        buttonID = myID;
        buttonView = myView;
        game = myGame;
    }

    /**
     * Returns buttonID.
     * @return buttonID
     */
    public int getButtonID() {
        return buttonID;
    }

    /** Returns TextView instance for this button.
     * @return TextView instance for this button
     */
    public TextView getButtonView() {
        return buttonView;
    }

    /**
     * Returns GamePlay instance that this button is a part of.
     * @return game
     */
    public GamePlay getGamePlay() {
        return game;
    }
}

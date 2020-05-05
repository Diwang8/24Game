package com.example.a24game;

import android.os.CountDownTimer;

public class GameTimer extends CountDownTimer {
    private SinglePlayer game;
    private long timeRemaining;

    public GameTimer(long millisInFuture, long countDownInterval, SinglePlayer myGame) {
        super(millisInFuture, countDownInterval);
        game = myGame;
        timeRemaining = millisInFuture;
    }
    public void onFinish() {
        if (game instanceof SinglePlayer) {
            game.end();
        }
    }
    public void onTick(long millisUntilFinished) {
        timeRemaining = millisUntilFinished;
        game.updateTimer();
    }

    public GameTimer timerChange(long change) {
        cancel();
        long newTime = timeRemaining + change;
        if (newTime < 0) {
            newTime = 0;
        }
        return new GameTimer(newTime, 50, game);
    }
    public long getTimeRemaining() {
        return timeRemaining;
    }
}

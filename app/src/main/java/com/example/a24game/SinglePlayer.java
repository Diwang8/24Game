package com.example.a24game;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

/**
 * Single Player mode, with a timer.
 */
public class SinglePlayer extends GamePlay {
    /** score */
    private int score;
    private GameTimer gameTimer;
    private final long START_TIME = 120000;
    /**
     * onCreate for practice mode.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.solverLayout);
        ConstraintSet constraints = new ConstraintSet();
        constraints.clone(constraintLayout);
        for (int i = 0; i <=1; i++) {
            constraints.connect(numbers[i].getButtonView().getId(), ConstraintSet.TOP,
                    R.id.h30, ConstraintSet.TOP);
            constraints.connect(numbers[i].getButtonView().getId(), ConstraintSet.BOTTOM,
                    R.id.h47, ConstraintSet.BOTTOM);
        }
        for (int i = 2; i <= 3; i++) {
            constraints.connect(numbers[i].getButtonView().getId(), ConstraintSet.TOP,
                    R.id.h47, ConstraintSet.TOP);
            constraints.connect(numbers[i].getButtonView().getId(), ConstraintSet.BOTTOM,
                    R.id.h64, ConstraintSet.BOTTOM);
        }
        constraints.connect(R.id.plus, ConstraintSet.TOP, R.id.h64, ConstraintSet.TOP);
        constraints.connect(R.id.plus, ConstraintSet.BOTTOM, R.id.h77, ConstraintSet.BOTTOM);

        constraints.connect(R.id.minus, ConstraintSet.TOP, R.id.h64, ConstraintSet.TOP);
        constraints.connect(R.id.minus, ConstraintSet.BOTTOM, R.id.h77, ConstraintSet.BOTTOM);

        constraints.connect(R.id.times, ConstraintSet.TOP, R.id.h64, ConstraintSet.TOP);
        constraints.connect(R.id.times, ConstraintSet.BOTTOM, R.id.h77, ConstraintSet.BOTTOM);

        constraints.connect(R.id.divides, ConstraintSet.TOP, R.id.h64, ConstraintSet.TOP);
        constraints.connect(R.id.divides, ConstraintSet.BOTTOM, R.id.h77, ConstraintSet.BOTTOM);

        constraints.connect(R.id.left_paren, ConstraintSet.TOP, R.id.h77, ConstraintSet.TOP);

        constraints.connect(R.id.right_paren, ConstraintSet.TOP, R.id.h77, ConstraintSet.TOP);

        constraints.applyTo(constraintLayout);

        TextView timerDisplay = findViewById(R.id.timer);
        timerDisplay.setVisibility(View.VISIBLE);
        gameTimer = new GameTimer(START_TIME, 50, this);
        updateTimer();
        gameTimer.start();


        TextView scoreDisplay = findViewById(R.id.score);
        scoreDisplay.setVisibility(View.VISIBLE);
        score = 0;
        updateScore();
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

    private String timeBuilder(long timeRemaining) {
        timeRemaining /= 1000;
        long mins = timeRemaining / 60;
        long secs = timeRemaining % 60;
        if (secs > 9) {
            return "" + mins + ":" + secs;
        } else {
            return "" + mins + ":0" + secs;
        }

    }

    public void updateTimer() {
        TextView timerDisplay = findViewById(R.id.timer);
        timerDisplay.setText(getString(R.string.time) + " " + timeBuilder(gameTimer.getTimeRemaining()));
    }

    public void updateScore() {
        TextView scoreDisplay = findViewById(R.id.score);
        scoreDisplay.setText(getString(R.string.score) + " " + score);
    }

    /**
     * Sets up give up and submit buttons.
     */
    @Override
    protected void setUpAnswerButtons() {
        TextView giveUpButton = findViewById(R.id.give_up_button);
        giveUpButton.setText(getString(R.string.give_up_singleplayer));
        giveUpButton.setOnClickListener(v -> {
            gameTimer.cancel();
            String message;
            if (solvable.isSolvable()) {
                message = getString(R.string.give_up_text_solution,solvable.generateSolutionAsString());
            } else {
                message = getString(R.string.give_up_text_impossible);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setPositiveButton("OK", (unused1, unused2) -> { })
                    .setOnDismissListener(unused -> {
                loadProblem();
                gameTimer = gameTimer.timerChange(-3000);
                updateTimer();
                gameTimer.start();
            });
            builder.create().show();
        });

        submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(v -> {
            gameTimer.cancel();
            if (validity) {
                String message;
                boolean correct = checkSolution();
                if (correct) {
                    message = getString(R.string.submit_text_correct_singleplayer);
                } else {
                    message = getString(R.string.submit_text_incorrect_singleplayer);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(message)
                        .setPositiveButton("OK", (unused1, unused2) -> { })
                        .setOnDismissListener(unused -> {
                    if (correct) {
                        loadProblem();
                        gameTimer = gameTimer.timerChange(5000);
                        updateTimer();
                        gameTimer.start();
                        score++;
                        updateScore();
                    } else {
                        gameTimer = gameTimer.timerChange(-5000);
                        updateTimer();
                        gameTimer.start();
                    }
                });
                builder.create().show();
            }
        });
    }

    public void end() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.game_over_singleplayer, score))
                .setPositiveButton("OK", (unused1, unused2) -> {
                    finish();
                }).setOnDismissListener(unused -> {
            finish();
        });
        builder.create().show();
    }
}

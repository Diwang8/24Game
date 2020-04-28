package com.example.a24game;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract class for main 24 game for all game modes.
 */
public abstract class GamePlay extends AppCompatActivity {
    /**
     * Stores sequence of player's solution.
     */
    protected ArrayList<InputButton> givenSoln;

    /**
     * Stores buttons for each number.
     */
    protected NumButton[] numbers;

    /**
     * TextView of solution input.
     */
    protected TextView inputField;

    /**
     * TextView of submit button.
     */
    protected TextView submit_button;

    /** RNG For problem generation. */
    protected Random rng;

    /** stores whether solution is valid or not */
    protected boolean validity;

    /**
     * onCreate for practice mode.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solverscreen);
        inputField = findViewById(R.id.player_input);
        rng = new Random();
        validity = false;
        numbers = new NumButton[4];
        numbers[0] = new NumButton(InputIDs.NUM_1_INPUT, findViewById(R.id.num1), this);
        numbers[1] = new NumButton(InputIDs.NUM_2_INPUT, findViewById(R.id.num2), this);
        numbers[2] = new NumButton(InputIDs.NUM_3_INPUT, findViewById(R.id.num3), this);
        numbers[3] = new NumButton(InputIDs.NUM_4_INPUT, findViewById(R.id.num4), this);

        new OpButton(InputIDs.PLUS_INPUT, findViewById(R.id.plus), this, '+');
        new OpButton(InputIDs.MINUS_INPUT, findViewById(R.id.minus), this, '-');
        new OpButton(InputIDs.TIMES_INPUT, findViewById(R.id.times), this, 'x');
        new OpButton(InputIDs.DIVIDES_INPUT, findViewById(R.id.divides), this, '/');
        new OpButton(InputIDs.LEFT_PAREN_INPUT, findViewById(R.id.left_paren), this, '(');
        new OpButton(InputIDs.RIGHT_PAREN_INPUT, findViewById(R.id.right_paren), this, ')');

        TextView backspaceButton = findViewById(R.id.backspace_button);
        backspaceButton.setOnClickListener(v -> {
            if (givenSoln.size() > 0) {
                InputButton lastInput = givenSoln.remove(givenSoln.size() - 1);
                if (lastInput instanceof NumButton) {
                    NumButton lastNumButton = (NumButton) lastInput;
                    lastNumButton.unPress();
                }
                updateSolutionDisplay();
            }
        });

        TextView clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(v -> {
            clear();
        });

        TextView giveUpButton = findViewById(R.id.give_up_button);
        giveUpButton.setOnClickListener(v -> {
            String message;
            if (isPossible()) {
                message = getString(R.string.give_up_text_solution,getSolution());
            } else {
                message = getString(R.string.give_up_text_impossible);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setPositiveButton("OK", (unused1, unused2) -> {
                        loadProblem();
                    }).setOnDismissListener(unused -> {
                        loadProblem();
            });
            builder.create().show();
        });

        submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(v -> {
            if (validity) {
                String message;
                boolean correct = checkSolution();
                if (correct) {
                    message = getString(R.string.submit_text_correct);
                } else {
                    message = getString(R.string.submit_text_incorrect);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(message)
                        .setPositiveButton("OK", (unused1, unused2) -> {
                            if (correct) {
                                loadProblem();
                            }
                        }).setOnDismissListener(unused -> {
                            if (correct) {
                                loadProblem();
                            }
                });
                builder.create().show();
            }
        });
        loadProblem();
    }

    /**
     * Loads a new 24 problem.
     */
    protected void loadProblem() {
        for (NumButton i : numbers) {
            i.newValue(rng.nextInt(24) + 1);
        }
        clear();
    }

    /**
     * Clears input and resets number buttons.
     */
    protected void clear() {
        givenSoln = new ArrayList<InputButton>();
        for (NumButton i : numbers) {
            i.unPress();
        }
        updateSolutionDisplay();
    }

    /**
     * Updates the solution line based on the button pressed
     * @param newInput the button pressed.
     */
    protected void solutionAdd(InputButton newInput) {
        givenSoln.add(newInput);
        updateSolutionDisplay();
    }

    /**
     * Updates the solution display to reflect any changes made.
     */
    protected void updateSolutionDisplay() {
        StringBuilder newSolution = new StringBuilder();
        for (InputButton i : givenSoln) {
            newSolution.append(i.toString());
        }
        inputField.setText(newSolution.toString());

        if (isValid()) {
            validity = true;
            submit_button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.submit_good_back));
            submit_button.setTextColor(getResources().getColor(R.color.black));
        } else {
            validity = false;
            submit_button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.submit_bad_back));
            submit_button.setTextColor(getResources().getColor(R.color.grey));
        }
    }

    /**
     * Returns number of inputs in typed in solution.
     * @return size of givenSoln
     */
    public int solnSize() {
        return givenSoln.size();
    }

    /**
     * Checks whether given solution is valid as an answer to the problem.
     * @return whether the solution is valid or not
     */
    public boolean isValid() {
        // all numbers used
        boolean[] hasNum = new boolean[4];
        for (InputButton i : givenSoln) {
            if (i.getButtonID() < 5) {
                hasNum[i.getButtonID() - 1] = true;
            }
        }
        for (boolean b : hasNum) {
            if (!b) {
                return false;
            }
        }
        // no 2 numbers in a row

        // does not begin or end with arithmetic operations

        // no 2 arithmetic operations in a row

        // no operations immediately after a left parenthesis or before a right parenthesis
        // exceptions: multiple left parentheses or right parentheses in a row are allowed

        // parentheses properly matched

        return true;
    }

    /**
     * Checks given solution.
     * @return whether the solution is correct or not.
     */
    public boolean checkSolution() {
        //TODO write this
        return false;
    }

    /**
     * Returns solution for the given numbers.
     * @return solution for given numbers.
     */
    public String getSolution() {
        //TODO write this
        return ":)";
    }

    /**
     * Returns whether the current problem is possible.
     * @return whether the current problem is possible.
     */
    public boolean isPossible() {
        //TODO write this
        return true;
    }
}


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

    /** stores solvable object for the current problem. */
    protected Solvable solvable;

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

        setUpAnswerButtons();

        loadProblem();
    }

    /**
     * Sets up give up and submit buttons.
     */
    protected void setUpAnswerButtons() {
        TextView giveUpButton = findViewById(R.id.give_up_button);
        giveUpButton.setOnClickListener(v -> {
            String message;
            if (solvable.isSolvable()) {
                message = getString(R.string.give_up_text_solution,solvable.generateSolutionAsString());
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
    protected boolean isValid() {
        if (givenSoln.size() == 0) {
            return false;
        }
        int[] idArray = new int[givenSoln.size()];
        for (int i = 0; i < idArray.length; i++) {
            idArray[i] = givenSoln.get(i).getButtonID();
        }
        // does not begin or end with operations
        // except for possibly beginning with ( or ending with ), or beginning with -
        if (idArray[0] > 4 && idArray[0] != InputIDs.LEFT_PAREN_INPUT
                && idArray[0] != InputIDs.MINUS_INPUT) return false;
        if (idArray[idArray.length - 1] > 4 && idArray[idArray.length - 1] != InputIDs.RIGHT_PAREN_INPUT) return false;

        boolean[] hasNum = new boolean[4];
        int parenCount = 0;
        for (int i = 0; i < idArray.length; i++) {
            // all numbers used
            if (idArray[i] < 5) {
                hasNum[idArray[i] - 1] = true;
            }

            if (i < idArray.length - 1) {
                // no 2 numbers in a row
                if (idArray[i] < 5 && idArray[i + 1] < 5) {
                    return false;
                }

                // no 2 arithmetic operations in a row
                if (idArray[i] > 4 && idArray[i] < InputIDs.LEFT_PAREN_INPUT
                    && idArray[i + 1] > 4 && idArray[i + 1] < InputIDs.LEFT_PAREN_INPUT) {
                    return false;
                }
                // no operations immediately after a left parenthesis or before a right parenthesis
                // exceptions: minus after a left parentheses, or multiple left parentheses
                //      or right parentheses in a row are allowed
                if (idArray[i] == InputIDs.LEFT_PAREN_INPUT && idArray[i + 1] > 4
                    && idArray[i + 1] != InputIDs.LEFT_PAREN_INPUT
                        && idArray[i + 1] != InputIDs.MINUS_INPUT) {
                    return false;
                }
                if (idArray[i] > 4 && idArray[i] != InputIDs.RIGHT_PAREN_INPUT
                        && idArray[i + 1]== InputIDs.RIGHT_PAREN_INPUT) {
                    return false;
                }
            }
            // parentheses properly matched
            if (idArray[i] == InputIDs.LEFT_PAREN_INPUT) {
                parenCount++;
            } else if (idArray[i] == InputIDs.RIGHT_PAREN_INPUT) {
                parenCount--;
            }

            if (parenCount < 0) {
                return false;
            }
        }

        if (parenCount != 0) return false;

        // all numbers used check
        for (boolean b : hasNum) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks given solution.
     * @return whether the solution is correct or not.
     */
    protected boolean checkSolution() {
        position = 0;
        // Scan for parentheses
        try {
            return Math.abs(calculateSubmission() - 24) < 1E-7;
        } catch (ArithmeticException e) {
            return false;
        }

    }

    /** tracks position of parser for solution submission. */
    protected int position;
    /**
     * Returns value of player submission or some part thereof.
     * The considered part ranges from the current position to the end of the solution/parenthetical expression.
     * @return value of evaluated portion
     */
    protected double calculateSubmission() {
        double result = 0;
        while (position < givenSoln.size()) {
            int currentID = givenSoln.get(position).getButtonID();
            if (currentID == InputIDs.MINUS_INPUT) {
                position++;
                result -= calculateTerm();
            } else if (currentID == InputIDs.PLUS_INPUT) {
                position++;
                result += calculateTerm();
            } else if (currentID == InputIDs.RIGHT_PAREN_INPUT){
                position++;
                break;
            } else {
                result += calculateTerm();
            }
        }
        // System.out.println(result);
        return result;
    }
    /**
     * Returns value of term in player submission. A term is basically anything you reach by a series of
     * multiplications and divisions.
     * @return value of evaluated portion
     */
    protected double calculateTerm() {
        double result = 1;
        while (position < givenSoln.size()) {
            int currentID = givenSoln.get(position).getButtonID();
            if (currentID == InputIDs.TIMES_INPUT) {
                position++;
                if (givenSoln.get(position).getButtonID() <= 4) {
                    result *= ((NumButton) givenSoln.get(position)).getValue();
                    position++;
                } else {
                    // must be parenthetical expression
                    position++;
                    result *= calculateSubmission();
                }
            } else if (currentID == InputIDs.DIVIDES_INPUT) {
                position++;
                if (givenSoln.get(position).getButtonID() <= 4) {
                    result /= ((NumButton) givenSoln.get(position)).getValue();
                    position++;
                } else {
                    // must be parenthetical expression
                    position++;
                    result /= calculateSubmission();
                }
            } else if (currentID == InputIDs.LEFT_PAREN_INPUT) {
                position++;
                result *= calculateSubmission();
            } else if (currentID <= 4) {
                result *= ((NumButton) givenSoln.get(position)).getValue();
                position++;
            } else {
                break;
            }
        }
        // System.out.println(result);
        return result;
    }
}


package edu.gatech.seclass.sdpscramble;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * @author John Youngblood
 */

public class GameActivity extends AppCompatActivity {

    private EditText guess;
    private TextView uidView;
    private TextView scrambleView;
    private TextView clueView;
    String activeUser, selectedId, phrase, clue, scrambledPhrase, lastGuess = new String();
    boolean isInProgress;


    //kill keyboard when non-text field touched
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inmm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inmm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        activeUser = settings.getString("user", null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        final MainMenuActivity menu = new MainMenuActivity();
        Cursor scrambleCursor = menu.getTableCursor(WordScrambleTable.class);       //get cursor with all scramble data

        //get selected scramble passed from select screen
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            selectedId= extras.getString("CHOSEN_SCRAMBLE");
            isInProgress = extras.getBoolean("IN_PROGRESS");
        } else {
            selectedId= (String) savedInstanceState.getSerializable("CHOSEN_SCRAMBLE");
            isInProgress= (Boolean) savedInstanceState.getSerializable("IN_PROGRESS");
        }


        try {
            boolean scrambleFound = false;
            //iterate through cursor
            while(scrambleCursor.moveToNext() && !scrambleFound){
                String rowUid = scrambleCursor.getString(scrambleCursor.getColumnIndex("uniqueIdentifier"));
                if(selectedId.equals(rowUid)){
                    scrambleFound = true;
                    clue = scrambleCursor.getString(scrambleCursor.getColumnIndex("clue"));
                    phrase = scrambleCursor.getString(scrambleCursor.getColumnIndex("phrase"));
                    scrambledPhrase = scrambleCursor.getString(scrambleCursor.getColumnIndex("scrambledPhrase"));

                    //found word in db, set fields in view
                    //set uid
                    uidView = (TextView) findViewById(R.id.wordScrambleUID);
                    uidView.setText(selectedId);

                    //set clue
                    clueView = (TextView) findViewById(R.id.clueGame);
                    clueView.setText(clue);

                    //set scrambled phrase
                    scrambleView = (TextView) findViewById(R.id.scrambleGame);
                    scrambleView.setText(scrambledPhrase);

                    //set in progress, if it was in progress
                    guess = (EditText)findViewById(R.id.scrambleGuess);
                    if(isInProgress){
                        lastGuess = menu.retrieveinProgressPhrase(activeUser, selectedId);
                        guess.setText(lastGuess);
                    } else {
                        //set phrase but replace all characters with a _
                        guess.setHint(phrase.replaceAll("[A-Z]+?|[a-z]+?", "_"));
                    }

                }
            }
        } finally {
            scrambleCursor.close();
        }

        //guess button action
        Button submitGuess = (Button) findViewById(R.id.submitGuess);
        submitGuess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lastGuess = guess.getText().toString();
                if(lastGuess.toLowerCase().equals(phrase.toLowerCase())){


                    //report solve
                    menu.reportSolve(selectedId, activeUser);
                    setContentView(R.layout.scramble_guess_successful);

                    //run code when OK button is clicked
                    final Button okButton = (Button) findViewById(R.id.okScrambleGuessCorrect);
                    okButton.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v) {
                            Intent reportSolveActivity = new Intent(v.getContext(), MainMenuActivity.class);
                            startActivity(reportSolveActivity);
                        }
                    });
                }
                else{
                    guess.setError("Wrong Answer. Try Again!");
                }

            }
        });


        Button exitGame = (Button) findViewById(R.id.exitGame);
        exitGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if user hasn't guessed yet, set to
                if(lastGuess.isEmpty() || lastGuess == null){
                    lastGuess = scrambledPhrase;
                }

                //create a ProgressTrackerTable record
                menu.setInProgress(activeUser, selectedId, lastGuess);

                Intent exitGameActivity = new Intent(v.getContext(), MainMenuActivity.class);
                startActivity(exitGameActivity);
            }
        });

    }

}

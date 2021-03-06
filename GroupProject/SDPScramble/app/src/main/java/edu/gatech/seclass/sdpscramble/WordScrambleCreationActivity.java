package edu.gatech.seclass.sdpscramble;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Brian Greenwald
 */

public class WordScrambleCreationActivity extends AppCompatActivity {

    private WordScramble currentWordScramble;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_word_scramble_creation);

        final Button cancelButton = (Button) findViewById(R.id.cancelWordScrambleCreation);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainMenuActivity = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuActivity);
            }
        });
    }

    public void scramble(View view) {
        EditText phraseText = (EditText) findViewById(R.id.phraseCreation);
        String currentPhrase = phraseText.getText().toString();

        if (currentPhrase.isEmpty()) {
            phraseText.setError("Missing Phrase");
            phraseText.requestFocus();
            return;
        }

        if (!isPhraseValid(currentPhrase)) {
            phraseText.setError(
                "Phrases must between " + WordScramble.MIN_PHRASE_LENGTH + " and " +
                    WordScramble.MAX_PHRASE_LENGTH + " characters");
            phraseText.requestFocus();
            return;
        }

        EditText clueText = (EditText) findViewById(R.id.clueCreation);
        String currentClue = clueText.getText().toString();

        try {
            currentWordScramble = new WordScramble(currentPhrase, currentClue);
        } catch (IllegalArgumentException iae) {
            phraseText.setError(iae.getMessage());
            phraseText.requestFocus();
            return;
        }

        String scrambledPhrase;
        try {
            scrambledPhrase = currentWordScramble.scramble();
        } catch (IllegalArgumentException  iae) {
            phraseText.setError(iae.getMessage());
            phraseText.requestFocus();
            return;
        }

        TextView scrambledPhraseText = (TextView) findViewById(R.id.scrambledPhraseCreation);
        scrambledPhraseText.setText(scrambledPhrase);
        scrambledPhraseText.setKeyListener(null);
    }

    private boolean isPhraseValid(String currentPhrase) {
        int length = currentPhrase.length();

        return length >= WordScramble.MIN_PHRASE_LENGTH && length <= WordScramble.MAX_PHRASE_LENGTH;
    }

    public void submit(View view) {
        //validate phrase and scramble
        TextView scrambledPhraseText = (TextView) findViewById(R.id.scrambledPhraseCreation);
        String scrambledPhrase = scrambledPhraseText.getText().toString();

        if (scrambledPhrase.isEmpty()) {
            EditText phraseText = (EditText) findViewById(R.id.phraseCreation);
            String currentPhrase = phraseText.getText().toString();
            if(currentPhrase.isEmpty()){
                phraseText.setError("Enter a valid scramble");
                phraseText.requestFocus();
            } else {
                phraseText.setError("You must scramble your phrase by clicking 'Scramble'");
                phraseText.requestFocus();
            }

            return;
        }

        //validate clue - there must be a clue
        TextView clueText = (TextView) findViewById(R.id.clueCreation);
        String clue = clueText.getText().toString();
        if(clue.isEmpty()){
            clueText.setError("You must enter a clue");
            clueText.requestFocus();
            return;
        }

        String wordScrambleUid;

        SharedPreferences settings = getSharedPreferences(MainMenuActivity.PREFS_NAME, 0);
        String creator = settings.getString("user", null);

        MainMenuActivity mainMenuActivity = new MainMenuActivity();
        wordScrambleUid = mainMenuActivity.createWordScramble(currentWordScramble.getPhrase(), scrambledPhrase, currentWordScramble.getClue(), creator);


        setContentView(R.layout.word_scramble_creation_successful);

        TextView wordScrambleUidText = (TextView) findViewById(R.id.newUid);
        wordScrambleUidText.setText(wordScrambleUid);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inmm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inmm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void returnToMainMenu(View view) {
        Intent mainMenuActivity = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(mainMenuActivity);
    }
}


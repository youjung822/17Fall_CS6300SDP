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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_word_scramble_creation);

        final Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainMenuActivity = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuActivity);
            }
        });
    }

    public void scramble(View view) {
        EditText phraseText = (EditText) findViewById(R.id.phrase);
        String currentPhrase = phraseText.getText().toString();

        if (currentPhrase.isEmpty()) {
            phraseText.setError("Missing Phrase.");
            return;
        }

        if (!isPhraseValid(currentPhrase)) {
            phraseText.setError(
                "Phrases must between " + WordScramble.MIN_PHRASE_LENGTH + " and " +
                    WordScramble.MAX_PHRASE_LENGTH + " characters.");
            return;
        }

        EditText clueText = (EditText) findViewById(R.id.clue);
        String currentClue = clueText.getText().toString();

        try {
            currentWordScramble = new WordScramble(currentPhrase, currentClue);
        } catch (IllegalArgumentException iae) {
            phraseText.setError(iae.getMessage());
            return;
        }

        String scrambledPhrase;
        try {
            scrambledPhrase = currentWordScramble.scramble();
        } catch (IllegalArgumentException  iae) {
            phraseText.setError(iae.getMessage());
            return;
        }

        TextView scrambledPhraseText = (TextView) findViewById(R.id.scrambledPhrase);
        scrambledPhraseText.setText(scrambledPhrase);
    }

    private boolean isPhraseValid(String currentPhrase) {
        int length = currentPhrase.length();

        return length >= WordScramble.MIN_PHRASE_LENGTH && length <= WordScramble.MAX_PHRASE_LENGTH;
    }

    public void submit(View view) {
        TextView scrambledPhraseText = (TextView) findViewById(R.id.scrambledPhrase);
        String scrambledPhrase = scrambledPhraseText.getText().toString();


        String wordScrambleUid;

        SharedPreferences settings = getSharedPreferences(MainMenuActivity.PREFS_NAME, 0);
        String creator = settings.getString("user", null);

        MainMenuActivity mainMenuActivity = new MainMenuActivity();
        wordScrambleUid = mainMenuActivity.createWordScramble(currentWordScramble.getPhrase(), scrambledPhrase, currentWordScramble.getClue(), creator);

        setContentView(R.layout.word_scramble_creation_successful);
        
        TextView wordScrambleUidText = (TextView) findViewById(R.id.wordScrambleUID);
        wordScrambleUidText.setText(wordScrambleUid);
    }

    private WordScramble currentWordScramble;

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


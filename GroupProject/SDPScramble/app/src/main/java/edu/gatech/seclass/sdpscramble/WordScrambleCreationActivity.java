package edu.gatech.seclass.sdpscramble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.net.SocketTimeoutException;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * @author Brian Greenwald
 */
public class WordScrambleCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }

        EditText clueText = (EditText) findViewById(R.id.clue);
        String currentClue = clueText.getText().toString();

        try {
            currentWordScramble = new WordScramble(currentPhrase, currentClue);
        } catch (IllegalArgumentException iae) {
            phraseText.setError(iae.getMessage());
            return;
        }

        String scrambledPhrase = "";
        try {
            currentWordScramble.scramble();
        } catch (IllegalArgumentException  iae) {
            phraseText.setError(iae.getMessage());
            return;
        }

        EditText scrambledPhraseText = (EditText) findViewById(R.id.scrambledPhrase);
        scrambledPhraseText.setText(scrambledPhrase);
    }

    private boolean isPhraseValid(String currentPhrase) {
        int length = currentPhrase.length();

        return length >= WordScramble.MIN_PHRASE_LENGTH && length <= WordScramble.MAX_PHRASE_LENGTH;
    }

    public void submit(View view) {
        EditText scrambledPhraseText = (EditText) findViewById(R.id.scrambledPhrase);
        String scrambledPhrase = scrambledPhraseText.getText().toString();

        ExternalWebService ews = ExternalWebService.getInstance();

        String wordScrambleUid;

        try {
            // TODO: Replace creator with current user's ID
            wordScrambleUid = ews.newScrambleService(
                currentWordScramble.getPhrase(), scrambledPhrase, currentWordScramble.getClue(), "");
        } catch (SocketTimeoutException ste) {
            scrambledPhraseText.setError("Error accessing server. Please try again later.");
            return;
        }

        SavedWordScramble savedWordScramble = new SavedWordScramble(
            wordScrambleUid, currentWordScramble.getPhrase(), scrambledPhrase,
            currentWordScramble.getClue());

        // TODO: Persist savedWordScramble to local DB
    }

    private WordScramble currentWordScramble;
}


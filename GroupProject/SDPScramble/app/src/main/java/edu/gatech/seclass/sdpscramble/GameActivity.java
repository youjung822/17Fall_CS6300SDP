package edu.gatech.seclass.sdpscramble;

import android.app.Activity;
import android.content.Context;
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

import java.util.List;

/**
 * @author John Youngblood
 */

public class GameActivity extends AppCompatActivity {

    private EditText guess;
    private TextView uidView;
    private TextView scrambleView;
    private TextView clueView;
    private List<String> currentScramble;

    //kill keyboard when non-text field touched
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inmm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inmm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        Context context = this.getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences(getString(R.string.word_scramble), Context.MODE_PRIVATE);
        String uid = settings.getString(getString(R.string.word_scramble), "");

        MainMenuActivity menu = new MainMenuActivity();
        currentScramble = menu.getScramble(uid);

        uidView = (TextView) findViewById(R.id.wordScrambleUID);
        uidView.setText(currentScramble.get(0));

        scrambleView = (TextView) findViewById(R.id.scrambleGame);
        scrambleView.setText(currentScramble.get(2));

        clueView = (TextView) findViewById(R.id.clueGame);
        clueView.setText(currentScramble.get(3));

        guess = (EditText)findViewById(R.id.scrambleGuess);
        guess.setHint(currentScramble.get(2).replaceAll("[A-Z]+?|[a-z]+?", "_"));
        System.out.println(guess.getText().toString());
        Button submitGuess = (Button) findViewById(R.id.submitGuess);
        submitGuess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println(guess.getText().toString());
                System.out.println(currentScramble.get(1));
                if(guess.getText().toString().equals(currentScramble.get(1))){
                    System.out.println(guess.getText().toString());
                    Intent reportSolveActivity = new Intent(v.getContext(), MainMenuActivity.class);
                    startActivity(reportSolveActivity);
                }
                else{
                    guess.setError("Wrong Answer. Try Again!");
                }

            }
        });

        Button exitGame = (Button) findViewById(R.id.exitGame);
        exitGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO save scrambleGuess to db
                Intent exitGameActivity = new Intent(v.getContext(), MainMenuActivity.class);
                startActivity(exitGameActivity);
            }
        });

    }

}

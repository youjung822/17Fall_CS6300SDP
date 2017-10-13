package edu.gatech.seclass.sdpscramble;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

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

        String uid = savedInstanceState.getString("uid");
        MainMenuActivity menu = new MainMenuActivity();
        currentScramble = menu.getScramble(uid);

        uidView = (TextView) findViewById(R.id.wordScrambleUID);
        uidView.setText(currentScramble.get(0));

        scrambleView = (TextView) findViewById(R.id.scrambleGame);
        scrambleView.setText(currentScramble.get(2));

        clueView = (TextView) findViewById(R.id.clueGame);
        clueView.setText(currentScramble.get(3));

        guess = (EditText)findViewById(R.id.scrambleGuess);
        guess.setText(currentScramble.get(1).replaceAll("[A-Z]+?|[a-z]+?","_"));

        Button createScramble = (Button) findViewById(R.id.exitGame);
        createScramble.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent createActivity = new Intent(getApplicationContext(), WordScrambleCreationActivity.class);
                startActivity(createActivity);
            }
        });

    }

    //used by submit button
    public void checkSolution(View view){
        if(guess.getText().toString().equals(currentScramble.get(1))){

        }
    }

    //used by cancel button
    public void exit(View view){
        //pass the string id and current guess to progressTracker\
        //no need to pass username since progressTracker is wiped out after logging out/login
    }

}

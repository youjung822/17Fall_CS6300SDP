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
import android.widget.TextView;


/**
 * Created by John Youngblood on 10/10/17.
 */

public class PlayerCreationActivity extends AppCompatActivity {

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inmm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inmm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_player_creation);

        //run code when Sign Up button is clicked
        final Button submit = (Button) findViewById(R.id.addPlayer);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainMenuActivity menu = new MainMenuActivity();

                //get user's input f
                TextView inputFirstName = (TextView) findViewById(R.id.firstName);
                TextView inputLastName = (TextView) findViewById(R.id.lastName);
                TextView inputEmail = (TextView) findViewById(R.id.email);
                TextView inputUsername = (TextView) findViewById(R.id.usernameInput);

                String returnedUsername = menu.createPlayer(inputFirstName.getText().toString(), inputLastName.getText().toString(), inputEmail.getText().toString(), inputUsername.getText().toString());

                setContentView(R.layout.player_creation_successful);
                //Intent playerCreation = new Intent(getApplicationContext(), PlayerCreationActivity.class);
                //startActivity(playerCreation);

                TextView finalUsername = (TextView) findViewById(R.id.userName);
                finalUsername.setText(returnedUsername);
            }
        });

    }

}

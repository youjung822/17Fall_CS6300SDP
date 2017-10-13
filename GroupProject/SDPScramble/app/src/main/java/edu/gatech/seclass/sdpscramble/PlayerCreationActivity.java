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

        //run code when add player button is clicked
        final Button addPlayer = (Button) findViewById(R.id.addPlayer);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainMenuActivity menu = new MainMenuActivity();
                boolean validInput = true;

                //get user's input
                TextView inputFirstName = (TextView) findViewById(R.id.firstName);
                TextView inputLastName = (TextView) findViewById(R.id.lastName);
                TextView inputEmail = (TextView) findViewById(R.id.email);
                TextView inputUsername = (TextView) findViewById(R.id.usernameInput);

                //if the firstname field is empty, display an error
                if (inputFirstName.getText().toString().length() == 0) {
                    inputFirstName.setError("First Name Required");
                    inputFirstName.requestFocus();
                    validInput = false;
                }

                //if the lastname field is empty, display an error
                if (inputLastName.getText().toString().length() == 0) {
                    inputLastName.setError("Last Name Required");
                    inputLastName.requestFocus();
                    validInput = false;
                }

                //if the email field is empty, display an error
                if (inputEmail.getText().toString().length() == 0) {
                    inputEmail.setError("Email Required");
                    inputEmail.requestFocus();
                    validInput = false;
                }

                //if the username field is empty, display an error
                if (inputUsername.getText().toString().length() == 0) {
                    inputUsername.setError("Username Required");
                    inputUsername.requestFocus();
                    validInput = false;
                }

                if(validInput){
                    String returnedUsername = menu.createPlayer(inputUsername.getText().toString(), inputFirstName.getText().toString(), inputLastName.getText().toString(), inputEmail.getText().toString());
                    setContentView(R.layout.player_creation_successful);

                    TextView finalUsername = (TextView) findViewById(R.id.userName);
                    finalUsername.setText(returnedUsername);

                    //run code when OK button is clicked
                    final Button okButton = (Button) findViewById(R.id.ok);
                    okButton.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v) {
                            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(loginActivity);
                        }
                    });
                }

            }
        });

    }


}

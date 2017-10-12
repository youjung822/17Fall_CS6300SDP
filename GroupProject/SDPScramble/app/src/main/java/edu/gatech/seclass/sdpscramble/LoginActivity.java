package edu.gatech.seclass.sdpscramble;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {
    private static String userID = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inmm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inmm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //run code when Login button is clicked
        final Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                //get user's input for username
                TextView inputUsernameText = (TextView) findViewById(R.id.usernameInput);

                //if the username field is empty, display an error
                if (inputUsernameText.getText().toString().length() == 0) {
                    inputUsernameText.setError("Username Required");
                    inputUsernameText.requestFocus();
                } else {
                    final MainMenuActivity menu = new MainMenuActivity();
                    //call MainMenu.login - returns true, the username is valid
                    if (menu.login(inputUsernameText.getText().toString())) {
                        userID = inputUsernameText.getText().toString();
                        //username was valid - redirect to main_menu.xml and MainMenuActivity
                        Intent mainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
                        startActivity(mainMenu);

                    } else {
                        //username was invalid
                        inputUsernameText.setError("Username not found.");
                        inputUsernameText.requestFocus();
                    }

                }

            }
        });

        //run code when Sign Up button is clicked
        final Button signup = (Button) findViewById(R.id.createPlayer);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent playerCreation = new Intent(getApplicationContext(), PlayerCreationActivity.class);
                startActivity(playerCreation);
            }
        });



    }

}

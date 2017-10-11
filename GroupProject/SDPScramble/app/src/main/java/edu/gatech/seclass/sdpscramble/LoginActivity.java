package edu.gatech.seclass.sdpscramble;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

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

        final MainMenuActivity menu = new MainMenuActivity();

        //run code when Login button is clicked
        final Button button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                //get user's input for username
                TextView inputUsernameText = (TextView) findViewById(R.id.usernameInput);

                //if the username field is empty, display an error
                if(inputUsernameText.getText().toString().length()== 0) {
                    inputUsernameText.setError("Username Required");
                    inputUsernameText.requestFocus();
                } else {
                    //call MainMenu.login - returns true, the username is valid
                    if(menu.login(inputUsernameText.toString())){
                        //username was valid
                    } else {
                        //username was invalid
                        inputUsernameText.setError("Username not found.");
                        inputUsernameText.requestFocus();
                    }

                }

            }
        });

    }






}

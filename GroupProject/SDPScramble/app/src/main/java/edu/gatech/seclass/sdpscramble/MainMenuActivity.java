package edu.gatech.seclass.sdpscramble;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * Created by John Youngblood on 10/10/17.
 */

public class MainMenuActivity extends AppCompatActivity {
    //need to create instance onCreate and pass same instance whenever EWS is called
    final ExternalWebService ews = ExternalWebService.getInstance();

    private String userID = "";

    //set loggedInUser - usually called when a new player is created
    public void setLoggedInUser(String username){
        this.userID = username;
    }

    //get logged in username
    public String getLoggedInUser(String username){
        return this.userID;
    }

    //check if user is logged in
    public boolean isUserLoggedIn(){
        if(userID.isEmpty()){
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isUserLoggedIn()){
            //user is already logged in
            setContentView(R.layout.main_menu);
        } else {
            //user is not logged in - direct to LoginActivity
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);

        }

    }

    //return true if username is valid
    public boolean login(String username){
        return this.login(ews, username);
    }

    //return unique username
    public String createPlayer(String username, String firstname, String lastname, String email){
        return this.createPlayer(ews, username, firstname, lastname, email);
    }




    /**
     * createPlayer()
     * calls EWS and creates a new player
     * @return unique username
     */

    private static String createPlayer(ExternalWebService ews, String username, String firstname, String lastname, String email) {

        String strPlayerNewID = "";

        try {
            strPlayerNewID = ews.newPlayerService(username,firstname,lastname,email);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }

        return strPlayerNewID;
    }

    /**
     * login()
     * calls EWS and logs a player in
     * @return unique username
     */

    private static boolean login(ExternalWebService ews, String username) {
        boolean validUsername = false;
        if (username != null && !username.isEmpty()) {
            List<List<String>> playerList = ews.retrievePlayerListService();

            Iterator<List<String>> iter = playerList.iterator();
            while (iter.hasNext()) {
                String usr = iter.next().get(0);
                if (usr.equals(username)) {
                    validUsername = true;
                }
            }

        }
        return validUsername;
    }


    //kill keyboard when non-text field is touched
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inmm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inmm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}



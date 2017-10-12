package edu.gatech.seclass.sdpscramble;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * Created by John Youngblood on 10/10/17.
 */

public class MainMenuActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";


    //need to create instance onCreate and pass same instance whenever EWS is called
    final ExternalWebService ews = ExternalWebService.getInstance();

    //get active user
    public String getActiveUser(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        //returns active user or null if there is no logged in user
        return settings.getString("user", null);
    }

    //check if user logged in
    public boolean isLoggedIn(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if(settings.getString("user", null) == null || settings.getString("user", null).isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    //log out user
    public void logout(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mainmenu code goes here
        if(isLoggedIn()){
            //user is already logged in
            setContentView(R.layout.main_menu);
            TextView userInfo = (TextView) findViewById(R.id.usernameInput);
            userInfo.setText(getActiveUser());


            //user logs out
            final Button logout = (Button) findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    logout();
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);
                }
            });



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


}



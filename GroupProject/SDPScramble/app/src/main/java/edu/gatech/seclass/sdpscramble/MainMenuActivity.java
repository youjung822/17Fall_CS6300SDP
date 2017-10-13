package edu.gatech.seclass.sdpscramble;

import android.content.SharedPreferences;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * Created by John Youngblood on 10/10/17.
 */

public class MainMenuActivity extends AppCompatActivity {
    static SQLiteDatabase db;
    final ExternalWebService ews = ExternalWebService.getInstance(); //need to create instance once and pass same instance whenever EWS is called

    /**
     * ACTIVE USER PREFERENCES - START
     * current logged in user stored as a user preference
     */

    public static final String PREFS_NAME = "MyPrefsFile";

    //returns active user or null if there is no logged in user
    public String getActiveUser(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("user", null);
    }

    //check if a user is logged in
    public boolean isLoggedIn(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if(settings.getString("user", null) == null || settings.getString("user", null).isEmpty())
            return false;
        else
            return true;
    }

    //log user out
    public void logout(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * ACTIVE USER PREFERENCES - END
     */

    //return true if username is valid
    public boolean login(String username){
        return this.login(ews, username);
    }

    //return unique username
    public String createPlayer(String username, String firstname, String lastname, String email){
        return this.createPlayer(ews, username, firstname, lastname, email);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * INITIALIZE DATABASE
         */

        SDPDatabaseHelper dbHelper = new SDPDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        //when a user is logged in
        if(isLoggedIn()){
            setContentView(R.layout.main_menu);
            TextView userInfo = (TextView) findViewById(R.id.usernameInput);
            userInfo.setText(getActiveUser());


            //if a user logs out
            final Button logout = (Button) findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    logout();
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);
                }
            });

            //user clicks on Solve a Word Scramble
            final ImageButton solveScramble = (ImageButton) findViewById(R.id.solveWordScrambles);
            solveScramble.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(gameActivity);
                }
            });

            //user clicks on Create a Word Scramble
            final ImageButton createScramble = (ImageButton) findViewById(R.id.createWordScramble);
            createScramble.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent createActivity = new Intent(getApplicationContext(), WordScrambleCreationActivity.class);
                    startActivity(createActivity);
                }
            });

            //user clicks on View Player Statistics
            final ImageButton viewPlayerStats = (ImageButton) findViewById(R.id.viewPlayerStatistics);
            viewPlayerStats.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent playerStatsActivity = new Intent(getApplicationContext(), PlayerStatisticsActivity.class);
                    startActivity(playerStatsActivity);
                }
            });

            //user clicks on Word Scramble Statistics
            final ImageButton wordStats = (ImageButton) findViewById(R.id.viewWordScrambleStatistics);
            wordStats.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent wordStatsActivity = new Intent(getApplicationContext(), WordScrambeStatisticsActivity.class);
                    startActivity(wordStatsActivity);
                }
            });

        //user is not logged in - direct to LoginActivity
        } else {
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }

    }


    /**
     *  ALL CALLS TO ExternalWebService - START
     */



    /**
     * createPlayer()
     * calls EWS and creates a new player - then adds the person to the local database
     * @return unique username
     */

    private static String createPlayer(ExternalWebService ews, String username, String firstname, String lastname, String email) {

        String strPlayerNewID = "";

        //insert new player in EWS
        try {
            strPlayerNewID = ews.newPlayerService(username,firstname,lastname,email);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }

        //insert new player in local db
        insertPlayerData(strPlayerNewID, firstname, lastname, email);

        //return unique username
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

    /**
     *  ALL CALLS TO ExternalWebService - END
     */

    /**
     * Database CRUD - START
     */


    // delete all data from the table passed as a class
    public static void clearAllData(Class Tbl) {
        cupboard().withDatabase(db).delete(Tbl, null);
    }

    //get Table cursor with all data
    public static Cursor getTableCursor(Class Tbl){
        return cupboard().withDatabase(db).query(Tbl).getCursor();
    }

    // create player in local db
    public static void insertPlayerData(String username, String firstname, String lastname, String email){
        //create player in local database
        PlayerTable newPlayer = new PlayerTable(username, firstname, lastname, email, 0, 0, 0.0);
        long id = cupboard().withDatabase(db).put(newPlayer);

        /**

        samle code for iterating through a cursor - can remove at end
        try {
            //iterate cursor
            String column_name = "username";
            while(cursor.moveToNext()){
                //DatabaseUtils.dumpCurrentRow(player);
                System.out.println(cursor.getString(cursor.getColumnIndex(column_name)));
            }

        } finally {
            cursor.close();
        }
         */

    }



    /**
     * Database CRUD - END
     */
}



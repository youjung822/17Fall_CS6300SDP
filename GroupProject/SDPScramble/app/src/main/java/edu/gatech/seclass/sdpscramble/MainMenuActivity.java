package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.gatech.seclass.utilities.ExternalWebService;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by John Youngblood on 10/10/17.
 */

public class MainMenuActivity extends AppCompatActivity {

    final ExternalWebService ews = ExternalWebService.getInstance(); //need to create instance once and pass same instance whenever EWS is called

    /**
     * ACTIVE USER PREFERENCES - START
     * current logged in user stored as a user preference
     */

    public static final String PREFS_NAME = "MyPrefsFile";
    static SQLiteDatabase db;

    //returns active user or null if there is no logged in user
    private String getActiveUser() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("user", null);
    }

    //check if a user is logged in
    private boolean isLoggedIn() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return !(settings.getString("user", null) == null || settings.getString("user", null).isEmpty());
    }

    //log user out
    private void logout() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * ACTIVE USER PREFERENCES - END
     */


    /**
     * PUBLIC METHODS - START
     */

    //return true if username is valid
    public boolean login(String username) {
        return login(ews, username);
    }

    //return unique username
    public String createPlayer(String username, String firstname, String lastname, String email) {
        return createPlayer(ews, username, firstname, lastname, email);
    }

    //return specified scramble
    public List<String> getScramble(String wordScrambleUid) {
        return getScramble(ews, wordScrambleUid);
    }

    //create a word scramble
    public String createWordScramble(String phrase, String scrambledPhrase, String clue, String creator) {
        return createWordScramble(ews, phrase, scrambledPhrase, clue, creator);
    }

    //report solved scramble by player
    public boolean reportSolve(String wordScrambleUid, String username) {
        return reportSolve(ews, wordScrambleUid, username);
    }

    /**
     * PUBLIC METHODS - END
     */


    /**
     * PRIVATE METHODS AND EWS CALLS - START
     */


    /**
     * createPlayer()
     * calls EWS and creates a new player - then adds the person to the local database
     * @return unique username
     *
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
     * listUnsolvedScrambles()
     * creates a ArrayList<String> of all unsolved scrambles not created by the user.
     * each string in the ArrayList is of the format "wordScrambleUid: scrambledPhrase" to be used in a ListView
     * @return ArrayList of unsolved scrambles
     */
    private static ArrayList<String> listUnsolvedScrambles(ExternalWebService ews, String username){
        List<List<String>> scrambles = ews.retrieveScrambleService();
        List<List<String>> players = ews.retrievePlayerListService();

        //just usernames for quick indexing
        List<String> usernames = new ArrayList<String>();
        for (List<String> player : players) {
            usernames.add(player.get(0));
        }
        //find index of current player's username in EWS
        List<String> player = players.get(usernames.indexOf(username));

        //grab list of wordScrambleUIDs of scrambles solved by the player.
        List<String> solvedScrambles;
        if (player.size() > 4) {
            solvedScrambles = player.subList(4, player.size());
        } else {
            solvedScrambles = new ArrayList<String>();
        }

        //create list of unsolved scrambles to show users in a ListView by
        //filtering out all scrambles solved
        ArrayList<String> unsolvedScrambles = new ArrayList<>();
        for (List<String> scramble : scrambles){
            if (!(solvedScrambles.contains(scramble.get(0))) && !(scramble.get(4).equals(username))) {
                unsolvedScrambles.add(scramble.get(0) + ": " + scramble.get(2));

            }
        }
        return unsolvedScrambles;

    }



    /**
     * getScramble()
     * grabs the scramble with the matching wordScrambleUid
     * @return scramble
     */
    private static List<String> getScramble(ExternalWebService ews,  String wordScrambleUid){
        List<List<String>> scrambles = ews.retrieveScrambleService();
        List<String> uids = new ArrayList<String>();

        for (List<String> scramble: scrambles) {
            uids.add(scramble.get(0));
        }

        return scrambles.get(uids.indexOf(wordScrambleUid));

    }

    /**
     * reportSolve()
     * report a solved scramble by a player.
     * @return boolean representing success
     */
    private static boolean reportSolve(ExternalWebService ews, String wordScrambleUid, String username){
        return ews.reportSolveService(wordScrambleUid,username);
    }

    private static String createWordScramble(ExternalWebService ews, String phrase, String scrambledPhrase, String clue, String creator) {
        String wordScrambleUid = "";

        try {
            wordScrambleUid = ews.newScrambleService(phrase, scrambledPhrase, clue, creator);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }

        if (!wordScrambleUid.isEmpty()) {
            insertWordScrambleData(wordScrambleUid, phrase, clue, scrambledPhrase, creator);
        }

        return wordScrambleUid;
    }

    /**
     * PRIVATE METHODS AND EWS CALLS - END
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

         sample code for iterating through a cursor - can remove at end
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

    public static void insertWordScrambleData(String wordScrambleUid, String phrase, String clue, String scrambledPhrase, String creator) {
        WordScrambleTable newWordScramble = new WordScrambleTable(wordScrambleUid, phrase, clue, scrambledPhrase, creator, 0);
        long id = cupboard().withDatabase(db).put(newWordScramble);
    }

    /**
     * Retrieve PlayerStat
     * @return
     */
    public ArrayList<PlayerTable>  retrievePlayerStatistic() {
        return retrievePlayerStatistic(ews);
    }

    //Pull the playser statistic data.
    private ArrayList<PlayerTable>  retrievePlayerStatistic(ExternalWebService ews)
    {
        List<List<String>> playerList = ews.retrievePlayerListService();
        ArrayList<PlayerTable> arrPlayer = new ArrayList<>();

        Iterator<List<String>> iter = playerList.iterator();
        while (iter.hasNext()) {
            List<String> curIter = iter.next();
            PlayerTable pt = new PlayerTable(curIter.get(0), curIter.get(1), curIter.get(2), curIter.get(3), 4,5,6.0);
            arrPlayer.add(pt);
        }
        return arrPlayer;
    }


    /**
     * Database CRUD - END
     */


    /**
     * ONCREATE()
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * INITIALIZE DATABASE
         */

        SDPDatabaseHelper dbHelper = new SDPDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        /**
         * CODE FOR MOCK EWS - START
         */

        //if a user has a local account but not one on EWS, make sure mock EWS doesn't crash it
        if(isLoggedIn()){
            if(!login(getActiveUser())){
                logout();
            }
        }


        /**
         * CODE FOR MOCK EWS - END
         */

        /**
         * IF USER LOGGED IN
         */

        //when a user is logged in
        if (isLoggedIn()) {
            setContentView(R.layout.main_menu);
            TextView userInfo = (TextView) findViewById(R.id.usernameMainMenu);
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
                    Context context = getApplicationContext();
                    SharedPreferences settings = context.getSharedPreferences(getString(R.string.select_word_scramble), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    List<String> list = listUnsolvedScrambles(ews, getActiveUser());
                    Set<String> set = new HashSet<String>(list);
                    editor.clear();
                    editor.putStringSet(getString(R.string.select_word_scramble), set);
                    editor.commit();
                    Intent selectScrambleActivity = new Intent(getApplicationContext(), UnsolvedScrambleSelectActivity.class);
                    startActivity(selectScrambleActivity);
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
                    Intent wordStatsActivity = new Intent(getApplicationContext(), WordScrambleStatisticsActivity.class);
                    startActivity(wordStatsActivity);
                }
            });

            /**
             * USER NOT LOGGED IN
             */

        } else {
            //direct to LoginActivity
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }

    }

    /**
     * Checks to make sure
     */


    /**
     * Database CRUD - END
     */
}


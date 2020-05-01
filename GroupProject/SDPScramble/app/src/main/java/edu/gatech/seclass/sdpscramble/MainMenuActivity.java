package edu.gatech.seclass.sdpscramble;

import android.content.ContentValues;
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
import java.util.Iterator;
import java.util.List;

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

    public boolean isValidScramble(String uid) {
        return isValidScramble(ews, uid);
    }

    //return unique username
    public String createPlayer(String username, String firstname, String lastname, String email) {
        return createPlayer(ews, username, firstname, lastname, email);
    }

    //return list of all players from EWS
    public List<List<String>> getAllPlayers(){
        return getAllPlayersFromEWS(ews);
    }

    //return list of all scrambles from EWS
    public List<List<String>> getAllScrambles(){
        return getAllScramblesFromEWS(ews);
    }

    //create a word scramble
    public String createWordScramble(String phrase, String scrambledPhrase, String clue, String creator) {
        return createWordScramble(ews, phrase, scrambledPhrase, clue, creator);
    }

    //report solved scramble by player
    public boolean reportSolve(String wordScrambleUid, String username) {
        return reportSolve(ews, wordScrambleUid, username);
    }

    //setInProgress() - set a scramble in progress if a user exits before solving
    public void setInProgress(String username, String scrambleUID, String inProgressPhrase){
        createProgressTracker(username, scrambleUID, "inprogress", inProgressPhrase);
    }

    //retrieve in progress phrase
    public String retrieveinProgressPhrase(String user, String wordScrambleUID) {
        return retrieveProgressPhrase(user, wordScrambleUID);
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
            List<List<String>> playerList = getAllPlayersFromEWS(ews);

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

    //validate scramble exists in EWS
    private static boolean isValidScramble(ExternalWebService ews, String uid) {
        boolean validScramble = false;
        if (uid != null && !uid.isEmpty()) {
            List<List<String>> scrambleList = getAllScramblesFromEWS(ews);

            Iterator<List<String>> iter = scrambleList.iterator();
            while (iter.hasNext()) {
                String ewsId = iter.next().get(0);
                if (uid.equals(ewsId)) {
                    validScramble = true;
                } else {
                }

            }
        }

        return validScramble;
    }

    /**
     * getAllPlayers() - gets all players from EWS - then calls db insert func
     */
    private static List<List<String>> getAllPlayersFromEWS(ExternalWebService ews){
        //call EWS
        List<List<String>> playerList = ews.retrievePlayerListService();

        //iterate through each and insert into local db (db function handles existing records)
        Iterator<List<String>> iter = playerList.iterator();
        while(iter.hasNext()){
            List<String> player = iter.next();
                //insert player in db - username,
                insertPlayerData(player.get(0), player.get(1), player.get(2), player.get(3));
        }

        return playerList;
    }



    /**
     * getAllScrambles() - gets all scrambles from EWS - then calls db insert func
     */

    private static List<List<String>> getAllScramblesFromEWS(ExternalWebService ews) {
        List<List<String>> scrambleList = ews.retrieveScrambleService();

        //iterate through each and insert into local db (db function handles existing records)
        Iterator<List<String>> iter = scrambleList.iterator();
        while(iter.hasNext()){
            List<String> scramble = iter.next();
            //insert player in db - username,
            insertWordScrambleData(scramble.get(0), scramble.get(1), scramble.get(3), scramble.get(2), scramble.get(4));
        }

        return scrambleList;
    }


    /**
     * reportSolve()
     * report a solved scramble by a player.
     * @return boolean representing success
     */
    private static boolean reportSolve(ExternalWebService ews, String wordScrambleUid, String username){
        //increment db fields - PlayerTable.numOfScramblesSolved and WordScrambleTable.numOfTimesSolved
        incrementIntField(PlayerTable.class, "numOfScramblesSolved", username);
        incrementIntField(WordScrambleTable.class, "numOfTimesSolved", wordScrambleUid);

        return ews.reportSolveService(wordScrambleUid, username);
    }

    //create a word scramble
    private static String createWordScramble(ExternalWebService ews, String phrase, String scrambledPhrase, String clue, String creator) {
        String wordScrambleUid = "";

        try {
            wordScrambleUid = ews.newScrambleService(phrase, scrambledPhrase, clue, creator);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }

        if (!wordScrambleUid.isEmpty()) {
            //insert scramble into local db
            insertWordScrambleData(wordScrambleUid, phrase, clue, scrambledPhrase, creator);

            //increment PlayerTable.numOfScramblesCreated
            incrementIntField(PlayerTable.class, "numOfScramblesCreated", creator);
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
    private static void clearAllData(Class Tbl) {
        //YOU SHOULD ALMOST NEVER CALL THIS
        cupboard().withDatabase(db).delete(Tbl, null);
    }

    //get Table cursor with all data
    public static Cursor getTableCursor(Class Tbl){
        return cupboard().withDatabase(db).query(Tbl).getCursor();
    }

    // create player in local db
    public static void insertPlayerData(String username, String firstname, String lastname, String email){
        boolean isUnique = true;
        //verify username doesn't already exist in local db
        Cursor playerCursor = getTableCursor(PlayerTable.class);

        try {
            //iterate through cursor
            while(playerCursor.moveToNext()){
                String rowUser = playerCursor.getString(playerCursor.getColumnIndex("username"));
                if(username.equals(rowUser)){
                    //user already exists in database
                    isUnique = false;
                }
            }
        } finally {
            playerCursor.close();
        }

        //username doesn't already exist in db, so insert
        if(isUnique){
            PlayerTable newPlayer = new PlayerTable(username, firstname, lastname, email, 0, 0, 0.0);
            long id = cupboard().withDatabase(db).put(newPlayer);
        }
    }

    //wordScrambleUid() - verify scramble uid doesn't already exist in db, then insert in db
    public static void insertWordScrambleData(String wordScrambleUid, String phrase, String clue, String scrambledPhrase, String creator) {
        boolean isUnique = true;

        //verify wordScrambleUid doesn't already exist in db before inserting
        Cursor scrambleCursor = getTableCursor(WordScrambleTable.class);

        try {
            //iterate through cursor
            while(scrambleCursor.moveToNext()){
                String rowUid = scrambleCursor.getString(scrambleCursor.getColumnIndex("uniqueIdentifier"));
                if(wordScrambleUid.equals(rowUid)){
                    //user already exists in database
                    isUnique = false;
                }
            }
        } finally {
            scrambleCursor.close();
        }

        //username doesn't already exist in db, so insert
        if(isUnique){
            WordScrambleTable newWordScramble = new WordScrambleTable(wordScrambleUid, phrase, clue, scrambledPhrase, creator, 0);
            long id = cupboard().withDatabase(db).put(newWordScramble);
        }
    }

    private static void incrementIntField(Class tbl, String field, String uid){
        //get table cursor
        Cursor cursor = getTableCursor(tbl);

        //get record with our uid - username for PlayerTable and uniqueIdentifier for WordScrambleTable
        try {
            while(cursor.moveToNext()) {
                String rowUid = cursor.getString(cursor.getColumnIndex(field));
                if(uid.equals(rowUid)){
                    //found our record
                    Integer intField = cursor.getInt(cursor.getColumnIndex(field));
                    intField++;
                    //update field in db
                    ContentValues values = new ContentValues(1);
                    values.put(field, intField);
                    cupboard().withDatabase(db).update(tbl, values, field + " = ?", uid);

                }
            }
        } finally {
            cursor.close();
        }

    }

    //check for existing progress trackers and create a new one if it doesn't already exist
    private static void createProgressTracker(String user, String wordScrambleUID, String wordState, String progressPhrase) {

        //check if there's an existing ProgressTracker
        Cursor progressCursor = getTableCursor(ProgressTrackerTable.class);

        try {
            while(progressCursor.moveToNext()) {
                String cursorScrambleUid = progressCursor.getString(progressCursor.getColumnIndex("wordScrambleUID"));
                String cursorPlayer = progressCursor.getString(progressCursor.getColumnIndex("player"));
                long localId = progressCursor.getLong(progressCursor.getColumnIndex("_id"));
                if(user.equals(cursorPlayer) && wordScrambleUID.equals(cursorScrambleUid)){
                    //there is an existing progress tracker and we'll just delete it
                    cupboard().withDatabase(db).delete(ProgressTrackerTable.class, localId);
                }
            }
        } finally {
            progressCursor.close();
        }

        ProgressTrackerTable newProgressTracker = new ProgressTrackerTable(user, wordScrambleUID, wordState, progressPhrase);
        long id = cupboard().withDatabase(db).put(newProgressTracker);

    }

    //retrieve progress phrase from db
    private static String retrieveProgressPhrase(String user, String wordScrambleUID) {
        String progressPhrase = "";

        Cursor progressCursor = getTableCursor(ProgressTrackerTable.class);

        //find progress tracker
        try {
            while(progressCursor.moveToNext()) {
                String cursorScrambleUid = progressCursor.getString(progressCursor.getColumnIndex("wordScrambleUID"));
                String cursorPlayer = progressCursor.getString(progressCursor.getColumnIndex("player"));
                if(user.equals(cursorPlayer) && wordScrambleUID.equals(cursorScrambleUid)){
                    //there is an existing progress tracker and we'll just delete it
                   progressPhrase = progressCursor.getString(progressCursor.getColumnIndex("inProgressPhrase"));
                }
            }
        } finally {
            progressCursor.close();
        }

        return progressPhrase;
    }

    //delete data in the local db that is not not in EWS to prevent crash
    private void deleteInvalidLocalData(){

        //iterate through local PlayerTable
        Cursor localPlayerCursor = getTableCursor(PlayerTable.class);
        try {
            while(localPlayerCursor.moveToNext()) {
                String localUsername = localPlayerCursor.getString(localPlayerCursor.getColumnIndex("username"));
                long localId = localPlayerCursor.getLong(localPlayerCursor.getColumnIndex("_id"));
                if(!login(localUsername)){
                    //player doesn't exist in EWS
                    cupboard().withDatabase(db).delete(PlayerTable.class, localId);
                }
            }
        } finally {
            localPlayerCursor.close();
        }

        //iterate through local word scramble table
        Cursor localScrambleCursor = getTableCursor(WordScrambleTable.class);
        try {
            while(localScrambleCursor.moveToNext()) {
                String localScrambleId = localScrambleCursor.getString(localScrambleCursor.getColumnIndex("uniqueIdentifier"));
                long localId = localScrambleCursor.getLong(localScrambleCursor.getColumnIndex("_id"));
                if(!isValidScramble(localScrambleId)){
                    //player doesn't exist in EWS
                    cupboard().withDatabase(db).delete(WordScrambleTable.class, localId);
                }
            }
        } finally {
            localScrambleCursor.close();
        }


        //iterate through local progress tracker
        Cursor localProgressCursor = getTableCursor(ProgressTrackerTable.class);
        try {
            while(localProgressCursor.moveToNext()) {
                String localScrambleId = localProgressCursor.getString(localProgressCursor.getColumnIndex("wordScrambleUID"));
                String localUsername = localProgressCursor.getString(localProgressCursor.getColumnIndex("player"));
                long localId = localProgressCursor.getLong(localProgressCursor.getColumnIndex("_id"));
                if(!(isValidScramble(localScrambleId) && login(localUsername))){
                    //player doesn't exist in EWS
                    cupboard().withDatabase(db).delete(ProgressTrackerTable.class, localId);
                }
            }
        } finally {
            localProgressCursor.close();
        }


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


        //initialize db
        SDPDatabaseHelper dbHelper = new SDPDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        //delete data in local db that doesn't exist in EWS
        deleteInvalidLocalData();

        //if a user has a local account but not one on EWS, make sure mock EWS doesn't crash it
        if(isLoggedIn()){
            if(!login(getActiveUser())){
                logout();
            }
        }


        //when a user is logged in
        if (isLoggedIn()) {
            setContentView(R.layout.main_menu);
            TextView userInfo = (TextView) findViewById(R.id.usernameMainMenu);
            userInfo.setText(getActiveUser());


            //if a user clears scrambles
            final Button logout = (Button) findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    logout();
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);
                }
            });

            /**
            //user clicks on clear scrambles
            final Button clearScramble = (Button) findViewById(R.id.clearScrambles);
            clearScramble.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clearAllData(WordScrambleTable.class);
                }
            });
            */

            //user clicks on Solve a Word Scramble
            final ImageButton solveScramble = (ImageButton) findViewById(R.id.solveWordScrambles);
            solveScramble.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent selectActivity = new Intent(getApplicationContext(), UnsolvedScrambleSelectActivity.class);
                    startActivity(selectActivity);
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


        //user isn't logged in
        } else {
            //direct to LoginActivity
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }

    }

}


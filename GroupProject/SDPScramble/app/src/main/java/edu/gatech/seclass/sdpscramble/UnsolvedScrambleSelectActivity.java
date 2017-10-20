package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author John Youngblood
 */

public class UnsolvedScrambleSelectActivity extends AppCompatActivity {
    String selectedScrambleID = new String();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_scramble_select);

        MainMenuActivity menu = new MainMenuActivity();

        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        String username = settings.getString("user", null);

        menu.getAllScrambles();      //get scrambles from EWS

        //get cursor of all data in word scramble class
        Cursor scrambleCursor = MainMenuActivity.getTableCursor(WordScrambleTable.class);
        List<String> solvedScrambles = new ArrayList<String>();           //our user's solved scrambles
        List<String> unSolvedScrambles = new ArrayList<String>();   //our user's solved scrambles

        //iterate through players' solved scrambles and add to solvedScrambles
        List<List<String>> playerList = menu.getAllPlayers(); //list of all players and their solved scrambles
        Iterator<List<String>> iterPlayer = playerList.iterator();
        boolean playerFound = false;
        while(iterPlayer.hasNext() && !playerFound) {
            List<String> player = iterPlayer.next();
            //find current player
            if (username.equals(player.get(0))) {
                //player found
                playerFound = true;

                //check if there are any scrambles for this player
                if (player.size() > 4)
                    solvedScrambles = player.subList(4, player.size());
            }
        }

        //get all of the player's in progress scrambles
        Cursor progressCursor = MainMenuActivity.getTableCursor(ProgressTrackerTable.class);   //get cursor with all progress data
        List<String> inProgressScrambles = new ArrayList<String>(); //user's in progress scrambles
        long progressId;

        while(progressCursor.moveToNext()){
            String progressUser = progressCursor.getString(progressCursor.getColumnIndex("player"));

            if(progressUser.equals(username)){
                //found a progress tracker for current player - add it to list of in progress scrambles
                inProgressScrambles.add(progressCursor.getString(progressCursor.getColumnIndex("wordScrambleUID")));
            }

        }

        //iterate WordScrambleTable cursor and get scrambles user hasn't solved or created
        try {
            while(scrambleCursor.moveToNext()){
                String creator = scrambleCursor.getString(scrambleCursor.getColumnIndex("createdBy"));
                String uid = scrambleCursor.getString(scrambleCursor.getColumnIndex("uniqueIdentifier"));
                String scrambledPhrase = scrambleCursor.getString(scrambleCursor.getColumnIndex("scrambledPhrase"));
                boolean isSolved = false;
                boolean isInProgress = false;

                //user is not the creator
                if(!username.equals(creator)){
                    //iterate through list of user's solved scrambles
                    for(String i : solvedScrambles){
                        if(i.equals(uid)){
                            //scramble is already solved
                            isSolved = true;
                        }
                    }
                    if(!isSolved){
                        //user isn't the creator and hasn't solved the scramble yet

                        //check if scramble is in progress by iterating through list of user's in progress scrambles
                        if(inProgressScrambles.size() > 0){
                            for(String i : inProgressScrambles){
                                if(i.equals(uid)){
                                    //this scramble is in progress
                                    isInProgress = true;
                                }
                            }
                        }

                        if(isInProgress){
                            unSolvedScrambles.add("IN PROGRESS - [" + uid + "] " + scrambledPhrase);
                        } else {
                            unSolvedScrambles.add("[" + uid + "] " + scrambledPhrase);
                        }
                    }
                }
            }
        } finally {
            scrambleCursor.close();
        }

        Collections.sort(unSolvedScrambles);

        //populate ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unSolvedScrambles);
        ListView listView = (ListView) findViewById(R.id.unsolvedWordScramblesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
                String ws = parent.getItemAtPosition(position).toString();

                selectedScrambleID = ws.substring(ws.indexOf('[')+1, ws.indexOf(']'));
                gameActivity.putExtra("CHOSEN_SCRAMBLE", selectedScrambleID);
                startActivity(gameActivity);
            }
        });


        //back button takes back to MainMenuActivity
        Button back = (Button)findViewById(R.id.backToMainMenu);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent backToMainMenu = new Intent(view.getContext(), MainMenuActivity.class);
                startActivity(backToMainMenu);
            }
        });
    }


}


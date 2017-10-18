package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnsolvedScrambleSelectActivity extends AppCompatActivity {
    String selectedScrambleID = new String();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_scramble_select);

        MainMenuActivity menu = new MainMenuActivity();

        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        String username = settings.getString("user", null);

        //get scrambles from EWS
        menu.getAllScrambles();

        //get cursor of all data in word scramble class
        Cursor scrambleCursor = menu.getTableCursor(WordScrambleTable.class);
        List<String> solvedScrambles = new ArrayList<String>();  //our user's solved scrambles
        List<String> unSolvedScrambles = new ArrayList<String>();  //our user's solved scrambles

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

        //iterate WordScrambleTable cursor and get scrambles user hasn't solved or created
        try {
            while(scrambleCursor.moveToNext()){
                String creator = scrambleCursor.getString(scrambleCursor.getColumnIndex("createdBy"));
                String uid = scrambleCursor.getString(scrambleCursor.getColumnIndex("uniqueIdentifier"));
                String scrambledPhrase = scrambleCursor.getString(scrambleCursor.getColumnIndex("scrambledPhrase"));
                boolean isSolved = false;
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
                        unSolvedScrambles.add("[" + uid + "] " + scrambledPhrase);
                    }
                }
            }
        } finally {
            scrambleCursor.close();
        }

        //populate ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unSolvedScrambles);
        ListView listView = (ListView) findViewById(R.id.unsolvedWordScramblesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
                String ws = parent.getItemAtPosition(position).toString();
                selectedScrambleID = ws.substring(1, ws.indexOf(']'));
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


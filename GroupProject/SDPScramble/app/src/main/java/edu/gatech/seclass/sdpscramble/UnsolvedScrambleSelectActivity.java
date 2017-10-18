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



        /**
        //listener for ListView
        ListView.OnItemClickListener scrambleClickedHandler = new ListView.OnItemClickListener() {
            Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
            gameActivity.putExtra("SELECTED_SCRAMBLE", select)

            //Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
           //startActivity(gameIntent);
        };

        listView.setOnItemClickListener(scrambleClickedHandler);

        */

        /**
        Context context = this.getApplicationContext();


        SharedPreferences settings = context.getSharedPreferences(getString(R.string.select_word_scramble), Context.MODE_PRIVATE);
        Set<String> set = settings.getStringSet(getString(R.string.select_word_scramble), null);
        List<String> unsolvedScrambles = new ArrayList<String>(set);


        //passes just the WordScramble ID to GameActivity when a wordScramble is selected (for now)
        ListView.OnItemClickListener scrambleClickedHandler = new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent gameIntent = new Intent(view.getContext(), GameActivity.class);
                String ws = adapterView.getItemAtPosition(i).toString();
                SharedPreferences settings = getSharedPreferences(getString(R.string.word_scramble), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.putString(getString(R.string.word_scramble), ws.substring(0, ws.indexOf(':')));
                editor.commit();
                Intent selectScrambleActivity = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(gameIntent);
            }
        };

        ListView listView = (ListView) findViewById(R.id.unsolvedWordScramblesList);
        //populate ListView

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unsolvedScrambles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(scrambleClickedHandler);

         */


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


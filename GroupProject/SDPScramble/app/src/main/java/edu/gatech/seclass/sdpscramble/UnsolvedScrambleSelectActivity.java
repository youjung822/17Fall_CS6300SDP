package edu.gatech.seclass.sdpscramble;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;


public class UnsolvedScrambleSelectActivity extends ListActivity {

    //pull scramble List data from EWS and and send to the ListView using an ArrayAdapter

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_scramble_select);

        //for when this stuff is passed to this activity by mainMenu
//        ExternalWebService ews = (ExternalWebService) savedInstanceState.get("ews");
//        String currentPlayer = savedInstanceState.getString("currentPlayer");

        ExternalWebService ews = ExternalWebService.getInstance();
        String currentPlayer = "Active User";

        List<List<String>> scrambles = ews.retrieveScrambleService();
        List<List<String>> players = ews.retrievePlayerListService();

        //pull list of solved scrambles by player to filter out list of unsolved scrambles(this can be passed by MainMenu)
        List<String> solvedScrambles = new ArrayList<>();
        for(List<String> pl : players){
            if(pl.get(0).equals(currentPlayer)){
                solvedScrambles=pl.subList(3,pl.size());
                break;
            }
        }

        //create list of unsolved scrambles to show users (this can also be passed by MainMenu)
        List<String> unsolvedScrambles = new ArrayList<>();
        for (List<String> ws : scrambles){
            if(!solvedScrambles.contains(ws.get(0)) && !(ws.get(4).equals(currentPlayer))){
                String wsLabel = ws.get(0) + ": " + ws.get(2);
                unsolvedScrambles.add(wsLabel);
            }

        }

        //passes just the WordScramble ID to GameActivity when a wordScramble is selected (for now)
        ListView.OnItemClickListener scrambleClickedHandler = new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(view.getContext(), GameActivity.class);
                String ws = adapterView.getItemAtPosition(i).toString();
                myIntent.putExtra("uid",ws.substring(0,ws.indexOf(':')));
                startActivity(myIntent);
            }
        };

        //populate ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,unsolvedScrambles);
        ListView listView = findViewById(R.id.unsolvedWordScrambles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(scrambleClickedHandler);
    }


}


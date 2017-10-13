package edu.gatech.seclass.sdpscramble;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;


public class WordScrambleSelect extends ListActivity {

    //pull scramble List data from EWS and and send to the ListView using an ArrayAdapter


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_scramble_select);
        List<String> unsolvedScrambles = savedInstanceState.getStringArrayList("unsolvedScrambles");

        Button back = (Button) findViewById(R.id.backToMainMenu);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent backToMainMenu = new Intent(view.getContext(), MainMenuActivity.class);
                startActivity(backToMainMenu);
            }
        });

        //passes just the WordScramble ID to GameActivity when a wordScramble is selected (for now)
        ListView.OnItemClickListener scrambleClickedHandler = new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent gameIntent = new Intent(view.getContext(), GameActivity.class);
                String ws = adapterView.getItemAtPosition(i).toString();
                gameIntent.putExtra("uid",ws.substring(0,ws.indexOf(':')));
                startActivity(gameIntent);
            }
        };

        //populate ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,unsolvedScrambles);
        ListView listView = findViewById(R.id.unsolvedWordScrambles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(scrambleClickedHandler);
    }


}


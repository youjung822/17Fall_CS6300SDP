package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class UnsolvedScrambleSelectActivity extends AppCompatActivity {

    //pull scramble List data from EWS and and send to the ListView using an ArrayAdapter


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_scramble_select);
        Context context = this.getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences(getString(R.string.select_word_scramble), Context.MODE_PRIVATE);
        Set<String> set = settings.getStringSet(getString(R.string.select_word_scramble), null);
        List<String> unsolvedScrambles = new ArrayList<String>(set);

        Button back = (Button)findViewById(R.id.backToMainMenu);
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
    }


}


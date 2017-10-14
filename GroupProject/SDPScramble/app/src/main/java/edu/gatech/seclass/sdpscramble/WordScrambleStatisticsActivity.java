package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John Youngblood on 10/13/2017.
 */

public class WordScrambleStatisticsActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_scramble_statistics);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String username = settings.getString("user", "Unknown");

        MainMenuActivity main = new MainMenuActivity();
        List<List<String>> scrambles = main.ews.retrieveScrambleService();
        List<List<String>> players = main.ews.retrievePlayerListService();

        ArrayList<String> scrambleStats = new ArrayList<String>();

        for (List<String> scramble : scrambles) {
            String stat = "";
            ArrayList<String> solvers = new ArrayList<String>();
            String uid = scramble.get(0);
            String creator = scramble.get(4);
            stat = stat + uid;

            for (List<String> player : players) {
                if (player.subList(4, player.size()).contains(uid)) {
                    solvers.add(player.get(0));
                }
            }

            if (creator.equals(username)) {
                stat = stat + " | creator | ";
            } else if (solvers.contains(username)) {
                stat = stat + " | solver  | ";
            } else {
                stat = stat + " |         | ";
            }

            stat = stat + solvers.size();

            scrambleStats.add(stat);

        }

        ListView listView = (ListView) findViewById(R.id.wordScrambleStatisticsList);
        //populate ListView

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scrambleStats);
        listView.setAdapter(adapter);

        Button back = (Button) findViewById(R.id.exitWordScrambleStatistics);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent backToMainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(backToMainMenu);
            }
        });
    }
}
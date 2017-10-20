package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author John Youngblood
 */

public class WordScrambleStatisticsActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_scramble_statistics);

        Button back = (Button) findViewById(R.id.exitWordScrambleStatistics);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent backToMainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(backToMainMenu);
            }
        });

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String username = settings.getString("user", "Unknown");

        MainMenuActivity main = new MainMenuActivity();
        List<List<String>> scrambles = main.getAllScrambles();
        List<List<String>> players = main.getAllPlayers();

        ArrayList<WordScrambleTable> scrambleStats = new ArrayList<>();

        for (List<String> scramble : scrambles) {
            String uid = scramble.get(0);
            String creator = scramble.get(4);
            String phrase = scramble.get(1);
            String scrambledPhrase = scramble.get(2);
            String clue = scramble.get(3);
            List<String> solvers = getUsersWhoSolvedScramble(uid, players);

            WordScrambleTable wst = new WordScrambleTable(uid, phrase, clue, scrambledPhrase, creator, solvers.size());
            if (!(uid.isEmpty())) {
                scrambleStats.add(wst);
            }
        }


        ListView listView = (ListView) findViewById(R.id.wordScrambleStatisticsList);

        //populate ListView
        WordScrambleStatAdapter adapter = new WordScrambleStatAdapter(this, R.layout.word_scramble_statistics_adapter, orderDesc(scrambleStats), getScramblesSolvedByUser(username, players));
        listView.setAdapter(adapter);

    }

    private List<String> getUsersWhoSolvedScramble(String scrambleUID, List<List<String>> playerList) {

        List<String> usersWhoSolvedScramble = new ArrayList<String>();
        for (List<String> player : playerList) {
            if (player.size() > 4) {
                for (int i = 4; i < player.size(); i++) {
                    if (player.contains(scrambleUID)) usersWhoSolvedScramble.add(player.get(0));
                }
            }
        }


        return usersWhoSolvedScramble;
    }

    private List<String> getScramblesSolvedByUser(String playerUID, List<List<String>> playerList) {
        List<String> solvedScrambles = new ArrayList<String>(10);
        for (List<String> player : playerList) {
            if (player.get(0).equals(playerUID)) {
                if (player.size() > 4) {
                    solvedScrambles = player.subList(4, player.size());
                }
                break;
            }
        }
        return solvedScrambles;
    }

    private ArrayList<WordScrambleTable> orderDesc(ArrayList<WordScrambleTable> stats) {
        Collections.sort(stats, new Comparator<WordScrambleTable>() {
            public int compare(WordScrambleTable ws1, WordScrambleTable ws2) {
                if (ws1.numOfTimesSolved > ws2.numOfTimesSolved) return -1;
                else if (ws1.numOfTimesSolved < ws2.numOfTimesSolved) return 1;
                else return 0;
            }
        });

        ArrayList<WordScrambleTable> arrSortedWordScramble = new ArrayList<>();
        for (WordScrambleTable wst : stats) {
            arrSortedWordScramble.add(wst);
        }

        return arrSortedWordScramble;
    }
}
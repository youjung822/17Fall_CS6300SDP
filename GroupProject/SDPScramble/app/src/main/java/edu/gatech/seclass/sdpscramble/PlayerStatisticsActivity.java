package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author youjungkim
 */

public class PlayerStatisticsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_statistics);
        Button back = (Button) findViewById(R.id.exitPlayerStatistics);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent backToMainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(backToMainMenu);
            }
        });

        ListView lvPlayerStatisticsList = (ListView)findViewById(R.id.playerStatisticsList);
        MainMenuActivity mMA = new MainMenuActivity();
        //ArrayList<PlayerTable> arrPlayerTable = mMA.retrievePlayerStatistic();
        List<List<String>> scrambleList = mMA.getAllScrambles();
        List<List<String>> playerList = mMA.getAllPlayers();

        ArrayList<PlayerTable> arrPlayer = new ArrayList<>();

        Iterator<List<String>> iter = playerList.iterator();

        while (iter.hasNext()) {
            List<String> curIter = iter.next();
            List<String> scramblesCreatedByUser = retrieveScramblesCreatedByUser(scrambleList,curIter.get(0));
            Double avgSolve=0.0;

            if(scramblesCreatedByUser.size() > 0){
                int countPlayer = countNumberOfPlayerSolved(scramblesCreatedByUser,playerList);
                avgSolve = Math.round((double)countPlayer/scramblesCreatedByUser.size()*100d)/100d;

            }

            PlayerTable pt = new PlayerTable(curIter.get(0), curIter.get(1), curIter.get(2), curIter.get(3)
                    , curIter.size()-4 ,scramblesCreatedByUser.size(), avgSolve);

            arrPlayer.add(pt);
        }


        PlayerStatAdapter adapter = new PlayerStatAdapter(this,R.layout.player_statistics_adapter, orderDesc(arrPlayer));
        lvPlayerStatisticsList.setAdapter(adapter);

    }


    private List<String> retrieveScramblesCreatedByUser(List<List<String>> scrambleList,String userName){

        /**
         * may need to be moved to PlayerStatisticsActivity
         */
        Iterator<List<String>> iter = scrambleList.iterator();
        int i = 0;
        List<String> listScramble = new ArrayList<String>();
        while (iter.hasNext()) {
            List<String> curIter = iter.next();
            if(curIter.get(4)== userName){
                listScramble.add(curIter.get(0));
            }
        }
        return listScramble;

    }

    private int countNumberOfPlayerSolved(List<String> scrambleList, List<List<String>> playerList){

        /**
         * may need to be moved to PlayerStatisticsActivity
         */

        Iterator<String> iterScramble = scrambleList.iterator();
        int count = 0;
        while(iterScramble.hasNext()){
            String curScramble = iterScramble.next();
            Iterator<List<String>> iterPlayer = playerList.iterator();
            while(iterPlayer.hasNext()){
                List<String> curIndi = iterPlayer.next();
                if(curIndi.size() > 4) {
                    for(int i =4; i<curIndi.size(); i++){
                        if(curIndi.get(i) == curScramble) count ++;
                    }
                }
            }
        }

        return count;
    }

    private  ArrayList<PlayerTable> orderDesc(ArrayList<PlayerTable> statics)
    {
        Collections.sort(statics, new Comparator<PlayerTable>(){
            public int compare(PlayerTable p1, PlayerTable p2){
                if(p1.numOfScramblesSolved > p2.numOfScramblesSolved) return -1;
                else if(p1.numOfScramblesSolved < p2.numOfScramblesSolved) return 1;
                else return 0;
            }
        });

        ArrayList<PlayerTable> arrSortedPlayer = new ArrayList<>();
        for(PlayerTable pt : statics){
            arrSortedPlayer.add(pt);
        }

        return arrSortedPlayer;
    }


}

package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John Youngblood on 10/10/17.
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
        ArrayList<PlayerTable> arrPlayerTable = mMA.retrievePlayerStatistic();

        PlayerStatAdapter adapter = new PlayerStatAdapter(this,R.layout.player_statistics_adapter, arrPlayerTable);
        lvPlayerStatisticsList.setAdapter(adapter);

    }

}

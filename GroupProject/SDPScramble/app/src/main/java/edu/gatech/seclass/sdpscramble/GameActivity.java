package edu.gatech.seclass.sdpscramble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * @author John Youngblood
 */

public class GameActivity extends AppCompatActivity {

    private EditText guess;
    private TextView uidView;
    TextView phraseView;
    TextView clueView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        String id = savedInstanceState.getString("uid");
        ExternalWebService ews = ExternalWebService.getInstance();
        List<List<String>> scrambles = ews.retrieveScrambleService();
        String currentPlayer = "Active User";
        List<String> currentScramble = new ArrayList<>(5);

        //pull scramble info from ews
        for(List<String> ws: scrambles){
            if(ws.get(0).equals(id)){
                currentScramble=ws;
                break;
            }
        }

        uidView = (TextView) findViewById(R.id.wordScrambleUID);
        uidView.setText(currentScramble.get(0));

        phraseView = (TextView) findViewById(R.id.phrase);
        phraseView.setText(currentScramble.get(2));

        clueView = (TextView) findViewById(R.id.clue);
        clueView.setText(currentScramble.get(3));

        guess = (EditText)findViewById(R.id.scrambleGuess);
    }

    //used by submit button
    public boolean checkSolution(View view){
        return true;
    }

    //used by cancel button
    public void exit(View view){
        //pass the string id and current guess to progressTracker\
        //no need to pass username since progressTracker is wiped out after logging out/login
    }

}

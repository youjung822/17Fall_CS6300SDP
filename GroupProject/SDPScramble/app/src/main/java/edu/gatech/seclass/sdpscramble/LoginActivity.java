package edu.gatech.seclass.sdpscramble;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.net.SocketTimeoutException;

import edu.gatech.seclass.utilities.ExternalWebService;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }



    /**
     * createPlayer()
     * calls EWS and creates a new player
     * @return unique username
     */

    private static String createPlayer(String username, String firstname, String lastname, String email) {
        ExternalWebService ews = ExternalWebService.getInstance();
        String strPlayerNewID = "";

        try {
            strPlayerNewID = ews.newPlayerService(username,firstname,lastname,email);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }

        return strPlayerNewID;
    }




}

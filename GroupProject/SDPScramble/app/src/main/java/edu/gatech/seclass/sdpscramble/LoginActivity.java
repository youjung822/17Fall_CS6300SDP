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

        ExternalWebService ews = null;
        ews.getInstance();

        //Testing
        try {
            String  strPlayerNewID = ews.newPlayerService("ykim691","Youjung","Kim","yjkim691@gatech.edu");
            System.out.println(strPlayerNewID);

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        //tamur's git config test

    }


}

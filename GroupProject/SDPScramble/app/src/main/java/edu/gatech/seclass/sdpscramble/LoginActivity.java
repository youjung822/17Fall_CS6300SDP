package edu.gatech.seclass.sdpscramble;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Iterator;

import edu.gatech.seclass.utilities.ExternalWebService;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //need to create instance onCreate and pass same instance whenever EWS is called
        ExternalWebService ews = ExternalWebService.getInstance();

        String returnedUsername = createPlayer(ews, "testUsername", "Test", "User", "test@abc.com");
        System.out.println(login(ews, returnedUsername));
    }



    /**
     * createPlayer()
     * calls EWS and creates a new player
     * @return unique username
     */

    private static String createPlayer(ExternalWebService ews, String username, String firstname, String lastname, String email) {

        String strPlayerNewID = "";

        try {
            strPlayerNewID = ews.newPlayerService(username,firstname,lastname,email);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }

        return strPlayerNewID;
    }

    /**
     * login()
     * calls EWS and creates a new player
     * @return unique username
     */

    private static boolean login(ExternalWebService ews, String username){
        boolean validUsername = false;
        if(username != null && !username.isEmpty()){
            List<List<String>> playerList = ews.retrievePlayerListService();

            Iterator<List<String>> iter = playerList.iterator();
            while(iter.hasNext()){
                String usr = iter.next().get(0);
                if(usr.equals(username)){
                    validUsername = true;
                }
            }

        }

        return validUsername;
    }



}

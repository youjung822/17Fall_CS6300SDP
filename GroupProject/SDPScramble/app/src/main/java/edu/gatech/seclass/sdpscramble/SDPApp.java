package edu.gatech.seclass.sdpscramble;

import android.app.Application;

/**
 * Created by tamur on 10/12/17.
 */

public class SDPApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //when app starts log the user out since EWS mock doesn't persist data
        MainMenuActivity menu = new MainMenuActivity();
        menu.logout();

    }
}

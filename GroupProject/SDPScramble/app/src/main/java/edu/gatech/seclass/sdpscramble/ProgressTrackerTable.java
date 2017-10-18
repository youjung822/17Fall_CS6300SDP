package edu.gatech.seclass.sdpscramble;

/**
 * Created by tamur on 10/12/17.
 */

public class ProgressTrackerTable {
    public Long _id;
    public String player;
    public String wordScrambleUID;
    public String wordState;
    public String inProgressPhrase;

    public ProgressTrackerTable (String plyr, String wrd, String wrdState, String prgsPhrs) {
        this.player = plyr;
        this.wordScrambleUID = wrd;
        this.wordState = wrdState;
        this.inProgressPhrase = prgsPhrs;
    }
}

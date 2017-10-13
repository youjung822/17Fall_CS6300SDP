package edu.gatech.seclass.sdpscramble;

/**
 * Created by tamur on 10/12/17.
 */

public class ProgressTrackerTable {
    public Long _id;
    public PlayerTable player;
    public WordScrambleTable word;
    public String wordState;
    public String inProgressPhrase;

    public ProgressTrackerTable (PlayerTable plyr, WordScrambleTable wrd, String wrdState, String prgsPhrs) {
        this.player = plyr;
        this.word = wrd;
        this.wordState = wrdState;
        this.inProgressPhrase = prgsPhrs;
    }
}

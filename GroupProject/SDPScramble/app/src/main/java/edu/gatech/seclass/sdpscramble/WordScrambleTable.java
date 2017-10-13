package edu.gatech.seclass.sdpscramble;

/**
 * Created by tamur on 10/12/17.
 */

public class WordScrambleTable {

    public Long _id;
    public String uniqueIdentifier;
    public String phrase;
    public String clue;
    public String scrambledPhrase;
    public String createdBy;
    public Integer numOfTimesSolved;

    public WordScrambleTable(String uid, String phrs, String clu, String scrmbldPhrs, String crtdBy, Integer tmsSvd){
        this.uniqueIdentifier = uid;
        this.phrase = phrs;
        this.clue = clu;
        this.scrambledPhrase = scrmbldPhrs;
        this.createdBy = crtdBy;
        this.numOfTimesSolved = tmsSvd;
    }

}

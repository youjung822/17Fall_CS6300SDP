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

    public WordScrambleTable(String uniqueIdentifier, String phrase, String clue, String scrambledPhrase, String createdBy, int numOfTimesSolved) {
        this.uniqueIdentifier = uniqueIdentifier;
        this.phrase = phrase;
        this.clue = clue;
        this.scrambledPhrase = scrambledPhrase;
        this.createdBy = createdBy;
        this.numOfTimesSolved = numOfTimesSolved;
    }
}

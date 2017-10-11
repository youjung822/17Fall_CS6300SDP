package edu.gatech.seclass.sdpscramble;

/**
 *  @author Brian Greenwald
 */
public class SavedWordScramble extends WordScramble {

    public SavedWordScramble(
        String wordScrambleUid, String phrase, String scrambledPhrase, String clue) {

        super(phrase, clue);

        this.scrambledPhrase = scrambledPhrase;
        this.wordScrambleUid = wordScrambleUid;
    }

    public String getScrambledPhrase() {
        return scrambledPhrase;
    }

    public String getWordScrambleUid() {
        return wordScrambleUid;
    }

    private String scrambledPhrase;
    private String wordScrambleUid;
}

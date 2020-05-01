package edu.gatech.seclass.sdpscramble;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Brian Greenwald
 */
public class WordScrambleTest {

    @Test
    public void scramble_phraseWithSingleWord_phraseScrambledCorrectly() {
        String phrase = "test";
        String scrambledPhrase = createAndScramble(phrase);

        Assert.assertTrue(containsSameChars(phrase, scrambledPhrase));
    }

    @Test(expected = IllegalArgumentException.class)
    public void scramble_phraseWithNoWords_IllegalArgumentExceptionThrown() {
        String phrase = "1234 !!$ %%";
        createAndScramble(phrase);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scramble_phraseWithWordsThatCannotBeScrambled_IllegalArgumentExceptionThrown() {
        String phrase = "aaaa2bb ccccc.";
        createAndScramble(phrase);
    }

    @Test
    public void scramble_phraseWithMultipleWords_wordsScrambledInIsolation() {
        String phrase = "The cat is loud :-).";
        String scrambledPhrase = createAndScramble(phrase);

        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 0, 3);   // The
        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 4, 7);   // cat
        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 8, 10);  // is
        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 11, 15); // loud
    }

    @Test
    public void scramble_phraseWithSomeLettersUppercase_lettersAtSameIndicesAreUppercaseInScramble() {
        String phrase = "The cAt is louD :-).";
        String scrambledPhrase = createAndScramble(phrase);

        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 0, 3);   // The
        Assert.assertTrue(Character.isUpperCase(phrase.charAt(0)));     // T

        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 4, 7);   // cAt
        Assert.assertTrue(Character.isUpperCase(phrase.charAt(5)));     //  A

        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 8, 10);  // is

        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 11, 15); // louD
        Assert.assertTrue(Character.isUpperCase(phrase.charAt(14)));    //    D
    }

    @Test(expected = IllegalArgumentException.class)
    public void scramble_emptyPhraseString_IllegalArgumentExceptionThrown() {
        String phrase = "";
        String scrambledPhrase = createAndScramble(phrase);

        Assert.assertEquals(scrambledPhrase, phrase);
    }

    @Test
    public void scramble_wordsSeparatedByNonLetterCharacters_wordsParsedAndScrambledCorrectly() {
        String phrase = "The123cat**is@2loud/:-).";
        String scrambledPhrase = createAndScramble(phrase);

        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 0, 3);   // The
        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 6, 9);   // cat
        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 11, 13);  // is
        assertSuccessfulWordScrambled(phrase, scrambledPhrase, 15, 19); // loud
    }

    private void assertSuccessfulWordScrambled(
        String phrase, String scrambledPhrase, int wordStartIndex, int wordEndIndex) {

        String phraseWords = phrase.substring(wordStartIndex, wordEndIndex);
        String scrambledPhraseWord = scrambledPhrase.substring(wordStartIndex, wordEndIndex);

        Assert.assertNotEquals(phraseWords, scrambledPhraseWord);
        Assert.assertTrue(containsSameChars(phrase, scrambledPhrase));
    }

    private String createAndScramble(String phrase) {
        WordScramble wordScramble = new WordScramble(phrase, null);
        return wordScramble.scramble();
    }

    private boolean containsSameChars(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        char[] s1chars = s1.toLowerCase().toCharArray();
        char[] s2chars = s2.toLowerCase().toCharArray();

        Arrays.sort(s1chars);
        Arrays.sort(s2chars);

        return Arrays.equals(s1chars, s2chars);
    }

}

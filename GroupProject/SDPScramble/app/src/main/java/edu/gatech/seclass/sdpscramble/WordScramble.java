package edu.gatech.seclass.sdpscramble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Brian Greenwald
 */

public class WordScramble implements Scramble {

    public static final int MAX_PHRASE_LENGTH = 127;
    public static final int MIN_PHRASE_LENGTH = 2;
    private String clue;
    private String phrase;

    public WordScramble(String phrase, String clue) {
        if (phrase == null || phrase.isEmpty() || phrase.length() > MAX_PHRASE_LENGTH) {
            throw new IllegalArgumentException(
                "Phrases must between " + MIN_PHRASE_LENGTH + " and " + MAX_PHRASE_LENGTH +
                    " characters.");
        }
        this.clue = clue;
        this.phrase = phrase;
    }

    public String getClue() {
        return clue;
    }

    public String getPhrase() {
        return phrase;
    }

    public String scramble() {
        StringBuilder scrambledPhraseSB = new StringBuilder();

        List<Integer> uppercaseIndices = new ArrayList<>();

        StringBuilder currentWordSb = new StringBuilder();

        int currentWordStartIndex = -1;
        int currentWordEndIndex = -1;

        int scrambledWordCount = 0;

        for (int i = 0; i < phrase.length(); i++) {
            char currentChar = phrase.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                uppercaseIndices.add(i);
            }

            currentChar = Character.toLowerCase(currentChar);

            if (Character.isAlphabetic(currentChar)) {
                if (currentWordEndIndex == -1) {
                    currentWordStartIndex = i;
                }

                currentWordSb.append(currentChar);
                currentWordEndIndex = i;
            } else {
                if (currentWordSb.length() > 0) {
                    String currentWord = currentWordSb.toString();

                    String scrambledWord = currentWord;
                    if (canBeScrambled(currentWord)) {
                        while (scrambledWord.equals(currentWord)) {
                            scrambledWord = scrambleWord(currentWord);
                        }
                        scrambledWordCount ++;
                    }

                    scrambledPhraseSB.replace(
                        currentWordStartIndex, currentWordEndIndex, scrambledWord);
                    currentWordSb = new StringBuilder();

                    currentWordStartIndex = -1;
                    currentWordEndIndex = -1;
                }

                scrambledPhraseSB.append(currentChar);
            }
        }
        if (currentWordSb.length() > 0) {
            String currentWord = currentWordSb.toString();

            String scrambledWord = currentWord;
            if (canBeScrambled(currentWord)) {
                while (scrambledWord.equals(currentWord)) {
                    scrambledWord = scrambleWord(currentWord);
                }
                scrambledWordCount ++;
            }

            scrambledPhraseSB.replace(currentWordStartIndex, currentWordEndIndex, scrambledWord);
        }

        for (Integer index : uppercaseIndices) {
            char c = Character.toUpperCase(scrambledPhraseSB.charAt(index));

            scrambledPhraseSB.replace(index, index + 1, String.valueOf(c));
        }

        if (scrambledWordCount == 0) {
            throw new IllegalArgumentException(
                "Phrase did not contain any words that can be scrambled");
        }

        return scrambledPhraseSB.toString();
    }

    private String scrambleWord(String word) {
        int length = word.length();

        List<Character> chars = new ArrayList<>();
        for (char c : word.toCharArray()) {
            chars.add(c);
        }

        StringBuilder scrambledWordSB = new StringBuilder();

        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = Math.abs(rand.nextInt() % chars.size());
            char selectedChar = chars.remove(randomIndex);
            scrambledWordSB.append(selectedChar);
        }
        return scrambledWordSB.toString();
    }

    private boolean canBeScrambled(String word) {
        int length = word.length();

        if (length < 2) {
            return false;
        }

        char[] wordChars = word.toCharArray();
        char firstChar = wordChars[0];

        for (char c : wordChars) {
            if (firstChar != c) {
                return true;
            }
        }
        return false;
    }

}

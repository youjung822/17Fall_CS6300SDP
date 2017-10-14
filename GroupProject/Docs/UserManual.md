
# User Manual - Team 44

## Overview

In this game, Users can create, share and solve Word Scrambles. They can save their progress and continue at a later time. They can also see statistics of Word Scrambles and Players and compare progress.

## Instructions

Users must have an existing username to log into the system. Upon starting the application, Users are prompted to either log in or sign up for an account.

  * __Login__ - Logging in only requires that a User enters a valid username that was previously registered with the application.
  * __Sign Up__ - Choosing to sign up from the login screen prompts the user with a form in which they are to enter their First and Last names along with their email address and the username they wish to use.
     * To ensure the entered username is unique, the user will be presented with their desired username appended with a number upon submitting the form.
     * Clicking OK will bring the User back to the login screen where they may then login with the username that was displayed to them.

The central "hub" of the application lies in the Main Menu. From here, Users are presented with options to __Solve a Word Scramble__, __Create a Word Scramble__, __View Player Statistics__, or __View Scramble Statistics__. Users are also able to log out of the application from this menu.

  * __Solve a Word Scramble__ - Clicking this option will present the user with a screen where they may select a Word Scramble from a list of those created by themselves and other users. Any scrambles the User may have progress saved will be displayed at the top of the list. Selecting a scramble will start a new game.
     * To solve a scramble, Users are prompted to enter their guess of what the unscrambled phrase might be. A successful guess will inform the User and return them to the list of scrambles. If at any time the user wishes to exit a game, the may simple click the option to return to the main menu. The last guess they made will be saved for when they return to solve the scramble.
  * __Create a Word Scramble__ - To create a new Word Scramble, Users are prompted to enter a phrase that they wish to be scrambled along with an optional clue to help those trying to unscramble the phrase. After entering a phrase, the User must hit 'Scramble' to scramble the phrase. Users may elect to scramble the phrase as many times as they wish until they are satisfied by the resulting scrambled phrase. Users may then Phrases are scrambled according to the following rules:
     * Phrases must contain at least 2 characters and no more than 127 characters.
     * Phrases are scrambled by the 'words' they contain. Each word is scrambled individually in the resulting scrambled phrase.
     * A word is any continuous sequence of 2 or more letters. Words are separated by any non-letter character, such as symbols, spaces, punctuation, numbers, etc.
     * The letters in each word are randomly mixed up such that the resulting scrambled word contains all of the same letters in the original word in a different sequence.
     * If a letter appears as uppercase in the original phrase, whichever letter ends up in that position in the scrambled phrase will also appear as uppercase.
     * Non-letter characters will remain in the same position as they appear in the original phrase.
     * A phrase is deemed 'unscramble-able' if it does not contain any valid words that can be scrambled.  
         __EX: The dog's name is Finn! -> Eht ogd's mean si Infn!__  

      Once satisfied with their scramble, Users may then submit their scramble. Assuming the phrase they entered was valid and subsequently scrambled at least once, the UID of the scramble they created will be presented to the User. Clicking OK will return the User to the Main Menu.
  

  * __View Player Statistics__ - Users can check various statistics concerning themselves and other Players here. Each Player is listed with their First and Last names along with the total number of Word Scrambles they have solved, the total number of Word Scrambles they've created, and the average number of times that the Word Scrambled they've created have been solved by other players. Each Player is listed in decreasing order by the number of Word Scrambled they have solved.
  * __View Scramble Statistics__ - Users may also check the statistics concerning the Word Scrambled themselves. Here, all Word Scrambles are listed with their UID, an indication of whether they were solved or created by the logged-in User, and the number of times any player has solved them. They are listed in decreasing order by the total number of times they have been solved.

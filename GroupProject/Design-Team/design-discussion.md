Individual Designs
==================

Design 1
--------
![bgreenwald3](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Design-Team/images/bgreenwald3.png?raw=true "Brian Greenwald's design")
* Pros:
  * Having a separate class for the creation of a scramble is great since the creation process outlined in requirement 5 is not  straightforward like most class constructors.
  * EWS is represented well by having 5 concise operations for its 5 distinct uses listed within requirement 4.
  * Attribute and Operation scope are clearly and accurately marked in the class diagram.

* Cons:
  * Some of the Manager classes represent distinct operations that can be rolled into other classes for simplicity.
  * The multiple Manager classes cause somewhat of an oversaturation of associations within the class diagram.
  * Some associations could be better defined with use of notation in the class diagram to represent ownership, navigability, and arity.

  
Design 2
--------
![jyoungblood8](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Design-Team/images/jyoungblood8.png?raw=true "John Youngblood's design")
* Pros:
  * Good use of encapsulation, especially with classes representing each statistic.
  * Associations are clearly marked within the class diagram which helps understanding how classes interact with each other without reading the design-information doc.
  * Having the scramble() operation within its own utility class helps compartmentalize its complexity outlined in requirements 7 and 8.

* Cons:
  * Having a separate instance for each PlayerStatistic and WordScrambleStatistic might be unnecessary since EWS always pulls the statistics for all Players and WordScrambles with its getWordScramble() and getPlayerStatistics() operations, not individually.
  * Lack of defining how players view and select word scrambles to solve in the class diagram.
  * The Solves association class is a little ambiguous on how it saves the progress of a Player when they stop working on a WordScramble before they solve it.


Design 3
--------
![tkhan30](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Design-Team/images/tkhan30.png?raw=true "Tamur Khan's design")
* Pros:
  * The use of a separate class that is composed of Players and WordScramples to keep track of individual user's progress with each WordScramble is a great design choice.
  * Well defined attribute and operation names leaves little room for ambiguity.
  * The ScrambleState enumeration within the PlayerWordM2M class is a great way to represent the 3 distinct reasons a Player and WordScramble would be associated with each other: Player has solved the WordScramble, Player has progress with the WordScramble, and a Player is the creator of the WordScramble.

* Cons:
  * Mutator methods are more suited for implementation diagrams.
  * The Player and WordScramble classes take on too much responsibilities and lead to a more monolithic design instead of a modular design that is easier to implement and maintain.
  * The addUniquePlayer() and verifyUsername() operations in EWS can be combined into one operation for this diagram since neither would be done without the other.


Design 4
--------
![ykim691](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Design-Team/images/ykim691.png?raw=true "Youjung Kim's design")
* Pros:
  * Having a separate class for the creation of a scramble is great since the creation process outlined in requirement 5 is not straightforward like most class constructors.
  * The use of the Game class as a way to represent how a Player interacts with a WordScramble when attempting to solve it is a nice design choice for fulfilling requirement 9.
  * Offloading the statistics of Players and WordScrambles to separate classes helps modularize the design of the application and would increase the responsiveness of the application when pulling statistics as opposed to having the statistics wrapped in each individual Player and WordScramble instance.

* Cons:
  * The class diagram marks all attributes as public, which is not necessary and may allow players to access the phrases of WordScrambles without first solving them.
  * No need for the alterUserName() operation within the Player class since EWS automatically alters the user's requested username to make it unique.
  * The association between WordScramble and Game in the class diagram might need to be reversed to better reflect was it outlined in the design-information doc.

Team Design
===========
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Design-Team/images/team44Design.png?raw=true "Team design")

### Commonalities and Differences with individuals
| Member | Commonality | differences |
| ------ | ------ | ------ |
| Brian Greenwald |  |  |
| John Youngblood |  |  |
| Tamur Khan |  |  |
| Youjung Kim |  |  |


### Justification of the Fianl Design
1. When starting the application, a user may choose to either create a new player or log in.  For simplicity, authentication is optional.  A (unique) username will be sufficient for logging in.
    </br><b>To realize this requirement, the Main Menu class offers both login and createPlayer methods.</b>

2. After logging in, the application shall allow players to  (1) create a word scramble, (2) choose and solve word scrambles, (3) see statistics on their created and solved word scrambles, and (4) view the player statistics. 
    </br><b>The MainMenu class also offers the createWordScramble and viewScrambles to satisfy requirements (1) and (2), respectively. The main menu also implements a statistic interface (described further in requirements 11 and 12), that offers methods to calculate the statistics to be displayed to the user. </b>


3. The application shall maintain an underlying database to save persistent information across runs (e.g., word scrambles, player information, statistics). 
    </br><b>In our design, whenever a call to the EWS is made, a local database update is made with the information fetched from it. An update to the local database is also made when a new ProgressTracker is created (explained further in requirement 10).</b>


4. Word scrambles and statistics will be shared with other instances of the application.  An external web service utility will be used by the application to communicate with a central server to:
```sh
Add a player and ensure that their username is unique.
Send new word scrambles and receive a unique identifier for them.
Retrieve the list of scrambles, together with information on which player created each of them. 
Report a solved scramble.
Retrieve the list of players and the scrambles each have solved.
```
    You should represent this utility as a utility class that (1) is called "ExternalWebService", (2) is connected to the classes in the system that use it, and (3) explicitly list relevant methods used by those classes.  This class is provided by the system, so it should only contain what is specified here. You do not need to include any aspect of the server in your design besides this utility class.
    </br><b>To realize this, our design includes the ExternalWebService with a method for each required functionality lited above.</b>
    
5. When creating a new player, a user will:
```sh
Enter the player’s first name.
Enter the player’s last name.
Enter the player’s desired username.
Enter the player’s email.
Save the information.
Receive the returned username, with possibly a number appended to it to ensure that it is unique.
```
</br><b>The Player class realizes this requirement. It has fields for each of these pieces of information. The uniqueness requirement is satisfied in that a call to createPlayer() in MainMenu will issue a call to the EWS's addPlayer method, which returns either the same username entered or one with a number appended to ensure uniqueness.</b>


6. To add a word scramble, the player will:
```sh
  * Enter a phrase (not scrambled).
  * Enter a clue. 
  * View the phrase scrambled by the system. If the player does not like the result, they may choose for the system to re-scramble it until they are satisfied.
  * Accept the results or return to previous steps.
  * View the returned unique identifier for the word scramble. The scramble may not be further edited after this point.
```  
    </br><b>Scrambles are created through the createWordScramble method of MainMenu, mentioned earlier. First, a WordScramble class with fields for the entered phrase and clue is created. This class implements the Scramble interface, whose singular method allows for the entered phrase to be scrambled and saved in the scrambledPhrase field. The scramble operation can be executed multiple times until the user is satisfied. At this time, the MainMenu can then call the EWS method addScramble to receive the scramble's UID and save the scramble as a SavedWordScramble.</b>


7. A scramble shall only mix up alphabetic characters, keeping each word together. Words are contiguous sequences of alphabetic characters separated by one or more non-alphabetic characters.
    </br><b>Business logic. Not represented in our design.</b>


8. All other characters and spacing will remain as they originally are.

    </br><b>Business logic. Not represented in our design.</b>


9. When solving word scrambles, a player will:
  * View the list of unsolved word scrambles, by identifier, with any in progress scrambles marked and shown first.
  * Choose one word scramble to work on.
  * View the scramble.
  * Enter the letters in a different order to try to solve the scramble.
  * Submit a solution.
  * View whether it was correct.
  * Return either to the puzzle, if wrong, or to the list, if correct.
  
    </br><b>The viewScrambles method of MainMenu satisfies the requirement to list the word scrambles. When the user selects a WordScramble from this list, it initiates a new Game instance. This allows users to enter their guesses and submit them. The checkSolution method returns whether or not their guess was correct. If incorrect, they simply stay within the Game and can attempt more guesses. Otherwise, they are returned back to the list in the main menu and a call to the EWS reportSolvedScramble can be made.</b>


10. A player may exit any scramble in progress at any time and return to it later.  The last state of the puzzle will be preserved.
</br><b>When a Player chooses to stop solving an unsolved scramble, the exitGame method is called from the Game class. Since the scramble was not solved, a ProgressTracker instance is created, which maintains information regarding the user, the scramble they were on, and their last guess. Note that this data is local and will not be passed along to the EWS. If the same Player were to load up the game on a different machine, they would not have access to their saved progress.</b>


11. The scramble statistics shall list all scrambles with (1) their unique identifier, (2) information on whether they were solved or created by the player, and (3) the number of times any player has solved them. This list shall be sorted by decreasing number of solutions.
</br><b>Our Statistics interface has a getScrambleStatistics method that handles this by making calls to the EWS methods getPlayersAndSolvedScrambles and getScramblesAndCreators and processing them accordingly.</b>


12. The player statistics will list players’ first names and last names, with (1) the number of scrambles that the player has solved, (2) the number of new scrambles created, and (3) the average number of times that the scrambles they created have been solved by other players.  It will be sorted by decreasing number of scrambles that the player has solved.
</br><b>Similar to above, our Statistics interface has a getScrambleStatistics method that handles this by making calls to the EWS methods getPlayersAndSolvedScrambles and getScramblesAndCreators and processing them accordingly.</b>


13. The User Interface (UI) shall be intuitive and responsive.
</br><b>GUI requirement. Not represented in our design.</b>


Summary
=======
summarizes the lessons learnt in the process of discussing the designs, in terms of design, team work, and any other aspect that the team members consider relevant


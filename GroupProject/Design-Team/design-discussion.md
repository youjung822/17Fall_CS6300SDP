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
| Member | Commonalities | Differences |
| ------ | ------ | ------ |
| Brian Greenwald | The EWS we used was based off the one used in my individual design. The Manager classes I used to represent game functionality was essentially realized in our design as the MainMenu - a simpler central point of application control. | Whereas all of my Manager classes would make calls to the EWS, only the MainMenu class in our design does. My individual design had the Player class respnsible for keeping track of in-progress scrambles, but we felt it was cleaner to use the ProgressTracker class take care of that. |
| John Youngblood |  |  |
| Tamur Khan |The PlayerM2M class (renamed to ProgressTracker) is used to keep track of all scrambles that are in progress. The EWS utility is mostly the same and the Player and WordScramble classes are retained.  | A dedicated MainMenu class is now central to the application and exclusively handles all communication with the EWS. A Game class was created to handle everythign related to actual GamePlay. WordScramble and Player classes are now more dedicated objects that don't handle other functionality.  |
| Youjung Kim | The design of the Game and ProgressTracker classes and their relationship in the final design were similarly represented in the design of SolveGame and Game classes in the individual diagram. |  Separate operation classes in the individual design were explicitly expressed as a relationship in the final design. The final design has been simplified and the statistics class changed into a interface class. Other various types of classes (e.g.interface, abstract, and utility classes) and relationships are present. |


### Justifications of Main Design Decisions
>**1)**	When starting the application, a user may choose to either create a new player or log in.  For simplicity, authentication is optional.  A (unique) username will be sufficient for logging in.

A MainMenu class was created to handle creation and login of players by interacting directly with the EWS. 

>**2)** After logging in, the application shall allow players to  (1) create a word scramble, (2) choose and solve word scrambles, (3) see word scramble statistics on their created and solved word scrambles, and (4) view the player statistics.


The MainMenu class also offers the createWordScramble() and viewScrambles() methods to satisfy requirements (1) and (2), respectively. The main menu also implements a statistic interface (described further in requirements 11 and 12) that offers methods to calculate all player and word scramble statistics. 

These will be detailed in their individual requirements below.


>**3)** The application shall maintain an underlying database to save persistent information across runs (e.g., word scrambles, player information, statistics).


Whenever MainMenu calls EWS, it also updates the local database update with all new information retrieved. An update to the local database is also made when a new ProgressTracker is created (explained further in requirement 10).


>**4)**	Word scrambles and statistics will be shared with other instances of the application.  An external web service utility will be used by the application to communicate with a central server to:
>
>1.	Add a player and ensure that their username is unique.
>2.	Send new word scrambles and receive a unique identifier for them.
>3.	Retrieve the list of scrambles, together with information on which player created each of them. 
>4.	Report a solved scramble.
>5.	Retrieve the list of players and the scrambles each have solved.

> You should represent this utility as a utility class that (1) is called "ExternalWebService", (2) is connected to the classes in the system that use it, and (3) explicitly list relevant methods used by those classes.  This class is provided by the system, so it should only contain what is specified here. You do not need to include any aspect of the server in your design besides this utility class. 

AN ExternalWebService utility class was created with a method for each required functionality listed above. The MainMenu class was architected so that it exclusively handles all interactions with EWS.
    

> **5)** When creating a new player, a user will:
	
> 1.	Enter the player’s first name.
> 2.	Enter the player’s last name.
> 3.	Enter the player’s desired username.
> 4.	Enter the player’s email.  
> 5.	Save the information.
> 6.	Receive the returned username, with possibly a number appended to it to ensure that it is unique.

A Player class was created to realize this requirement. It has attributes for each of the pieces listed above. The requirement for the username to be unique is satisfied when the MainMenu.callPlayer() method calls EWS.addPlayer(), which returns either the same username or one with a unique number appended to it.


>**6)**	To add a word scramble, the player will:

> 1. Enter a phrase (not scrambled).
> 2.	Enter a clue. 
> 3.	View the phrase scrambled by the system. If the player does not like the result, they may choose for the system to re-scramble it until they are satisfied.
> 4.	Accept the results or return to previous steps.
> 5.	View the returned unique identifier for the word scramble. The scramble may not be further edited after this point. 

Scrambles are created by the MainMenu.createWordScramble() method. First, an object of the WordScramble class with the appropriate attributes is created. WordScramble then implements the Scramble interface, whose singular method allows for the entered phrase to be scrambled and saved in the scrambledPhrase field. The scramble() operation can be executed multiple times until the user is satisfied. Once the user is satisfied, MainMenu calls EWS.addScramble() to add a scramble in the central server and stores the returned scramble unique identifier in the local database.

>**7)** A scramble shall only mix up alphabetic characters, keeping each word together. Words are contiguous sequences of alphabetic characters separated by one or more non-alphabetic characters.

This will be handled by the Scramble interface mentioned above. 

>**8)**	All other characters and spacing will remain as they originally are.

	Example:  
	The cat is loud :-).  ->  Het atc si ulod :-)

This will be handled by the Scramble interface mentioned above. Details regarding the business logic are out of scope of a class diagram.


>**9)** When solving word scrambles, a player will:
	
>1.	View the list of unsolved word scrambles, by identifier, with any in progress scrambles marked and shown first.
>2.	Choose one word scramble to work on.
>3.	View the scramble.
>4.	Enter the letters in a different order to try to solve the scramble.
>5.	Submit a solution.
>6.	View whether it was correct.
>7.	Return either to the puzzle, if wrong, or to the list, if correct.
  
MainMenu.viewScrambles() satisfies the requirement to list word scrambles. When the user selects a WordScramble from this list, it initiates a new Game instance. This allows users to enter their guesses and submit them. The checkSolution method returns whether or not their guess was correct. If incorrect, they simply stay within the Game and can attempt more guesses. When a scramble is solved, the Player is returned to the list and MainMenu calls EWS.reportSolvedScramble() to report a solve to the central server.


>**10)** A player may exit any scramble in progress at any time and return to it later.  The last state of the puzzle will be preserved.

When a Player exits the game before solving a scramble, the Game.exitGame() method is called. Since the scramble was not solved, a ProgressTracker instance is created which maintains information regarding the user, the scramble's UID and their scramble progress. 

When the player chooses an in progress scramble from a list, the scramble progress will be retrieved from ProgressTracker.

Note that this data is local and will not be passed along to the EWS. If the same Player were to load up the game on a different machine, they would not have access to their saved progress.


>**11)** The scramble statistics shall list all scrambles with (1) their unique identifier, (2) information on whether they were solved or created by the player, and (3) the number of times any player has solved them. This list shall be sorted by decreasing number of solutions.

A Statistics interface was created with a getScrambleStatistics method. MainMenu calls EWS.getPlayersAndSolvedScrambles() and EWS.getScramblesAndCreators() and passes the results to thethe Statistics interface. The Statistics interface processes the result and displays all relevant statistics. 

>**12)** The player statistics will list players’ first names and last names, with (1) the number of scrambles that the player has solved, (2) the number of new scrambles created, and (3) the average number of times that the scrambles they created have been solved by other players.  It will be sorted by decreasing number of scrambles that the player has solved.

Similar to above, the Statistics interface has a getPlayerStatistics() method that processes the result passed to it by MainMenu after making calls to EWS.getPlayersAndSolvedScrambles() and EWS.getScramblesAndCreators().

>**13)** The User Interface (UI) shall be intuitive and responsive.

GUI is not a requirement in this design.


Summary
=======
In our individual designs, we did not have a dedicated MainMenu class. Instead, operations related to creating a player or word scramble were spread out. A MainMenu class helped clear the design so that the WordScramble classes and Player classes could be better seen as just objects of their respective entities. The MainMenu class also exclusively handles all interaction with EWS making it easier to track. In addition, the MainMenu class handles menu options, player logins and initializing Games.

A Game class was created to act as a Game engine and perform any operations specific to the game. This helped combine all operations related to solving, saving and retrieving scrambles and made the purpose of the appication more defined in the class diagram.

Since all statistics are availabe through EWS and only required to be processed, dedicated Statistic classes that stored statistic values as attributes were removed. Instead a Statistics interface was created that processed the results from EWS. 

In our individual designs, we all had parts of the final team design. A lesson learned was how every team member's active participation and open mind in each meeting was crucial to communicating and creating a consensus for the final design. Working together we were able to identify which components of our individual designs worked well and also any pitfalls in them. We were able take the pros in each design and use them to rebuild a class diagram much better than any of our individual designs. Also, because each teammate understands concepts at different rates it was beneficial to have someone comprehensively summarize the discussion from the meeting. This  allowed all team members to understand the discussion that took place.


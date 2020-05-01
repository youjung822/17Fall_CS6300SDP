Assignment 5: Software Design
=============================
Design Information
------------------

John Youngblood (jyoungblood8)


1. **When starting the application, a user may choose to either create a new player or log in.  For simplicity, authentication is optional.  A (unique) username will be sufficient for logging in.**

This requirement was fulfilled with the addition of a Player class with a unique userName attribute. The requirement also signified that a createPlayer() operations was needed so it was added to the *ExternalWebService* since it handles database operations. The login process is done by the user through the UI and server so we do not need to show it in a class diagram.

2. **After logging in, the application shall allow players to  (1) create a word scramble, (2) choose and solve word scrambles, (3) see statistics on their created and solved word scrambles, and (4) view the player statistics.**

Parts (1) and (2) of this requirement lead to the creation of the abstract WordScramble class and its stereotyped class, SavedWordScramble. The abstract Statistic class with its two generalizations, WordScrampleStatistic and PlayerSatistic, were also created to store the data that the UI needs to show based on Parts (3) and (4) of this requirement. A 1 to many binary association called 'create' that goes from Player to SavedWordScramble was put in since a SavedWordScramble can only be created by 1 Player. To show that a player can solve a word scramble the association class, Solves, was added in between Player and SavedWordScramble as well. Its attributes and operation are outlined in requirement 9.

3. **The application shall maintain an underlying database to save persistent information across runs (e.g., word scrambles, player information, statistics).**

The database represented in this requirement is a part of the central server that the *ExternalWebService* communicates with, and the information it persists is already present in the design.

4. **Word scrambles and statistics will be shared with other instances of the application.  An _external web service utility_ will be used by the application to communicate with a central server to:**
   * **Add a player and ensure that their username is unique.**
   * **Send new word scrambles and receive a unique identifier for them.**
   * **Retrieve the list of scrambles, together with information on which player created each of them.**
   * **Report a solved scramble.**
   * __Retrieve the list of players and the scrambles each have solved.__
   
**You should represent this utility as a [utility class](http://www.uml-diagrams.org/class-reference.html) that (1) is called _"ExternalWebService"_, (2) is connected to the classes in the system that use it, and (3) explicitly list relevant methods used by those classes.  This class is provided by the system, so it should only contain what is specified here. You do not need to include any aspect of the server in your design besides this utility class.**

The requirement specifically asks for a *ExternalWebService* utility class, so one was added to the design. The createPlayer() operation that is used to add players and check username uniqueness is outlined further in Requirement 5. The saveWordScramble() operation which saves new word scrambles and receives a unique identifier for them is outlined further in Requirement 6. The getWordScrambles() operation retrieves the list of scrambles, together with information on which player created each of them, and is outlined further in Requirement 11. The reportSolvedScrambles() operation updates both the corresponding PlayerStatistic and WordScrambleStatistic attributes after a Player's submitted solution is found to be correct by the checkSolution() operation. Finally, retrieving the list of players and their solved scrambles is done by the getPlayerStatistic() operation that is outlined further in requirement 12.

5. **When creating a new player, a user will:**
   * **Enter the player’s first name.**
   * **Enter the player’s last name.**
   * **Enter the player’s desired username.**
   * **Enter the player’s email.**
   * **Save the information.**
   * **Receive the returned username, with possibly a number appended to it to ensure that it is unique.**
   
The Player class was given a firstName, lastName, userName, and email attribute all of type String due to this requirement. This requirement is also shown in the createPlayer() operation within *ExternalWebService*s, which creates new instances of Player and PlayerStatistic and saves these instances to the database after checking the provided userNames uniqueness. If another instance of Player is found in the database to have the same userName then increasingly larger numbers are added on to the end of userName until a unique userName is determined. Afterwards, the unique userName attribute is returned to the instance of Player for the user to view whether it was changed from the original one the Player entered or not.

6. **To add a word scramble, the player will:**
   * **Enter a phrase (not scrambled).**
   * **Enter a clue.**
   * **View the phrase scrambled by the system. If the player does not like the result, they may choose for the system to re-scramble it until they are satisfied.**
   * **Accept the results or return to previous steps.**
   * **View the returned unique identifier for the word scramble. The scramble may not be further edited after this point.**
   
The attributes phrase, clue, and scramblePhrase were given to the abstract WordScramble class and the attribute uid was given to the stereotyped SavedWordScramble class to fulfill this requirement. Also the ScrambleGenerator utility and its operation scramble() were added in to handle the requirements for scrambling a phrase brought up here and in requirements 7 and 8. When creating a WordScramble, the player may use scramble() on the phrase they would like to use over and over again until they accept it and invoke the saveWordScramble() operation of the *ExternalWebService*. At this point in time, the system creates a new instance of SavedWordScramble and WordScrambleStatistic that is not editable by the Player, and assigns a unique identifier to its uid attribute. saveWordScramble() returns the Integer that was used as the uid so that the player may view it in the application's UI.

7. **A scramble shall only mix up alphabetic characters, keeping each word together. Words are contiguous sequences of alphabetic characters separated by one or more non-alphabetic characters.**

This requirement is handled by the scramble() operation outlined by the ScrambleGenerator interface.

8. **All other characters and spacing will remain as they originally are.**

![alt text](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17jyoungblood8/blob/master/Assignment5/img/req8_example.jpg "Example of Requirement 9")
This requirement is handled by the scramble() operation outlined by the ScrambleGenerator interface..

9. **When solving word scrambles, a player will:**
   * **View the list of unsolved word scrambles, by identifier, with any in progress scrambles marked and shown first.**
   * **Choose one word scramble to work on.**
   * **View the scramble.**
   * **Enter the letters in a different order to try to solve the scramble.**
   * **Submit a solution.**
   * **View whether it was correct.**
   * **Return either to the puzzle, if wrong, or to the list, if correct.**
   
The list of unsolved word scrambles of a certain player can be obtained programmatically by starting with a collection of all SavedWordScrambles and using the PlayerStatistic class and the getWordScrambleStatistics() operation of *ExternalWebService* to eliminate any Solved or Created SavedWordScrambles from that collection. Then show any SavedWordScrambles whose Solves association with the Player has a non-null instance of the currentState attribute. The rest of this requirement related more to the UI implementation and are not represented in this class diagram, besides the **Submit a solution** part. That is represented by the checkSolution() operation that compares the Player supplied answer with the phrase attribute of the WordScramble, and returns a Boolean based on that comparison.

10. **A player may exit any scramble in progress at any time and return to it later.  The last state of the puzzle will be preserved.**

This requirement is fulfilled by the association class, Solves, that exist between Player and SavedWordScramble. This class represents the unique relationship that each Player has with a single SavedWordScramble. It has the operation exit() that will take the Player back to the list of unsolved word scrambles and save what they currently have entered as the solution for the SavedWordScramble they are viewing and save it to the currentState attribute.

11. **The _scramble statistics_ shall list all scrambles with (1) their unique identifier, (2) information on whether they were solved or created by the player, and (3) the number of times any player has solved them. This list shall be sorted by decreasing number of solutions.**

This requirement is fulfilled by the WordScrambleStatistic class and the getWordScrambleStatistics() operation of the *ExternalWebService* which pulls all current instances of WordScrambleStatistic in the database used by the system. Part (1) is represented by the inclusion of the uid attribute within WordScrambleSatistic. Part (2) is represented by the creator and solvers attribute within WordScrambleSatistic. Part (3) is represented by the attribute /totalSolves which is calculated from the length of the solvers attribute. Sorting is achieved through the compare() operation inherited from the Statistic abstract class.

12. **The _player statistics_ will list players’ first names and last names, with (1) the number of scrambles that the player has solved, (2) the number of new scrambles created, and (3) the average number of times that the scrambles they created have been solved by other players.  It will be sorted by decreasing number of scrambles that the player has solved.**

This requirement is fulfilled by the PlayerStatistic class and the getPlayerStatistics() operation of the *ExternalWebService* which pulls all current instances of PlayerStaistic in the database used by the system. Parts (1) and (2) are represented by the attributes /totalSolved and /totalCreated (these two values are essentially the length of the attributes scramblesSolved and scramblesCreated respectively). Part (3) can be programmatically generated with the attributes listed in Parts (1) and (2). Sorting is achieved through the compare() operation inherited from the Statistic abstract class.

13. **The User Interface (UI) shall be intuitive and responsive.**

I disregarded this requirement for the Class Diagram since it does not affect any of the classes nor their attributes, operations, or relationships with one another.

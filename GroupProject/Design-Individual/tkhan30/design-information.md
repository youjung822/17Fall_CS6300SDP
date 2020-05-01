# CS6300 Assignment5 - Design Information 

## Tamur Khan (tkhan30)

### Requirement List and their Corresponding Design

>**1)**	When starting the application, a user may choose to either create a new player or log in.  For simplicity, authentication is optional.  A (unique) username will be sufficient for logging in.


Here I describe the create player and login process. 

A class **Player** was added to the design. Based on req#5 it was first given the following attriutes: *username*, *loggedIn*, *firstName*, *lastName*, *email*.

This class and its attributes mirror the database table schema. So there is a table called Player with the same fields.  

*loggedIn* is a boolean that's true if the user creates a new player or logs in using their unique *username*.

An operation called *promptNewOrExistingPlayer()* is added to the Player class. 

A utility class called **ExternalWebService** (referred to as EWS) was created based on req#4. **EWS** has some given functions. Two of these are called *addUniquePlayer()* and *verifyUsername()*. 

**Player**.*promptNewOrExistingPlayer()* asks whether the user is a new or existing player. 

If the user is new, they are prompted for a new username, their first name, last name and email and these are validated and stored in the respective attributes. This operation then calls **ExternalWebService**.*addUniquePlayer()* passing the user input as parameters. **EWS** sends a request to the central server and returns a username with possibly a number added to it to make it unique.

The new value is printed for the user and the *username* attribute is updated with this value. This Player record is then inserted into the Player table. The *loggedIn* field is set to true. 

If the user is an existing user, they are prompted for their username. **ExternalWebService**.*verifyUsername()* is called and if it returns true, *loggedIn* is set to true and the *username* attribute is updated with the input.  

>**2)** After logging in, the application shall allow players to  (1) create a word scramble, (2) choose and solve word scrambles, (3) see word scramble statistics on their created and solved word scrambles, and (4) view the player statistics.

An operation called *printPlayerOptions()* was added to the **Player** class that prompts the user to choose one of the options listed in this requirement. When the user selects an option, the corresponding operation in the relevant class is called. Instead of explaining all four options here, these are detailed in their respective requirements below.

>**3)** The application shall maintain an underlying database to save persistent information across runs (e.g., word scrambles, player information, statistics).

An underlying database contains tables that corresponds to the class names (excluding the utility class). There are three tables called: **Player**, **WordScramble** and **PlayerWordM2M**. The columns/fields in these tables are the same as the attributes in each class in the UML class diagram.

Each row in each table represents an  instance of the class. The **Player** table holds information about the player and the **WordScramble** table holds information about word scrambles.

The **PlayerWordM2M** table forms a many-to-many relationship between **Player** and **WordScramble** in addition to holding information about the user's progress on a word scramble. Each record is in a created, inprogress or solved state which is depicted in the enumeration called ScrambleState.

Functions were created to populate these tables and setter and getter functions were created to access and modify their values. These are described in detail in the requirements below. 

>**4)**	Word scrambles and statistics will be shared with other instances of the application.  An external web service utility will be used by the application to communicate with a central server to:
>
>1.	Add a player and ensure that their username is unique.
>2.	Send new word scrambles and receive a unique identifier for them.
>3.	Retrieve the list of scrambles, together with information on which player created each of them. 
>4.	Report a solved scramble.
>5.	Retrieve the list of players and the scrambles each have solved.

> You should represent this utility as a utility class that (1) is called "ExternalWebService", (2) is connected to the classes in the system that use it, and (3) explicitly list relevant methods used by those classes.  This class is provided by the system, so it should only contain what is specified here. You do not need to include any aspect of the server in your design besides this utility class.

A utility class called **ExternalWebService** was created with six operations. Their functions are listed in this requirement:

* *addUniquePlayer()* 
* *verifyUsername()* 
* *sendScrambleGetUID()* 
* *getScramblesAndCreators()* 
* *reportSolvedScramble()* 
* *retrievePlayerAndSolvedScrambles()* 

This utility class is used by the **Player** and **WordScramble** classes. There are methods in both these classes that call one of the methods in **EWS**. I have already outlined how the **Player** classes uses **EWS** to verify usernames under req#1 above.

Outlining all of the remaining methods that use **EWS** here will be confusing and so I will explicitly call out any references made to **EWS** below. 

> **5)** When creating a new player, a user will:
	
> 1.	Enter the player’s first name.
> 2.	Enter the player’s last name.
> 3.	Enter the player’s desired username.
> 4.	Enter the player’s email.  
> 5.	Save the information.
> 6.	Receive the returned username, with possibly a number appended to it to ensure that it is unique.
	
Please see the description under req#1 above where this was discussed in detail as it is closely associated with the initial process of creating a new user or logging in with an existing username. 

How the Player table will be populated is described further below. 
	
>**6)**	To add a word scramble, the player will:

> 1. Enter a phrase (not scrambled).
> 2.	Enter a clue. 
> 3.	View the phrase scrambled by the system. If the player does not like the result, they may choose for the system to re-scramble it until they are satisfied.
> 4.	Accept the results or return to previous steps.
> 5.	View the returned unique identifier for the word scramble. The scramble may not be further edited after this point. 

A **WordScramble** class created with the following attributes: 

* *uniqueIdentifier*
* *phrase*
* *clue*
* *scrambledPhrase*
* *createdBy*
* *numOfTimesSolved*. 

The following two methods were added to **WordScramble**: 

* *createScramble()*
* *scrambleWord()*

*createScramble()* prompts the user for a *phrase* and *clue* and the input is validated and assigned to the respective attributes. Next, *scrambleWord()* is called passing the *phrase* as a parameter.  

*scrambleWord()* scrambles the *phrase* and returns it. Each time it's called the user is asked if they are satisfied with the new scrambled value. This runs in a while loop until the user is satisfied. 

Once the user is satisfied, the *createScramble()* method calls **ExternalWebService**.*sendScrambleGetUID()* with the *phrase*, *scrambledPhrase*, player's username (using **Player**.*getActiveUser()*, see below) and *clue* as parameters. This returns a unique identifier from the central server. The value of this unique identifier is assigned to the *uniqueIdentifier* attribute.

*timesSolved* is set to 0, *createdBy* is set to the current user and the record is inserted into the **WordScramble** table.

In the **Player** class an attribute called *numOfScramblesCreated* was created which shows the number of scrambles created by the player. A  *setNumOfScramblesCreated()* operation is also created in the **Player** class that increments the value of *numOfScramblesCreated*. 

**WordScramble**.*createScramble()* calls **Player**.*setNumOfScramblesCreated()*.

>**7)** A scramble shall only mix up alphabetic characters, keeping each word together. Words are contiguous sequences of alphabetic characters separated by one or more non-alphabetic characters.

This functionality will be a part of **WordScramble**.*scrambleWord()* method discussed above in req#6. The algorithm of methods are not depicted in the UML class diagram. An explanation outline of how it works is given beneath the next requirement.

>**8)**	All other characters and spacing will remain as they originally are.

	Example:  
	The cat is loud :-).  ->  Het atc si ulod :-)

This functionality will be a part of the **WordScramble**.*scrambleWord()* method. The function will split the string using any character that's not a letter. A contiguous sequence of letter is one type of object and their order will be scrambled. The non-letter characters between two letters are a different object and will not be scrambled. The objects will retain their order and returned. 

>**9)** When solving word scrambles, a player will:
	
>1.	View the list of unsolved word scrambles, by identifier, with any in progress scrambles marked and shown first.
>2.	Choose one word scramble to work on.
>3.	View the scramble.
>4.	Enter the letters in a different order to try to solve the scramble.
>5.	Submit a solution.
>6.	View whether it was correct.
>7.	Return either to the puzzle, if wrong, or to the list, if correct.
	
A new method called *solveScramble()* was created in the **WordScramble** class. 

A new class is created called **PlayerWordM2M**. This has the following four attributes: 

* *player* - An object of the **Player** class
* *word* - An object of the **WordScramble** class
* *wordState* - An enumeration called ScrambleState with options of inprogress, solved or created
* *inProgressPhrase* - when a user exits a scramble while solving it, the current state of the *phrase* is stored in this variable

The **PlayerWordM2M** class has 6 methods:

* *getInProgressScrambles()* - this returns all records that have a wordState of inprogress. Can accept a username as a parameter to only return that user's records.
* *addInProgressScramble()* - creates a record that has a wordState of inprogress. This also updates the value of the *inProgressPhrase* variable. Accepts the, in progress phrase, username and word's uniqueIdentifier as parameters.
* *deleteInProgressScramble()* - deletes a record that has a wordState of inprogress. This normall happens when a user solves a scramble that previously had a state of inprogress. Accepts as a parameter the username and word's uniqueIndentifier.
* *getInProgressPhrase()* - returns the value of the *inProgressPhrase* variable. The username and word's uniqueIdentifier are passed as parameters.
* *getSolvedScrambles()* - returns all scrambles that are in a solved state. Can accept a user as a parameter to only return that user's records.
* *addSolvedScramble()* - creates a record that has a wordState of solved. Accepts the username and word's uniqueIdentifier as parameters.


**WordScramble**.*solveScrambel()* calls **ExternalWebService**.*getScramblesAndCreators()* that returns a list of all word scrambles and their creators. 

Next, **PlayerWordM2M**.*getSolvedScrambles()* is called passing the username as a parameter. The result is used to filter the list returned by **EWS** removing all words that the user has already solved. 

Next **PlayerWordM2M**.*getInProgressScramble()* passing the username as a parameter. Result is used to mark the in progress scrambels in the list returned by **EWS**. This list is printed for the user.

The user is able to choose a scramble from the list. If the user chooses a scramble that is in an In Progress state, the *scrambledPhrase* variable is updated with the return value of **PlayerWordM2m**.*getInProgressPhrase()*. This is the saved value of the state of the phrase when the user last exited without solving the word scramble.

The user is shown the values of *clue* and *scrambledPhrase*. There can now be 3 scenarios:

 The user submits a correct solution. If this previously had a state of In Progress, **PlayerWordM2M**.*deleteInProgressScramble()* is called passing the *username* and word's *uniqueIdentifer* and the in progress record is deleted from the PlayerWordM2M table.
 
 Next **PlayerWordM2M**.*addSolvedScramble()* is called passing the *username* and word's *uniqueIdentifier*. This creates a solved record in the **PlayerWordM2M** table for this word.
 
An attribute called *numOfTimesSolved* was added to **WordScramble**. This attribute is incremented now that a user has solved the scramble. It will be incremented every time this word scrambel is solved.

**ExternalWebService**.reportSolvedScramble() is called with the username and word's uniqueIdentifier reporting a solve. 

The second scenatio is that a user submits an incorrect solution. They are asked to try again.

Third scenario is discussed under req#10 below.
	
>**10)** A player may exit any scramble in progress at any time and return to it later.  The last state of the puzzle will be preserved.	
	
The third scenario is that a user exits before finishing. The **Player**.*loggedIn* attribute is set to false. **PlayerWordM2M**.*addInProgressScramble()* is called with the username and word's uniqueIdentifier to create a new record int the **PlayerWordM2M** table. This function also updates the value of the **PlayerWordM2M**.*inProgressPhrase* attribute with the player's progress and current value of the unscrambled phrase.

>**11)** The scramble statistics shall list all scrambles with (1) their unique identifier, (2) information on whether they were solved or created by the player, and (3) the number of times any player has solved them. This list shall be sorted by decreasing number of solutions.
	
The following functions were added to the **WordScramble** class:

* *viewWordScrambleStats()* - this function prints records in the **WordScramble** table.
* *populateWordScramblesFromEWS()* - makes a call to **EWS** and processes the result to make sure the **WordScramble** table is up-to-date.
* *setNumOfTimesSolved()* -calculates and sets the value of the *numOfTimesSolved* attribute. This also calls **EWS** to get the players and the scrambles they've solved.

The *viewWordScrambleStats()* function is called which calls *populateWordScramblesFromEWS()*. 

*populateWordScramblesFromEWS()* calls **ExternalWebService**.*getScrambleAndCreators()* that returns all scrambles along with their creator's name. 

The result of all scrambles is used to populate the **WordScramble** table. If a *uniqueIdentifier* already exists, the record will be coalesced, meaning that a new record will not be inserted but instead the same record will be updated with the new values. 

Next, **PlayerWordM2M**.*getSolvedScrambles()* is called passing the Player's *username* as a parameter. The result is cross-referenced with the UIDs returned by **EWS**. If a scramble has been solved previously, it will be labeled as solved in the list when printed for the user. 

Next *setNumOfTimesSolved()* is called which calls **ExternalWebService**.*retrievePlayerAndSolvedScrambles()*. The result is a list of players and the scrambles they have solved. The method processes the result calculating how many players have solved each word and sets the value of *timesSolved* for each word in the **WordScramble** table.

Finally, *viewWordScrambleStats()* prints the list of records in the WordScramble table sorting it by decreasing *timesSolved* value and marking the solved records.

>**12)** The player statistics will list players’ first names and last names, with (1) the number of scrambles that the player has solved, (2) the number of new scrambles created, and (3) the average number of times that the scrambles they created have been solved by other players.  It will be sorted by decreasing number of scrambles that the player has solved.

The following attributes are added to the **Player** class:

* *numOfScramblesSolved* - total number of scrambles solved by the Player 
* *avgCreationsSolved* - average number of times a scramble that was created by a player was solved by other players

The following method are added to the **Player** class.

* *getActiveUser()* - returns the username of the Player who has a loggedIn value of true
* *viewPlayerStats()* - called for printing the player stats. This in turn calls all the other relevant methods.
* *populatePlayersFromEWS()* - makes a call to **ExternalWebService**.*retrievePlayerAndSolvedScrambles()*, processes the result and populates the Player table 
* *populateAvgCreationsSolved()* - this calculates the value of *avgCreationsSolved* and calls **EWS**.*retrievePlayerAndSolvedScrambles()*

The following method is added to the **WordScramble** class:

* *getScrambleUIDByUser()* - this accepts the username as a a parameter and returns all word scrambles where that user is the creator

The **Player**.*viewPlayerStats()* method is caleld to print player stats. This method in turn calls **Player**.*populatePlayersFromEWS()* to populate the Player table with up-to-date values. 

*populatePlayersFromEWS()* calls **ExternalWebService**.*retrievePlayerAndSolvedScrambles()* that returns a list of players and the scrambles they've solved. This result is used to create records in the Player table with the following fields: *username*, *firstName*, *lastName*, *email*, *numOfScramblesSolved*. The rest of the fields will be updated later and are left blank for now.

Next *populatePlayersFromEWS()* calls **ExternalWebService**.*getScramblesAndCreators()* to get a list of all word scrambles and their creators. The result is grouped by username, and the number of records are counted to get the number of word scrambles each player has created and so the value of *numOfScramblesCreated* and this field is updated for each player.

Finally, *populatePlayersFromEWS()* calls *populateAvgCreationsSolved()*. This iterates through each row of Player in the Player table. With each iteration, the player at the current index will be referred to as the 'current player' in this description. This function calls **ExternalWebService**.*retrievePlayerAndSolvedScrambles()* which returns all players and their solved scrambles. 

The list is filtered to remove all records where creator is the current user. We group the list by the uniqueIdentifier of the word scramble getting all word scrambles solved by other players. 

Next, we call **WordScramble**.*getScrambleUIDsByUser()* passing the current Player's username using *getActiveUser()*. This returns an array of uniqueIdentifiers of word scrambles created by the current user.

We go back to our filtered list of all word scrambles solved by other players grouped by the uniqueIdentifier. We remove all groups where the uniqueIdentifier is not in our array. Now we have all word scrambles created by the current user but solved by other users. We get a count of these records. 

We set the value of *avgCreationsSolved* to be the count of this divided by the value of *numOfScramblesCreated*. This field is updated for each **Player** record.

Now we have the **Player** table fully populated with all players and all their attributes. Finally, *viewPlayerStats()* prints our result for the user.

>**13)** The User Interface (UI) shall be intuitive and responsive.

Implementing a GUI is not required as part of this application. In my design, I have leveraged the command line for all user interaction and it is described under each requirement above. Vi
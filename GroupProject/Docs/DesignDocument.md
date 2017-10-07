# Design Document

**Author**: John Youngblood (jyoungblood8)

1 Design Considerations
-----------------------
### 1.1 Assumptions

* The External Web Service (EWS) outlined in Requirement 4 will be provided to us.
* The unique identifier returned by the EWS for Word Scrambles will be alphanumeric.
* The Central Server the EWS interacts with will have a sufficently large database.
* The EWS will be reliable and responsive.

### 1.2 Constraints

* The application must be produced within the time frame established by the class.
* The application must run with a minimum Android API Level of 19.
* The EWS will not return data types that we create.

### 1.3 System Environment

* Our application will be developed for Android with a minimum target of API Level 19.

2 Architectural Design
----------------------
### 2.1 Component Diagram

* Our system is comprised of just a single APK that will run on an Android device.

### 2.2 Deployment Diagram

* Our system is comprised of just a single APK that will run on an Android device.

3 Low-Level Design
---------------------
### 3.1 Class Diagram

![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Design-Team/images/team44Design.png?raw=true "Team design")

### 3.2 Other Diagrams

*<u>Optionally</u>, you can decide to describe some dynamic aspects of your system using one or more behavioral diagrams, such as sequence and state diagrams.*

4 User Interface Design
-----------------------
### 4.1 Login Screen
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/LoginScreen.png "Login Screen")
* This mockup is of the login screen outlined in requirement 1. Here we can log in to a Player account by providing the system with the Player's username and clicking the 'Login' button, or create a new Player through the 'Sign Up' button. If the provided username is not in the system, an error should pop up saying so.

### 4.2 New Player Creation
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/NewPlayerCreation.png "New Player Creation")
* When a User selects 'Sign Up' from the login screen, they are taken here. The process of creating a new Player that we show here is outlined in requirement 5. The user provides a First Name, Last Name, Email, and Desired Username. We call this 'Desired' Username since if the username is already being used by another Player, then the system will append numbers to the end of the provided username to make it unique.

### 4.3 Player Creation Successful
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/PlayerCreation.png "Player Creation Successful")
* After a new Player has been created, the system will show the username, with possibly a number appended to it to ensure that it is unique as outlined in requirement 5.

### 4.4 Main Menu
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/MainMenu.png "Main Menu")
* This is the main menu of the system. The user will initially come here after successfully logging in or creating a new Player account. The options the user has from the main menu are outlined in requirement 2:
	* create a word scramble,
	* choose and solve word scrambles,
	* see statistics on their created and solved word scrambles, and
	* view the player statistics.

### 4.5 New Word Scramble Creation
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/NewWordScrambleCreator.png "New Word Scramble Creation")
* This view is for creating a new Word Scramble and is gotten to by selecting 'Create Word Scramble' on the Main Menu. Here we fufill requirement 6 which states 'To add a word scramble, the player will:
	* Enter a phrase (not scrambled).
	* Enter a clue.
	* View the phrase scrambled by the system. If the player does not like the result, they may choose for the system to re-scramble it until they are satisfied.
	* Accept the results or return to previous steps.
	* View the returned unique identifier for the word scramble. (This is done in the follow up screen after selecting submit)

### 4.6 Word Scramble Creation Successful
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/WordScrambleCreation.png "Word Scramble Creation Successful")
* After the Player has created a Word Scramble, the system will show the unique identifier that it has assigned the new Word Scramble.

### 4.7 Unsolved Word Scrambles View
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/ViewUnsolvedWordScramble.png "Unsolved Word Scrambles View")
* When a Player chooses 'Solve Word Scramble' from the main menu, they are shown the list of all Word Scrambles that have not solved. The Player is able to select one of the Word Scrambles to start solving it by just clicking on its nameplate. If a Player has previously worked on a Word Scramble, but have yet to solve it, their progress is saved. These 'In-Progress' Word Scrambles are shown first in this list.

### 4.8 The Game
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/Game.png "The Game")
* This is the screen the Player sees when solving a Word Scramble. The ID of the Word Scramble is shown at the top, and the Scrambled Phrase and Clue are shown in the middle. The Player will type their guesses in the text box at the bottom of the screen. At any time the player may exit out of solving the Word Scramble by selecting 'Back'

### 4.9 Player Statistics
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/PlayerStatistics.png "Player Statistics")
* The user gets this view when they select 'View Player Statistics' from the Main Menu. This view helps satisfy requirement 12 by:
	* Listing player's first name & last name.
	* Listing the number of scrambles that the player has solved.
	* Listing the number of new scrambles created.
	* Listing the average number of times that the scrambles they created have been solved by other players.
	* Sorting by decreasing number of scrambles that the player has solved. (We also allow sorting on the other metrics shown here)

### 4.10 Word Scramble Statistics
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images//WordScrambleStatistics.png "Word Scramble Statistics")
* When a Player on the main menu chooses to view statistics on Word Scrambles, they will be taken to this page. Here we list the unique identifier, whether it was solved or created by the Player, and the total number of Players that have solved the WordScramble. This page helps satisfy requirements 2 and 11 of this system. Solved % is not particular outlined in the requirements, but it is a clean way of representing total number of players that have solved the WordScramble by keeping the stats in a limited range instead of letting them continoulsy increase as the player base grows. Solved % can be calculated by Number of Players that have solved the WordScramble divided by the total number of unique players in the whole system - 1 (the creator).

![This GUI mockup was generated using Balsamiq.](../GUI_Mockup.pdf)

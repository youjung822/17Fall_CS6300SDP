# Use Case Model

**Author**: Team44

## 1 Use Case Diagram

![Use Case Model](https://github.gatech.edu/raw/gt-omscs-se-2017fall/6300Fall17Team44/master/GroupProject/Docs/Images/UseCaseDiagram.png)

## 2 Use Case Descriptions

### Actors
* **Local Player** - the player currently interacting with our application.
* **Central Server** - the entity that maintains informations for all instances of word scramble applications, including ours.

### Use Cases

* **Maintain user login**
	* Requirements
		* Local Player can create an acount
		* Local Player can login using existing credentials
		* Central Server accepts and stores new logins created by the Local Player
		* Central Server verifies login credentials entered by Local Player
	* Pre-conditions
		* Local Player may only login using an existing credential maintained by the Central Server
		* When creating an account the username must be valid
		* Local Player create a new account or login from the relevant view
	* Post-conditions
		* Local Player successfully creates an account or enters a valid credential
	
	* Scenarios
		* Normal flow of events
			* The Local Player choose to create an account.
			* The Local Player enters their first name, last name, email and username.
			* The username is validated to be in the correct format and unique.
			* Once the account is created or the Local Player has an existing account, the Local Player chooses to log in.
			* The login is validated by the Central Server and the Local Player is logged in ad returned to the main menu
		* Alternative flow of events
			* The Local Player chooses to create an account.
			* The Local Player enters their first name, last name, email and username.
			* Central Server recognizes the username entered is not unique. It adds a unique number and returns the username.
		* Exceptional flow of events
			*  The Local Player chooses to log in.
			*  The login username is invalid.
			*  Login is denied and the Local Player is notified and asked to create a new account or enter a valid username.

			
* **Create a word scramble**
	* Requirements
		* The Local Player creates a new scramble.
		* The Local Player is able to scramble the phrase till satisfied.
		* The Central Server accepts and stores the newly created phrase. 
	* Pre-conditions
		* The Local Player is logged in.
		* The Local Player chooses to create a cramble from the main menu. 
	* Post-conditions
		* All the information for the create scramble process has been entered.
		* The Local Player submits the scramble and is returned to the Main Menu. 
	* Scenarios
		* Normal flow of events
			* The Local Player chooses to create a scramble from the main menu
			* The Local Player enters the unscrambled phrase and a hint
			* The Local Player views the phrase scrambled by the application.
			* The Local Player accepts the scrambled phrase and is returned to the main menu.
		* Alternative flow of events
			* When the Local Player enters the unscrambles phrase and hint, they do not like the phrase scrambled by the application.
			* The Local Player chooses to scramble again.
			* The application scrambles the word again and rpesents it to the Local Player. 
			* The Local Player repeats this several times.
			* The Local Player accepts the scrambled phrase and is returned to the main menu.
		* Exceptional flow of events
			* The user doesn't enter a valid phrase or hint.
			* They are notified or returned to the main menu. 
* **View scramble list and stats**
	* Requirements
		* Local Player should see a list of all unsolved word scrambles.
		* Local Player should see which unsolved scrambles are in progress.
		* Local Player should see creators of the scrambles.
		* Local Player should see 	scrambles they created.
		* Local Player should see which number of times a scramble has been solved by anyone.
		* Central Server provides application with all of the above, excluding in progress tracking.
	* Pre-conditions
		* Local Player needs to be logged in. 
		* Local Player chooses to view scrambles.
		* Central Server already has scrambles that were created by a Local Player or Other Player previously. 
	* Post-conditions
		* The Local Player chooses a scramble to solve or to return to the main menu. 
	* Scenarios
		* Normal flow of events
			* Local Player chooses to view scrambles from the main menu.
			* The scrambles are displayed along with the information mentioned above.
			* The Local Player chooses a scramble to solve. 
		* Alternative flow of events
			* The Local Player chooses to view scrambles and their statistics from the main menu.
			* The Local Player views the statistics.
			* The Local Player returns to the main menu. 
		* Exceptional flow of events
			*  The Local Player chooses to view scrambles from the main menu,
			*  The Local Player sees an in progress scramble and chooses to continue solving it.
* **Solve a scramble**
	* Requirements
		* The Local Player solves a scramble.
		* The Local Player is informed if the answer is correct or not.
		* The Central Server stores solved scrambles.
	* Pre-conditions
		* The Local Player is logged in.
		* The Local Player has selected a scramble to solve.
		* The Central Server has existing scrambles that the Local Player can solve.
	* Post-conditions
		* The Local Player solves a scramble.
		* The Local Player exits a scramble without solving. 
	* Scenarios
		* Normal flow of events
			* The Local Player selects a scramble from the list.
			* The Local Player is displayed a scramble phrase and hint.
			* The Local Player guess what the unscrambled phrase 
			* If their answer is correct, they are notified.
			* The Central Server is notified that the scramble has been solved. 
			* The Local Player is returned to the Main Menu.
		* Alternative flow of events
			* The Local Player selects an in progress scramble from the list.
			* The Local Player sees their progress and attempt to solve it. 
			* They solve the scramble.
			* The Central Server is notified that the scramble has been solved. 
			* They are returned to the Main Menu.
		* Exceptional flow of events
			* The Local Player chooses any scramble from the list. 
			* The Local Player attempts to solve the scramble
			* The answer is wrong
			* The Local Player is notified and the Local Player tries again.
			* The Local Player exits the scramble without solving and returns to the main menu.
* **Save and retrieve game progress**
	* Requirements
		* Local Player should be able to exit in the middle of solving a scramble and the progress should not be lost.
		* The Local Player should be able to see any scrambles that the user previously attempted to solve and are in progress.
		* The Local Player is able to retrieve an in progress scramble and see what their last guess was.
	* Pre-conditions
		* Local Player must be logged in.
		* Local Player must have chosen a scramble to solve.
		* Local Player must have exited without solving the scramble. 
	* Post-conditions
		* The Local Player solves the scramble or exits again without solving. 
	* Scenarios
		* Normal flow of events
			* The Local Player picks a scramble to solve from the scramble list. 
			* The Local Player makes a guess that is incorrect.
			* The Local Player exits the scramble.
			* The Local Player views the list of scrambles.
			* Local Player sees the original scramble in their list marked as in progress.
			* The Local Player chooses the same scramble.
			* The Local Player sees their progress with that scramble.
		* Alternative flow of events
			* The Local Player chooses an in progress scramble in the list of scrambles.
			* The Local Player sees their progress.
			* Local Player solves the scramble.
			* The Local Player is returned to the scramble list and the scramble is no longer marked as in progress.
		* Exceptional flow of events
			* Local Player views the list of scrambles and sees their in progress scrambles.
			* Local Player exits to Main Menu. 
* **View player list and stats**
	* Requirements
		* Local Player sees the first and last names of all players
		* Local Player sees the number of scrambles each player has solved.
		* Local Player sees the number of scrambles each player created.
		* Local Player sees the average number of times their scrambles have been solved by others
		* Local Player sees players sorted by decreasing number of scrambles solved.
		* Central Server provides all of the information above.
	* Pre-conditions
		* Local Player is logged in.
		* Local Player chooses to view Player Statistics in the Main Menu.  
	* Post-conditions
		* Local Player chooses to exit Player Statistics and return to Main Menu. 
	* Scenarios
		* Normal flow of events
			* Local Player chooses to view Player Statistics from the main menu.
			* Local Player reads the statistics and exits back to Main Menu.
		* Alternative flow of events
			* None
		* Exceptional flow of events
			* None

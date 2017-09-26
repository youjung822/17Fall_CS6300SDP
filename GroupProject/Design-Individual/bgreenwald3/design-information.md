Assignment 5 - Design Information
Brian Greenwald

Overall Assumptions:
- getters and setters exist for each field but are not displayed in the diagram
- only non-default constructors are displayed. No default constructor has private access
- the *Manager Classes can be thought of as the boundary between the frontend and the backend. (ie the frontend interacts with these Classes)

1. When starting the application, a user may choose to either create a new player or log in.  For simplicity, authentication is optional.  A (unique) username will be sufficient for logging in.
<b>To realize this requirement, Class PlayerManager offers both login and createPlayer methods.</b>


2. After logging in, the application shall allow players to  (1) create a word scramble, (2) choose and solve word scrambles, (3) see statistics on their created and solved word scrambles, and (4) view the player statistics. 
<b>To realize the requirement for multiple "views" (without including the GUI), each *Manager class' methods provide the relevant functionaltiy or data retrieval needed for each view</b>


3. The application shall maintain an underlying database to save persistent information across runs (e.g., word scrambles, player information, statistics). 
<b>My design makes the asssumption that object creation and the updates thereof are persisted to a database</b>


4. Word scrambles and statistics will be shared with other instances of the application.  An external web service utility will be used by the application to communicate with a central server to:
	* Add a player and ensure that their username is unique.
	* Send new word scrambles and receive a unique identifier for them.
	* Retrieve the list of scrambles, together with information on which player created each of them. 
	* Report a solved scramble.
	* Retrieve the list of players and the scrambles each have solved.

    You should represent this utility as a utility class that (1) is called "ExternalWebService", (2) is connected to the classes in the system that use it, and (3) explicitly list relevant methods used by those classes.  This class is provided by the system, so it should only contain what is specified here. You do not need to include any aspect of the server in your design besides this utility class.
<b>To realize this, my design includes the ExternalWebService with a method for each required functionality lited above.</b>
    
5. When creating a new player, a user will:
	* Enter the player’s first name.
	* Enter the player’s last name.
	* Enter the player’s desired username.
	* Enter the player’s email.  
	* Save the information.
	* Receive the returned username, with possibly a number appended to it to ensure that it is unique.
<b>The Player class realizes this requirement. It has fields for each of these pieces of information, along with a constructor. The uniqueness requirement is business logic and thus cannot be represented.</b>


6. To add a word scramble, the player will:
	* Enter a phrase (not scrambled).
	* Enter a clue. 
	* View the phrase scrambled by the system. If the player does not like the result, they may choose for the system to re-scramble it until they are satisfied.
	* Accept the results or return to previous steps.
	* View the returned unique identifier for the word scramble. The scramble may not be further edited after this point.
<b>My ScrambleCreationManager takes care of initiating the scrambling of the input phrase with help from its Scrambler instance - which I made as an interfact to allow for future extendability (maybe I'll want to dynamically change how I perform the scrambling?). The scramblePhrase method returns the scrambled phrase so that it can be passed back to the player through the UI and can thus be repeated until the player is satisfied. Once they are, the createScramble takes care of actually creating the Scramble. With help from the EWS's addScramble we can retrieve its uuid and return it to the player. Navigation is UI-related and is thus not represented.</b>


7. A scramble shall only mix up alphabetic characters, keeping each word together. Words are contiguous sequences of alphabetic characters separated by one or more non-alphabetic characters.
<b>Business logic. Not represented in design.</b>


8. All other characters and spacing will remain as they originally are.
<b>Business logic. Not represented in design.</b>


9. When solving word scrambles, a player will:
	* View the list of unsolved word scrambles, by identifier, with any in progress scrambles marked and shown first.
	* Choose one word scramble to work on.
	* View the scramble.
	* Enter the letters in a different order to try to solve the scramble.
	* Submit a solution.
	* View whether it was correct.
	* Return either to the puzzle, if wrong, or to the list, if correct.
<b>The ScrambleSolverManager realizes this requirement with methods to list the available scrambles and solve the selected scramble. The details of how the list is constructed is business logic and thus not represented. UI-related requirements are not represented.</b>


10. A player may exit any scramble in progress at any time and return to it later.  The last state of the puzzle will be preserved.
<b>I created a ScrambleSolver class that represents a player's session in solving a scramble. The Player class thus also maintains a list of all ScrambleSolvers in progress. It offers an update method for these ScrambleSolvers such that they can be removed when their solved attribute is set to true, or added/kept in the list otherwise. It also keeps track of whatever their last guess was.</b>


11. The scramble statistics shall list all scrambles with (1) their unique identifier, (2) information on whether they were solved or created by the player, and (3) the number of times any player has solved them. This list shall be sorted by decreasing number of solutions.
<b>The ScrambleStatisticsManager realizes these requirements. The UI would simply need to grab the list provided by this class' single method to gather all the information it needs. Each Scramble instance keeps track of its uuid, its creator, and the number of times it has been solved. The details of how the list is constructed is business logic and thus not represented.</b>


12. The player statistics will list players’ first names and last names, with (1) the number of scrambles that the player has solved, (2) the number of new scrambles created, and (3) the average number of times that the scrambles they created have been solved by other players.  It will be sorted by decreasing number of scrambles that the player has solved.
13. The User Interface (UI) shall be intuitive and responsive.
<b>The PlayerStatisticsManager realizes these requirements. Unlike the previous set of requirements, this class needs to fetch both the full list of Players in addition to the Scrambles to derive the necessary information. The details of how the list is constructed is business logic and thus not represented. </b>

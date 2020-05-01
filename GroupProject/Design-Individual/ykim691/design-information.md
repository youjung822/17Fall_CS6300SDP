# Introduction
This software Design Document is to provide some description which will be used to aid understanding the UML Class Diagram for software design: a word scramble game.  
 
# Classes
## 1. Player
- Player class is for main subject to use the application. When a user successfully signs into the system with `Username` , a player instance is generated.
- When a new user register oneself, system is required to process duplication check. If there is duplicated a username, the application offers you the requested username with a digit. For thses cases, alterUserName() have designed working along with ExternalWebService addUser(), and checkUniqueUserName().

## 2. Word Scramble
  - Word Scramble class is for main object to be used by the subject. 
  - In the class, there are several attributes and one functionality to keep its own purpose and characteristics.
  
### 2-1. CreateScramble
- This class is to create a new Word Scramble. All the related actions are under the class such as scramble(), saveScramble() and etc. 
- The reason for the separation from WordScramble Class is those two classes are different concepts. By putting them all together, it will cause the waste of data storage due to unnecessary variable definition. 
- Moreover, the only time functions scramble(), saveScramble() used is when a new scramble game is created, because editting the submitted word scramble is not allowed. 
- Rescramble will be conditionally performed per attribute "numberOfScrambled" from Word Scramble Class.

### 2-2. Game
- This class is for every instance that user playes on to a set of Word Scramble. Because the word scramble quizes are shared through web service, there should be multiple instances for one word scramble.
- Also a game needs to have a player information and a set of Word Scramble information, those information are referenced by adding WSID(Word Scramble ID) and Username.
- For the statistic purpose, this game classes will be used frequently because Game classes will contains comprehensive usage and history data.

### 2-3. SolveGame
- When a user picked a word scramble there are a set of actions follows. This class is to include those set of actions and imperatively necessary attributes.
- This class is also driven by an association between Player and Games. 
- This class will communicate with the web service to see if given solution is correct and save processing status.

## 3. Statistics
- This class is to show the statistics data per player or game. There are shared portion of two sub classes(PlayerStatistics and ScrambleStatistics). Those shared components are added into higher level of class such as OrderbyDecreasing(). 

### 3-1. PlayerStatistics 
- This class is mainly focused on the record by player.
- It will have some method to meet the requirements for Player Statistics.

### 3-2. ScrambleStatistics 
- This class is mainly focused on the record by Word Scramble game.
- It will have some method to meet the requirements for Scramble Statistics.
 
 ## 4. Others
- There are two requirements that have not expressed in this design.
The following two(Requirement 3. The database, Requirement 13. User Interface) are missing because they do not affect the design.

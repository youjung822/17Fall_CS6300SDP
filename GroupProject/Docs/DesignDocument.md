# Design Document

**Author**: John Youngblood (jyoungblood8)

1 Design Considerations
-----------------------
### 1.1 Assumptions

* The External Web Service outlined in Requirement 4 will be provided to us.
* The unique identifier returned by the EWS for Word Scrambles will be alphanumeric.
* The scramble() operation will identify phrases that are 'un-scrambable' (any phrase that does not have at least 2 letters next to each other)

### 1.2 Constraints

* The application must run with a minimum Android API Level of 19

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

### 4.2 New Player Creation
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/NewPlayerCreation.png "New Player Creation")

### 4.3 Player Creation Successful
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/PlayerCreation.png "Player Creation Successful")

### 4.4 Main Menu
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/MainMenu.png "Main Menu")

### 4.5 New Word Scramble Creation
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/NewWordScrambleCreator.png "New Word Scramble Creation")

### 4.6 Word Scramble Creation Successful
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images/WordScrambleCreation.png "Word Scramble Creation Successful")

### 4.7 Unsolved Word Scrambles View
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images//UnsolvedWordScramble.png "Unsolved Word Scrambles View")

### 4.8 The Game
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images//Game.png "The Game")

### 4.9 Player Statistics
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images//PlayerStatistics.png "Player Statistics")

### 4.10 Word Scramble Statistics
![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Docs/Images//WordScrambleStatistics.png "Word Scramble Statistics")

![This GUI mockup was generated using Balsamiq.](../GUI_Mockup.pdf)

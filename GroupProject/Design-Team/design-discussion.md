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

Summary
=======

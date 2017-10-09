**External Web Service**
**VERSION 1.1**

This jar contains a mocked web service utility jar, with the corresponding javadoc.  An updated version and/or a live (cloud-synchronizing) version may be provided later, at the same URL.  **Any updates will be noted in Piazza.**  The method signatures will be kept consistent, and you should be able to substitute any updated or live jars into your project's build path without any changes to your own code.  **Do not add new methods or variables to the provided jar** - they will not be there if you change between the mocked and live jars.  Your TA may swap jars for testing, and will not use any alterations you make to the ExternalWebService jar.

The mock jar will not persist data between runs, and will start with a small set of sample data.  It WILL accept data updates within one run.  It also contains two initialization functions that may be used to simulate it maintaining data between runs (by initializing the data when starting).   Do not rely on the hardcoded values in the sample data in the jar (as those may change if you are using the live jar later), but this should give you examples of the relevant data and errors, as well as provide a way for you to run your app locally, without outside connectivity if necessary.


Refer to the javadoc for further explanations of the methods provided:


 - **getInstance()** : returns static ExternalWebService
 - **initializePlayers(List&lt;List&lt;String>> init)** : Optional initialize players function for use in mock EWS jar.
 - **initializeScramble(List&lt;List&lt;String>> init)** : Optional initialize scrambles function for use in mock EWS jar.
 - **newScrambleService(String answer, String scrambled, String clue, String creator) throws SocketTimeoutException, IllegalArgumentException** : returns unique scramble id
 - **retrieveScrambleService()** : returns List of scramble data
 - **retrievePlayerListService()** : returns List of player data
 - **reportSolveService(String scrambleid, String username)** :  returns boolean true for successful recording of solve.  The puzzle and the player must already exist!

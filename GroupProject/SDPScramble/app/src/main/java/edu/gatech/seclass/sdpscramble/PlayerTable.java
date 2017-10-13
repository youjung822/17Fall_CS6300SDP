package edu.gatech.seclass.sdpscramble;

/**
 * Created by tamur on 10/12/17.
 */

public class PlayerTable {

    public Long _id;        // for cupboard
    public String username; // username
    public String firstName;// firstname
    public String lastName; // lastname
    public String email;    // email
    public Integer numOfScramblesSolved;    // total number of scrambles solved
    public Integer numOfScramblesCreated;   // total number of scrambles created
    public Double avgCreationsSolved;       // average number of creations that were solved by others

    public PlayerTable(String usr, String fName, String lName, String eml, Integer scrmblsSlvd,
                       Integer scrmblCrtd, Double avgCrtsSlvd) {
        this.username = usr;
        this.firstName = fName;
        this.lastName = lName;
        this.email = eml;
        this.numOfScramblesSolved = scrmblsSlvd;
        this.numOfScramblesCreated = scrmblCrtd;
        this.avgCreationsSolved = avgCrtsSlvd;
    }

}

package edu.gatech.seclass.sdpscramble;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * Created by youjungkim on 10/20/17.
 */

public class PlayerStatisticTest {
    MainMenuActivity mainMenuActivity = new MainMenuActivity();
    private List<List<String>> player_RES;
    private List<List<String>> scramble_RES;
    private ArrayList<PlayerTable> player_STAT;
    private List<List<String>> ewsPlayerList;
    private List<List<String>> ewsScrambleList;

    public PlayerStatisticTest(){

        //Testing data creation
        player_RES = new ArrayList<>();
        player_RES.add(new ArrayList(Arrays.asList("testUser1", "Test1", "User1", "TestUser1@lge.com")));
        player_RES.add(new ArrayList(Arrays.asList("testUser2","Test2","User2","TestUser2@lge.com","Scramble1","Scramble2","Scramble3")));
        player_RES.add(new ArrayList(Arrays.asList("testUser3","Test3","User3","TestUser3@lge.com","Scramble2","Scramble0")));
        player_RES.add(new ArrayList(Arrays.asList("testUser4","Test4","User4","TestUser4@lge.com","Scramble1","Scramble3")));

        scramble_RES = new ArrayList<>();
        scramble_RES.add(new ArrayList(Arrays.asList("Scramble0","Scramble0","Elbmarcs0","md","testUser1")));
        scramble_RES.add(new ArrayList(Arrays.asList("Scramble1","Scramble1","Elbmarcs1","md2","testUser2")));
        scramble_RES.add(new ArrayList(Arrays.asList("Scramble2","Scramble2","Elbmarcs2","md3","testUser1")));
        scramble_RES.add(new ArrayList(Arrays.asList("Scramble3","Scramble3","Elbmarcs3","md4","testUser1")));

        boolean blPlayer = mainMenuActivity.ews.initializePlayers(player_RES);
        boolean blScramble = mainMenuActivity.ews.initializeScramble(scramble_RES);

        ewsPlayerList = mainMenuActivity.ews.retrievePlayerListService();
        ewsScrambleList = mainMenuActivity.ews.retrieveScrambleService();
    }

   @Test
   public void retrieveScramblesCreated_ByRegisteredUser() {
       PlayerStatisticsActivity mMA = new PlayerStatisticsActivity();
       Assert.assertEquals(3,mMA.retrieveScramblesCreatedByUser(ewsScrambleList,"testUser1").size());
    }
    @Test
    public void retrieveScramblesCreated_ByUnregisteredUser() {
        PlayerStatisticsActivity mMA = new PlayerStatisticsActivity();
        Assert.assertEquals(0,mMA.retrieveScramblesCreatedByUser(ewsScrambleList,"testUser6").size());
    }
    @Test
    public void countNumberOfPlayerSolved_NotExistScrambles(){
        PlayerStatisticsActivity mMA = new PlayerStatisticsActivity();
        List<String> srambles = new ArrayList<String>(Arrays.asList("Scramble5","Scramble6"));
        Assert.assertEquals(0,mMA.countNumberOfPlayerSolved(srambles,ewsPlayerList));
    }
    @Test
    public void countNumberOfPlayerSolved_ExistScrambles(){
        PlayerStatisticsActivity mMA = new PlayerStatisticsActivity();
        List<String> srambles = new ArrayList<String>(Arrays.asList("Scramble1","Scramble9"));
        Assert.assertEquals(2,mMA.countNumberOfPlayerSolved(srambles,ewsPlayerList));
    }

}

package ca.mcmaster.se2aa4.island.team220;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// import java.util.LinkedList; // THIS IS FOR LATER 
// import java.util.Queue; // THIS IS FOR LATER

import org.json.JSONObject;

public class GridSearch implements IDecisionHandler {

    private final Logger logger = LogManager.getLogger(); // for logger instructions
    private Compass compass; // creates a compass

    private int count = 0; // helps run different actions in a round, gets 'reset'
    private int searchCount = 0; // keeps track of number of times searchSite() is run
    private String found = ""; // returns "GROUND" or "OUT_OF_RANGE", aka 'echo' results
    private int range = 0; // returns the range of the 'echo' result
    private String scanBiomes = ""; // returns the first biome found from 'scan' results 
    private String scanSites = ""; // returns the site if the site is found from 'scan' results 
    private boolean down = false; // determines whether the drone is facing upwards or downwards when it exits the island for intoPosition()

    private boolean findIslandMode = true; // runs findIsland() (we always start with this mode)
    private boolean changeHeading = false; // runs faceIsland()
    private boolean reachIslandMode = false; // runs reachIsland()
    private boolean searchSite = false; // runs searchSite()
    private boolean intoPosition = false; // runs intoPosition()
    private boolean interlaceTurnA = false; // runs interlaceTurnA()
    private boolean interlaceTurnB = false; // runs interlaceTurnB()
    private boolean interlaceTurnC1 = false; // runs interlaceTurnC1()
    private boolean interlaceTurnC2 = false; // runs interlaceTurnC2()


    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();

    boolean islandFound = false;
    boolean halfComplete = false;
    Translator translator = new Translator();

    @Override
    public void determineDecision(AreaMap map, Drone drone) {

    //--------------------------------------------------------------------------------------------------------
    }

    public void findIsland(){
       //called once in the beginning of the search
    } 

    public void faceIsland(){
        //called once in the beginning of the search
    }

    public boolean reachIsland(){
        return true;
        //only repeated if in interlaceB interlaceC1 interlaceC2
    }

    public int searchSite(){
        return 0;
        //always happens after reachIsland is called
    }

    public void intoPosition(){
        //always follows searchSite
    }

    public void interlaceA(){
        //always follows intoPosition
    }

    public void interlaceB(){
        //always happens after interlaceA, and after excecuted, depending on what echo observes we either go interlaceC1, interlaceC2, go back to reachIsland or we stop
        //if condtion is met at interlaceB we stop
    }

    public boolean interlaceC1(){
        return true;
        //occurs if the number of searchSitesCount is odd
    }

    //either call interlaceC1 or interlaceC2
    public boolean interlaceC2(){
        return true;
        //occurs if the number of searchSitesCount is even
    }

    public void executeGridSearch(Compass compass) {
        while 
    }


    
}

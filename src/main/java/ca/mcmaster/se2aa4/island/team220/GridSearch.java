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

    private boolean findIslandMode = true; // DELETE LATER: round 1 (we always start with this mode)
    private boolean changeHeading = false; // DELETE LATER: round 2
    private boolean reachIslandMode = false; // DELETE LATER: round 3
    private boolean searchSite = false; // DELETE LATER: round 4
    private boolean intoPosition = false; // DELETE LATER: round 5
    private boolean interlaceTurnA = false; // DELETE LATER: round 6
    private boolean interlaceTurnB = false; // DELETE LATER: round 6
    private boolean interlaceTurnC1 = false; // DELETE LATER: round 6
    private boolean interlaceTurnC2 = false; // DELETE LATER: round 6



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
        if (this.findIslandMode == true) {
            if (!(this.found).equals("GROUND")) { // while the island is not found
                logger.info(this.count); // total count = 106
                if (this.count % 4 == 0) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "S")); // echo right
                } else if (this.count % 4 == 1) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "N")); // echo left
                } else if (this.count % 4 == 2) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "E")); // echo straight
                } else if (this.count % 4 == 3) {
                    decision.put("action", "fly"); // fly
                }
                this.count++;
            } else {
                this.findIslandMode = false;
                this.changeHeading = true;
                logger.info("This is the final count: {}", (this.count-1));
                logger.info("THIS IS THE RANGE --------------------------------> {}", this.range); // 27 for map20
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 1 COMPLETE: findIsland Mode");
                logger.info("");
            }
        }
    } 

    public void faceIsland(){
        //called once in the beginning of the search
        if (this.changeHeading == true) {
            if ((this.count-1) % 4 == 0) {
                decision.put("action", "heading");
                decision.put("parameters", parameters.put("direction", compass.turnRight().toString())); // "S"
                this.count = 3;
            } else if ((this.count-1) % 4 == 1) {
                decision.put("action", "heading");
                decision.put("parameters", parameters.put("direction", "N"));
                this.count = 3;
            } else {
                this.changeHeading = false;
                this.reachIslandMode = true;
                decision.clear();
                this.count = 0;
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 2 COMPLETE: changeHeading Mode");
            }
        }
    }

    public boolean reachIsland(){
        //only repeated if in interlaceB interlaceC1 interlaceC2
        if (this.reachIslandMode == true) {
            if (!(this.scanBiomes).equals("BEACH")) { // condition for finding land
                logger.info(this.count); // count for scan and fly
                if (this.count % 2 == 0) {
                    decision.put("action", "scan");
                } else if (this.count % 2 == 1) {
                    decision.put("action", "fly");
                }
                this.count++;
            } else {
                this.reachIslandMode = false;
                this.searchSite = true;
                decision.clear();
                this.count = 0; // reset counter
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 3 COMPLETE: reachIsland Mode");
            }
        }
        return true;
    }

    public int searchSite(){
        //always happens after reachIsland is called
        if (this.searchSite == true) {
            if (!(this.scanBiomes).equals("OCEAN")) { // condition for finding land
                logger.info(this.count); // count for scan and fly
                if (this.count % 2 == 0) {
                    decision.put("action", "scan");
                } else if (this.count % 2 == 1) {
                    decision.put("action", "fly");
                }
                this.count++;
            } else {
                this.searchCount++;
                this.searchSite = false;
                this.intoPosition = true;
                decision.clear();
                this.count = 0; // reset counter
                // this.found = null; // MIGHT NOT NEED THIS GOTTA DOUBLE CHECK THE LOGIC UGH
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 4 COMPLETE: searchSite Mode");
            }
        }
        return 0;
    }

    public void intoPosition(){
        //always follows searchSite
        if (this.intoPosition == true) {
            if (!(this.found).equals("OUT_OF_RANGE")) { // while we don't 'echo' find OUT_OF_RANGE 
                logger.info(this.count);
                if (this.count % 2 == 0) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "E")); // echo East
                } else if (this.count % 2 == 1) {
                    decision.put("action", "fly");
                }
                this.count++;
            } else {
                if ((compass.getHeading().toString()).equals("S")) { // this.down is the initial direction of interlace turning
                    this.down = true;
                }
                this.intoPosition = false;
                this.interlaceTurnA = true;
                decision.clear();
                this.count = 0; // reset counter
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 5 COMPLETE: intoPosition Mode");
            }
        }
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

    public void executeGridSearch(){
        while 
    }


    
}

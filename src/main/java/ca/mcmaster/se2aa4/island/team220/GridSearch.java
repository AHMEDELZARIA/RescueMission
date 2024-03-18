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

    // private boolean findIslandMode = true; // runs findIsland() (we always start with this mode)
    // private boolean changeHeading = false; // runs faceIsland()
    // private boolean reachIslandMode = false; // runs reachIsland()
    // private boolean searchSite = false; // runs searchSite()
    // private boolean intoPosition = false; // runs intoPosition()
    // private boolean interlaceTurnA = false; // runs interlaceTurnA()
    // private boolean interlaceTurnB = false; // runs interlaceTurnB()
    // private boolean interlaceTurnC1 = false; // runs interlaceTurnC1()
    // private boolean interlaceTurnC2 = false; // runs interlaceTurnC2()

    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();

    boolean islandFound = false;
    boolean halfComplete = false;
    Translator translator = new Translator();

    @Override
    public void determineDecision(AreaMap map, Drone drone) {}

    public void findIsland() {
        //called once in the beginning of the search
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
            //this.findIslandMode = false;
            //this.changeHeading = true;
            logger.info("This is the final count: {}", (this.count-1));
            logger.info("THIS IS THE RANGE --------------------------------> {}", this.range); // 27 for map20
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 1 COMPLETE: findIsland Mode");
            logger.info("");
        }
    } 

    public void faceIsland(){
        //called once in the beginning of the search
        if ((this.count-1) % 4 == 0) {
            decision.put("action", "heading");
            decision.put("parameters", parameters.put("direction", compass.turnRight().toString())); // "S"
            this.count = 3;
        } else if ((this.count-1) % 4 == 1) {
            decision.put("action", "heading");
            decision.put("parameters", parameters.put("direction", "N"));
            this.count = 3;
        } else {
            //this.changeHeading = false;
            //this.reachIslandMode = true;
            decision.clear();
            this.count = 0;
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 2 COMPLETE: changeHeading Mode");
        }
    }

    public boolean reachIsland(){
        //only repeated if in interlaceB interlaceC1 interlaceC2
        if (!(this.scanBiomes).equals("BEACH")) { // condition for finding land
            logger.info(this.count); // count for scan and fly
            if (this.count % 2 == 0) {
                decision.put("action", "scan");
            } else if (this.count % 2 == 1) {
                decision.put("action", "fly");
            }
            this.count++;
        } else {
            //this.reachIslandMode = false;
            //this.searchSite = true;
            decision.clear();
            this.count = 0; // reset counter
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 3 COMPLETE: reachIsland Mode");
        }
        return true;
    }

    public int searchSite(){
        //always happens after reachIsland is called
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
            //this.searchSite = false;
            //this.intoPosition = true;
            decision.clear();
            this.count = 0; // reset counter
            // this.found = null; // MIGHT NOT NEED THIS GOTTA DOUBLE CHECK THE LOGIC UGH
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 4 COMPLETE: searchSite Mode");
        }
        return 0;
    }

    public void intoPosition(){
        //always follows searchSite
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
            //this.intoPosition = false;
            //this.interlaceTurnA = true;
            decision.clear();
            this.count = 0; // reset counter
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 5 COMPLETE: intoPosition Mode");
        }
    }

    public void interlaceA(){
        //always follows intoPosition
        if (this.count < 2) {
            if (this.down == true) {
                decision.put("action", "heading");
                decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
            } else { // if (this.down == false) 
                decision.put("action", "heading");
                decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
            }
            this.count++;
        } else {
            //this.interlaceTurnA = false;
            //this.interlaceTurnB = true;
            decision.clear();
            this.count = 0; // reset counter
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceA");
        }
    }

    public void interlaceB(){
        //always happens after interlaceA, and after excecuted, depending on what echo observes we either go interlaceC1, interlaceC2, go back to reachIsland or we stop
        //if condtion is met at interlaceB we stop
        if (this.count == 0) {
            if (this.down == false) { // reversed bc this.down is direction before all of interlaceTurn
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "S"));
            } else if (this.down == true) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "N"));
            }
            this.count++;
        } else {
            // decision.clear();
            // this.interlaceTurnB = false;
            logger.info(this.searchCount);
            if ((this.found).equals("GROUND")) {
                logger.info("christmas");
                // decision.put("action", "stop");
                // this.reachIslandMode = true;
            } else {
                decision.put("action", "stop");
                /* 
                if (this.searchCount % 2 == 1) {
                    this.interlaceTurnC1 = true; // CASE 1
                } else {
                    this.interlaceTurnC2 = true; // CASE 2
                }
                */
            }
            this.count = 0; // reset counter
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceB");
        }
    }

    public boolean interlaceC1(){
        //occurs if the number of searchSitesCount is odd
        if (this.count < 5) {
            if (this.down == true) {
                if (this.count % 5 == 3) {
                    decision.put("action", "fly");
                } else {
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
                }
            } else {
                if (this.count % 5 == 3) {
                    decision.put("action", "fly");
                } else {
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
                }
            }
            this.count++;
        } else {
            // this.interlaceTurnC1 = false;
            // decision.clear();
            // this.count = 0; // reset counter
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceC Case 1 COMPLETE");
        }
        return true;
    }

    //either call interlaceC1 or interlaceC2
    public boolean interlaceC2(){
        //occurs if the number of searchSitesCount is even
        if (this.count < 7) {
            if (this.down == true) {
                if (this.count % 7 >= 3 && this.count % 7 <= 5) {
                    decision.put("action", "fly");
                } else {
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
                }
            } else {
                if (this.count % 7 >= 3 && this.count % 7 <= 5) {
                    decision.put("action", "fly");
                } else {
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
                }
            }
            this.count++;
        } else {
            // this.interlaceTurnC2 = false;
            // decision.clear();
            // this.count = 0; // reset counter
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceC Case 2 COMPLETE");
        }
        return true; // change this is just a placeholder
    }

    public void executeGridSearch(Compass compass) {
        /* while condition: loops until this.range = out_of_range(from interlaceB) && if halfComplete == true (from interlaceC1 or interlaceC2)
         * 
         * 
         * 
         */
    }
}

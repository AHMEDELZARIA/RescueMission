package ca.mcmaster.se2aa4.island.team220;

import static org.junit.Assert.fail;

import java.io.StringReader;
import java.lang.reflect.GenericDeclaration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private Translator translator;
    private AreaMap map;
    // private Action action; // NEW CLASS ONLY IN THIS BRANCH

    private int count = 0;
    private int rangeCount = 0; // DELETE LATER
    private String found = ""; // DELETE LATER, for echo results
    private int range = 0; // DELETE LATER, for echo results
    private String scan = ""; // DELETE LATER
    private int count2 = 0; // DELETE LATER JUST A TEST
    private boolean headingDone = false;

    private boolean findIslandMode = true; // DELETE LATER, we always start with this mode
    private boolean reachIslandMode = false; // DELETE LATER, we do this mode after findIsland mode is complete (aka false)
    private boolean changeHeading = false;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject context = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",context.toString(2));

        map = new AreaMap();
        translator = new Translator();
        
        // Initialize the drone's heading and battery level
        Direction heading = Direction.toDirection(context.getString("heading"));
        Integer batteryLevel = context.getInt("budget");
        drone = new Drone(batteryLevel, heading);
        
        logger.info("The drone is facing {}", drone.getHeading());
        logger.info("Battery level is {}", drone.getBattery());
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        GridSearch search = new GridSearch(); // DELETE LATER
        Translator echoTime = new Translator(); // DELETE LATER

        // findIsland Mode
        if (this.findIslandMode == true) {
            if (!(this.found).equals("GROUND")) { // while the island is not found
                logger.info(this.count); // total count = 106
                if (this.count % 4 == 0) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "S")); // echo left
                } else if (this.count % 4 == 1) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "N")); // echo right
                } else if (this.count % 4 == 2) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "E")); // echo straight
                } else if (this.count % 4 == 3) {
                    decision.put("action", "fly"); // fly
                }
                this.count++;
            } else if ((this.found).equals("GROUND")) {
                // decision.put("action", "stop");
                this.findIslandMode = false;
                // this.reachIslandMode = true;
                this.changeHeading = true;
                logger.info("This is the final count: {}", (this.count-1));
                logger.info("THIS IS THE RANGE --------------------------------> {}", this.range);
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII we have reached the end of round 1");
                logger.info("");
            }
        }
        
        // changeHeading mode
        if (this.changeHeading == true) {
            if (this.headingDone == false) {
                if ((this.count-1) % 4 == 0) {
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", "S"));
                    this.headingDone = true;
                } else if ((this.count-1) % 4 == 1) {
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", "N"));
                    this.headingDone = true;
                }
            } else {
                this.changeHeading = false;
                this.reachIslandMode = true;
                decision.clear();
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII we have reached the end of round 2");
            }
        }
 
        if (this.reachIslandMode == true) {
            if (this.count2 < 52) {
                logger.info(this.count2); // count for scan and fly
                // add a condition for finding land
                if (this.count2 % 2 == 0) {
                    decision.put("action", "fly");
                    // this.count2++;
                } else if (this.count2 % 2 == 1) {
                    decision.put("action", "scan");
                    // this.count2++;
                }
                this.count2++;
            }
        }

        /* 
        if (this.findIslandMode == true) {
            if (!(this.found).equals("GROUND")) { // while the island is not found
                logger.info(this.count); // total count = 106
                if (this.count % 4 == 0) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "S")); // echo left
                } else if (this.count % 4 == 1) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "N")); // echo right
                } else if (this.count % 4 == 2) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "E")); // echo straight
                } else if (this.count % 4 == 3) {
                    decision.put("action", "fly"); // fly
                }
                this.count++;
        */

        /* 
        if (this.changeHeading == true && this.reachIslandMode == false) {
            decision.put("action", "scan");
        }
        */
            
            /* 
            else if (this.changeHeading == true && !(this.scan).equals("LAND")) {
                if (this.testCount % 2 == 0) {
                    decision.put("action", "fly");
                    this.testCount++;
                }
                if (this.testCount % 2 == 1) {
                    decision.put("action", "scan");
                    this.testCount++;
                }
            }
            */


            /* 
            if (this.rangeCount < 29 && this.testCount == 6) {
                decision.put("action", "fly");
                logger.info("THIS IS THE RANGE --------------------------------> {}", this.range);
                logger.info("THIS IS THE RANGECOUNT --------------------------------> {}", this.rangeCount);
                this.rangeCount++;
            } else if (this.rangeCount == 29 && this.testCount == 6) {
                decision.put("action", "scan");
                //decision.put("parameters", parameters.put("direction", "S"));
                this.reachIslandMode = false;
            }
            */
                

        // execute decision
        logger.info("** Decision: {}", decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        // found = s; // DELETE LATER
        logger.info (s); // DELETE LATER
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        
        // DELETE THIS LATER: ECHO EXTRACT
        if (extraInfo.has("found")) { // if extraInfo has information ('fly' will yield no info so we skip it)
            this.found = extraInfo.getString("found"); // (now this will contain either OUT_OF_RANGE or GROUND)
            this.range = extraInfo.getInt("range"); // we get the range of GROUND
        }

        // DELETE THIS LATER: SCAN EXTRACT
        if (extraInfo.has("biomes")) {
            logger.info("heck yeah");
            JSONArray smurf = extraInfo.getJSONArray("biomes");
            this.scan = smurf.getString(0);
            logger.info("-------------------------------------------> This is scan: {}", this.scan);
        }
        
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}

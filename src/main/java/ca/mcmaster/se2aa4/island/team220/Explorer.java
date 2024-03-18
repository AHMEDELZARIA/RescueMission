package ca.mcmaster.se2aa4.island.team220;

import java.io.StringReader;
import java.lang.reflect.GenericDeclaration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import scala.annotation.tailrec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private Translator translator;
    private AreaMap map;
    private Compass compass;
    // private Action action; // NEW CLASS ONLY IN THIS BRANCH

    // Direction straight = null;
    // Direction right = null;
    // Direction left = null;
    // String heading = ""; // holder for the heading (take from initial) 

    private int count = 0;
    private int searchCount = 0;
    private String found = ""; // DELETE LATER: findIsland, for echo results
    private int range = 0; // DELETE LATER: findIsland, for echo results
    private String scanBiomes = ""; // DELETE LATER: reachIsland, 
    private String scanSites = ""; // DELETE LATER: reachIsland, 
    private boolean headingDone = false;
    private boolean down = false; // for interlaceTurn

    private boolean findIslandMode = true; // DELETE LATER: round 1 (we always start with this mode)
    private boolean changeHeading = false; // DELETE LATER: round 2
    private boolean reachIslandMode = false; // DELETE LATER: round 3
    private boolean searchSite = false; // DELETE LATER: round 4
    private boolean intoPosition = false; // DELETE LATER: round 5
    private boolean interlaceTurnA = false; // DELETE LATER: round 6
    private boolean interlaceTurnB = false; // DELETE LATER: round 6
    private boolean interlaceTurnC = false; // DELETE LATER: round 6
    
    

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

        // Design Compass!
        compass = new Compass(heading); // ADDED
        // this.heading = heading.toString();

        // this.straight = compass.turnRight(); // DELETE LATER
        // this.right = compass.getHeading(); // DELETE LATER
        // this.left = compass.turnLeft(); // DELETE LATER
        // logger.info(this.straight.toString()); // DELETE LATER
        // logger.info(this.right.toString()); // DELETE LATER
        // logger.info(this.left.toString()); // DELETE LATER
        
        logger.info("The drone is facing {}", drone.getHeading());
        logger.info("Battery level is {}", drone.getBattery());
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        // findIsland Mode: FIND THE ISLAND
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
        
        // changeHeading mode: TURN TOWARDS THE ISLAND
        if (this.changeHeading == true) {
            if ((this.count-1) % 4 == 0) {
                decision.put("action", "heading");
                decision.put("parameters", parameters.put("direction", compass.turnRight().toString())); // "S"
                this.headingDone = true;
                this.count = 3;
            } else if ((this.count-1) % 4 == 1) {
                decision.put("action", "heading");
                decision.put("parameters", parameters.put("direction", "N"));
                this.headingDone = true;
                this.count = 3;
            } else {
                this.changeHeading = false;
                this.reachIslandMode = true;
                decision.clear();
                this.count = 0;
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 2 COMPLETE: changeHeading Mode");
            }
        }
 
        // reachIsland: REACH THE ISLAND
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

        //searchSite: FIND THE EMERGENCY SITE
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

        // intoPosition: get ready to U-turn
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

        // interlaceTurn TIME -> flip around (Segments A, B, C1, C2)
        
        // interlaceA
        if (this.interlaceTurnA == true) {
            // Segment A
            if (this.count < 2) {
                if (this.down == true) {
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
                    // this.count++;
                } else { // if (this.down == false) 
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
                    // this.count++;
                }
                this.count++;
            } else {
                this.interlaceTurnA = false;
                this.interlaceTurnB = true;
                decision.clear();
                this.count = 0; // reset counter
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceA");
            }
        }

        // Segment B
        if (this.interlaceTurnB == true) {
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
                this.interlaceTurnB = false;
                // this.interlaceTurnC = true; // TEST COMMAND
                /* 
                if (this.searchCount %2 == 1) {
                    this.interlaceTurnC1 = true; // CASE 1
                } else {
                    this.interlaceTurnC2 = true; // CASE 2
                }
                */
                decision.clear();
                this.count = 0; // reset counter
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceB");
            }
        }


        /* 
        if (this.interlaceTurnC == true) {
            if (!(this.found).equals("OUT_OF_RANGE") && this.count < 3) {
                // if (this.count < 3) {
                logger.info(this.count);
                if (this.count % 3 == 0) {
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
                    // compass.getHeading();
                } else if (this.count % 3 == 1) {
                    decision.clear();
                    decision.put("action", "fly");
                    // compass.getHeading();
                } else if (this.count % 3 == 2) {
                    logger.info("HUH");
                    decision.put("action", "heading");
                    decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
                }
                this.count++;
                // }
            } else {
                this.count = 3;
            }
            if (this.count == 3) {
                decision.clear();
                this.interlaceTurnC = false;
                this.count = 0; // reset counter
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6 COMPLETE: interlaceTurn Mode");
            }
        }
        */
  

            /* 
            // Segment C UPDATED
            if ((this.found).equals("OUT_OF_RANGE")) {
                logger.info("why doesz this work: {}", this.count);
                if (this.count < 6) {
                    if (this.count % 3 == 0) {
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
                    } else if (this.count % 3 == 1) {
                        decision.put("action", "fly");
                    } else if (this.count % 3 == 2) {
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
                    }
                    this.count++;
                }
            } else if (this.count == 3) {
                decision.clear();
                decision.put("action", "scan");
                this.interlaceTurn = false;
                // this.reachIslandMode = true;
                this.count = 0; // reset counter
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6 COMPLETE: interlaceTurn Mode");
            }
            */

            // ---------------------------------------------------------
            /* 
            // Segment C UPDATED
            if ((this.found).equals("OUT_OF_RANGE")) {
                logger.info("why doesz this work: {}", this.count);
                if (this.count < 6) {
                    if (this.count % 3 == 0) {
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
                    } else if (this.count % 3 == 1) {
                        decision.put("action", "fly");
                    } else if (this.count % 3 == 2) {
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
                    }
                    this.count++;
                }
            } else if (this.count == 3) {
                decision.clear();
                decision.put("action", "scan");
                this.interlaceTurn = false;
                // this.reachIslandMode = true;
                this.count = 0; // reset counter
                logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6 COMPLETE: interlaceTurn Mode");
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

        // DELETE THIS LATER: SCAN EXTRACT BIOMES
        if (extraInfo.has("biomes")) {
            logger.info("heck yeah");
            JSONArray biomes = extraInfo.getJSONArray("biomes");
            this.scanBiomes = biomes.getString(0);
            extraInfo.clear();
        }

        // DELETE THIS LATER: SCAN EXTRACT SITES (worry about creeks later)
        if (extraInfo.has("sites")) {
            JSONArray sites = extraInfo.getJSONArray("sites");
            this.scanSites = sites.getString(0);
            extraInfo.clear();
        }
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }
}

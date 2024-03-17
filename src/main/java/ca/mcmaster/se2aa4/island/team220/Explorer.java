package ca.mcmaster.se2aa4.island.team220;

import static org.junit.Assert.fail;

import java.io.StringReader;
import java.lang.reflect.GenericDeclaration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private Translator translator;
    private AreaMap map;
    // private Action action; // NEW CLASS ONLY IN THIS BRANCH

    private int count = 0;
    private String found = ""; // DELETE LATER, for echo results
    private int range = 0; // DELETE LATER, for echo results

    private boolean findIslandMode = true; // DELETE LATER, we always start with this mode
    private boolean reachIslandMode = false; // DELETE LATER, we do this mode after findIsland mode is complete (aka false)

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
                if (this.count % 2 == 0) {
                    decision.put("action", "echo");
                    decision.put("parameters", parameters.put("direction", "S"));
                    this.count++;
                }
                else if (this.count % 2 == 1) {
                    decision.put("action", "fly");
                    this.count++;
                }
            } else if ((this.found).equals("GROUND")) {
                decision.put("action", "stop");
                this.count = 0;
                this.findIslandMode = false;
                this.reachIslandMode = true;
            }
        }

        if (this.reachIslandMode == true) {
            logger.info("Time to reach the island!");
        }
                
        logger.info("** Decision: {}", decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        // found = s; // DELETE LATER
        // logger.info (s); // DELETE LATER
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        
        // DELETE THIS LATER
        if (extraInfo.has("found")) { // if extraInfo has information ('fly' will yield no info so we skip it)
            this.found = extraInfo.getString("found"); // (now this will contain either OUT_OF_RANGE or GROUND)
            this.range = extraInfo.getInt("range"); // we get the range of GROUND
        }
        
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}

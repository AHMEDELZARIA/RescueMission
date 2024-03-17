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
    private boolean islandFound = false; // DELETE LATER
    private String found; // DELETE LATER

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


        if (this.count % 2 == 0) { // && this.found != "GROUND"
            decision.put("action", "echo");
            decision.put("parameters", parameters.put("direction", "S"));
            this.count++;
        }
        else if (this.count % 2 == 1) {
            decision.put("action", "fly");
            this.count++;
        }
        logger.info("----------------------->", found);
        logger.info(this.count); // total fly count = 106
        
 

        // {"cost":6,"extras":{"found":"OUT_OF_RANGE","range":52},"status":"OK"}
        
       /* 
       if (this.count == 0) {
            decision.put("action", "scan");
            this.count++;
        } else if (this.count == 1 && this.islandFound == false) { // && this.islandFound == false
            decision.put("action", "scan");
            this.count++;
            //this.islandFound = true;
        } else if (this.count == 2 && this.islandFound == false) {
            decision.put("action", "echo");
            decision.put("parameters", parameters.put("direction", "E"));
            this.count++;
        } else if (this.count < 6) {
            decision.put("action", "fly");
            this.count++;
        }

        /*
        while (true) {
            if (islandFound == false) {
                decision.put("action", "fly");
                islandFound = false;
                break;
            } else {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "E"));
                islandFound = true;
                break;
            }
        }
        */


                
        logger.info("** Decision: {}", decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        // found = s; // DELETE LATER
        logger.info(s); // DELETE LATER
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        logger.info("-----------------------------------------------------TEST I");
        if (extraInfo.isNull("found")) {
            logger.info("------------------------------------ This works");
        }

        /* 
        if (extraInfo.isNull("found")) {
            found = extraInfo.getString("found");
        }
        // found = extraInfo.toString(); // DELETE LATER

        // String yeehaw = extraInfo.getString("found"); // DELETE LATER
        // found = yeehaw; // DELETE LATER
        // logger.info(found); // DELETE LATER
        */
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}

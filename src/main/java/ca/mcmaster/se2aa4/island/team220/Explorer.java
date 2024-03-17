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
    private boolean islandFound = false;

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
        GridSearch search = new GridSearch();


        
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

        while (islandFound == false) {
            if (this.count % 2 == 0) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "S"));
                
            }
            else if (this.count % 2 == 1) {
                decision.put("action", "fly");
            }
        }


        
       /* 
        while (this.count < 4) {
            if (this.count == 0) {
                decision.put("action", "scan");
                this.count++;
                break;
            } else if (this.count == 1) { // && this.islandFound == false
                decision.put("action", "fly");
                this.count++;
                break;
            } else if (this.count == 2) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "E"));
                this.count++;
                break;
            } else {
                decision.put("action", "scan");
                this.count++;
                break;
            }
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
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}

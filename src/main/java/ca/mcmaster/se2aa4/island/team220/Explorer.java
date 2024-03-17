package ca.mcmaster.se2aa4.island.team220;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import scala.annotation.tailrec;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private Translator translator;
    private AreaMap map;
    private ResponseProcessor results;
    private int count = 0;
    private String found = "";
    private int gridCount = 0;
    private boolean isFound = false;

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
    
        // echo to find land, fly, if echo finds land, change heading, fly, echo until you hit land, scan, gridmap 
        if (!(this.found).equals("GROUND")) {
            logger.info(this.count); // total fly count = like 106 idk lol
            if (this.count % 3 == 0) {
                decision.put("action", "scan");
                this.count++;
            } else if (this.count % 3 == 1) {
                decision.put("action", "fly");
                this.count++;
            } else {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "S"));
                this.count++;
            }
        } else {
            if (!isFound) {
                switch (gridCount) {
                    case 0:
                        decision.put("action", "scan");
                        gridCount++;
                        break;
                    case 1: 
                        decision.put("action", "echo");
                        decision.put("parameters", parameters.put("direction", "W"));
                        gridCount++;
                        break;
                    case 2: 
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", "W"));
                        gridCount++;
                        break;
                    case 3: 
                        decision.put("action", "echo");
                        decision.put("parameters", parameters.put("direction", "N")); 
                        gridCount++;
                        break;
                    case 4: 
                        if (this.found.equals("LAND")) {
                            gridCount = 0; 
                        }
                        break;
                    case 5: 
                        if (this.found.equals("OCEAN")) {
                            decision.put("action", "heading");
                            decision.put("parameters", parameters.put("direction", "E"));
                            gridCount = 6; 
                        }
                        break;
                    case 6: 
                        decision.put("action", "scan");
                        gridCount++;
                        break;
                    case 7:
                        decision.put("action", "echo");
                        decision.put("parameters", parameters.put("direction", "E"));
                        gridCount++;
                        break;
                    case 8: 
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", "E"));
                        gridCount++;
                        break;
                    default: 
                        gridCount = 0; 
                        break;
                }
            } else {
                decision.put("action", "stop");
            }
        }
    
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

        if (!extraInfo.isNull("found")) {
            this.found = extraInfo.getString("found");
        }
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}

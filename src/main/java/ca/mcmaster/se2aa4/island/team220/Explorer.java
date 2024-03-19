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
    private Compass compass;
    private CommandBook command; // ADDED 19/03

    private int count = 0;
    private String found = ""; // NEW

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject context = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", context.toString(2));

        map = new AreaMap();
        translator = new Translator();

        // Initialize the drone's heading and battery level
        Direction heading = Direction.toDirection(context.getString("heading"));
        Integer batteryLevel = context.getInt("budget");
        drone = new Drone(batteryLevel, heading);
        compass = new Compass(heading); // ADDED 19/03
        command = new CommandBook(heading); // ADDED 19/03

        logger.info("The drone is facing {}", drone.getHeading());
        logger.info("Battery level is {}", drone.getBattery());
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        // NEW
        if (!(this.found).equals("GROUND")) {
            logger.info(this.count); 
            if (this.count % 3 == 0) {
                // this.count++;
                // return command.getEchoSouth();
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "S"));
                this.count++;
            }
            //scans before drone flies
            else if (this.count % 3 == 1) {
                // decision.put("action", "scan");
                this.count++;
                logger.info(command.getScan());
                return command.getScan();
            } else{
                this.count++;
                logger.info(command.getFly());
                return command.getFly();
                // decision.put("action", "fly");
            }
        } else if ((this.found).equals("GROUND")) {
            // logger.info(command.getStop());
            // return command.getStop();
            decision.clear();
            logger.info(command.getTurnRight(compass));
            return command.getTurnRight(compass); // same as decision.put("action", "stop");
            // decision.put("action", "stop");
        }


        /*
        Original Code:
        while (this.count < 4) {
            if (this.count == 0) {
                decision.put("action", "scan");
                this.count++;
                break;
            } else if (this.count == 1) {
                decision.put("action", "fly");
                this.count++;
                break;
            } else if (this.count == 2) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "E"));
                this.count++;
                break;
            } else {
                decision.put("action", "stop");
                this.count++;
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
        logger.info("** Response received:\n" + response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        // NEW
        if (!extraInfo.isNull("found")) {
            this.found = extraInfo.getString("found");
        }
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }
}

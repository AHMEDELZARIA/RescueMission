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
        // JSONObject decision = new JSONObject();
        // JSONObject parameters = new JSONObject();

        // NEW
        if (!(this.found).equals("GROUND")) {
            logger.info(this.count); 
            if (this.count % 3 == 0) {
                this.count++;
                logger.info(command.getEchoSouth());
                return command.getEchoSouth();
            }
            //scans before drone flies
            else if (this.count % 3 == 1) {
                this.count++;
                logger.info(command.getScan());
                return command.getScan();
            } else{
                this.count++;
                logger.info(command.getFly());
                return command.getFly();
            }
        } else {
            logger.info(command.getStop());
            return command.getStop();
        }

        // logger.info("** Decision: {}", decision.toString());
        // return decision.toString();
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

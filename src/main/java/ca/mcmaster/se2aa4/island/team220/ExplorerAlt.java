package ca.mcmaster.se2aa4.island.team220;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xerces.impl.dv.xs.IntegerDV;

import eu.ace_design.island.bot.IExplorerRaid;
import scala.annotation.tailrec;

import org.json.JSONObject;
import org.json.JSONTokener;

public class ExplorerAlt implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;

    private InformationAlt results;
    private GridSearch search;

    private String decision;
    private Compass compass;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject context = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", context.toString(2));
        results = new InformationAlt(context.getInt("budget"), "OK"); // initialize budget(battery) and status (always starts 'OK') 
        search = new GridSearch();

        // Initialize the drone's heading and battery level
        Direction heading = Direction.toDirection(context.getString("heading"));
        Integer batteryLevel = context.getInt("budget");
        drone = new Drone(batteryLevel, heading);
        compass = new Compass(heading);

        logger.info("The drone is facing {}", drone.getHeading());
        logger.info("Battery level is {}", drone.getBattery());
    }

    @Override
    public String takeDecision() {
        this.decision = search.makeDecision(results.getFound(), results.getRange(), results.getBiome(), compass);
        logger.info("** Decision: {}", this.decision);
        return this.decision;
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

        results.setFound(extraInfo);
        results.setRange(extraInfo);
        results.setBiome(extraInfo);
        results.setSite(extraInfo);
        results.setCreek(extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        logger.info("This is the site: {}", results.getSite());
        logger.info("These are the creeks: {}", results.getCreeks());
        return results.getSite();
        // return "no creek found";
    }
}

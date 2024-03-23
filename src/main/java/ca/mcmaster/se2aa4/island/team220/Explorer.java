package ca.mcmaster.se2aa4.island.team220;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xerces.impl.dv.xs.IntegerDV;

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
    private Information results; // added 19/03
    private GridSearch search;

    private Integer count = 0;
    private String decision;
    // private Boolean droneStart = false;

    private Compass compass; // ADDED 20/03
    private CommandBook command; // ADDED 20/03

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject context = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", context.toString(2));

        map = new AreaMap();
        translator = new Translator();
        results = new Information(context.getInt("budget"), "OK"); // initialize budget(battery) and status (always starts 'OK') 
        search = new GridSearch();

        // Initialize the drone's heading and battery level
        Direction heading = Direction.toDirection(context.getString("heading"));
        Integer batteryLevel = context.getInt("budget");
        drone = new Drone(batteryLevel, heading);
        compass = new Compass(heading); // ADDED 19/03
        command = new CommandBook();

        logger.info("The drone is facing {}", drone.getHeading());
        logger.info("Battery level is {}", drone.getBattery());
    }

    @Override
    public String takeDecision() {

        logger.info(this.count);
        if (this.count < 6000) { //1608 // Map03: 2133 // Map10: 5046
            this.decision = search.makeDecision(results.getFound(), results.getRange(), results.getBiome(), compass);
            this.count++;
        } else {
            this.decision = command.getStop();
        }
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


        // NEW
        results.processFound(extraInfo);
        results.processRange(extraInfo);
        results.processBiome(extraInfo);
        results.processSite(extraInfo);
        results.processCreek(extraInfo);

        // old
        /*
        if (!extraInfo.isNull("found")) {
            results.setFound(extraInfo.getString("found"));
            logger.info(results.getFound());
        }
        */

        /*
        // FOR RANGE
        if (!extraInfo.isNull("range")) {
            results.setRange(extraInfo.getInt("range"));
            logger.info(results.getRange());
        }

        if (extraInfo.has("biomes")) {
            JSONArray biomes = extraInfo.getJSONArray("biomes");
            results.setBiome(biomes.getString(0));
            // loop until we can determine if the only biome is OCEAN. 
            for (int i = 1; i < biomes.length(); i++) {
                if (!biomes.getString(i).equals("OCEAN")) {
                    results.setBiome(biomes.getString(i));
                    break;
                }
            }
            logger.info(results.getBiome());
        }

        // SCAN EXTRACT SITES
        if (extraInfo.has("sites")) {
            if (!extraInfo.getJSONArray("sites").isNull(0)) {
                JSONArray sites = extraInfo.getJSONArray("sites");
                results.setSite(sites.getString(0));
                logger.info(results.getSite());
            }
        }
        
        // SCAN EXTRACT CREEKS
        if (extraInfo.has("creeks")) { // extraInfo.has("sites")
            if (!extraInfo.getJSONArray("creeks").isNull(0)) {
                JSONArray creek = extraInfo.getJSONArray("creeks");
                results.setCreek(creek.getString(0));
                logger.info(results.getCreeks());
            }
        }  
        */ 
    }

    @Override
    public String deliverFinalReport() {
        logger.info("This is the site: {}", results.getSite());
        logger.info("These are the creeks: {}", results.getCreeks());
        return results.getSite();
        // return "no creek found";
    }
}

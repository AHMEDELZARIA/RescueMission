package ca.mcmaster.se2aa4.island.team220;

import java.io.StringReader;
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
    private Information results; // added 19/03
    private GridSearch search;

    private int count = 0;
    private String decision;

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
        // this.count++;
        logger.info(this.count);

        // BAD NEWS: FOR SOME REASON THIS IS NOT WORKING FOR MORE THAN 200 ACTIONS
        // GOOD NEWS: IT'S NOT A BATTERY ISSUE, SO THERE'S STILL HOPE
        // IDEA: REVIEW THE STATE SWITCH METHOD FOR ERRORS + THE MAP
        // LOL PSYCHE I KNOW THE ANSWER ITS BECAUSE I DIDNT ACCOUNT FOR THIS.DOWN == FALSE, FIX THAT TOMORROW :^D

        // PROBLEM 2: INTERLACE C NEVER GETS REACHED
        // SOLUTION?: UHH IDK LOL BUT WE'LL FIGURE IT OUT >:^]
        //if ((results.getSite()).equals("N/A")) {}

        if (count < 831) { //853
            this.decision = search.makeDecision(results.getFound(), results.getBiome(), compass);
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
        if (!extraInfo.isNull("found")) {
            results.setFound(extraInfo.getString("found"));
            logger.info("-------------------------------------------- {}", results.getFound());
        }

        if (extraInfo.has("biomes")) {
            JSONArray biomes = extraInfo.getJSONArray("biomes");
            results.setBiome(biomes.getString(0));
            logger.info(results.getBiome());
            // extraInfo.clear();
        }

        // SCAN EXTRACT SITES (worry about creeks later)
        if (extraInfo.has("sites")) { // extraInfo.has("sites")
            if (!extraInfo.getJSONArray("sites").isNull(0)) {
                logger.info("YO THE SITE");
                JSONArray sites = extraInfo.getJSONArray("sites");
                results.setSite(sites.getString(0));
                logger.info(results.getSite());
                // extraInfo.clear();
            }
        }
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }
}

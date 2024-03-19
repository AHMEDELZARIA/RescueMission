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
    private Compass compass;
    private CommandBook command; // ADDED 19/03
    private Information results; // added 19/03

    private int count = 0;
    private String decision;
    // private String found = "";
    // private String scanBiomes = "";
    // private String scanSites = ""; 

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject context = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", context.toString(2));

        map = new AreaMap();
        translator = new Translator();
        results = new Information(context.getInt("budget"), "OK"); // initialize budget/battery and status (always starts off 'OK') 

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
        // String decision;

        // NEW
        if (!(results.getFound()).equals("GROUND")) { // FIX IF DOESN'T WORK
            logger.info(this.count);
            if (this.count % 3 == 0) {
                this.count++;
                this.decision = command.getEchoSouth();
            } else if (this.count % 3 == 1) { // scans before drone flies
                this.count++;
                this.decision = command.getScan();
            } else {
                this.count++;
                this.decision = command.getFly();
            }
        } else {
            this.decision = command.getStop();
        }
        
        logger.info("** Decision: {}", this.decision);
        return this.decision;

        //attempt to implement queue
        //CALL CLASS (GridQueue queue = new GridQueue();)
        // String nextDecision;

        /* if (!(results.getFound()).equals("GROUND")) 
         *      if (queue.isEmpty()) 
         *          queue.enqueue(command.getEchoSouth());
         *          queue.enqueue(command.getScan());
         *          queue.enqueue(command.getFly());
         *      
         *     nextDecision = queue.dequeue(); 
         * else
         *     nextDecision = command.getStop();
         * 
         * 
         * logger.info(nextDecision);
         * return nextDecision;
         * 
         */
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
            // this.found = extraInfo.getString("found");
            results.setFound(extraInfo.getString("found"));
            logger.info(results.getFound());
        }

        if (extraInfo.has("biomes")) {
            JSONArray biomes = extraInfo.getJSONArray("biomes");
            // this.scanBiomes = biomes.getString(0);
            
            results.setBiome(biomes.getString(0));
            logger.info(results.getBiome());
            extraInfo.clear();
        }

        // SCAN EXTRACT SITES (worry about creeks later)
        if (extraInfo.has("sites")) {
            JSONArray sites = extraInfo.getJSONArray("sites");
            // this.scanSites = sites.getString(0);

            results.setSite(sites.getString(0));
            logger.info(results.getSite());
            extraInfo.clear();
        }
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }
}

package ca.mcmaster.se2aa4.island.team220;

import java.io.StringReader;

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
    private DecisionHandler decisionHandler;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject context = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", context.toString(2));


        this.translator = new Translator();
        this.decisionHandler = new DecisionHandler();

        // Initialize the drone's heading and battery level
        Direction heading = Direction.toDirection(context.getString("heading"));
        Integer batteryLevel = context.getInt("budget");
        this.drone = new Drone(batteryLevel, heading);
        this.decisionHandler.setStartHeading(heading);
        this.map = new AreaMap(drone);

        logger.info("Decision Made was {}", drone.echoForward());
        logger.info("The drone is facing {}", drone.getHeading());
        logger.info("Battery level is {}", drone.getBattery());
    }

    @Override
    public String takeDecision() {
        String decision = decisionHandler.makeDecision(this.drone, this.map);
        logger.info("** Decision: {}", decision.toString());
        return decision;
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

        Information translated_response = this.translator.translateDecision(this.decisionHandler.getActionTaken(),
                response);
        this.map.update(translated_response);
        this.drone.updateBattery(translated_response.getCost());
    }

    @Override
    public String deliverFinalReport() {
        //logger.info(map.printMap());
        logger.info("THIS IS THE CLOSEST CREEK: {}", map.getClosestCreek());
        return "YOOOOOOOOOOOOOOOOOOOOOOO";
    }
}

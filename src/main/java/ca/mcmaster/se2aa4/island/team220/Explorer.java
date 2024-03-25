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
        JSONObject context = new JSONObject(new JSONTokener(new StringReader(s)));
        this.translator = new Translator();
        this.decisionHandler = new DecisionHandler();

        // Initialize the drone's heading and battery level
        Direction heading = Direction.toDirection(context.getString("heading"));
        Integer batteryLevel = context.getInt("budget");
        this.drone = new Drone(batteryLevel, heading);
        this.map = new AreaMap(drone);
    }

    @Override
    public String takeDecision() {
        return decisionHandler.makeDecision(this.drone, this.map);
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        Information translated_response = this.translator.translateDecision(this.decisionHandler.getActionTaken(),
                response);
        this.map.update(translated_response);
        this.drone.updateBattery(translated_response.getCost());
    }

    @Override
    public String deliverFinalReport() {
        logger.info("THIS IS THE CLOSEST CREEK: {}", map.getClosestCreek());
        return this.map.getClosestCreek();
    }
}

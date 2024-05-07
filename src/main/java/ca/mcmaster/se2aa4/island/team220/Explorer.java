package ca.mcmaster.se2aa4.island.team220;

import java.io.StringReader;

import ca.mcmaster.se2aa4.island.team220.drone.Direction;
import ca.mcmaster.se2aa4.island.team220.drone.Drone;
import ca.mcmaster.se2aa4.island.team220.map.AreaMap;
import ca.mcmaster.se2aa4.island.team220.statemachine.DecisionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {
    private final Logger logger = LogManager.getLogger();

    private Drone drone; // Drone used for the exploration
    private Translator translator; // Responsible for parsing the results from decisions
    private AreaMap map; // Maps the key exploration of the drone throughout the grid
    private DecisionHandler decisionHandler; // Takes care of deciding what decisions are to be made

    /**
     * Initializes the explorer mission.
     * @param s String holding the context of the mission
     */
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

    /**
     * Performs decisions to be made during the exploration
     * @return String JSON representation of the decision made
     */
    @Override
    public String takeDecision() {
        return decisionHandler.makeDecision(this.drone, this.map);
    }

    /**
     * Parses the results from the decision made and updates the map and drone accordingly
     * @param s String holding the results of the decision
     */
    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        Information translated_response = this.translator.translateDecision(this.decisionHandler.getActionTaken(),
                response);
        this.map.update(translated_response);
        this.drone.updateBattery(translated_response.getCost());
    }

    /**
     * Final results from the exploration. Returns the closest creek to the emergency site if available.
     * @return String id of the closest creek to the emergency site
     */
    @Override
    public String deliverFinalReport() {
        logger.info("CLOSEST CREEK: {}", this.map.getClosestCreek());
        return this.map.getClosestCreek();
    }
}

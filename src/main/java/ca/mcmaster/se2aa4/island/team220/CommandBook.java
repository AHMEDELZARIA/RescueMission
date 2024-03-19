package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import org.json.JSONObject;

public class CommandBook {

    // private Direction heading;
    // private Compass compass;

    private HashMap<Action, JSONObject> command;
    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();

    JSONObject action = new JSONObject();
     

    public CommandBook(Compass compass) {
        this.command = new HashMap<>();
        // buildStop();
        buildFly();
        buildStop();
        buildScan();
        buildEchoNorth();
        buildEchoEast();
        buildEchoSouth();
        buildEchoWest();
        buildHeadingLeft(compass);
        buildHeadingRight(compass);
        // this.command.put("stop", decision);
    }

    public void buildAction(Action action, String direction) { // Action are the enums
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", action.toString().toLowerCase()); // ex. Action.STOP becomes 'stop'

        if (direction != null && !direction.isEmpty()) { // for echo and heading
            parameters.put("direction", direction);
            decision.put("parameters", parameters);
        }

        this.command.put(action, decision);
    }

    public JSONObject getStop() {
        JSONObject stopAction = command.get(Action.STOP);
        return stopAction;
    }

    public void updateHeadingLeft(Compass compass) {
        decision.clear();
        compass.getHeading();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
        this.command.put(Action.HEADING_LEFT, decision);
    }
    
    public void updateHeadingRight(Compass compass) {
        decision.clear();
        compass.getHeading();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
        this.command.put(Action.HEADING_RIGHT, decision);
    }

    public void buildStop() {
        decision.clear();
        this.decision.put("action", "stop");
        this.command.put(Action.STOP, decision);
        // return command.get(Action.STOP);
    }
    public void buildFly() {
        decision.clear();
        this.decision.put("action", "fly");
        this.command.put(Action.FLY, decision);
    }

    public void buildScan() {
        decision.clear();
        this.decision.put("action", "scan");
        this.command.put(Action.SCAN, decision);
    }

    public void buildEchoNorth() {
        decision.clear();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "N"));
        this.command.put(Action.ECHO_NORTH, decision);
    }

    public void buildEchoEast() {
        decision.clear();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "E"));
        this.command.put(Action.ECHO_EAST, decision);
    }
    
    public void buildEchoSouth() {
        decision.clear();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "S"));
        this.command.put(Action.ECHO_SOUTH, decision);
    }

    public void buildEchoWest() {
        decision.clear();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "W"));
        this.command.put(Action.ECHO_WEST, decision);
    }

    public void buildHeadingLeft(Compass compass) {
        decision.clear();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
        this.command.put(Action.HEADING_LEFT, decision);
    }

    public void buildHeadingRight(Compass compass) {
        decision.clear();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
        this.command.put(Action.HEADING_RIGHT, decision);
    }
}

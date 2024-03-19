package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import org.json.JSONObject;

public class CommandBook {

    // private Direction heading;
    // private Compass compass;

    private HashMap<Action, JSONObject> command;
    JSONObject decision = new JSONObject(); // DELETE LATER
    JSONObject parameters = new JSONObject(); // DELETE LATER

    // JSONObject action = new JSONObject();
    
    public CommandBook(Compass compass) {
        this.command = new HashMap<>();
        buildAction(Action.STOP, null);
        buildAction(Action.FLY, null);
        buildAction(Action.SCAN, null);
        buildAction(Action.ECHO_NORTH, "N");
        buildAction(Action.ECHO_EAST, "E");
        buildAction(Action.ECHO_SOUTH, "S");
        buildAction(Action.ECHO_WEST, "W");
        buildAction(Action.HEADING_LEFT, compass.turnLeft().toString());
        buildAction(Action.HEADING_RIGHT, compass.turnRight().toString());
    }

    public JSONObject getStop() {
        return command.get(Action.STOP);
    }

    public JSONObject getFly() {
        return command.get(Action.FLY);
    }
    
    public JSONObject getScan() {
        return command.get(Action.SCAN);
    }

    public JSONObject getEchoNorth() {
        return command.get(Action.ECHO_NORTH);
    }

    public JSONObject getEchoEast() {
        return command.get(Action.ECHO_EAST);
    }

    public JSONObject getEchoSouth() {
        return command.get(Action.ECHO_SOUTH);
    }

    public JSONObject getEchoWest() {
        return command.get(Action.ECHO_WEST);
    }

    public JSONObject getHeadingLeft(Compass compass) {
        updateHeadingLeft(compass);
        return command.get(Action.HEADING_LEFT);
    }

    public JSONObject getHeadingRight(Compass compass) {
        updateHeadingRight(compass);
        return command.get(Action.HEADING_RIGHT);
    }

    public void updateHeadingLeft(Compass compass) {
        // compass.getHeading();
        // decision.put("action", "echo");
        // decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
        // this.command.put(Action.HEADING_LEFT, decision);
        buildAction(Action.HEADING_LEFT, compass.turnLeft().toString());
    }
    
    public void updateHeadingRight(Compass compass) {
        // compass.getHeading();
        // decision.put("action", "echo");
        // decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
        // this.command.put(Action.HEADING_RIGHT, decision);
        buildAction(Action.HEADING_RIGHT, compass.turnRight().toString());
    }

    public void buildAction(Action action, String direction) { // Action are the enums
        JSONObject decision = new JSONObject();
        decision.put("action", action.toString().toLowerCase()); // ex. Action.STOP becomes 'stop'

        if (direction != null && !direction.isEmpty()) { // for echo and heading
            JSONObject parameters = new JSONObject();
            parameters.put("direction", direction);
            decision.put("parameters", parameters);
        }

        this.command.put(action, decision);
    }
}

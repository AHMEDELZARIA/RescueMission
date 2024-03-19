package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import org.json.JSONObject;

public class CommandBook {

    private HashMap<Action, JSONObject> command;
    
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

    public JSONObject getStop() { return command.get(Action.STOP); }

    public JSONObject getFly() { return command.get(Action.FLY); }
    
    public JSONObject getScan() { return command.get(Action.SCAN); }

    public JSONObject getEchoNorth() { return command.get(Action.ECHO_NORTH); }

    public JSONObject getEchoEast() { return command.get(Action.ECHO_EAST); }

    public JSONObject getEchoSouth() { return command.get(Action.ECHO_SOUTH); }

    public JSONObject getEchoWest() { return command.get(Action.ECHO_WEST); }

    public JSONObject getHeadingLeft() { return command.get(Action.HEADING_LEFT); }

    public JSONObject getHeadingRight() { return command.get(Action.HEADING_RIGHT); }

    public void updateHeadingLeft(Compass compass) { buildAction(Action.HEADING_LEFT, compass.turnLeft().toString()); } // MIGHT NOT NEED
    
    public void updateHeadingRight(Compass compass) { buildAction(Action.HEADING_RIGHT, compass.turnRight().toString()); } // MIGHT NOT NEED

    public void buildAction(Action action, String direction) { // Action are the enums
        JSONObject decision = new JSONObject();

        if (action == Action.ECHO_EAST)


        decision.put("action", action.toString().toLowerCase()); // ex. Action.STOP becomes 'stop'

        if (direction != null && !direction.isEmpty()) { // for echo and heading
            JSONObject parameters = new JSONObject();
            parameters.put("direction", direction);
            decision.put("parameters", parameters);
        }

        this.command.put(action, decision); // putting decision into the HashMap, an ex. of action (the key) is Action.STOP
    }
}

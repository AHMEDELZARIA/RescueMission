package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import org.json.JSONObject;

public class CommandBook {

    // private HashMap<Action, JSONObject> command;
    private Compass compass;
    
    public CommandBook(Direction heading) {
        this.compass = new Compass(heading);
        // this.command = new HashMap<>();
        // buildAction(Action.STOP, null);
        // buildAction(Action.FLY, null);
        // buildAction(Action.SCAN, null);
        // buildAction(Action.ECHO_NORTH, "N");
        // buildAction(Action.ECHO_EAST, "E");
        // buildAction(Action.ECHO_SOUTH, "S");
        // buildAction(Action.ECHO_WEST, "W");
        // buildAction(Action.HEADING_LEFT, compass.turnLeft().toString());
        // buildAction(Action.HEADING_RIGHT, compass.turnRight().toString());
    }

    public String getStop() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop");
        return decision.toString();
    }

    public String getFly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");
        return decision.toString();
    }
    
    public String getScan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");
        return decision.toString();
    }

    public String getEchoNorth() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", 'N'));
        // parameters.clear();

        return decision.toString();
    }

    public String getEchoEast() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", 'E'));

        return decision.toString();
    }

    public String getEchoSouth() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", Direction.SOUTH.toString())); // 'S'

        return decision.toString();
    }

    public String EchoWest() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", 'W'));

        return decision.toString();
    }

    public String getTurnLeft(Compass compass) {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "fly");
        decision.put("parameters", parameters.put("direction", this.compass.turnLeft().toString()));

        return decision.toString();
    }

    public String getTurnRight(Compass compass) {
        compass.getHeading();

        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "fly");
        decision.put("parameters", parameters.put("direction", this.compass.turnRight().toString()));

        return decision.toString();
    }

    /* 
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
    */
}

/*

public class Drone {

    private Integer battery;
    private Compass compass;

    public Drone(Integer battery, Direction heading) {
        this.battery = battery;
        this.compass = new Compass(heading);
    }

    public Integer getBattery() {
        return this.battery;
    }

    public Direction getHeading() {
        return this.compass.getHeading();
    }
}
*/
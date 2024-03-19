package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import org.json.JSONObject;

public class CommandBook {

    // private HashMap<Action, JSONObject> command;
    private Compass compass;
    
    public CommandBook(Direction heading) {
        this.compass = new Compass(heading);
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
        decision.put("parameters", parameters.put("direction", Direction.NORTH.toString()));
        return decision.toString();
    }

    public String getEchoEast() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", Direction.EAST.toString()));
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
        decision.put("parameters", parameters.put("direction", Direction.WEST.toString()));
        return decision.toString();
    }

    public String getTurnLeft(Compass compass) {
        compass.getHeading();
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
package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import org.json.JSONObject;

public class CommandBook {

    private Action action;
    private Direction heading;
    private Compass compass;

    private HashMap<Action, JSONObject> command;
    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();
     

    public CommandBook() {

        this.command = new HashMap<>();
        decision.put("action", "stop");
        // this.command.put("stop", decision);
    }


    public void updateHeadingLeft() {
        compass.getHeading();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
        this.command.put(Action.HEADING_LEFT, decision);
    }
    
    public void updateHeadingRight() {
        compass.getHeading();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
        this.command.put(Action.HEADING_RIGHT, decision);
    }

    public void buildStop() {
        this.decision.put("action", "stop");
        this.command.put(Action.STOP, decision);
        // return command.get(Action.STOP);
    }
    public void buildFly() {
        this.decision.put("action", "fly");
        this.command.put(Action.FLY, decision);
    }

    public void buildScan() {
        this.decision.put("action", "scan");
        this.command.put(Action.SCAN, decision);
    }

    public void buildEchoNorth() {
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "N"));
        this.command.put(Action.ECHO_NORTH, decision);
    }

    public void buildEchoEast() {
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "N"));
        this.command.put(Action.ECHO_EAST, decision);
    }
    
    public void buildEchoSouth() {
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "N"));
        this.command.put(Action.ECHO_SOUTH, decision);
    }

    public void buildEchoWest() {
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "N"));
        this.command.put(Action.ECHO_WEST, decision);
    }

    public void buildHeadingLeft() {
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
        this.command.put(Action.HEADING_LEFT, decision);
    }

    public void buildHeadingRight() {
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
        this.command.put(Action.HEADING_RIGHT, decision);
    }
    
    
}

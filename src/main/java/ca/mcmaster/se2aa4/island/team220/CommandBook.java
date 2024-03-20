package ca.mcmaster.se2aa4.island.team220;

// import java.util.HashMap;
import org.json.JSONObject;

public class CommandBook {

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
        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
        return decision.toString();
    }

    public String getTurnRight(Compass compass) {
        compass.getHeading();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        decision.put("action", "fly");
        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
        return decision.toString();
    }
}
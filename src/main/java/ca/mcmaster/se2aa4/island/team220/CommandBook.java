package ca.mcmaster.se2aa4.island.team220;

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

    public String getEchoForward(Compass compass) {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.getHeading().toString()));
        return decision.toString();
    }

    public String getEchoLeft(Compass compass) {
        compass.getHeading();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
        compass.turnRight();
        return decision.toString();
    }

    public String getEchoRight(Compass compass) {
        compass.getHeading();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
        compass.turnLeft();
        return decision.toString();
    }

    public String getTurnLeft(Compass compass) {
        compass.getHeading();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        decision.put("action", "heading");
        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
        return decision.toString();
    }

    public String getTurnRight(Compass compass) {
        compass.getHeading();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();
        decision.put("action", "heading");
        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
        return decision.toString();
    }
}
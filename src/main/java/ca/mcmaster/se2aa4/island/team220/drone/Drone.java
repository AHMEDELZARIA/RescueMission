package ca.mcmaster.se2aa4.island.team220.drone;

import org.json.JSONObject;

public class Drone {

    private Integer battery;
    private Compass compass;

    /**
     * Creates a Drone.
     * @param battery Integer value of the initial battery amount
     * @param heading Direction the Drone will initially face
     */
    public Drone(Integer battery, Direction heading) {
        this.battery = battery;
        this.compass = new Compass(heading);
    }

    public Integer getBattery() { return this.battery; }

    public void updateBattery(Integer drain) { this.battery -= drain; }

    public Direction getHeading() { return this.compass.getHeading(); }

    public Direction getInitialHeading() { return this.compass.getInitialHeading(); }

    public Direction getPrevHeading() { return  this.compass.getPrevHeading(); }

    /**
     * Send an echo to the right of the drone.
     * @return String JSON representation of the echo right instruction
     */
    public String echoRight() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "" + this.compass.getRight()));

        return decision.toString();
    }

    /**
     * Send an echo to the left of the drone.
     * @return String JSON representation of the echo left instruction
     */
    public String echoLeft() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "" + this.compass.getLeft()));

        return decision.toString();
    }

    /**
     * Send an echo to the forward of the drone.
     * @return String JSON representation of the echo forward instruction
     */
    public String echoForward() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "echo");
        decision.put("parameters", parameters.put("direction", "" + this.getHeading()));

        return decision.toString();
    }

    /**
     * Scans the MapTile below the Drone.
     * @return String JSON representation of the scan instruction
     */
    public String scan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");
        return decision.toString();
    }

    /**
     * Drone flies forward.
     * @return String JSON representation of the echo right instruction
     */
    public String fly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");
        this.compass.updatePrevHeading();
        return decision.toString();
    }

    /**
     * Drone turns left.
     * @return String JSON representation of the turn left instruction
     */
    public String turnLeft() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "heading");
        decision.put("parameters", parameters.put("direction", "" + this.compass.turnLeft()));

        return decision.toString();
    }

    /**
     * Drone turns right.
     * @return String JSON representation of the turn right instruction
     */
    public String turnRight() {
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        decision.put("action", "heading");
        decision.put("parameters", parameters.put("direction", "" + this.compass.turnRight()));

        return decision.toString();
    }

    /**
     * Drone stops and returns back home.
     * @return String JSON representation of the stop instruction
     */
    public String stop() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop");
        return decision.toString();
    }
}

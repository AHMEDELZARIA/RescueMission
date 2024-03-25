package ca.mcmaster.se2aa4.island.team220;

/**
 * State machine, following the State Pattern, that facilitates the decision-making process of the Drone exploration
 */
public class DecisionHandler {
    private State currentState;
    /*Threshold obtained by experiments to which the drone is taken to the opposite end of the map and commanded to stop
    Experiment was run multiple times and the cost of this action was averaged. This is the worst case scenario threshold.*/
    private Integer batteryThresh = 100;
    private Actions actionTaken;
    private Direction startHeading;

    /**
     * Create a DecisionHandler which begins at the InitialXState
     */
    public DecisionHandler() { this.currentState = new InitialXState(); }

    public void setState(State state) { this.currentState = state; }

    public void setActionTaken(Actions action) { this.actionTaken = action; }

    public Actions getActionTaken() { return this.actionTaken; }

    public String makeDecision(Drone drone, AreaMap map) {
        if (drone.getBattery() < batteryThresh) {
            setState(new StopState());
        }
        return this.currentState.handle(drone, map, this);
    }
}

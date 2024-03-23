package ca.mcmaster.se2aa4.island.team220;

public class DecisionHandler {
    private State currentState;
    private Integer batteryThresh = 100;
    private Actions actionTaken;
    private TurnDirection startCorner;
    private Direction startHeading;

    public DecisionHandler() {
        this.currentState = new StartState();
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void setActionTaken(Actions action) {
        this.actionTaken = action;
    }

    public Actions getActionTaken() {
        return this.actionTaken;
    }

//    public Integer getBatteryThresh() {
//        return this.batteryThresh;
//    }

    public void setStartCorner(TurnDirection corner) {
        this.startCorner = corner;
    }

    public TurnDirection getStartCorner() {
        return this.startCorner;
    }

    public void setStartHeading(Direction startHeading) {
        this.startHeading = startHeading;
    }

    public Direction getStartHeading() {
        return this.startHeading;
    }

    public String makeDecision(Drone drone, AreaMap map) {
        if (drone.getBattery() < batteryThresh) {
            setState(new StopState());
        }
        return this.currentState.handle(drone, map, this);
    }
}

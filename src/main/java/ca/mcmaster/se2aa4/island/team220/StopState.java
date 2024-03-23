package ca.mcmaster.se2aa4.island.team220;

public class StopState implements State {
    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        decisionHandler.setActionTaken(Actions.STOP);
        return drone.stop();
    }
}
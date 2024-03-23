package ca.mcmaster.se2aa4.island.team220;

public interface State {
    String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler);
}

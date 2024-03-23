package ca.mcmaster.se2aa4.island.team220;

public class SearchIslandState implements State {
    private ISearchAlgorithm technique = new EfficientSearch();

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        return technique.searchArea(drone, map, decisionHandler);
    }
}
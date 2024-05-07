package ca.mcmaster.se2aa4.island.team220.statemachine;

import ca.mcmaster.se2aa4.island.team220.drone.Drone;
import ca.mcmaster.se2aa4.island.team220.map.AreaMap;

/**
 * State responsible for searching the island.
 */
public class SearchIslandState implements State {
    // To use a different searching algorithm, simply swap out the implementation below
    private final ISearchAlgorithm technique = new EfficientSearch();

    /**
     * Handles searching the island.
     * @param drone Drone that is exploring the island
     * @param map AreaMap which maps the exploration of the drone on the grid
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action taken
     */
    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        return technique.searchArea(drone, map, decisionHandler);
    }
}
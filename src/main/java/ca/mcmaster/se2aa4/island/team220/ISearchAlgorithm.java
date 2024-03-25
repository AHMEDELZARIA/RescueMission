package ca.mcmaster.se2aa4.island.team220;

/**
 * Interface which offers the service to search the Island of the grid.
 */
public interface ISearchAlgorithm {
    /**
     * Searches the area of the island, performing the necessary actions.
     * @param drone Drone which is exploring a grid
     * @param map AreaMap which maps the exploration of the drone on the grid
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action performed
     */
    String searchArea(Drone drone, AreaMap map, DecisionHandler decisionHandler);
}

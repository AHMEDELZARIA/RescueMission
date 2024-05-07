package ca.mcmaster.se2aa4.island.team220.statemachine;

import ca.mcmaster.se2aa4.island.team220.drone.Drone;
import ca.mcmaster.se2aa4.island.team220.map.AreaMap;

/**
 * State that skips over a body of water that is not to be scanned.
 */
public class SkipWaterState implements State {
    private Integer numMoves; // Number of skips to be made
    private Integer iteration = 0;

    /**
     * Create a SkipWaterState.
     * @param numMoves Integer number representing the number of skips to be made
     */
    public SkipWaterState(Integer numMoves) { this.numMoves = numMoves; }

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            // Scan the first block to handle the edge case of missing a creak along the shore
            this.iteration++;
            decisionHandler.setActionTaken(Actions.SCAN);
            return drone.scan();
        } if (this.iteration < this.numMoves) {
            // Continue flying until you have made the right number of skips
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.fly();
        } else {
            // Scan the last block to handle the edge case of missing a creek along the shore
            decisionHandler.setActionTaken(Actions.SCAN);
            decisionHandler.setState(new SearchIslandState());
            return drone.scan();
        }
    }

}
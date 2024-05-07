package ca.mcmaster.se2aa4.island.team220.statemachine;

import ca.mcmaster.se2aa4.island.team220.drone.Drone;
import ca.mcmaster.se2aa4.island.team220.map.AreaMap;

/**
 * State which moves the drone forward on the island. A substate of SearchIslandState.
 */
public class MoveForwardOnIslandState implements State {
    private Integer iteration = 0;

    /**
     * Handles moving the drone forward on the island.
     * @param drone Drone which is performing the exploration
     * @param map AreaMap which maps the drone's exploration on the grid
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action performed
     */
    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            // First fly forward
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.fly();
        } else if (this.iteration == 1) {
            // Then scan to reveal MapTile
            this.iteration++;
            decisionHandler.setActionTaken(Actions.SCAN);
            return drone.scan();
        } else {
            // Then transition back to SearchIslandState to determine next search state
            decisionHandler.setState(new SearchIslandState());
            return decisionHandler.makeDecision(drone, map);
        }
    }
}

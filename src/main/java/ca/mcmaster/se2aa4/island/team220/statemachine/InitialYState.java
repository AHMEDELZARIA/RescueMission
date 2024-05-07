package ca.mcmaster.se2aa4.island.team220.statemachine;

import ca.mcmaster.se2aa4.island.team220.drone.Drone;
import ca.mcmaster.se2aa4.island.team220.map.AreaMap;
import ca.mcmaster.se2aa4.island.team220.map.MapTerrain;

/**
 * State which handles finding the nearest corner's Y-coordinate.
 */
public class InitialYState implements State {
    private Integer iteration = 0;
    private TurnDirection turnDir;

    /**
     * Create a state to find the nearest Y-coordinate. Indicate whether it is the left or right corner
     * @param turnDir indicates which corner, relative to the drone, was reached
     */
    public InitialYState(TurnDirection turnDir) {
        this.turnDir = turnDir;
    }

    /**
     * Handles how to get to the Island's nearest corner, assuming InitialXState was already encountered
     * @param drone Drone that is exploring the grid
     * @param map AreaMap that maps the exploration of the drone on the grid
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action taken
     */
    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        return switch (this.turnDir) {
            case RIGHT -> { yield handleRight(drone, map, decisionHandler); }
            case LEFT -> { yield handleLeft(drone, map, decisionHandler); }
        } ;
    }

    /**
     * Handles the right corner case.
     * Continue echoing to the left until LAND is returned. Finds the Y-coordinate of the nearest corner of the island.
     * @param drone Drone which performs the search on the grid
     * @param map Represents the explored area on the grid being explored
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action performed
     */
    private String handleRight(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration % 2 == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHOLEFT);
            return drone.echoLeft();
        } else {
            this.iteration++;
            if (map.getLeft() != MapTerrain.LAND) {
                decisionHandler.setActionTaken(Actions.FLY);
                return drone.fly();
            } else {
                decisionHandler.setActionTaken(Actions.TURNLEFT);
                decisionHandler.setState(new SearchIslandState());
                return drone.turnLeft();
            }
        }
    }

    /**
     * Handles the left corner case.
     * Continue echoing to the right until LAND is returned. Finds the Y-coordinate of the nearest corner of the island.
     * @param drone Drone which performs the search on the grid
     * @param map Represents the explored area on the grid being explored
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action performed
     */
    private String handleLeft(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        // Alternate between echoing to the right and flying
        if (this.iteration % 2 == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHORIGHT);
            return drone.echoRight();
        } else {
            this.iteration++;
            // Check if echo returned LAND
            if (map.getRight() != MapTerrain.LAND) {
                // If not, continue flying
                decisionHandler.setActionTaken(Actions.FLY);
                return drone.fly();
            }

            // If so, turn right and transition to the next state
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            decisionHandler.setState(new SearchIslandState());
            return drone.turnRight();
        }
    }
}
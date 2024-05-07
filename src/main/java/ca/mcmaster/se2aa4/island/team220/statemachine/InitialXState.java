package ca.mcmaster.se2aa4.island.team220.statemachine;

import ca.mcmaster.se2aa4.island.team220.drone.Drone;
import ca.mcmaster.se2aa4.island.team220.map.AreaMap;
import ca.mcmaster.se2aa4.island.team220.map.MapTerrain;

/**
 * State that determines the closest X-coordinate of the nearest corner of the island to the start position.
 */
public class InitialXState implements State {
    private Integer iteration = 0;
    private TurnDirection turnDir;

    /**
     * If water is straight ahead, then move to handleWaterAheadCase. If land is straight ahead, move to
     * handleLandAheadCase. Both will handle navigating the drone the X-coordinate of the nearest corner of the island.
     * @param drone Drone which is exploring a grid
     * @param map The grid which is being explored
     * @param decisionHandler The state machine facilitator
     * @return String JSON Representation of the action taken
     */
    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHOFORWARD);
            return drone.echoForward();
        } else {
            if (map.getForward() == MapTerrain.LAND) {
                return handleLandAheadCase(drone, map, decisionHandler);
            } else {
                return handleWaterAheadCase(drone, map, decisionHandler);
            }
        }
    }

    /**
     * Handles the case where land is ahead, aka the island.
     * @param drone Drone which is exploring a grid
     * @param map The grid which is being explored
     * @param decisionHandler The state machine facilitator
     * @return String JSON Representation of the action taken
     */
    private String handleLandAheadCase(Drone drone, AreaMap map, DecisionHandler decisionHandler) {

        switch (this.iteration) {
            case (1):
                // Check distance to the left
                this.iteration++;
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
            case (2):
                // Check distance to the right
                this.iteration++;
                decisionHandler.setActionTaken(Actions.ECHORIGHT);
                return drone.echoRight();
            case (3):
                // Determine which distance is smaller and turn that direction
                Integer leftDistance = map.getLeftRange();
                Integer rightDistance = map.getRightRange();

                this.iteration++;
                if (leftDistance <= rightDistance) {
                    this.turnDir = TurnDirection.LEFT;
                    decisionHandler.setActionTaken(Actions.TURNLEFT);
                    return drone.turnLeft();
                }
                this.turnDir = TurnDirection.RIGHT;
                decisionHandler.setActionTaken(Actions.TURNRIGHT);
                return drone.turnRight();
            case (4):
                // Echo to the opposite direction to which the drone turned
                this.iteration++;
                if (this.turnDir == TurnDirection.LEFT) {
                    decisionHandler.setActionTaken(Actions.ECHORIGHT);
                    return drone.echoRight();
                }
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
            default:
                // Alternate between echoing and flying
                // Fly case
                if (this.iteration % 2 == 0) {
                    this.iteration++;
                    // If turn direction was left
                    if (this.turnDir == TurnDirection.LEFT) {
                        // Check if the echo returned OUTOFBOUNDS
                        if (map.getRight() != MapTerrain.OUTOFBOUNDS) {
                            // If not, continue flying ahead
                            decisionHandler.setActionTaken(Actions.FLY);
                            return drone.fly();
                        }

                        // If it did, turn right towards the island and transition to next state
                        decisionHandler.setActionTaken(Actions.TURNRIGHT);
                        decisionHandler.setState(new InitialYState(TurnDirection.LEFT));
                        return drone.turnRight();
                    }

                    // Same, but opposite, process if turn direction was right
                    if (map.getLeft() != MapTerrain.OUTOFBOUNDS) {
                        decisionHandler.setActionTaken(Actions.FLY);
                        return drone.fly();
                    }
                    decisionHandler.setActionTaken(Actions.TURNLEFT);
                    decisionHandler.setState(new InitialYState(TurnDirection.RIGHT));
                    return drone.turnLeft();
                }

                // Echo case
                this.iteration++;
                // Echo to the opposite direction the drone turned
                if (this.turnDir == TurnDirection.RIGHT) {
                    decisionHandler.setActionTaken(Actions.ECHOLEFT);
                    return drone.echoLeft();
                }
                decisionHandler.setActionTaken(Actions.ECHORIGHT);
                return drone.echoRight();
        }
    }

    /**
     * Handles the case where water is straight ahead, aka OUTOFBOUNDS.
     * @param drone Drone which is exploring a grid
     * @param map The grid which is being explored
     * @param decisionHandler The state machine facilitator
     * @return String JSON Representation of the action taken
     */
    private String handleWaterAheadCase(Drone drone, AreaMap map, DecisionHandler decisionHandler) {

        switch (this.iteration) {
            case (1):
                // Check distance to the left
                this.iteration++;
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
            case (2):
                // Check distance to the right
                this.iteration++;
                decisionHandler.setActionTaken(Actions.ECHORIGHT);
                return drone.echoRight();
            case (3):
                // Turn towards the longer distance of the two
                Integer leftDistance = map.getLeftRange();
                Integer rightDistance = map.getRightRange();

                this.iteration++;
                if (leftDistance <= rightDistance) {
                    this.turnDir = TurnDirection.RIGHT;
                    decisionHandler.setActionTaken(Actions.TURNRIGHT);
                    return drone.turnRight();
                }
                this.turnDir = TurnDirection.LEFT;
                decisionHandler.setActionTaken(Actions.TURNLEFT);
                return drone.turnLeft();
            case (4):
                // Echo to the opposite direction of turn
                this.iteration++;
                if (this.turnDir == TurnDirection.LEFT) {
                    decisionHandler.setActionTaken(Actions.ECHORIGHT);
                    return drone.echoRight();
                }
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
            default:
                // Alternate between echoing and flying
                // Fly case
                if (this.iteration % 2 == 0) {
                    this.iteration++;
                    // If turn direction is left
                    if (this.turnDir == TurnDirection.LEFT) {
                        // Check echo to the right
                        if (map.getRight() != MapTerrain.LAND) {
                            // Continue flying if LAND wasn't returned
                            decisionHandler.setActionTaken(Actions.FLY);
                            return drone.fly();
                        }

                        // If it was returned, turn right and transition to next state
                        decisionHandler.setActionTaken(Actions.TURNRIGHT);
                        decisionHandler.setState(new InitialYState(TurnDirection.RIGHT));
                        return drone.turnRight();
                    }

                    // Same, but opposite, process if the turn direction was right
                    if (map.getLeft() != MapTerrain.LAND) {
                        decisionHandler.setActionTaken(Actions.FLY);
                        return drone.fly();
                    }
                    decisionHandler.setActionTaken(Actions.TURNLEFT);
                    decisionHandler.setState(new InitialYState(TurnDirection.LEFT));
                    return drone.turnLeft();
                }

                // Echo case
                this.iteration++;
                // Echo to the opposite direction of turn
                if (this.turnDir == TurnDirection.LEFT) {
                    decisionHandler.setActionTaken(Actions.ECHORIGHT);
                    return drone.echoRight();
                }
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
        }
    }
}
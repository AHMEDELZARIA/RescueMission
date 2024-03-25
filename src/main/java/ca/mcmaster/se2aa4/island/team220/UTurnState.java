package ca.mcmaster.se2aa4.island.team220;

/**
 * State that makes the Drone perform a U-turn
 */
public class UTurnState implements State {
    private Integer iteration = 0;
    private Direction heading;
    private Direction startHeading;

    /**
     * Create a UTurnState
     * @param heading Direction Drone is going
     * @param startHeading Direction of Drone at the beginning of the exploration
     */
    public UTurnState(Direction heading, Direction startHeading) {
        this.heading = heading;
        this.startHeading = startHeading;
    }

    /**
     * Determines which way the drone must go and takes care of performing a U-turn towards that way
     * @param drone Drone which is exploring the grid
     * @param map AreaMap that maps the exploration of the drone on the grid
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action performed
     */
    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        // Based on the startheading and current heading, U-turn right or left
        switch (this.startHeading) {
            case NORTH:
                if (this.heading == Direction.WEST) { return handleRight(drone, map, decisionHandler);
                } else { return handleLeft(drone, map, decisionHandler); }
            case SOUTH:
                if (this.heading == Direction.WEST) { return handleLeft(drone, map, decisionHandler); }
                else { return handleRight(drone, map, decisionHandler); }
            case EAST:
                if (this.heading == Direction.NORTH) { return handleRight(drone, map, decisionHandler);
                } else { return handleLeft(drone, map, decisionHandler); }
            case WEST:
                if (this.heading == Direction.NORTH) { return handleLeft(drone, map, decisionHandler);
                } else { return handleRight(drone, map, decisionHandler); }
        }
        return null;
    }

    /**
     * Performs a right U-turn
     * @param drone Drone which is exploring the grid
     * @param map AreaMap that maps the exploration of the drone on the grid
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action performed
     */
    public String handleRight(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        // Sequence of actions to make a U-turn right
        if (this.iteration == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNLEFT);
            return drone.turnLeft();
        } else if (this.iteration == 1) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.fly();
        } else if (this.iteration == 2 || this.iteration == 3 || this.iteration == 4) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNLEFT);
            return drone.turnLeft();
        } else if (this.iteration == 5 || this.iteration == 6) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            return drone.turnRight();
        } else if (this.iteration == 7) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHOFORWARD);
            return drone.echoForward();
        } else {
            if (map.getForward() == MapTerrain.OUTOFBOUNDS) {
                decisionHandler.setState(new StopState());
            } else {
                decisionHandler.setState(new SearchIslandState());
            }

            // Scan last part to handle edge case
            decisionHandler.setActionTaken(Actions.SCAN);
            return drone.scan();
        }
    }

    /**
     * Performs a left U-turn
     * @param drone Drone which is exploring the grid
     * @param map AreaMap that maps the exploration of the drone on the grid
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the action performed
     */
    public String handleLeft(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            return drone.turnRight();
        } else if (this.iteration == 1) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.fly();
        } else if (this.iteration == 2 || this.iteration == 3 || this.iteration == 4) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            return drone.turnRight();
        } else if (this.iteration == 5 || this.iteration == 6) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNLEFT);
            return drone.turnLeft();
        } else if (this.iteration == 7) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHOFORWARD);
            return drone.echoForward();
        } else {
            if (map.getForward() == MapTerrain.OUTOFBOUNDS) {
                decisionHandler.setState(new StopState());
            } else {
                decisionHandler.setState(new SearchIslandState());
            }

            // Scan last part to handle edge case
            decisionHandler.setActionTaken(Actions.SCAN);
            return drone.scan();
        }
    }

}
package ca.mcmaster.se2aa4.island.team220;

public class GetReadyToTurnState implements State {
    private Integer iteration = 0;
    private Direction startHeading;
    private Direction heading;
    private TurnDirection turnDir;

    /**
     * Create a GetReadyToTurn state
     * @param heading Direction of current heading
     * @param startHeading Direction of start heading
     */
    public GetReadyToTurnState(Direction heading, Direction startHeading) {
        this.heading = heading;
        this.startHeading = startHeading;
        this.turnDir = detTurnDir();
    }

    /**
     * Keep moving forward until the echo to the turn direction returns a range to ground > 0
     * @param drone Drone which is exploring
     * @param map AreaMap being explored
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of action taken
     */
    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {

        if (iteration % 2 == 0) {
            // First echo to the turn direction
            iteration++;
            return echoToTurnDir(turnDir, drone, decisionHandler);

        } else {
            // Transition to a U-turn state if echo to turn direction returns a range > 0
            iteration++;
            if (turnDirEchoAmount(turnDir, map) > 0) {
                decisionHandler.setState(new UTurnState(drone.getHeading(), drone.getInitialHeading()));
                return decisionHandler.makeDecision(drone, map);
            } else {
                decisionHandler.setActionTaken(Actions.FLY);
                return drone.fly();
            }

        }
    }

    /**
     * Returns the echo range to the turn direction.
     * @param turnDir Direction to turn
     * @param map AreaMap being explored
     * @return Range of echo
     */
    private Integer turnDirEchoAmount(TurnDirection turnDir, AreaMap map) {
        return switch (turnDir) {
            case LEFT -> map.getLeftRange();
            case RIGHT -> map.getRightRange();
        };
    }

    /**
     * Determines the direction to turn for the U-turn.
     * @return Direction to turn
     */
    private TurnDirection detTurnDir() {
        switch (this.startHeading) {
            case NORTH:
                if (this.heading == Direction.WEST) { return TurnDirection.RIGHT;
                } else { return TurnDirection.LEFT; }
            case SOUTH:
                if (this.heading == Direction.WEST) { return TurnDirection.LEFT; }
                else { return TurnDirection.RIGHT; }
            case EAST:
                if (this.heading == Direction.NORTH) { return TurnDirection.RIGHT;
                } else { return TurnDirection.LEFT; }
            case WEST:
                if (this.heading == Direction.NORTH) { return TurnDirection.LEFT;
                } else { return TurnDirection.RIGHT; }
            default:
                throw new IllegalStateException("Unexpected value: " + this.startHeading);
        }
    }

    /**
     * Echos to the turn direction.
     * @param turnDir Direction to turn
     * @param drone Drone that will turn
     * @param decisionHandler state machine facilitator
     * @return String JSON representation of the echo instruction
     */
    private String echoToTurnDir(TurnDirection turnDir, Drone drone, DecisionHandler decisionHandler) {
        return switch (turnDir) {
            case LEFT -> {
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                yield drone.echoLeft();
            }
            case RIGHT -> {
                decisionHandler.setActionTaken(Actions.ECHORIGHT);
                yield drone.echoRight();
            }
        };
    }
}
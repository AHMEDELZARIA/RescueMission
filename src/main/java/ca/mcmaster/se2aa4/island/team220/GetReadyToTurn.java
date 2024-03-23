package ca.mcmaster.se2aa4.island.team220;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetReadyToTurn implements State {
    private Direction heading;
    private Direction startHeading;
    private Integer iteration = 0;
    private TurnDirection turnDir;
    private final Logger logger = LogManager.getLogger();


    public GetReadyToTurn(Direction heading, Direction startHeading) {
        this.heading = heading;
        this.startHeading = startHeading;
        this.turnDir = detTurnDir();
    }

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (iteration % 2 == 0) {
            iteration++;
            return echoToTurnDir(turnDir, drone);
        } else {
            iteration++;
            if (checkTurnDirEchoAmount(turnDir, map) > 0) {
                decisionHandler.setState(new UTurnState(drone.getHeading(), decisionHandler.getStartHeading()));
                return decisionHandler.makeDecision(drone, map);
            } else {
                logger.info("HEYYYYYY I AM HEREEEE");
                return drone.fly();
            }
        }
    }

    private Integer checkTurnDirEchoAmount(TurnDirection turnDir, AreaMap map) {
        switch (turnDir) {
            case LEFT -> {
                return map.getLeftAmount();
            }
            case RIGHT -> {
                return map.getRightAmount();
            }
        }
        return null;
    }

    private String echoToTurnDir(TurnDirection turnDir, Drone drone) {
        switch (turnDir) {
            case LEFT -> {
                return drone.echoLeft();
            }
            case RIGHT -> {
                return drone.echoRight();
            }
        }
        return null;
    }

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
        }
        return null;
    }

}

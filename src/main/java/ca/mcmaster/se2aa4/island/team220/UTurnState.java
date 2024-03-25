package ca.mcmaster.se2aa4.island.team220;

public class UTurnState implements State {
    private Integer iteration = 0;
    private Direction heading;
    private Direction startHeading;

    public UTurnState(Direction heading, Direction startHeading) {
        this.heading = heading;
        this.startHeading = startHeading;
    }

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {

        switch (this.startHeading) {
            case NORTH:
                if (this.heading == Direction.WEST) { return handleNorth(drone, map, decisionHandler);
                } else { return handleSouth(drone, map, decisionHandler); }
            case SOUTH:
                if (this.heading == Direction.WEST) { return handleSouth(drone, map, decisionHandler); }
                else { return handleNorth(drone, map, decisionHandler); }
            case EAST:
                if (this.heading == Direction.NORTH) { return handleNorth(drone, map, decisionHandler);
                } else { return handleSouth(drone, map, decisionHandler); }
            case WEST:
                if (this.heading == Direction.NORTH) { return handleSouth(drone, map, decisionHandler);
                } else { return handleNorth(drone, map, decisionHandler); }
        }

        return null;
    }
    public String handleNorth(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNLEFT);
            return drone.turnLeft();
        } else if (this.iteration == 1) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.fly();
        } else if (this.iteration == 2) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNLEFT);
            return drone.turnLeft();
        } else if (this.iteration == 3) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNLEFT);
            return drone.turnLeft();
        } else if (this.iteration == 4) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNLEFT);
            return drone.turnLeft();
        } else if (this.iteration == 5) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            return drone.turnRight();
        } else if (this.iteration == 6) {
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

            decisionHandler.setActionTaken(Actions.SCAN);
            return drone.scan();
        }
    }

    public String handleSouth(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            return drone.turnRight();
        } else if (this.iteration == 1) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.fly();
        } else if (this.iteration == 2) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            return drone.turnRight();
        } else if (this.iteration == 3) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            return drone.turnRight();
        } else if (this.iteration == 4) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            return drone.turnRight();
        } else if (this.iteration == 5) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.TURNLEFT);
            return drone.turnLeft();
        } else if (this.iteration == 6) {
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

            decisionHandler.setActionTaken(Actions.SCAN);
            return drone.scan();
        }
    }

}
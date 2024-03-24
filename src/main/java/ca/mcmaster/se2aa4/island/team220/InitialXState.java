package ca.mcmaster.se2aa4.island.team220;

public class InitialXState implements State {
    private Integer iteration = 0;
    private Direction turnDir;

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (map.getForward() == MapFeature.LAND) {
            return handleLandAheadCase(drone, map, decisionHandler);
        } else {
            return handleWaterAheadCase(drone, map, decisionHandler);
        }
    }

    public String handleLandAheadCase(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        switch (this.iteration) {
            case (0):
                this.iteration++;
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
            case (1):
                this.iteration++;
                decisionHandler.setActionTaken(Actions.ECHORIGHT);
                return drone.echoRight();
            case (2):
                Integer leftDistance = map.getLeftAmount();
                Integer rightDistance = map.getRightAmount();

                this.iteration++;
                if (leftDistance <= rightDistance) {
                    this.turnDir = Direction.WEST;
                    decisionHandler.setActionTaken(Actions.TURNLEFT);
                    //decisionHandler.setStartCorner(StartCorner.LEFT);
                    return drone.turnLeft();
                }
                this.turnDir = Direction.EAST;
                //decisionHandler.setStartCorner(StartCorner.RIGHT);
                decisionHandler.setActionTaken(Actions.TURNRIGHT);
                return drone.turnRight();
            case (3):
                this.iteration++;
                if (this.turnDir == Direction.WEST) {
                    decisionHandler.setActionTaken(Actions.ECHORIGHT);
                    return drone.echoRight();
                }
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
            default:
                if (this.iteration % 2 == 0) {
                    this.iteration++;
                    if (this.turnDir == Direction.WEST) {
                        if (map.getRight() != MapFeature.OUTOFBOUNDS) {
                            decisionHandler.setActionTaken(Actions.FLY);
                            return drone.fly();
                        }

                        decisionHandler.setActionTaken(Actions.TURNRIGHT);
                        decisionHandler.setState(new InitialYLeftState());
                        return drone.turnRight();
                    }

                    if (map.getLeft() != MapFeature.OUTOFBOUNDS) {
                        decisionHandler.setActionTaken(Actions.FLY);
                        return drone.fly();
                    }
                    decisionHandler.setActionTaken(Actions.TURNLEFT);
                    decisionHandler.setState(new InitialYRightState());
                    return drone.turnLeft();
                }

                this.iteration++;
                if (this.turnDir == Direction.WEST) {
                    decisionHandler.setActionTaken(Actions.ECHORIGHT);
                    return drone.echoRight();
                }
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
        }
    }

    public String handleWaterAheadCase(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        switch (this.iteration) {
            case (0):
                this.iteration++;
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
            case (1):
                this.iteration++;
                decisionHandler.setActionTaken(Actions.ECHORIGHT);
                return drone.echoRight();
            case (2):
                Integer leftDistance = map.getLeftAmount();
                Integer rightDistance = map.getRightAmount();

                this.iteration++;
                if (leftDistance <= rightDistance) {
                    this.turnDir = Direction.EAST;
                    decisionHandler.setStartCorner(TurnDirection.LEFT);
                    decisionHandler.setActionTaken(Actions.TURNRIGHT);
                    return drone.turnRight();
                }
                this.turnDir = Direction.WEST;
                decisionHandler.setStartCorner(TurnDirection.RIGHT);
                decisionHandler.setActionTaken(Actions.TURNLEFT);
                return drone.turnLeft();
            case (3):
                this.iteration++;
                if (this.turnDir == Direction.WEST) {
                    decisionHandler.setActionTaken(Actions.ECHORIGHT);
                    return drone.echoRight();
                }
                decisionHandler.setActionTaken(Actions.ECHOLEFT);
                return drone.echoLeft();
            default:
                if (this.iteration % 3 == 0) {
                    this.iteration++;
                    if (this.turnDir == Direction.WEST) {
                        if (map.getRight() != MapFeature.LAND) {
                            decisionHandler.setActionTaken(Actions.FLY);
                            return drone.fly();
                        }

                        decisionHandler.setActionTaken(Actions.TURNRIGHT);
                        decisionHandler.setState(new InitialYRightState());
                        return drone.turnRight();
                    }

                    if (map.getLeft() != MapFeature.LAND) {
                        decisionHandler.setActionTaken(Actions.FLY);
                        return drone.fly();
                    }
                    decisionHandler.setActionTaken(Actions.TURNLEFT);
                    decisionHandler.setState(new InitialYLeftState());
                    return drone.turnLeft();
                } else if (this.iteration % 3 == 1) {
                    this.iteration++;
                    if (this.turnDir == Direction.WEST) {
                        decisionHandler.setActionTaken(Actions.ECHORIGHT);
                        return drone.echoRight();
                    }
                    decisionHandler.setActionTaken(Actions.ECHOLEFT);
                    return drone.echoLeft();
                } else {
                    this.iteration++;
                    decisionHandler.setActionTaken(Actions.SCAN);
                    return drone.scan();
                }

        }
    }
}
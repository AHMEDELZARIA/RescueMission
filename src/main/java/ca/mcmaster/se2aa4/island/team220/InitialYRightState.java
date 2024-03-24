package ca.mcmaster.se2aa4.island.team220;

public class InitialYRightState implements State {
    private Integer iteration = 0;

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration % 2 == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHOLEFT);
            return drone.echoLeft();
        } else {
            this.iteration++;
            if (map.getLeft() != MapFeature.LAND) {
                decisionHandler.setActionTaken(Actions.FLY);
                return drone.fly();
            } else {
                decisionHandler.setActionTaken(Actions.TURNLEFT);
                decisionHandler.setState(new SearchIslandState());
                return drone.turnLeft();
            }
        }
    }
}
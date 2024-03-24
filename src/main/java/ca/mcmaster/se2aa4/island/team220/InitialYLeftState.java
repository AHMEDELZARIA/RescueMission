package ca.mcmaster.se2aa4.island.team220;

public class InitialYLeftState implements State {
    private Integer iteration = 0;

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration % 2 == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHORIGHT);
            return drone.echoRight();
        } else {
            this.iteration++;
            if (map.getRight() != MapFeature.LAND) {
                decisionHandler.setActionTaken(Actions.FLY);
                return drone.fly();
            }

            decisionHandler.setActionTaken(Actions.TURNRIGHT);
            decisionHandler.setState(new SearchIslandState());
            return drone.turnRight();
        }
    }
}
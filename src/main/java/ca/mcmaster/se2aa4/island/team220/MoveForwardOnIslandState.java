package ca.mcmaster.se2aa4.island.team220;

public class MoveForwardOnIslandState implements State {
    private Integer iteration = 0;

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.fly();
        } else if (this.iteration == 1) {
            this.iteration++;
        } else {
            decisionHandler.setState(new SearchIslandState());
        }

        decisionHandler.setActionTaken(Actions.SCAN);
        return drone.scan();
    }
}

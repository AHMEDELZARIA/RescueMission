package ca.mcmaster.se2aa4.island.team220;

public class StartState implements State {
    private Integer iteration = 0;

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHOFORWARD);
            return drone.echoForward();
        }

        decisionHandler.setActionTaken(Actions.SCAN);
        decisionHandler.setState(new InitialXState());
        return drone.scan();
    }
}
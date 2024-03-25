package ca.mcmaster.se2aa4.island.team220;

/**
 * Stop state of the drone exploration.
 */
public class StopState implements State {
    /**
     * Stops the drone exploration by performing a stop action.
     * @param drone Drone which is exploring the grid
     * @param map AreaMap which maps the exploration of the drone on the grid
     * @param decisionHandler State machine facilitator
     * @return String JSON representation of the stop action
     */
    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        decisionHandler.setActionTaken(Actions.STOP);
        return drone.stop();
    }
}
package ca.mcmaster.se2aa4.island.team220;

/**
 * An Island Search algorithm. Efficiently scans only relevant parts of the map.
 */
public class EfficientSearch implements ISearchAlgorithm {
    private Integer iteration = 0;
    private Integer LandTraversalIteration = 0;

    /**
     * First echo forward. Then check the range returned. If the range is Ground in 0, this indicates Drone
     * is on Island, transition to MoveForwardOnIslandState. If the range is Ground in > 0, this indicates a
     * gap of water that can skipped, transition to SkipWaterState. If the range is OUTOFBOUNDS in X, then
     * current row/column of the island is done being explored, turn around to next row/column by transitioning
     * to GetReadyToTurn.
     * @param drone Drone exploring the grid
     * @param map AreaMap being explored
     * @param decisionHandler The facilitator of the state machine
     * @return String JSON representation of the action taken
     */
    public String searchArea(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {

            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHOFORWARD);
            return drone.echoForward();

        } else if (map.getForwardRange() > 0 && map.getForward() == MapTerrain.OUTOFBOUNDS) {

            // First move forward and scan to handle edge case
            if (this.LandTraversalIteration == 0) {
                this.LandTraversalIteration++;
                decisionHandler.setActionTaken(Actions.FLY);
                return drone.fly();
            } else if (this.LandTraversalIteration == 1) {
                this.LandTraversalIteration++;
                decisionHandler.setActionTaken(Actions.SCAN);
                return drone.scan();
            }

            // Transition to get ready to turn state
            Direction current = drone.getHeading();
            Direction startHeading = drone.getInitialHeading();
            decisionHandler.setState(new GetReadyToTurnState(current, startHeading));
            return decisionHandler.makeDecision(drone, map);

        } else if (map.getForwardRange() > 0 && map.getForward() == MapTerrain.LAND) {
            // Skip the gap of water, transition to skip water state
            // +1 is added to adjust the range as it will stop on the last water block otherwise
            decisionHandler.setState(new SkipWaterState(map.getForwardRange() + 1));
            return decisionHandler.makeDecision(drone, map);

        } else if (map.getForwardRange() == 0) {
            // Move forward on the island, transition to move forward state
            decisionHandler.setState(new MoveForwardOnIslandState());
            return decisionHandler.makeDecision(drone, map);
        }

        return null;
    }
}
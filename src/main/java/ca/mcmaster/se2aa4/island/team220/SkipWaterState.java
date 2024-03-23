package ca.mcmaster.se2aa4.island.team220;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class SkipWaterState implements State {
    private Integer numMoves;
    private Integer iteration = 0;
    private final Logger logger = LogManager.getLogger();

    public SkipWaterState(Integer numMoves) {
        this.numMoves = numMoves;
    }

    public String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.scan();
        } if (this.iteration < this.numMoves) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.FLY);
            return drone.fly();
        } else {
            decisionHandler.setActionTaken(Actions.SCAN);
            decisionHandler.setState(new SearchIslandState());
            return drone.scan();
        }
    }

}
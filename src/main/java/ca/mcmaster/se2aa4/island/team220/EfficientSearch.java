package ca.mcmaster.se2aa4.island.team220;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scala.Int;

public class EfficientSearch implements ISearchAlgorithm {
    private Integer iteration = 0;
    private Integer iteration2 = 0;
    private Integer iteration3 = 0;
    private final Logger logger = LogManager.getLogger();

    public String searchArea(Drone drone, AreaMap map, DecisionHandler decisionHandler) {
        if (this.iteration % 4 == 0) {
            this.iteration++;
            decisionHandler.setActionTaken(Actions.ECHOFORWARD);
            return drone.echoForward();
        } else if (map.getForwardAmount() > 0 && map.getForward() == MapFeature.OUTOFBOUNDS) {
            logger.info("DRONES CURRENT HEADING {}", drone.getHeading());
            if (iteration2 == 0) {
                iteration2++;
                decisionHandler.setActionTaken(Actions.FLY);
                return drone.fly();
            } else if (iteration2 == 1) {
                iteration2++;
                decisionHandler.setActionTaken(Actions.SCAN);
                return drone.scan();
            }
            decisionHandler.setState(new GetReadyToTurn(drone.getHeading(), decisionHandler.getStartHeading()));
            //decisionHandler.setState(new UTurnState(drone.getHeading(), decisionHandler.getStartHeading()));
        } else if (map.getForwardAmount() > 0 && map.getForward() == MapFeature.LAND) {
            decisionHandler.setState(new SkipWaterState(map.getForwardAmount() + 1));
        } else if (map.getForwardAmount() == 0) {
            this.iteration++;
            decisionHandler.setState(new MoveForwardOnIslandState());
        }

        decisionHandler.setActionTaken(Actions.SCAN);
        return drone.scan();
    }

}
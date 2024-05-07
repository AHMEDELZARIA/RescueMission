package ca.mcmaster.se2aa4.island.team220.statemachine;

import ca.mcmaster.se2aa4.island.team220.drone.Drone;
import ca.mcmaster.se2aa4.island.team220.map.AreaMap;

/**
 * Interface that offers the services a state requires to provide for a state machine.
 */
public interface State {
    String handle(Drone drone, AreaMap map, DecisionHandler decisionHandler);
}

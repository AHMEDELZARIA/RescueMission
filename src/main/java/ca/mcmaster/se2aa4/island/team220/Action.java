package ca.mcmaster.se2aa4.island.team220;

public enum Action {
    //refactor gridSearch by hiding instances of decision.put in a map in class Action.java
    FLY,
    ECHO_NORTH,
    ECHO_SOUTH,
    ECHO_EAST,
    ECHO_WEST,
    HEADING,
    SCAN,
    STOP;
}

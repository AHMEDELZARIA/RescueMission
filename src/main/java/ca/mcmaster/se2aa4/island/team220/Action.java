package ca.mcmaster.se2aa4.island.team220;

public enum Action {
    //refactor gridSearch by hiding instances of decision.put in a map in class Action.java
    FLY,
    ECHO,
    ECHO_NORTH, // MIGHT NOT NEED THIS
    ECHO_SOUTH, // MIGHT NOT NEED THIS
    ECHO_EAST, // MIGHT NOT NEED THIS
    ECHO_WEST, // MIGHT NOT NEED THIS
    HEADING,
    HEADING_LEFT, // MIGHT NOT NEED THIS
    HEADING_RIGHT, // MIGHT NOT NEED THIS
    SCAN,
    STOP;
}

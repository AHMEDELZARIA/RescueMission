package ca.mcmaster.se2aa4.island.team220;

public enum Action {
    //refactor gridSearch by hiding instances of decision.put in a map in class Action.java
    FLY,
    ECHO, // MIGHT NOT NEED THIS, DELETE LATER
    ECHO_NORTH,
    ECHO_SOUTH,
    ECHO_EAST,
    ECHO_WEST,
    HEADING_LEFT,
    HEADING_RIGHT,
    SCAN,
    STOP;
}

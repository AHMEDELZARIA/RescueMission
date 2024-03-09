package ca.mcmaster.se2aa4.island.team220;

public enum Direction {
    NORTH('N'),
    SOUTH('S'),
    EAST('E'),
    WEST('W');

    private final char dir;

    Direction(char dir) { this.dir = dir; }

    @Override
    public String toString() { return "" + dir; }
}

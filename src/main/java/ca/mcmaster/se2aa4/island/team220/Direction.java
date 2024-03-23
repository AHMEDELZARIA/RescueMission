package ca.mcmaster.se2aa4.island.team220;

public enum Direction {
    NORTH('N'),
    SOUTH('S'),
    EAST('E'),
    WEST('W');

    private final char dir;

    Direction(char dir) {
        this.dir = dir;
    } // constructor

    @Override
    public String toString() {
        return "" + dir;
    }

    public static Direction toDirection(String dir) {
        switch (dir.toUpperCase()) {
            case "N":
                return NORTH;
            case "S":
                return SOUTH;
            case "E":
                return EAST;
            case "W":
                return WEST;
            default:
                throw new IllegalArgumentException("Invalid direction: " + dir);
        }
    }
}

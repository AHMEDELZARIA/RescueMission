package ca.mcmaster.se2aa4.island.team220;

import java.util.EnumMap;
import java.util.Map;

/**
 * Compass class defines the heading behaviour for a Drone
 */
public class Compass {
    private Direction initialHeading;
    private Direction heading;
    private Direction prevHeading;
    private Map<Direction, Direction> goingRight;
    private Map<Direction, Direction> goingLeft;

    /**
     * Create a Compass which tracks and updates the heading.
     * @param heading Direction indicating the initial heading of the Compass
     */
    public Compass(Direction heading) {
        this.heading = heading;
        this.prevHeading = heading;
        this.initialHeading = heading;
        buildCompass();
    }

    /**
     * Sets the turn logic once and for all when Compass is instantiated.
     */
    private void buildCompass() {
        // Define turn right logic
        this.goingRight = new EnumMap<>(Direction.class);
        this.goingRight.put(Direction.NORTH, Direction.EAST);
        this.goingRight.put(Direction.EAST, Direction.SOUTH);
        this.goingRight.put(Direction.SOUTH, Direction.WEST);
        this.goingRight.put(Direction.WEST, Direction.NORTH);

        // Define turn left logic
        this.goingLeft = new EnumMap<>(Direction.class);
        this.goingLeft.put(Direction.NORTH, Direction.WEST);
        this.goingLeft.put(Direction.WEST, Direction.SOUTH);
        this.goingLeft.put(Direction.SOUTH, Direction.EAST);
        this.goingLeft.put(Direction.EAST, Direction.NORTH);
    }

    public Direction getInitialHeading() { return this.initialHeading; }

    public Direction getHeading() { return this.heading; }

    public Direction getPrevHeading() { return this.prevHeading; }

    public void updatePrevHeading() { this.prevHeading = this.heading; }

    public Direction getLeft() {
        return goingLeft.get(this.heading);
    }

    public Direction getRight() {
        return goingRight.get(this.heading);
    }

    /**
     * Updates and gives the heading if a left turn is performed.
     * @return Direction of new heading.
     */
    public Direction turnLeft() {
        this.prevHeading = this.heading;
        this.heading = goingLeft.get(this.heading);
        return this.heading;
    }

    /**
     * Updates and gives the heading if a right turn is performed.
     * @return Direction of new heading.
     */
    public Direction turnRight() {
        this.prevHeading = this.heading;
        this.heading = goingRight.get(this.heading);
        return this.heading;
    }
}

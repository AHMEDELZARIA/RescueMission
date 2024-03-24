package ca.mcmaster.se2aa4.island.team220;

import java.util.EnumMap;
import java.util.Map;

public class Compass {
    private Direction initialHeading;
    private Direction heading;
    private Direction prevHeading;
    private Map<Direction, Direction> goingRight;
    private Map<Direction, Direction> goingLeft;

    public Compass(Direction heading) {
        this.heading = heading;
        this.prevHeading = heading;
        this.initialHeading = heading;
        buildCompass();
    }

    public Direction getHeading() {
        return this.heading;
    }

    public Direction getPrevHeading() { return this.prevHeading; }

    public void updatePrevHeading() { this.prevHeading = this.heading; }
    public Direction getInitialHeading() { return this.initialHeading; }

    public void buildCompass() {
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

    public Direction turnLeft() {
        this.prevHeading = this.heading;
        this.heading = goingLeft.get(this.heading);
        return this.heading;
    }

    public Direction turnRight() {
        this.prevHeading = this.heading;
        this.heading = goingRight.get(this.heading);
        return this.heading;
    }

    public Direction getLeft() {
        return goingLeft.get(this.heading);
    }

    public Direction getRight() {
        return goingRight.get(this.heading);
    }

}

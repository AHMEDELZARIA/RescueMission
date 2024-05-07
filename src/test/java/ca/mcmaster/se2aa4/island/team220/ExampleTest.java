package ca.mcmaster.se2aa4.island.team220;

import ca.mcmaster.se2aa4.island.team220.drone.Compass;
import ca.mcmaster.se2aa4.island.team220.drone.Direction;
import ca.mcmaster.se2aa4.island.team220.drone.Drone;
import ca.mcmaster.se2aa4.island.team220.map.AreaMap;
import ca.mcmaster.se2aa4.island.team220.map.MapTerrain;
import ca.mcmaster.se2aa4.island.team220.map.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ExampleTest {

    private Compass compass;

    @BeforeEach
    public void setUp() {
        this.compass = new Compass(Direction.EAST);
    }

    @Test
    public void initialize() {
        Integer batteryLevel = 7000;
        Direction heading = Direction.EAST;
        Drone drone = new Drone(batteryLevel, heading);
        AreaMap map = new AreaMap(new Drone(1, Direction.EAST));

        Integer expectedBattery = 7000;
        Direction expectedHeading = Direction.EAST;

        Integer resultBattery = drone.getBattery();
        Direction resultHeading = drone.getHeading();

        assertEquals(expectedBattery, resultBattery);
        assertEquals(expectedHeading, resultHeading);
    }

    @Test
    public void stringToDirection() {
        String north = "N";
        String south = "S";
        String east = "E";
        String west = "W";

        Direction expectedNorth = Direction.NORTH;
        Direction expectedSouth = Direction.SOUTH;
        Direction expectedEast = Direction.EAST;
        Direction expectedWest = Direction.WEST;

        Direction resultNorth = Direction.toDirection(north);
        Direction resultSouth = Direction.toDirection(south);
        Direction resultEast = Direction.toDirection(east);
        Direction resultWest = Direction.toDirection(west);

        assertEquals(expectedNorth, resultNorth);
        assertEquals(expectedSouth, resultSouth);
        assertEquals(expectedEast, resultEast);
        assertEquals(expectedWest, resultWest);
    }

    @Test
    public void equalPoints() {
        Point origin1 = new Point(0, 0);
        Point origin2 = new Point(0, 0);
        int hashCode1 = origin1.hashCode();
        int hashCode2 = origin2.hashCode();

        assertTrue(hashCode1 == hashCode2);
        assertTrue(origin1.equals(origin2));
    }

    @Test
    public void pointNotInMap() {
        AreaMap map = new AreaMap(new Drone(1, Direction.EAST));
        Point point = new Point(1, 1);

        assertTrue(map.getPointTerrain(point) == MapTerrain.UNKNOWN);
    }

//    @Test
//    public void pointsInMap() {
//        AreaMap map = new AreaMap(new Drone(1, Direction.EAST));
//        List<Point> points = new ArrayList<>();
//        points.add(new Point(1, 1));
//        points.add(new Point(1, 2));
//        points.add(new Point(1, 3));
//        points.add(new Point(1, 4));
//        points.add(new Point(1, 5));
//
//        for (Point p : points) {
//            map.addPoint(p, MapTerrain.OCEAN);
//            assertTrue(map.getPointTerrain(p) != null);
//        }
//    }

    @Test
    public void testTurn() {
        this.compass.turnLeft();
        assertNotEquals(Direction.EAST, this.compass.getHeading());
    }

    @Test
    public void shouldTurnLeft() {
        this.compass.turnLeft();
        assertEquals(Direction.NORTH, this.compass.getHeading());
        this.compass.turnLeft();
        assertEquals(Direction.WEST, this.compass.getHeading());
        this.compass.turnLeft();
        assertEquals(Direction.SOUTH, this.compass.getHeading());
        this.compass.turnLeft();
        assertEquals(Direction.EAST, this.compass.getHeading());
    }

    @Test
    public void shouldTurnRight() {
        this.compass.turnRight();
        assertEquals(Direction.SOUTH, this.compass.getHeading());
        this.compass.turnRight();
        assertEquals(Direction.WEST, this.compass.getHeading());
        this.compass.turnRight();
        assertEquals(Direction.NORTH, this.compass.getHeading());
        this.compass.turnRight();
        assertEquals(Direction.EAST, this.compass.getHeading());
    }

    @Test
    public void shouldTurnAround() {
        this.compass.turnRight();
        this.compass.turnRight();
        assertEquals(Direction.WEST, this.compass.getHeading());
        this.compass.turnLeft();
        this.compass.turnLeft();
        assertEquals(Direction.EAST, this.compass.getHeading());
    }

    @Test
    public void shouldTurnAndGoBack() {
        this.compass.turnRight();
        this.compass.turnLeft();
        assertEquals(Direction.EAST, this.compass.getHeading());
        this.compass.turnLeft();
        this.compass.turnRight();
        assertEquals(Direction.EAST, this.compass.getHeading());
    }
}
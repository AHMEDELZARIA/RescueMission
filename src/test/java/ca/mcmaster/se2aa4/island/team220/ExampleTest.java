package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ExampleTest {

    @Test
    public void initialize() {
        Integer batteryLevel = 7000;
        Direction heading = Direction.EAST;
        Drone drone = new Drone(batteryLevel, heading);
        AreaMap map = new AreaMap();

        Integer expectedBattery = 7000;
        Direction expectedHeading = Direction.EAST;
        MapFeature expectedMapFeature = MapFeature.OCEAN;

        Integer resultBattery = drone.getBattery();
        Direction resultHeading = drone.getHeading();
        MapFeature resultMapFeature = map.getPoint(new Point(0, 0));

        assertEquals(expectedBattery, resultBattery);
        assertEquals(expectedHeading, resultHeading);
        assertEquals(expectedMapFeature, resultMapFeature);
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
        AreaMap map = new AreaMap();
        Point point = new Point(1, 1);

        assertTrue(map.getPoint(point) == null); 
    }

    @Test
    public void pointsInMap() {
        AreaMap map = new AreaMap();
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(1, 2));
        points.add(new Point(1, 3));
        points.add(new Point(1, 4));
        points.add(new Point(1, 5));

        for (Point p : points) {
            map.addPoint(p, MapFeature.OCEAN);
            assertTrue(map.getPoint(p) != null);
        }
    }

    

}

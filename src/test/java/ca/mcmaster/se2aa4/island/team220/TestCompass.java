package ca.mcmaster.se2aa4.island.team220;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCompass {
    private Compass compass;
   
    @BeforeEach
    public void setUpTest(){
        this.compass = new Compass(Direction.NORTH); //FIGURE OUT WHY THIS DOESNT WORK
    }
    @Test
    public void testFirstHeading() {
        assertEquals(Direction.NORTH, compass.getHeading());
    }
    @Test
    public void test3LeftTurns() {
        compass.turnLeft();
        compass.turnLeft();
        compass.turnLeft();
        assertEquals(Direction.EAST, compass.getHeading());
    }
    @Test
    public void test3RightTurns() {
        compass.turnRight();
        compass.turnRight();
        compass.turnRight();
        assertEquals(Direction.WEST, compass.getHeading());
    }
    @Test
    public void testLeftAndRightTurnsSwitch() {
        compass.turnLeft();
        compass.turnRight();
        compass.turnLeft();
        compass.turnRight();
        assertEquals(Direction.NORTH, compass.getHeading());
    }

    @Test
    public void testTurn() {
        this.compass = new Compass(Direction.EAST);
        this.compass.turnLeft();
        assertNotEquals(Direction.EAST, this.compass.getHeading());
    }

    @Test
    public void shouldTurnLeft() {
        this.compass = new Compass(Direction.EAST);
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
        this.compass = new Compass(Direction.EAST);
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
        this.compass = new Compass(Direction.EAST);
        this.compass.turnRight();
        this.compass.turnRight();
        assertEquals(Direction.WEST, this.compass.getHeading());
        this.compass.turnLeft();
        this.compass.turnLeft();
        assertEquals(Direction.EAST, this.compass.getHeading());
    }

    @Test
    public void shouldTurnAndGoBack() {
        this.compass = new Compass(Direction.EAST);
        this.compass.turnRight();
        this.compass.turnLeft();
        assertEquals(Direction.EAST, this.compass.getHeading());
        this.compass.turnLeft();
        this.compass.turnRight();
        assertEquals(Direction.EAST, this.compass.getHeading());
    }

    @Test
    public void testTurnLeftFromNorth() {
        Compass compass = new Compass(Direction.NORTH);
        compass.turnLeft();
        assertEquals(Direction.WEST, compass.getHeading());
    }

    @Test
    public void testTurnRightFromNorth() {
        Compass compass = new Compass(Direction.NORTH);
        compass.turnRight();
        assertEquals(Direction.EAST, compass.getHeading());
    }

    @Test
    public void testTurnLeftFromWest() {
        Compass compass = new Compass(Direction.WEST);
        compass.turnLeft();
        assertEquals(Direction.SOUTH, compass.getHeading());
    }

    @Test
    public void testTurnRightFromSouth() {
        Compass compass = new Compass(Direction.SOUTH);
        compass.turnRight();
        assertEquals(Direction.WEST, compass.getHeading());
    }

    @Test
    public void testTurnLeftFromEast() {
        Compass compass = new Compass(Direction.EAST);
        compass.turnLeft();
        assertEquals(Direction.NORTH, compass.getHeading());
    }
}

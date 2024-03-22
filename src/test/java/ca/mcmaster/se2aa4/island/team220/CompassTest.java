package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompassTest {

    private Compass compass;

    @BeforeEach
    public void setUpTest(){
        Compass compass = new Compass(Direction.NORTH);
    }
    @Test
    public void testFirstHeading() {
        Compass compass = new Compass(Direction.NORTH);
        assertEquals(Direction.NORTH, compass.getHeading());
    }
    @Test
    public void test3LeftTurns() {
        Compass compass = new Compass(Direction.NORTH);
        compass.turnLeft();
        compass.turnLeft();
        compass.turnLeft();
        assertEquals(Direction.EAST, compass.getHeading());
    }
    @Test
    public void test3RightTurns() {
        Compass compass = new Compass(Direction.NORTH);
        compass.turnRight();
        compass.turnRight();
        compass.turnRight();
        assertEquals(Direction.WEST, compass.getHeading());
    }
    @Test
    public void testLeftAndRightTurnsSwitch() {
        Compass compass = new Compass(Direction.NORTH);
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
    
}

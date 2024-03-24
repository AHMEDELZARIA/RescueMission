package ca.mcmaster.se2aa4.island.team220;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DroneTest {

    private Drone drone;
    @BeforeEach
    public void setUp(){
        this.drone = new Drone(100, Direction.NORTH);
    }
    
    @Test 
    public void testStop() {
        assertEquals("{\"action\":\"stop\"}", drone.stop());
    }

    @Test
    public void testFly() {
        assertEquals("{\"action\":\"fly\"}", drone.fly());
    }

    @Test
    public void testScan() {
        assertEquals("{\"action\":\"scan\"}", drone.scan());
    }

    @Test
    public void testEchoRight() {
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}", drone.echoRight());
    }

    @Test
    public void testEchoLeft() {
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}", drone.echoLeft());
    }

    @Test
    public void testEchoForward() {
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", drone.echoForward());
    }

    @Test
    public void testTurnLeft() {
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", drone.turnLeft());
    }

    @Test
    public void testTurnRight() {
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", drone.turnRight());
    }


    
}

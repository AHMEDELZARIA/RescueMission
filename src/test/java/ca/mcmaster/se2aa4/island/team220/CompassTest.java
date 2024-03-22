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
        assertEquals(Direction.NORTH, compass.getHeading());
    }
    



    
}

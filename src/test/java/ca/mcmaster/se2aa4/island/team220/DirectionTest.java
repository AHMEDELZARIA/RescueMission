package ca.mcmaster.se2aa4.island.team220;

import ca.mcmaster.se2aa4.island.team220.drone.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DirectionTest {

    @Test
    public void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            Direction.toDirection("invalid");
        });
    }

    @Test
    public void testDirection() {
        assertEquals(Direction.NORTH.toString(), "N");
        assertEquals(Direction.SOUTH.toString(), "S");
        assertEquals(Direction.EAST.toString(), "E");
        assertEquals(Direction.WEST.toString(), "W");
    }

    @Test
    public void testToDirection() {
        assertEquals(Direction.toDirection("N"), Direction.NORTH);
        assertEquals(Direction.toDirection("S"), Direction.SOUTH);
        assertEquals(Direction.toDirection("E"), Direction.EAST);
        assertEquals(Direction.toDirection("W"), Direction.WEST);
    }

}

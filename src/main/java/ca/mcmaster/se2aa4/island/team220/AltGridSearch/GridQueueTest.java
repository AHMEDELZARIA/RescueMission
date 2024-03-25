package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GridQueueTest {

    private GridQueue gridQueue;

    @BeforeEach
    public void setUp() {
        gridQueue = new GridQueue();
    }

    @Test
    public void testDequeueIfEmpty() {
        assertNull(gridQueue.dequeue());
    }

    @Test
    public void testIsEmptyIfEmpty() { //initial = is empty
        assertTrue(gridQueue.isEmpty());
    }

    @Test
    public void testEnqueueMethod() {
        gridQueue.enqueue("#1");
        gridQueue.enqueue("#2");
        assertEquals(2, gridQueue.getQueue().size());
    }

    @Test
    public void testIsEmptyIfNotEmpty() { //initial = is not empty
        gridQueue.enqueue("Action");
        assertFalse(gridQueue.isEmpty());
    }









    
}

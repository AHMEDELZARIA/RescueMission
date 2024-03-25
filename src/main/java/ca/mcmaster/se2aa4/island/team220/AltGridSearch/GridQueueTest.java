package ca.mcmaster.se2aa4.island.team220.AltGridSearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

public class GridQueueTest {

    private GridQueue gridQueue;

    @Before
    public void setUp() {
        gridQueue = new GridQueue();
    }

    @org.junit.Test
    public void testDequeueIfEmpty() {
        assertNull(gridQueue.dequeue());
    }

    @org.junit.Test
    public void testIsEmptyIfEmpty() { //initial = is empty
        assertTrue(gridQueue.isEmpty());
    }

    @org.junit.Test
    public void testEnqueueMethod() {
        gridQueue.enqueue("#1");
        gridQueue.enqueue("#2");
        assertEquals(2, gridQueue.getQueue().size());
    }

    @org.junit.Test
    public void testIsEmptyIfNotEmpty() { //initial = is not empty
        gridQueue.enqueue("Action");
        assertFalse(gridQueue.isEmpty());
    }









    
}

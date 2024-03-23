package ca.mcmaster.se2aa4.island.team220;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridSearchTest {

    private GridSearch gridSearch;
    private Compass compass;
    private Integer count = 0;
    private GridQueue queue;

    @BeforeEach
    public void setUp(){
        gridSearch = new GridSearch();
    }

    @Test
    public void testMakeDecision() {
        String finalDecision = gridSearch.makeDecision("found", 8, "biome", new Compass(Direction.NORTH));
        assertNotNull(finalDecision); 
    }

    @Test
    public void testCaseBPart1() {
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.caseBPart1(compass);
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test
    public void testCaseBPart2() {
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.caseBPart2(5, compass);
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test
    public void testCaseAPart1() {
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.caseAPart1(5, compass);
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }
    
    @Test
    public void testCaseAPart2() {
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.caseAPart2(1, compass);
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test
    public void testCaseAPart2ElseCondition() {
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.caseAPart2(3, compass);
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test
    public void checkStartTest() {
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.checkStart(compass);
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void loopExtraTest(){
        gridSearch.down = true;
        
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.loopExtra(compass);
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void loopExtraTestElseCondition(){
        gridSearch.down = false;
        
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.loopExtra(compass);
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void loopAroundTest(){
        gridSearch.down = true;
        
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.loopAround(compass);
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test
    public void loopAroundTestElseCondition(){
        gridSearch.down = false;

        Compass compass = new Compass(Direction.NORTH);
        gridSearch.loopAround(compass);
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());

    }
























    
}

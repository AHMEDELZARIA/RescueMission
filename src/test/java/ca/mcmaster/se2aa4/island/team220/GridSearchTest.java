package ca.mcmaster.se2aa4.island.team220;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test 
    public void uTurnTest(){
        gridSearch.down = true;
        gridSearch.interlaceCheck = false;
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.uTurn(compass);

        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());

    }

    @Test 
    public void uTurnTestElseCondition(){
        gridSearch.down = false;
        gridSearch.interlaceCheck = false;
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.uTurn(compass);

        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void intoPositionTest(){
        gridSearch.down = true;
        gridSearch.interlaceCheck = false;
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.intoPosition(compass);

        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void intoPositionTestElseCondition(){
        gridSearch.down = true;
        gridSearch.interlaceCheck = true;
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.intoPosition(compass);

        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void searchSiteTest(){
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.searchSite(compass);

        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"scan\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void reachIslandTest(){
        gridSearch.reachIsland();
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertEquals("{\"action\":\"scan\"}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void faceIslandTest(){
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.count = 4;
        gridSearch.faceIsland(compass);
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void faceIslandTestElseCondition(){
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.count = 5;
        gridSearch.faceIsland(compass);
        assertEquals("{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void findIslandTest(){
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.count = 2;
        gridSearch.findIsland(compass);
        assertEquals("{\"action\":\"fly\"}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void findIslandTestElseIfCondition(){
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.count = 3;
        gridSearch.findIsland(compass);
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }

    @Test 
    public void findIslandTestElseCondition(){
        Compass compass = new Compass(Direction.NORTH);
        gridSearch.count = 4;
        gridSearch.findIsland(compass);
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}", gridSearch.getQueue().dequeue());
        assertTrue(gridSearch.getQueue().isEmpty());
    }






























    
}

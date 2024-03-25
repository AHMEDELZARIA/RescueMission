package ca.mcmaster.se2aa4.island.team220.AltGridSearch;

import org.junit.Before;
import ca.mcmaster.se2aa4.island.team220.Compass;
import ca.mcmaster.se2aa4.island.team220.Direction;
import static org.junit.Assert.assertEquals;

public class CommandBookTest {

    private CommandBook testCommand;
    @Before
    public void setup() { 
        this.testCommand = new CommandBook(); 
    }

    @org.junit.Test
    public void testGetStop(){
        String expectedString = "{\"action\":\"stop\"}";
        String resultString = testCommand.getStop();
        assertEquals(expectedString, resultString);
    }

    @org.junit.Test
    public void testGetFly(){
        String expectedString = "{\"action\":\"fly\"}";
        String resultString = testCommand.getFly();
        assertEquals(expectedString, resultString);
    }

    @org.junit.Test
    public void testGetScan(){
        String expectedString = "{\"action\":\"scan\"}";
        String resultString = testCommand.getScan();
        assertEquals(expectedString, resultString);
    }

    @org.junit.Test
    public void testGetTurnLeft() {
        Compass compass = new Compass(Direction.NORTH); //Test case where North is Initial Position
        String expectedString = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}";
        String resultString = testCommand.getTurnLeft(compass);
        assertEquals(expectedString, resultString);
    }

    @org.junit.Test
    public void testGetTurnRight() {
        Compass compass = new Compass(Direction.NORTH); 
        String expectedString = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}";
        String resultString = testCommand.getTurnRight(compass);
        assertEquals(expectedString, resultString);
    }

    @org.junit.Test
    public void testGetEchoForward() {
        Compass compass = new Compass(Direction.NORTH); 
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
        String resultString = testCommand.getEchoForward(compass);
        assertEquals(expectedString, resultString);
    }

    @org.junit.Test
    public void getEchoLeft() {
        Compass compass = new Compass(Direction.NORTH); 
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}";
        String resultString = testCommand.getEchoLeft(compass);
        assertEquals(expectedString, resultString);
    }

    @org.junit.Test
    public void getEchoRight() {
        Compass compass = new Compass(Direction.NORTH); 
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}";
        String resultString = testCommand.getEchoRight(compass);
        assertEquals(expectedString, resultString);
    }
    
}

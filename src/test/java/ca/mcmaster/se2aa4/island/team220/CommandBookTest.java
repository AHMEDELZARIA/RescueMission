package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandBookTest {

    private CommandBook testCommand;
    @BeforeEach
    public void setup() { 
        this.testCommand = new CommandBook(); 
    }

    @Test
    public void testGetStop(){
        String expectedString = "{\"action\":\"stop\"}";
        String resultString = testCommand.getStop();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetFly(){
        String expectedString = "{\"action\":\"fly\"}";
        String resultString = testCommand.getFly();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetScan(){
        String expectedString = "{\"action\":\"scan\"}";
        String resultString = testCommand.getScan();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetTurnLeft() {
        Compass compass = new Compass(Direction.NORTH); //Test case where North is Initial Position
        String expectedString = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}";
        String resultString = testCommand.getTurnLeft(compass);
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetTurnRight() {
        Compass compass = new Compass(Direction.NORTH); 
        String expectedString = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}";
        String resultString = testCommand.getTurnRight(compass);
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testTestEchoForward() {
        Compass compass = new Compass(Direction.NORTH); 
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
        String resultString = testCommand.testEchoForward(compass);
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testEchoLeft() {
        Compass compass = new Compass(Direction.NORTH); 
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}";
        String resultString = testCommand.testEchoLeft(compass);
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testEchoRight() {
        Compass compass = new Compass(Direction.NORTH); 
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}";
        String resultString = testCommand.testEchoRight(compass);
        assertEquals(expectedString, resultString);
    }
    
}

package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandBookTest {

    private CommandBook testCommand;
    @BeforeEach
    public void setup() { 
        CommandBook testCommand = new CommandBook();
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

    /*
    @Test
    public void testGetEchoNorth() {
        CommandBook commandBook = new CommandBook();
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
        String resultString = commandBook.getEchoNorth();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetEchoEast() {
        CommandBook commandBook = new CommandBook();
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}";
        String resultString = commandBook.getEchoEast();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetEchoSouth() {
        CommandBook commandBook = new CommandBook();
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}";
        String resultString = commandBook.getEchoSouth();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetEchoWest() {
        CommandBook commandBook = new CommandBook();
        String expectedString = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}";
        String resultString = commandBook.getEchoWest();
        assertEquals(expectedString, resultString);
    }
    */

    @Test
    public void testGetTurnLeft() {
        Compass compass = new Compass(Direction.NORTH); // initial pos
        String expectedString = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}";
        String resultString = testCommand.getTurnLeft(compass);
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetTurnRight() {
        Compass compass = new Compass(Direction.NORTH); // initial pos change i think
        String expectedString = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}";
        String resultString = testCommand.getTurnRight(compass);
        assertEquals(expectedString, resultString);
    }
    
}

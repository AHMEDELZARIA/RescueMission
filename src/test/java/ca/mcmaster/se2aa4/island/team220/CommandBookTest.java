package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandBookTest {
    
    private Compass compass;

    @BeforeEach
    public void setup() {
        Commnad
    }

    @Test
    public void testGetStop(){
        CommandBook testCommand = new CommandBook(); 

        //lets test the string we should be returned when we stop the actions
        String expectedString = "{\"action\":\"stop\"}";
        String resultString = testCommand.getStop();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetFly(){
        CommandBook testCommand = new CommandBook();

        //testing the string returned when getFly is called from CommandBook
        String expectedString = "{\"action\":\"fly\"}";
        String resultString = testCommand.getFly();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetScan(){
        CommandBook testCommand = new CommandBook();
        String expectedString = "{\"action\":\"scan\"}";
        String resultString = testCommand.getFly();
        assertEquals(expectedString, resultString);
    }

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

    @Test
    public void testGetTurnLeft() {
        CommandBook commandBook = new CommandBook();
        Compass compass = new Compass(Direction.NORTH); // initial pos
        String expectedString = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}";
        String resultString = commandBook.getTurnLeft(compass);
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetTurnRight() {
        CommandBook commandBook = new CommandBook();
        Compass compass = new Compass(Direction.SOUTH); // initial pos change i think
        String expectedString = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}";
        String resultString = commandBook.getTurnRight(compass);
        assertEquals(expectedString, resultString);
    }
    
}

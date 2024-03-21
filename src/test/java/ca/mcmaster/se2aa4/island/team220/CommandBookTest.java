package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandBookTest {
    
    private Compass compass;

    @Test
    public void testGetStop(){
        CommandBook testCommand = new CommandBook(); 

        //lets test the string we should be returned when we stop the actions
        String correct = "{\"action\":\"stop\"}";
        String output = testCommand.getStop();
        assertEquals(correct, output);
    }

    @Test
    public void testGetFly(){
        CommandBook testCommand = new CommandBook();

        //testing the string returned when getFly is called from CommandBook
        
    }
}

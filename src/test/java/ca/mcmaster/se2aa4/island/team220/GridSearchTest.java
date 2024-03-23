package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GridSearchTest {

    private GridSearch gridSearch;

    @BeforeEach
    public void setUp(){
        gridSearch = new GridSearch();
    }

    @Test
    public void testMakeDecision() {
        String finalDecision = gridSearch.makeDecision("found", 8, "biome", new Compass(Direction.NORTH));
        assertNotNull(finalDecision); 
    }

    



    
}

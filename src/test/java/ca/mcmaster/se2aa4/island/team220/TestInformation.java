package ca.mcmaster.se2aa4.island.team220;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestInformation {
    
    @Test
    public void testGetters() {
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray().put("C1").put("C2"));
        extras.put("biomes", new JSONArray().put("OCEAN").put("MANGROVE"));
        extras.put("sites", new JSONArray().put("S1").put("S2"));
        extras.put("found", "LAND");
        extras.put("range", 10);

        Information information = new Information(Actions.FLY, 8, extras, true);

        assertEquals(Actions.FLY, information.getActionTaken());
        assertEquals(8, information.getCost());
        assertTrue(information.status());
        assertEquals(new JSONArray().put("C1").put("C2").toString(), information.getCreeks().toString());
        assertEquals(new JSONArray().put("OCEAN").put("MANGROVE").toString(), information.getBiomes().toString());
        assertEquals(new JSONArray().put("S1").put("S2").toString(), information.getSites().toString());
        assertEquals(MapTerrain.LAND, information.getFound());
        assertEquals(10, information.getRange());
    }
}

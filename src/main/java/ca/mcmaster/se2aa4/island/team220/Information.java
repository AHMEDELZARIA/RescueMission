package ca.mcmaster.se2aa4.island.team220;

import org.json.JSONArray;
import org.json.JSONObject;

// Holds all the relevant information received from the results of an action
public class Information {
    private Actions actionTaken;
    private Integer cost;
    private Boolean status;
    private JSONObject extras;
    // extras attributes
    private JSONArray creeks;
    private JSONArray biomes;
    private JSONArray sites;
    private MapTerrain found;
    private Integer range;

    /**
     * Creates an information object.
     * @param actionTaken Action taken
     * @param cost Integer cost of the action taken
     * @param extras JSONObject of the extras field
     * @param status Boolean representing the status of the Drone
     */
    public Information(Actions actionTaken, Integer cost, JSONObject extras, Boolean status) {
        this.actionTaken = actionTaken;
        this.cost = cost;
        this.status = status;
        this.extras = extras;
        updatedAttributes(this.extras);
    }

    /**
     * Parses the extras JSONObject into more manageable attributes.
     * @param extras JSONObject representing the extras field
     */
    private void updatedAttributes(JSONObject extras) {

        try { this.creeks = extras.getJSONArray("creeks"); }
        catch (Exception e) { this.creeks = null; }

        try { this.biomes = extras.getJSONArray("biomes"); }
        catch (Exception e) { this.biomes = null; }

        try { this.sites = extras.getJSONArray("sites"); }
        catch (Exception e) { this.sites = null; }

        try {
            String foundAttribute = extras.getString("found");
            if (foundAttribute != null) {
                if (foundAttribute.equals("OUT_OF_RANGE")) { this.found = MapTerrain.OUTOFBOUNDS; }
                else { this.found = MapTerrain.LAND; }
            }
        } catch (Exception e) { this.found = null; }

        try { this.range = extras.getInt("range"); }
        catch (Exception e) { this.range = null; }
    }

    public Actions getActionTaken() { return this.actionTaken; }

    public Integer getCost() { return this.cost; }

    public Boolean status() { return this.status; }

    public JSONArray getCreeks() { return this.creeks; }

    public JSONArray getBiomes() { return this.biomes; }

    public JSONArray getSites() { return this.sites; }

    public MapTerrain getFound() { return this.found; }

    public Integer getRange() { return this.range; }
}

package ca.mcmaster.se2aa4.island.team220;

import org.json.JSONArray;
import org.json.JSONObject;

public class Translator {
    int range = 0;
    String found = "";
    String status = "";
    JSONArray biomes = null;
    JSONArray creeks = null;
    JSONArray sites = null;

    public Information translate(JSONObject response) {

        Action action = determineAction(response.getJSONObject("extras"));

        Integer cost = response.getInt("cost");

        Boolean status;
        if (response.getString("status").equals("OK")) {
            status = true;
        } else {
            status = false;
        }

        // PLACEHOLDER
        return new Information(cost, null, status);
    }

    private Action determineAction(JSONObject extraInfo) {
        // extras can contain:
        // If echo: "found" and "range"
        // If fly: empty
        // If scan: "creeks": JSONArray, "biomes": JSONArray, and sites: JSONArray

        if (extraInfo.length() == 0) {
            return Action.FLY;
        } else if (extraInfo.has("found")) {
            return Action.ECHO;
        } else {
            return Action.SCAN;
        }
    }

    public void setRange(int range) {
        this.range = range;
    }
    public int getRange() {
        return range;
    }

    public String getStatus() {
        return status;
    }

    public JSONArray getBiomes() {
        return biomes;
    }

    public JSONArray getCreeks() {
        return creeks;
    }

    public JSONArray getSites() {
        return sites;
    }

    public void processEcho(JSONObject response) {
        JSONObject extras = response.getJSONObject("extras");
        range = extras.getInt("range");
        found = extras.getString("found");
        status = response.getString("status");
    }

    public void processScan(JSONObject response) {
        JSONObject extras = response.getJSONObject("extras");
        biomes = extras.getJSONArray("biomes");
        creeks = extras.getJSONArray("creeks");
        sites = extras.getJSONArray("sites");
    }
}

/*
package ca.mcmaster.se2aa4.island.team220;

public class Information {
    private Action actionTaken;
    private Integer cost;
    private Boolean status;

    private String found;
    private String biome;
    private String site;

    public Information(Integer cost, String biome, Boolean status) {
        // this.actionTaken = actionTaken;
        this.cost = cost;
        this.status = status;
    }

    /*
    public Action getActionTaken() {
        return this.actionTaken;
    }
    */

    /*
    // Added 19/03
    public void getFound(String found) {
        this.found = found;
    }

    public void getBiome(String biome) {
        this.biome = biome;
    }

    public void getSite(String site) {
        if (site == null) {
            this.site = "N/A";
        } else {
            this.site = site;
        }
    }


    public Integer getCost() {
        return this.cost;
    }

    public Boolean status() {
        return this.status;
    }
}
*/
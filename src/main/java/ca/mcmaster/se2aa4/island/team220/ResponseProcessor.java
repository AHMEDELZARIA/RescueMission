package ca.mcmaster.se2aa4.island.team220;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseProcessor {
    int range =0;

    public void setRange(int range) {
        this.range = range;
    }

    public void processEcho(JSONObject response){
        JSONObject extras = response.getJSONObject("extras");
        int range = extras.getInt("range");
        String status = response.getString("status");
    }

    public void processScan(JSONObject response){
        JSONObject extras = response.getJSONObject("extras");
        JSONArray biomes = extras.getJSONArray("biomes");
        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray sites = extras.getJSONArray("sites");
    }

}

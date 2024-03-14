package ca.mcmaster.se2aa4.island.team220;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseProcessor {
    int range =0;
    String status = "";
    JSONArray biomes = null;
    JSONArray creeks = null;
    JSONArray sites = null;


    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public String getStatus(){
        return status;
    }

    public JSONArray getBiomes(){
        return biomes;
    }

    public JSONArray getCreeks(){
        return creeks;
    }

    public JSONArray getSites(){
        return sites;
    }

    public void readResults(JSONObject response){
        if (response.has("extras")){
            JSONObject extras = response.getJSONObject("extras");
            if (extras.has("range") || extras.has("status")){
                processEcho(extras);
            }else if (extras.has("biomes") || extras.has("creeks") || extras.has("sites")){
                processScan(extras);
            }

        }
    }
    
    public void processEcho(JSONObject response){
        JSONObject extras = response.getJSONObject("extras");
        range = extras.getInt("range");
        status = response.getString("status");
    }

    public void processScan(JSONObject response){
        JSONObject extras = response.getJSONObject("extras");
        biomes = extras.getJSONArray("biomes");
        creeks = extras.getJSONArray("creeks");
        sites = extras.getJSONArray("sites");
    }

}

package ca.mcmaster.se2aa4.island.team220;

import org.json.JSONArray;
import org.json.JSONObject;

public class Information {
    private Integer cost;
    private String status;

    private String found;
    private Integer range;
    private String biome;
    private String site;
    private String creek;

    public Information(Integer cost, String status) {
        this.cost = cost;
        this.status = status;
        this.found = "N/A";
        this.range = 0;
        this.biome = "OCEAN";
        this.site = "N/A";
        this.creek = "N/A";
    }

    public String getFound() { return this.found; }

    public Integer getRange() { return this.range; }

    public String getBiome() { return this.biome; }

    public String getSite() { return this.site; }
    
    public String getCreeks() { return this.creek; }

    public Integer getCost() { return this.cost; }

    public String status() { return this.status; }

    public void setFound(JSONObject extraInfo) {
        if (!extraInfo.isNull("found")) {
            this.found = extraInfo.getString("found");
        }
    }

    public void setRange(JSONObject extraInfo){
        if (!extraInfo.isNull("range")) {
            this.range = extraInfo.getInt("range");
        }
    }

    public void setBiome(JSONObject extraInfo) {
        if (extraInfo.has("biomes")) {
            JSONArray biomes = extraInfo.getJSONArray("biomes");
            this.biome = biomes.getString(0); 
            for (int i = 1; i < biomes.length(); i++) {
                if (!biomes.getString(i).equals("OCEAN")) {
                    this.biome = biomes.getString(i);
                    break;
                }
            }
        }
    }

    public void setSite(JSONObject extraInfo) {
        if (extraInfo.has("sites") && !extraInfo.getJSONArray("sites").isNull(0)) {
            JSONArray sites = extraInfo.getJSONArray("sites");
            if (!site.isEmpty()) {
                this.site = sites.getString(0);
            }
        }

    }

    public void setCreek(JSONObject extraInfo) {
        if (extraInfo.has("creeks") && !extraInfo.getJSONArray("creeks").isNull(0)) {
            JSONArray creek = extraInfo.getJSONArray("creeks");
            if (!creek.isEmpty()) {
                if (this.creek != "N/A") {
                    this.creek = this.creek + ", " + creek.getString(0);
                } else {
                    this.creek = creek.getString(0);
                }
            }
        }
    }
}
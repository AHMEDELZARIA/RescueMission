package ca.mcmaster.se2aa4.island.team220;

import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

public class Information {
    private Actions actionTaken;
    private Integer cost;
    private Boolean status;

    private JSONObject extras;
    // extras attributes
    private JSONArray creeks;
    private JSONArray biomes;
    private JSONArray sites;
    private MapFeature found;
    private Integer range;

    public Information(Actions actionTaken, Integer cost, JSONObject extras, Boolean status) {
        this.actionTaken = actionTaken;
        this.cost = cost;
        this.status = status;
        this.extras = extras;
        updatedAttributes(this.extras);
    }

    private void updatedAttributes(JSONObject extras) {
        try {
            this.creeks = Optional.ofNullable(extras.getJSONArray("creeks")).orElse(null);
        } catch (Exception e) {
            this.creeks = null;
        }
        try {
            this.biomes = Optional.ofNullable(extras.getJSONArray("biomes")).orElse(null);
        } catch (Exception e) {
            this.biomes = null;
        }
        try {
            this.sites = Optional.ofNullable(extras.getJSONArray("sites")).orElse(null);
        } catch (Exception e) {
            this.sites = null;
        }

        try {
            String foundAttribute = Optional.ofNullable(extras.getString("found")).orElse(null);
            if (foundAttribute != null) {
                if (foundAttribute.equals("OUT_OF_RANGE")) {
                    this.found = MapFeature.OUTOFBOUNDS;
                } else {
                    this.found = MapFeature.LAND;
                }
            }
        } catch (Exception e) {
            this.found = null;
        }

        try {
            this.range = Optional.ofNullable(extras.getInt("range")).orElse(null);
        } catch (Exception e) {
            this.range = null;
        }
    }

    public Actions getActionTaken() {
        return this.actionTaken;
    }

    public Integer getCost() {
        return this.cost;
    }

    public Boolean status() {
        return this.status;
    }

    public JSONArray getCreeks() {
        return this.creeks;
    }

    public JSONArray getBiomes() {
        return this.biomes;
    }

    public JSONArray getSites() {
        return this.sites;
    }

    public MapFeature getFound() {
        return this.found;
    }

    public Integer getRange() {
        return this.range;
    }

}

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
        return new Information(action, cost, null, status);
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




}

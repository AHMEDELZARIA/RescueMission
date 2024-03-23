package ca.mcmaster.se2aa4.island.team220;

import org.json.JSONObject;

public class Translator {

    public Information translateDecision(Actions action, JSONObject response) {
        Integer cost = response.getInt("cost");

        JSONObject extras = response.getJSONObject("extras");

        Boolean status;
        if (response.getString("status").equals("OK")) {
            status = true;
        } else {
            status = false;
        }

        return new Information(action, cost, extras, status);
    }
}

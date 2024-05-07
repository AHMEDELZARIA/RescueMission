package ca.mcmaster.se2aa4.island.team220;

import ca.mcmaster.se2aa4.island.team220.statemachine.Actions;
import org.json.JSONObject;

/**
 * Translator class that parses the JSON results from an action into an Information object/
 */
public class Translator {

    /**
     * Translates the results from the decision taken into an Information object
     * @param action Action that was performed in the decision
     * @param response JSONObject results from the decision
     * @return Information object representing the results of the decision
     */
    public Information translateDecision(Actions action, JSONObject response) {
        // Parse the cost of the action
        Integer cost = response.getInt("cost");
        // Parse the extras JSONObject
        JSONObject extras = response.getJSONObject("extras");
        // Parse the status of the drone after the action
        Boolean status = response.getString("status").equals("OK");

        return new Information(action, cost, extras, status);
    }
}

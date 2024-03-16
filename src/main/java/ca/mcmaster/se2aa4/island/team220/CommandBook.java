package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import org.json.JSONObject;

public class CommandBook {

    private HashMap<String, JSONObject> command;
    JSONObject decision = new JSONObject();

    public CommandBook() {
        command = new HashMap<>();
        this.command.put("stop", decision.put("action", "stop"));
    }

    public JSONObject getCommand() {
        return command.get("stop");
    }
    
}

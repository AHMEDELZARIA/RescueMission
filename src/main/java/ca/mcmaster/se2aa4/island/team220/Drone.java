package ca.mcmaster.se2aa4.island.team220;

import org.json.JSONObject;

public class Drone {

    private Integer battery;
    private Direction heading;

    public Drone(Integer battery, Direction heading) {
        this.battery = battery;
        this.heading = heading;
    }

    public Integer getBattery() { return this.battery; }

    public Direction getHeading() { return this.heading; }
    
    public void setBattery(int battery) {
        this.battery = battery;
    }
    public void setDirection(Direction direction) {
        this.heading = direction;
    }
    public boolean Echo(JSONObject echo){
        JSONObject extras = echo.getJSONObject("extras");
        String found = extras.getString("found");
        if (found.equals("GROUND")){
            return true; 
        }else{
            return false; 
        }
    }

    public void moveForward(){
        
    }



        // public Direction getLeft() {
    //     switch(heading) {
    //         case "E":
    //         return "N";
    //     }
    // }
    
}

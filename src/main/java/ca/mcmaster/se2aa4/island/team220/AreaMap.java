package ca.mcmaster.se2aa4.island.team220;

import java.util.LinkedHashMap;
import java.util.Map;

public class AreaMap {

    // Use a LinkedHashMap as order of insertion is important to us
    private Map<Point, MapFeature> map; // maps the coordinate point to the area type it represents (ex. land, creek)

    public AreaMap() { // constructor
        map = new LinkedHashMap<>();
        this.map.put(new Point(0,0), MapFeature.OCEAN); 
    }

    public void addPoint(Point point, MapFeature feature) {
        if (!map.containsKey(point)) { map.put(point, feature); } 
    }

    public MapFeature getPoint(Point point) { 
        if (map.containsKey(point)) {
            return this.map.get(point); 
        } else {
            return null; 
        }
    }

}

package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;

/**
 * Relevant Coordinate System that represents the explored area of a grid
 */
public class AreaMap {
    private Map<Point, MapTile> map; // Relevant Coordinate System
    private Point currentPosition;
    private BiomeMapper biomeMapper; // Maps all possible biomes to either LAND or OCEAN
    private Drone drone;

    // Holds the surrounding map information given the drone's current position
    private Information decisionResults;
    private MapTerrain forward;
    private Integer forwardRange;
    private MapTerrain right;
    private Integer rightRange;
    private MapTerrain left;
    private Integer leftRange;

    // Holds key information about POIs of Island
    private Point emergencySite;
    private Point closestCreek;
    private Double minDistance;

    /**
     * Create an AreaMap which maps the exploration of a drone on a grid
     * @param drone Drone which is used to explore a given grid
     */
    public AreaMap(Drone drone) {
        // LinkedHashMap implementation maintains order of additions which is relevant to attaining the current position of drone
        this.map = new HashMap<>();
        // Starting coordinate
        this.currentPosition = new Point(0,0);
        this.biomeMapper = new BiomeMapper();
        this.drone = drone;
        this.decisionResults = null;
        this.forward = MapTerrain.UNKNOWN;
        this.forwardRange = 0;
        this.right = MapTerrain.UNKNOWN;
        this.rightRange = 0;
        this.left = MapTerrain.UNKNOWN;
        this.leftRange = 0;
        this.emergencySite = null;
        this.minDistance = Double.MAX_VALUE;
        this.closestCreek = null;
    }

    /**
     * Adds a coordinate to the coordinate system. Each coordinate has a Point and an associated MapTile.
     * @param point Point to be added to the relevant coordinate system
     * @param tile MapTile representing that point's tile
     */
    public void addPoint(Point point, MapTile tile) {
        if (!this.map.containsKey(point)) {
            this.map.put(point, tile);
        }
    }

    /**
     * Gives the terrain of the tile corresponding to a point
     * @param point Point of interest
     * @return MapTerrain associated to point
     */
    public MapTerrain getPointTerrain(Point point) {
        if (this.map.containsKey(point)) {
            return this.map.get(point).getTerrain();
        } else {
            return MapTerrain.UNKNOWN;
        }
    }

    /**
     * Determines the closest creek to the emergency site on the island.
     * @return String of closest creek's id or if not found, a message to indicate so
     */
    public String getClosestCreek() {
        // Find the emergency site
        for (Map.Entry<Point, MapTile> coordinate : this.map.entrySet()) {
            Point coordinatePoint = coordinate.getKey();
            MapTile coordinateTile = this.map.get(coordinatePoint);

            if (coordinateTile.hasSite()) {
                this.emergencySite = coordinatePoint;
            }
        }
        // If no emergency sites are found, stop\
        if (this.emergencySite == null) { return "No Emergency site was found!"; }

        // Find the creeks and compare to the site
        for (Map.Entry<Point, MapTile> coordinate : this.map.entrySet()) {
            Point coordinatePoint = coordinate.getKey();
            MapTile coordinateTile = this.map.get(coordinatePoint);

            if (coordinateTile.hasCreeks() && !coordinateTile.hasSite()) {
                Double distance = coordinatePoint.calcDistance(emergencySite);
                if (distance < this.minDistance) {
                    this.minDistance = distance;
                    this.closestCreek = coordinatePoint;
                }
            }
        }
        // Return results
        if (this.closestCreek == null) {
            return "No creeks were found!";
        } else {
            return this.map.get(closestCreek).getCreeks();
        }
    }

    public Integer getForwardRange() {
        return this.forwardRange;
    }

    public Integer getLeftRange() {
        return this.leftRange;
    }

    public Integer getRightRange() {
        return this.rightRange;
    }

    private void updateGetForwardRange() {
        if (this.decisionResults.getRange() != null) {
            this.forwardRange = this.decisionResults.getRange();
        }
    }

    private void updateGetLeftRange() {
        if (this.decisionResults.getRange() != null) {
            this.leftRange = this.decisionResults.getRange();
        }
    }

    private void updateGetRightRange() {
        if (this.decisionResults.getRange() != null) {
            this.rightRange = this.decisionResults.getRange();
        }
    }

    public MapTerrain getForward() {
        return this.forward;
    }

    public MapTerrain getLeft() {
        return this.left;
    }

    public MapTerrain getRight() {
        return this.right;
    }

    private void updateGetForward() {
        if (this.decisionResults.getFound() == MapTerrain.LAND) {
            this.forward = MapTerrain.LAND;
        } else {
            this.forward = MapTerrain.OUTOFBOUNDS;
        }
    }

    private void updateGetLeft() {
        if (this.decisionResults.getFound() == MapTerrain.LAND) {
            this.left = MapTerrain.LAND;
        } else {
            this.left = MapTerrain.OUTOFBOUNDS;
        }
    }

    private void updateGetRight() {
        if (this.decisionResults.getFound() == MapTerrain.LAND) {
            this.right = MapTerrain.LAND;
        } else {
            this.right = MapTerrain.OUTOFBOUNDS;
        }
    }

    /**
     * Updates the map accordingly, based on the action performed
     * @param decisionResults Information Object holding all relevant results from the action performed
     */
    public void update(Information decisionResults) {
        this.decisionResults = decisionResults;
        Actions actionTaken = this.decisionResults.getActionTaken();

        switch (actionTaken) {
            case ECHOFORWARD:
                updateGetForward();
                updateGetForwardRange();
                break;
            case ECHOLEFT:
                updateGetLeft();
                updateGetLeftRange();
                break;
            case ECHORIGHT:
                updateGetRight();
                updateGetRightRange();
                break;
            case FLY:
                this.currentPosition = this.currentPosition.translateForward(drone.getPrevHeading());
                break;
            case TURNLEFT:
                this.currentPosition = this.currentPosition.translateForwardLeft(drone.getPrevHeading());
                break;
            case TURNRIGHT:
                this.currentPosition = this.currentPosition.translateForwardRight( drone.getPrevHeading());
                break;
            case SCAN:
                JSONArray creeks = this.decisionResults.getCreeks();
                JSONArray sites = this.decisionResults.getSites();
                MapTerrain terrain = determinePointTerrain(this.decisionResults.getBiomes());
                MapTile tile = new MapTile(creeks, sites, terrain);
                addPoint(this.currentPosition, tile);
                break;
            default:
                break;
        }
    }

    /**
     * @return MapTerrain of the current position's point
     */
    private MapTerrain determinePointTerrain(JSONArray biomes) {
        if (biomes == null) {
            return null;
        } else {
            for (int i = 0; i < biomes.length(); i++) {
                if (this.biomeMapper.isLand(biomes.getString(i))) {
                    return MapTerrain.LAND;
                }
            }
            return MapTerrain.OCEAN;
        }
    }
}

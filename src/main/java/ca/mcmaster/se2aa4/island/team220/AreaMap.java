package ca.mcmaster.se2aa4.island.team220;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

public class AreaMap {
    // Use a LinkedHashMap as order of insertion is important to us
    private Map<Point, MapTile> map; // maps the coordinate point to the area type it represents (ex. land, creek)
    private BiomeMapper biomeMapper = new BiomeMapper();
    private Information decisionResults;
    private MapFeature forward;
    private Integer forwardRange;
    private MapFeature right;
    private Integer rightRange;
    private MapFeature left;
    private Integer leftRange;
    private Drone drone;
    private Point emergencySite;
    private Point closestCreek;
    private Double minDistance;
    private final Logger logger = LogManager.getLogger();


    public AreaMap(Drone drone) {
        this.map = new LinkedHashMap<>();
        this.map.put(new Point(0, 0), new MapTile(MapFeature.OCEAN));
        this.decisionResults = null;
        this.forward = MapFeature.UNKNOWN;
        this.forwardRange = 0;
        this.right = MapFeature.UNKNOWN;
        this.rightRange = 0;
        this.left = MapFeature.UNKNOWN;
        this.leftRange = 0;
        this.drone = drone;
        this.emergencySite = null;
        this.minDistance = Double.MAX_VALUE;
        this.closestCreek = null;
    }

    public void addPoint(Point point, MapFeature feature) {
        if (!this.map.containsKey(point)) {
            this.map.put(point, new MapTile(feature));
        } else {
            this.map.remove(point);
            this.map.put(point, new MapTile(feature));
        }
    }

    public void addPointFeature(Point point, MapFeature feature) {
        if (this.map.containsKey(point)) {
            if (getPointFeature(point) == MapFeature.UNKNOWN) {
                this.map.put(point, new MapTile(feature));
            }
        } else {
            addPoint(point, feature);
        }
    }

    public void addPointCreeks(Point point) {
        if (this.map.containsKey(point)) {
            JSONArray creeks = decisionResults.getCreeks();
            if (creeks != null) {
                for (int i = 0; i < creeks.length(); i++) {
                   map.get(point).addCreek(creeks.getString(i));
                }
            }
        }
    }

    public void addPointSites(Point point) {
        if (this.map.containsKey(point)) {
            JSONArray sites = decisionResults.getSites();
            if (sites != null) {
                for (int i = 0; i < sites.length(); i++) {
                    map.get(point).addEmergencySite(sites.getString(i));
                }
            }
        }
    }

    public String printMap() {
        String result = "";
        for (Map.Entry<Point, MapTile> entry : map.entrySet())
            result += "Key: " + entry.getKey() + ", Value: " + entry.getValue().toString() + " " + "| ";
        return result;
    }

    public MapFeature getPointFeature(Point point) {
        if (this.map.containsKey(point)) {
            return this.map.get(point).getTerrain();
        } else {
            return MapFeature.UNKNOWN;
        }
    }

    public Point currentPosition() {
        Set<Point> pointSet = this.map.keySet();
        Point lastPoint = null;
        for (Point point : pointSet) {
            lastPoint = point;
        }
        return lastPoint;
    }

    public void update(Information decisionResults) {
        this.decisionResults = decisionResults;

        switch (this.decisionResults.getActionTaken()) {
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
                addPoint(currentPosition().translateForward(drone.getPrevHeading()), MapFeature.UNKNOWN);
                break;
            case TURNLEFT:
                addPoint(currentPosition().translateForwardLeft(drone.getPrevHeading()), MapFeature.UNKNOWN);
                break;
            case TURNRIGHT:
                addPoint(currentPosition().translateForwardRight( drone.getPrevHeading()), MapFeature.UNKNOWN);
                break;
            case SCAN:
                addTileDetails();
                break;
            case STOP:
                break;
            default:
                break;
        }
    }

    private MapFeature determinePointFeature() {
        if (this.decisionResults.getBiomes() != null) {
            JSONArray biomes = this.decisionResults.getBiomes();
            for (int i = 0; i < biomes.length(); i++) {
                if (this.biomeMapper.isLand(biomes.getString(i))) {
                    return MapFeature.LAND;
                }
            }
            return MapFeature.OCEAN;
        } else {
            return null;
        }
    }

    private void addTileDetails() {
        addPointFeature(currentPosition(), determinePointFeature());
        if (this.decisionResults.getCreeks() != null) {
            addPointCreeks(currentPosition());
        }
        if (this.decisionResults.getSites() != null) {
            addPointSites(currentPosition());
        }
    }

    public String getClosestCreek() {
        for (Map.Entry<Point, MapTile> entry : this.map.entrySet()) {
            Point currentPoint = entry.getKey();
            MapTile currentTile = entry.getValue();

            if (currentTile.hasSite()) {
                this.emergencySite = currentPoint;
                logger.info("EMERGENCY SITE: {}", this.emergencySite);
            } else if (currentTile.hasCreeks() && this.emergencySite != null) {
                Double distance = currentPoint.calcDistance(emergencySite);
                if (distance < this.minDistance) {
                    this.minDistance = distance;
                    this.closestCreek = currentPoint;
                    logger.info("CLOSEST CREEK: {}", this.closestCreek);
                    logger.info("DISTANCE TO SITE {}", this.minDistance);
                }
            }
        }

        if (this.emergencySite == null) {
            return "No Emergency Site was found!";
        } else if (this.closestCreek == null) {
            return "No creek was found!";
        } else {
            return this.map.get(closestCreek).getCreeks();
        }
    }

    private void updateGetForward() {
        if (this.decisionResults.getFound() == MapFeature.LAND) {
            this.forward = MapFeature.LAND;
        } else {
            this.forward = MapFeature.OUTOFBOUNDS;
        }
    }

    private void updateGetForwardRange() {
        if (this.decisionResults.getRange() != null) {
            this.forwardRange = this.decisionResults.getRange();
        }
    }

    public MapFeature getForward() {
        return this.forward;
    }

    public Integer getForwardAmount() {
        return this.forwardRange;
    }

    private void updateGetLeft() {
        if (this.decisionResults.getFound() == MapFeature.LAND) {
            this.left = MapFeature.LAND;
        } else {
            this.left = MapFeature.OUTOFBOUNDS;
        }
    }

    private void updateGetLeftRange() {
        if (this.decisionResults.getRange() != null) {
            this.leftRange = this.decisionResults.getRange();
        }
    }

    public MapFeature getLeft() {
        return this.left;
    }

    public Integer getLeftAmount() {
        return this.leftRange;
    }

    private void updateGetRight() {
        if (this.decisionResults.getFound() == MapFeature.LAND) {
            this.right = MapFeature.LAND;
        } else {
            this.right = MapFeature.OUTOFBOUNDS;
        }
    }

    private void updateGetRightRange() {
        if (this.decisionResults.getRange() != null) {
            this.rightRange = this.decisionResults.getRange();
        }
    }

    public MapFeature getRight() {
        return this.right;
    }

    public Integer getRightAmount() {
        return this.rightRange;
    }

}

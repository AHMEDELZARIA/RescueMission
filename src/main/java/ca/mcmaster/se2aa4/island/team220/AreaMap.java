package ca.mcmaster.se2aa4.island.team220;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

public class AreaMap {
    // Use a LinkedHashMap as order of insertion is important to us
    private Map<Point, MapFeature> map; // maps the coordinate point to the area type it represents (ex. land, creek)
    private BiomeMapper biomeMapper = new BiomeMapper();
    private Information decisionResults;
    private MapFeature forward;
    private Integer forwardRange;
    private MapFeature right;
    private Integer rightRange;
    private MapFeature left;
    private Integer leftRange;

    public AreaMap() {
        this.map = new LinkedHashMap<>();
        this.map.put(new Point(0, 0), MapFeature.OCEAN);
        this.decisionResults = null;
        this.forward = MapFeature.UNKNOWN;
        this.forwardRange = 0;
        this.right = MapFeature.UNKNOWN;
        this.rightRange = 0;
        this.left = MapFeature.UNKNOWN;
        this.leftRange = 0;
    }

    public void addPoint(Point point, MapFeature feature) {
        if (!this.map.containsKey(point)) {
            this.map.put(point, feature);
        }
    }

    public void addPointFeature(Point point, MapFeature feature) {
        if (this.map.containsKey(point)) {
            if (getPointFeature(point) == MapFeature.UNKNOWN) {
                this.map.put(point, feature);
            }
        } else {
            addPoint(point, feature);
        }
    }

    public MapFeature getPointFeature(Point point) {
        if (this.map.containsKey(point)) {
            return this.map.get(point);
        } else {
            return null;
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
                // addPoint(currentPosition().translateForward(null), MapFeature.UNKNOWN);
                break;
            case TURNLEFT:
                // addPoint(currentPosition().translateForwardLeft(null), MapFeature.UNKNOWN);
                break;
            case TURNRIGHT:
                // addPoint(currentPosition().translateForwardRight(null), MapFeature.UNKNOWN);
                break;
            case SCAN:
                // addPointFeature(currentPosition(), determinePointFeature());
                // Add mechanism to detect creeks and emergency sites
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

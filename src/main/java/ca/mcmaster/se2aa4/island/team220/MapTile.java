package ca.mcmaster.se2aa4.island.team220;

import org.json.JSONArray;

import java.util.List;
import java.util.ArrayList;

/**
 * Class representing the tile of a map. The type of terrain it holds, creeks if any, and emergency site if applicable.
 */
public class MapTile {
    private MapTerrain terrain;
    private List<String> creeks = new ArrayList<>();
    private List<String> emergencySites = new ArrayList<>();

    /**
     * Creates a MapTile representation.
     * @param terrain MapTerrain of the current tile
     */
    public MapTile(JSONArray creeks, JSONArray sites, MapTerrain terrain) {
        buildTile(creeks, sites, terrain);
    }

    private void buildTile(JSONArray tileCreeks, JSONArray tileSites, MapTerrain tileTerrain) {
        // Assign creeks
        if (tileCreeks != null) {
            for (int i = 0; i < tileCreeks.length(); i++) {
                this.creeks.add(tileCreeks.getString(i));
            }
        } else { this.creeks = null; }

        // Assign emergency sites
        if (tileSites != null) {
            for (int i = 0; i < tileSites.length(); i++) {
                this.emergencySites.add(tileSites.getString(i));
            }
        } else { this.emergencySites = null; }

        // Assign terrain
        this.terrain = tileTerrain;
    }

    public MapTerrain getTerrain() { return this.terrain; }

    public String getCreeks() {
        if (!this.creeks.isEmpty()) { return this.creeks.get(0); }
        else { return null; }
    }

    public Boolean hasCreeks() { return !this.creeks.isEmpty(); }

    /**
     * Prints the creek(s) id associated with the MapTile
     * @return String creek id
     */
    public String printCreeks() {
        StringBuilder creeks = new StringBuilder();
        for (String creek : this.creeks) {
            creeks.append(creek).append(" ");
        }
        return creeks.toString();
    }

    public Boolean hasSite() { return !this.emergencySites.isEmpty(); }

    /**
     * Prints the emergency site id associated with the MapTile
     * @return String emergency site id
     */
    public String printSite() {
        StringBuilder site = new StringBuilder();
        for (String emergencySite : this.emergencySites) {
            site.append(emergencySite).append(" ");
        }
        return site.toString();
    }

    @Override
    public String toString() {
        return "[ terrain: " + this.terrain + ", Creeks: " + printCreeks() + ", Site: " + printSite() + ", hasSite: " + hasSite() + ", hasCreek: " + hasCreeks() + " ]";
    }

}

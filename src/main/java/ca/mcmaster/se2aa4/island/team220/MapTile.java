package ca.mcmaster.se2aa4.island.team220;

import java.util.List;
import java.util.ArrayList;


public class MapTile {
    private MapFeature feature;
    private List<String> creeks;
    private List<String> emergencySites;
    private Boolean hasCreeks;
    private Boolean hasEmergency;

    public MapTile(MapFeature feature) {
        this.feature = feature;
        this.creeks = new ArrayList<>();
        this.emergencySites = new ArrayList<>();
        this.hasCreeks = false;
        this.hasEmergency = false;
    }

    public void setTerrain(MapFeature terrain) {
        this.feature = terrain;
    }

    public MapFeature getTerrain() { return this.feature; }

    public void addCreek(String id) {
        this.creeks.add(id);
        this.hasCreeks = true;
    }

    public void addEmergencySite(String id) {
        this.emergencySites.add(id);
        this.hasEmergency = true;
    }

    public Boolean hasCreeks() {
        return hasCreeks;
    }

    public Boolean hasSite() {
        return hasEmergency;
    }

    public String printCreeks() {
        String creeks = "";
        for (int i = 0; i < this.creeks.size(); i++) {
            creeks += this.creeks.get(i) + " ";
        }
        return creeks;
    }

    public String getCreeks() {
        if (!this.creeks.isEmpty()) {
            return this.creeks.get(0);
        } else {
            return null;
        }
    }

    public String printSite() {
        String site = "";
        for (int i = 0; i < this.emergencySites.size(); i++) {
            site += this.emergencySites.get(i) + " ";
        }
        return site;
    }

    @Override
    public String toString() {
        return "[ Feature: " + this.feature + ", Creeks: " + printCreeks() + ", Site: " + printSite() + ", hasSite: " + this.hasEmergency + ", hasCreek: " + this.hasCreeks + " ]";
    }

}

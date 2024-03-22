package ca.mcmaster.se2aa4.island.team220;

public class Information {
    private Integer cost;
    private String status;

    private String found;
    private Integer range;
    private String biome;
    private String site;

    public Information(Integer cost, String status) {
        this.cost = cost;
        this.status = status;
        this.found = "N/A"; // replaced "OUT_OF_RANGE"
        this.range = 0;
        this.biome = "OCEAN";
        this.site = "N/A";
    }

    // Added 19/03

    public String getFound() {
        return this.found;
    }

    public Integer getRange() {
        return this.range;
    }

    public String getBiome() {
        return this.biome;
    }

    public String getSite() {
        return this.site;
    }
    
    public void setFound(String found) {
        this.found = found;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public void setSite(String site) {
        if (!site.isEmpty()) {
            this.site = site;
        }
    }


    public Integer getCost() {
        return this.cost;
    }

    public String status() {
        return this.status;
    }
}
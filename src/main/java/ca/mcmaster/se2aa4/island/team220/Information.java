package ca.mcmaster.se2aa4.island.team220;

public class Information {
    private Integer cost;
    private Boolean status;

    private String found;
    private String biome;
    private String site;

    public Information(Integer cost, String biome, Boolean status) {
        this.cost = cost;
        this.status = status;
    }

    // Added 19/03
    public void setFound(String found) {
        this.found = found;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public void setSite(String site) {
        if (site == null) {
            this.site = "N/A";
        } else {
            this.site = site;
        }
    }


    public Integer getCost() {
        return this.cost;
    }

    public Boolean status() {
        return this.status;
    }
}
package ca.mcmaster.se2aa4.island.team220;

public class Information {
    private Action actionTaken;
    private Integer cost;
    private Boolean status;

    public Information(Action actionTaken, Integer cost, String biome, Boolean status) {
        this.actionTaken = actionTaken;
        this.cost = cost;
        this.status = status;
    }

    public Action getActionTaken() {
        return this.actionTaken;
    }

    public Integer getCost() {
        return this.cost;
    }

    public Boolean status() {
        return this.status;
    }
}

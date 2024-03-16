package ca.mcmaster.se2aa4.island.team220;

public class GridSearch implements ISearchAlgorithm {

    @Override
    public void searchArea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'determineDecision'");
    }


    /* GridSearch Algorithm:
     * While Island not found:
     * echo straight (depends on heading)
     * if echo straight = island found -> fly there (# of fly commands = # of range tiles)
     * else, continue
     * echo right (depends on heading)
     * if echo right = island found -> heading right (depends on heading), fly there
     * else, continue
     * echo left (depends on heading)
     * if echo left = island found -> heading left (depends on heading), fly there
     * else, continue
     * fly straight (if island not found after one round of echoing in all directions)
    */

}

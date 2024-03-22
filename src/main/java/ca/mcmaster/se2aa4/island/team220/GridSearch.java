package ca.mcmaster.se2aa4.island.team220;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GridSearch implements ISearchAlgorithm {

    private final Logger logger = LogManager.getLogger(); // for logger instructions
    private CommandBook command = new CommandBook(); // ADDED 19/03

    private int searchCount = 0; // keeps track of number of times searchSite() is run
    private String scanSites = ""; // returns the site if the site is found from 'scan' results 
    private boolean down = true; // determines whether the drone is facing upwards or downwards when it exits the island for intoPosition()
    private Boolean interlaceCheck = false;

    Translator translator = new Translator();
    private GridQueue queue = new GridQueue();
    private String mode = "findIsland";

    @Override
    public void searchArea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'determineDecision'");
    }

    // Determines next decision to make (called from Explorer)
    public String makeDecision(String found, String biome, Compass compass) {
        if (queue.isEmpty()) {
            refillQueue(found, biome, compass);
        }
        return queue.dequeue();
    }

    // OFFICIAL REFILL METHOD
    public void refillQueue(String found, String biome, Compass compass) {

        // condition for "checkMoreGround" mode
        if (this.mode == "checkMoreGround" && found.equals("GROUND")) {
            this.mode = "reachIsland";
        } else if (this.mode == "checkMoreGround" && !found.equals("GROUND")) {
            this.mode = "intoPosition";
        }

        // condition for "uTurn" mode
        if (this.mode == "uTurn" && found.equals("GROUND")) {
            this.mode = "reachIsland";
        } else if (this.mode == "uTurn" && found.equals("OUT_OF_RANGE")) {
            if (this.interlaceCheck) {
                this.mode = "stop";
            } else {
                this.mode = "loopAround";
            }
        }

        // condition for "loopBack" mode
        if (this.mode == "loopAround" && this.interlaceCheck) { 
            this.mode = "reachIsland";
        }

        switch (this.mode) {
            case "findIsland": // FIND ISLAND
                if (found.equals("GROUND")) {
                    this.mode = "faceIsland";
                } else {
                    findIsland(compass);
                    break;
                }
                
            case "faceIsland": // FACE ISLAND
                faceIsland(compass);
                this.mode = "reachIsland";
                break;

            case "reachIsland": // REACH ISLAND
                if (!biome.equals("OCEAN")) {
                    this.mode = "searchSite";
                } else {
                    reachIsland();
                    break;
                }

            case "searchSite": // SEARCH SITE
                if (biome.equals("OCEAN")) {
                    this.searchCount++;
                    this.mode = "checkMoreGround"; // "intoPosition"
                } else {
                    searchSite();
                    break;
                }

            case "checkMoreGround": // CHECK FOR MORE GROUND BEFORE U-TURNING
                checkMoreGround(compass);
                break;
            
            case "intoPosition":  // INTO POSITION
                if (found.equals("OUT_OF_RANGE")) {
                    this.mode = "uTurn";
                } else {
                    intoPosition(compass);
                    break;
                }

            case "uTurn": // INTERLACE A + INTERLACE B
                uTurn(compass);
                this.down = !this.down;
                // logger.info(this.down);
                break;

            case "loopAround": // INTERLACE C
                loopAround(compass);
                this.interlaceCheck = true;
                break;

            case "stop":
                queue.enqueue(command.getStop());
                break;
        }
        // logger.info(this.down);
        logger.info("########################################### {}", this.mode);
    }

    // find the Island
    public void findIsland(Compass compass) {
        queue.enqueue(command.getFly());
        queue.enqueue(command.testEchoRight(compass));
        // queue.enqueue(command.getEchoSouth()); // replace with queue.enqueue(command.testEchoRight(compass)); - set parameter to Compass compass
        // queue.enqueue(command.getEchoEast());
        // queue.enqueue(command.getEchoNorth());
        // queue.enqueue(command.getScan());
    }

    public void faceIsland(Compass compass) {
        queue.enqueue(command.getTurnRight(compass));
    }

    public void reachIsland() {
        queue.enqueue(command.getFly());
        queue.enqueue(command.getScan());
    }

    public void searchSite() {
        queue.enqueue(command.getFly());
        queue.enqueue(command.getScan());
    }

    public void checkMoreGround(Compass compass) {
        queue.enqueue(command.testEchoForward(compass));
    }

    public void intoPosition(Compass compass) { 
        // logger.info(this.down);
        queue.enqueue(command.getFly());
        if ((this.interlaceCheck == false && this.down == true) || (this.interlaceCheck == true && this.down == false)) { // different booleans
            queue.enqueue(command.testEchoLeft(compass));
        } else if ((this.interlaceCheck == true && this.down == true) || (this.interlaceCheck == false && this.down == false)) { // same booleans
            queue.enqueue(command.testEchoRight(compass));
        }
    }

    public void uTurn(Compass compass) { // INTERLACE A AND INTERLACE B COMBINED
        if ((this.interlaceCheck == false && this.down == true) || (this.interlaceCheck == true && this.down == false)) { // opposite booleans
            queue.enqueue(command.getTurnLeft(compass));
            queue.enqueue(command.getTurnLeft(compass));
            queue.enqueue(command.testEchoForward(compass));
        } else if ((this.interlaceCheck == false && this.down == false) || (this.interlaceCheck == true && this.down == true)) { // same booleans
            queue.enqueue(command.getTurnRight(compass));
            queue.enqueue(command.getTurnRight(compass));
            queue.enqueue(command.testEchoForward(compass));
        }
    }

    public void loopAround(Compass compass) {
        if (this.searchCount % 2 == 1) {
            if (this.down == true) {
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.testEchoForward(compass));

            } else {
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.testEchoForward(compass));
            }
        } else {
            if (this.down == true) { 
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.testEchoForward(compass));

            } else {
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.testEchoForward(compass));
            }
        }
    }
}

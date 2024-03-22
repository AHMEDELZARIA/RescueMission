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
    // private Integer currentMode = 0;
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

    /* MODE GUIDE:
     * findIsland = 0
     * faceIsland = 1
     * reachIsland = 2
     * searchSite = 3
     * intoPosition = 4
     * uTurn = 5 // this was interlaceA & interlaceB
     * loopAround = 6 // this was interlaceC
     * Stop = 7
    */

    // OFFICIAL REFILL METHOD
    public void refillQueue(String found, String biome, Compass compass) {
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
                    refillFindIsland(compass);
                    break;
                }
            case "faceIsland": // FACE ISLAND
                refillFaceIsland(compass);
                this.mode = "reachIsland";
                break;
            case "reachIsland": // REACH ISLAND
                if (!biome.equals("OCEAN")) {
                    this.mode = "searchSite";
                } else {
                    refillReachIsland();
                    break;
                }
            case "searchSite": // SEARCH SITE
                if (biome.equals("OCEAN")) {
                    this.searchCount++;
                    this.mode = "intoPosition";
                } else {
                    refillSearchSite();
                    break;
                }
            case "intoPosition":  // INTO POSITION
                if (found.equals("OUT_OF_RANGE")) {
                    this.mode = "uTurn";
                } else {
                    refillIntoPosition(compass);
                    break;
                }
            case "uTurn": // INTERLACE A + INTERLACE B
                refillInterlaceA(compass);
                this.down = !this.down;
                // logger.info(this.down);
                break;
            case "loopAround": // INTERLACE C
                refillInterlaceC(compass);
                this.interlaceCheck = true;
                break;
            case "stop":
                queue.enqueue(command.getStop());
                break;
        }
        logger.info(this.down);
        logger.info("########################################### {}", this.mode);
    }

    // find the Island
    public void refillFindIsland(Compass compass) {
        queue.enqueue(command.getFly());
        queue.enqueue(command.testEchoRight(compass));
        // queue.enqueue(command.getEchoSouth()); // replace with queue.enqueue(command.testEchoRight(compass)); - set parameter to Compass compass
        // queue.enqueue(command.getEchoEast());
        // queue.enqueue(command.getEchoNorth());
        // queue.enqueue(command.getScan());
    }

    public void refillFaceIsland(Compass compass) {
        queue.enqueue(command.getTurnRight(compass));
    }

    public void refillReachIsland() {
        queue.enqueue(command.getFly());
        queue.enqueue(command.getScan());
    }

    public void refillSearchSite() {
        queue.enqueue(command.getFly());
        queue.enqueue(command.getScan());
    }

    public void refillIntoPosition(Compass compass) { 
        logger.info(this.down);
        queue.enqueue(command.getFly());
        if (this.interlaceCheck == true) {
            queue.enqueue(command.getEchoWest());
        } else {
            if (this.down == true) {
                queue.enqueue(command.testEchoLeft(compass));
            } else {
                queue.enqueue(command.testEchoRight(compass));
            }
            // queue.enqueue(command.getEchoEast());
        }
        
        
        /* REPLACE INNER CODE WITH THIS: (parameter is Compass compass)

        if (this.interlaceCheck == false) { // we have done zero/two interlaces - going left to right
            if (this.down = true) {
                queue.enqueue(command.testEchoLeft(compass));
            } else {
                queue.enqueue(command.testEchoRight(compass));
            }
        } else { // we have done one interlace - going right to left
            if (this.down = true) {
                queue.enqueue(command.testEchoRight(compass));
            } else {
                queue.enqueue(command.testEchoLeft(compass));
            }
        } 
        */
        
    }

    public void refillInterlaceA(Compass compass) { // INTERLACE A AND INTERLACE B COMBINED
        if (this.interlaceCheck == false) { // we have done zero/two interlaces - going left to right
            if (this.down == true) {
                logger.info("shouldnt be here");
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getEchoNorth()); // replace with queue.enqueue(command.testEchoForward(compass)); - set parameter to Compass compass
            } else {
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getEchoSouth()); // replace with queue.enqueue(command.testEchoForward(compass)); - set parameter to Compass compass
            }
        } else { // we have done one interlace - going right to left
            if (this.down == true) {
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getEchoNorth()); // replace with queue.enqueue(command.testEchoForward(compass)); - set parameter to Compass compass
            } else {
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getEchoSouth()); // replace with queue.enqueue(command.testEchoForward(compass)); - set parameter to Compass compass
            }
        }
    }

    public void refillInterlaceC(Compass compass) {
        if (this.searchCount % 2 == 1) {
            if (this.down == true) {
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getEchoSouth()); // replace with queue.enqueue(command.testEchoForward(compass)); - set parameter to Compass compass

            } else {
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getEchoNorth()); // replace with queue.enqueue(command.testEchoForward(compass)); - set parameter to Compass compass
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
                queue.enqueue(command.getEchoSouth()); // replace with queue.enqueue(command.testEchoForward(compass)); - set parameter to Compass compass

            } else {
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getEchoNorth()); // replace with queue.enqueue(command.testEchoForward(compass)); - set parameter to Compass compass
            }
        }
    }
}

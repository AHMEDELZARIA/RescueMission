package ca.mcmaster.se2aa4.island.team220;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GridSearch implements ISearchAlgorithm {

    private final Logger logger = LogManager.getLogger(); // for logger instructions
    private CommandBook command = new CommandBook(); // ADDED 19/03

    private Integer searchCount = 0; // keeps track of number of times searchSite() is run
    private String scanSites = ""; // returns the site if the site is found from 'scan' results 
    private Boolean down = true; // determines whether the drone is facing upwards or downwards when it exits the island for intoPosition()
    private Boolean interlaceCheck = false;
    private Boolean start = false;

    // Translator translator = new Translator();
    private GridQueue queue = new GridQueue();
    private String mode = "checkStart"; // replace "findIsland" as start mode (testing for various dron start conditions)

    @Override
    public void searchArea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'determineDecision'");
    }

    // Determines next decision to make (called from Explorer)
    public String makeDecision(String found, Integer range, String biome, Compass compass) {
        if (queue.isEmpty()) {
            if (!this.start) {
                refillStart(found, range, compass);
            } else {
                refillQueue(found, range, biome, compass);
            }
        }
        return queue.dequeue();
    }

    // OFFICIAL REFILL METHOD
    public void refillQueue(String found, Integer range, String biome, Compass compass) {
        logger.info(range);
        // condition for "loopBack" mode
        if (this.mode == "loopAround" && this.interlaceCheck) { 
            this.mode = "reachIsland";
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

        // search site condition
        if (this.mode == "searchSite" && biome.equals("OCEAN")) {
            if (found.equals("GROUND") && !range.equals(0)) {
                this.mode = "reachIsland";
            } else if (found.equals("OUT_OF_RANGE")) {
                this.searchCount++;
                found = "GROUND";
                this.mode = "intoPosition";
            }
        } else if (this.mode == "searchSite" && found.equals("OUT_OF_RANGE") && range < 3) { // this.mode == "searchSite" && !biome.equals("OCEAN")
            this.searchCount++;
            this.mode = "uTurn";
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
                searchSite(compass);
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
        // queue.enqueue(command.testEchoLeft(compass));
    }

    public void faceIsland(Compass compass) {
        queue.enqueue(command.getTurnRight(compass));
    }

    public void reachIsland() {
        queue.enqueue(command.getFly());
        queue.enqueue(command.getScan());
    }

    public void searchSite(Compass compass) {
        queue.enqueue(command.getFly());
        queue.enqueue(command.getScan());
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
        logger.info(this.searchCount);
        logger.info(this.down);
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
                queue.enqueue(command.getFly());
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
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.testEchoForward(compass));
            }
        }
    }

    // ------------------------------------------------------------------------------------------ DRONE START CASE!!! FIX THIS

    public void refillStart(String found, Integer range, Compass compass) {
        logger.info("###################################################### {}", this.mode);
        logger.info(range);
        logger.info(found);

        switch(this.mode) {
            case "checkStart":
                checkStart(compass);
                if (found.equals("GROUND")) {
                    this.mode = "caseBPart1";
                } else if (found.equals("OUT_OF_RANGE") && range == 52) {
                    // this.mode = "done";
                    this.start = true;
                    this.mode = "findIsland";
                    range = 0;
                } else if (found.equals("OUT_OF_RANGE")) {
                    this.mode = "caseAPart1";
                }
                break;
            case "caseAPart1":
                logger.info("###################################################### {}", this.mode);
                caseAPart1(range);
                this.mode = "caseAPart2";
                break;
            case "caseAPart2":
                logger.info("###################################################### {}", this.mode);
                caseAPart2(range, compass);
                // this.mode = "done";
                this.start = true;
                found = "OUT_OF_RANGE";
                range = 0;
                break;
            case "caseBPart1":
                logger.info("###################################################### {}", this.mode);
                caseBPart1(compass);
                this.mode = "caseBPart2";
                break;
            case "caseBPart2":
                logger.info("###################################################### {}", this.mode);
                caseBPart2(range, compass);
                // this.mode = "done";
                this.start = true;
                found = "OUT_OF_RANGE";
                range = 0;
                break;
            case "done":
                this.start = true;
                found = "OUT_OF_RANGE";
                range = 0;
                // queue.enqueue(command.getStop());
                // break;
        }
    }

    public void checkStart(Compass compass) {
        queue.enqueue(command.testEchoForward(compass));
    }

    public void caseAPart1(Integer range) {
        for (int i = 0; i < (range-1); i++) {
            queue.enqueue(command.getFly());
        }
    }

    public void caseAPart2(Integer range, Compass compass) {
        if (range == 0) {
            queue.enqueue(command.getTurnRight(compass));
        } else {
            queue.enqueue(command.getTurnLeft(compass));
        }
    }

    public void caseBPart1(Compass compass) {
        queue.enqueue(command.testEchoLeft(compass));
        queue.enqueue(command.getTurnLeft(compass));

    }

    public void caseBPart2(Integer range, Compass compass) {
        for (int i = 0; i < (range - 2); i++) {
            queue.enqueue(command.getFly());
        }
        queue.enqueue(command.getTurnRight(compass));
    }
}

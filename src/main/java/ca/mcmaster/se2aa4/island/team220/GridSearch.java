package ca.mcmaster.se2aa4.island.team220;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

public class GridSearch implements ISearchAlgorithm {

    private final Logger logger = LogManager.getLogger(); // for logger instructions
    private CommandBook command = new CommandBook(); // ADDED 19/03

    private int searchCount = 0; // keeps track of number of times searchSite() is run
    private String scanSites = ""; // returns the site if the site is found from 'scan' results 
    private boolean down = true; // determines whether the drone is facing upwards or downwards when it exits the island for intoPosition()
    private Boolean interlaceCheck = false;

    Translator translator = new Translator();
    private GridQueue queue = new GridQueue();
    private Integer currentMode = 0;

    @Override
    public void searchArea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'determineDecision'");
    }

    // Main generic code, to be called from Explorer
    public String makeDecision(String found, String biome, Compass compass) {
        if (queue.isEmpty()) {
            refillQueue(found, biome, compass);
        }
        return queue.dequeue(); // dequeue and run command
    }

    // OFFICIAL REFILL METHOD
    public void refillQueue(String found, String biome, Compass compass) {
        logger.info("########################################### {}", this.currentMode);
        if (this.currentMode == 5 && found.equals("GROUND")) { 
            this.currentMode = 2;
        } else if (this.currentMode == 5 && found.equals("OUT_OF_RANGE")) {
            if (this.interlaceCheck){
                this.currentMode = 7; 
            }else {
                this.currentMode = 6;
            }
        }
        if (this.currentMode == 6 && this.interlaceCheck) { // ok basically if interlacecheck = true then go to 2 and then another varible so that if 
            this.currentMode = 2;
        }
        switch (this.currentMode) {
            case 0: // FIND ISLAND
                if (found.equals("GROUND")) {
                    this.currentMode = 1;
                } else {
                    refillFindIsland(); // execute mode0
                    break;
                }
            case 1: // FACE ISLAND
                refillFaceIsland(compass); // execute mode1
                this.currentMode = 2;
                break;
            case 2: // REACH ISLAND
                if (!biome.equals("OCEAN")) {
                    this.currentMode = 3;
                } else {
                    refillReachIsland(); // execute mode2
                    break;
                }
            case 3: // SEARCH SITE
                if (biome.equals("OCEAN")) {
                    this.searchCount++;
                    this.currentMode = 4;
                } else {
                    refillSearchSite(); // execute mode3
                    break;
                }

            case 4:  // INTO POSITION
                if (found.equals("OUT_OF_RANGE")) {
                    this.currentMode = 5;
                } else {
                    refillIntoPosition(); // execute mode4
                    break;
                }

            case 5: // INTERLACE A + INTERLACE B
                refillInterlaceA(compass); // execute mode5
                this.down = !this.down;
                break;
            case 6: // INTERLACE C
            logger.info("--------------------------------------------------------------------reach here");
                refillInterlaceC(compass); // execute mode7
                this.interlaceCheck = true;
                break;
            case 7:
                logger.info("yipee");
                queue.enqueue(command.getStop()); // use stop command, end gridSearch
                break;
        }
    }

    public void refillFindIsland() {
        queue.enqueue(command.getFly());
        queue.enqueue(command.getEchoSouth());
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

    public void refillIntoPosition() { // decide what this.down is here, if it is true or false
        queue.enqueue(command.getFly());
        queue.enqueue(command.getEchoEast());
    }

    public void refillInterlaceA(Compass compass) { // INTERLACE A AND INTERLACE B COMBINED
        if (this.down == true) {
            queue.enqueue(command.getTurnLeft(compass));
            queue.enqueue(command.getTurnLeft(compass));
            queue.enqueue(command.getEchoNorth());
        } else {
            queue.enqueue(command.getTurnRight(compass));
            queue.enqueue(command.getTurnRight(compass));
            queue.enqueue(command.getEchoSouth());
        }
    }

    public void refillInterlaceC(Compass compass) {
        logger.info("--------------------------------------------INTERLACE C HAPPENED");
        logger.info(this.searchCount);
        if (this.searchCount % 2 == 1) {
            if (this.down == false) {
                logger.info("this.down is false");
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnRight(compass));
            } else {
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnLeft(compass));
            }
        } else {
            if (this.down == true) {
                logger.info("this.down is true");
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getTurnRight(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnRight(compass));        
            } else {
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getTurnLeft(compass));
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getFly());
                queue.enqueue(command.getTurnLeft(compass));
            }
        }
    }
}


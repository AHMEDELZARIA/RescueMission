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
        if (this.currentMode == 6 && found.equals("GROUND")) {
            logger.info("we made it");
            this.currentMode = 2;
            biome = "OCEAN";
        }
        if (this.currentMode == 7) {
            this.currentMode = 2;
            biome = "OCEAN";
        }
        switch (this.currentMode) {
            case 0:
                if (found.equals("GROUND")) {
                    this.currentMode = 1;
                } else {
                    refillFindIsland(); // execute mode0
                    break;
                }
            case 1:
                refillFaceIsland(compass); // execute mode1
                this.currentMode = 2;
                break;
            case 2:
                if (!biome.equals("OCEAN")) {
                    this.currentMode = 3;
                } else {
                    refillReachIsland(); // execute mode2
                    break;
                }
            case 3:
                if (biome.equals("OCEAN")) {
                    this.currentMode = 4;
                } else {
                    refillSearchSite(); // execute mode3
                    break;
                }

            case 4:
                if (found.equals("OUT_OF_RANGE")) {
                    this.currentMode = 5;
                } else {
                    refillIntoPosition(); // execute mode4
                    break;
                }

            case 5:
                refillInterlaceA(compass); // execute mode5
                this.currentMode = 6;
                break;
            case 6:
                refillInterlaceB(); // execute mode6
                if (this.interlaceCheck) {
                    this.currentMode = 8;
                } else {
                    this.currentMode = 7;
                }
                break;
            case 7:
                refillInterlaceC(compass); // execute mode7
                this.interlaceCheck = true;
                break;
            case 8:
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

    public void refillInterlaceA(Compass compass) {
        if (this.down == true) {
            queue.enqueue(command.getTurnLeft(compass));
            queue.enqueue(command.getTurnLeft(compass));
        } else {
            queue.enqueue(command.getTurnRight(compass));
            queue.enqueue(command.getTurnRight(compass));
        }
    }

    public void refillInterlaceB() {
        if (this.down == true) {
            queue.enqueue(command.getEchoNorth());
        } else {
            queue.enqueue(command.getEchoSouth());
        }
    }

    public void refillInterlaceC(Compass compass) {
        if (this.searchCount % 2 == 1) {
            if (this.down == true) {
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

    /*public void executeGridSearch(Compass compass) {
        findIsland();
        faceIsland();
        while (true){
            reachIsland();
            searchSite();
            intoPosition();
            interlaceA();
            interlaceB();
            if (!this.found.equals("OUT_OF_RANGE")){
                if (!halfComplete){
                    interlaceC();
                }else{
                    decision.put("action", "stop");
                    break;
                }
            }else{
                continue;
            }
        }*/
    }


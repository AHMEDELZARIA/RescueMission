package ca.mcmaster.se2aa4.island.team220;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

public class GridSearch implements ISearchAlgorithm {

    private final Logger logger = LogManager.getLogger(); // for logger instructions
    private Compass compass; // creates a compass DELETE LATER
    private CommandBook command = new CommandBook(); // ADDED 19/03
    private Information results; // added 19/03 DELETE LATER

    private int count = 0; // helps run different actions in a round, gets 'reset'
    private int searchCount = 0; // keeps track of number of times searchSite() is run
    private String found = ""; // returns "GROUND" or "OUT_OF_RANGE", aka 'echo' results
    private int range = 0; // returns the range of the 'echo' result
    private String scanBiomes = ""; // returns the first biome found from 'scan' results 
    private String scanSites = ""; // returns the site if the site is found from 'scan' results 
    private boolean down = false; // determines whether the drone is facing upwards or downwards when it exits the island for intoPosition()
    private boolean interlaceCheck = false;

    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();

    boolean islandFound = false;
    boolean halfComplete = false;
    Translator translator = new Translator();

    private GridQueue queue = new GridQueue();

    private Integer currentMode = 0;

    @Override
    public void searchArea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'determineDecision'");
    }

    // Main generic code, to be called from Explorer
    public String makeDecision() {
        if (queue.isEmpty()) {
            // refill queue
        }
        queue.dequeue(); // dequeue and run command
        return null;
    }

    public void refillQueue() {
        switch (this.currentMode) {
            case 0:
                if (this.found == "GROUND") {
                    this.currentMode = 1;
                } else {
                    // execute mode0
                }
            case 1:
                //execute mode1
                this.currentMode = 2;
            case 2:
                if (this.scanBiomes != "OCEAN") {
                    this.currentMode = 3;
                } else {
                    // execute mode2
                }
            case 3:
                if (this.scanBiomes == "OCEAN") {
                    this.currentMode = 4;
                } else {
                    // execute mode3
                }
            case 4:
                if (this.found == "OUT_OF_RANGE") {
                    this.currentMode = 5;
                } else {
                    // execute mode4
                }
            case 5:
                // execute mode5
                this.currentMode = 6;
            case 6:
                // execute mode6
                // return found
                if (this.found == "GROUND") {
                    this.currentMode = 2;
                } else {
                    if (this.interlaceCheck == true) {
                        this.currentMode = 8;
                    } else {
                        this.currentMode = 7;
                    }
                }
            case 7:
                // execute mode7
                this.currentMode = 2;
            case 8:
                // execute stop
        }
    }

    public void refillFindIsland() {
        queue.enqueue(command.getEchoSouth());
        queue.enqueue(command.getEchoEast());
        queue.enqueue(command.getEchoNorth());
        queue.enqueue(command.getScan());
        queue.enqueue(command.getFly());
    }

    /*
    //THIS METHOD WORKS 
    public String findIsland(String found) {
        //called once in the beginning of the search
        if (!found.equals("GROUND")) {
            logger.info(count);
            count++; //FOR OUR UNDERSTANDING DELETE LATER
            if (queue.isEmpty()) {
                queue.enqueue(command.getEchoSouth());
                queue.enqueue(command.getEchoEast());
                queue.enqueue(command.getEchoNorth());
                queue.enqueue(command.getScan());
                queue.enqueue(command.getFly());
            }
            return queue.dequeue(); 
        } else {
            return command.getStop();
        }
    }
    */
    public void refillFaceIsland(Compass compass) {
        queue.enqueue(command.getTurnRight(compass));
    }

    public void refillReachIsland() {
        queue.enqueue(command.getScan());
        queue.enqueue(command.getFly());
    }

    public void refillSearchSite() {
        queue.enqueue(command.getScan());
        queue.enqueue(command.getFly());
    }

    public void refillIntoPosition() { // decide what this.down is here, if it is true or false
        queue.enqueue(command.getEchoEast());
        queue.enqueue(command.getFly());
    }

    public void refillInterlaceA() {
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

    public boolean interlaceC() {
        //occurs if the number of searchCount is odd
        if (this.searchCount % 2 == 1) { // WE HAVE CASE 1
            if (this.count < 5) {
                if (this.down == true) {
                    if (this.count % 5 == 3) {
                        decision.put("action", "fly");
                    } else {
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
                    }
                } else {
                    if (this.count % 5 == 3) {
                        decision.put("action", "fly");
                    } else {
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
                    }
                }
                this.count++;
            }
        } else { // WE HAVE CASE 2
            if (this.count < 7) {
                if (this.down == true) {
                    if (this.count % 7 >= 3 && this.count % 7 <= 5) {
                        decision.put("action", "fly");
                    } else {
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
                    }
                } else {
                    if (this.count % 7 >= 3 && this.count % 7 <= 5) {
                        decision.put("action", "fly");
                    } else {
                        decision.put("action", "heading");
                        decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
                    }
                }
                this.count++;
            }
        }
        logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceC Case 1 COMPLETE");
        return true; // change this is just a placeholder
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
        /* while condition: loops until this.found = out_of_range(from interlaceB) && if halfComplete == true (from interlaceC1 or interlaceC2)
         *      reachIsland();
         *      searchSite();
         *      intoPosition();
         *      interlaceA();
         *      interlaceB();
         *      if this.found = out_of_range(from interlaceB)
         *          if halfComplete == false
`        *              interlaceC();
         *          else 
         *              decision.put("action", "stop")
         *      else 
         *          continue;
         */
    }


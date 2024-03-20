package ca.mcmaster.se2aa4.island.team220;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

public class GridSearch implements ISearchAlgorithm {

    private final Logger logger = LogManager.getLogger(); // for logger instructions
    private Compass compass; // creates a compass DELETE LATER
    private CommandBook command; // ADDED 19/03
    private Information results; // added 19/03

    private int count = 0; // helps run different actions in a round, gets 'reset'
    private int searchCount = 0; // keeps track of number of times searchSite() is run
    private String found = ""; // returns "GROUND" or "OUT_OF_RANGE", aka 'echo' results
    private int range = 0; // returns the range of the 'echo' result
    private String scanBiomes = ""; // returns the first biome found from 'scan' results 
    private String scanSites = ""; // returns the site if the site is found from 'scan' results 
    private boolean down = false; // determines whether the drone is facing upwards or downwards when it exits the island for intoPosition()

    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();

    boolean islandFound = false;
    boolean halfComplete = false;
    Translator translator = new Translator();

    public GridQueue queue = new GridQueue();

    @Override
    public void searchArea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'determineDecision'");
    }

    public String findIsland() {
        //called once in the beginning of the search
        /*if (!(this.found).equals("GROUND")) { // while the island is not found
            logger.info(this.count); // total count = 106
            if (this.count % 4 == 0) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "S")); // echo right
            } else if (this.count % 4 == 1) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "N")); // echo left
            } else if (this.count % 4 == 2) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "E")); // echo straight
            } else if (this.count % 4 == 3) {
                decision.put("action", "fly"); // fly
            }
            this.count++;
        } else {
            //this.findIslandMode = false;
            //this.changeHeading = true;
            logger.info("This is the final count: {}", (this.count-1));
            logger.info("THIS IS THE RANGE --------------------------------> {}", this.range); // 27 for map20
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 1 COMPLETE: findIsland Mode");
            logger.info("");
        }*/

        // ---------------------------------------------------------------------------------LOOK HERE HEHE
        String decision;

        if (!(results.getFound()).equals("GROUND")) {
            logger.info(this.count);
            this.count++;
            if (queue.isEmpty()) {
                queue.enqueue(command.getEchoSouth());
                queue.enqueue(command.getScan());
                queue.enqueue(command.getFly());
            }
            decision = queue.dequeue(); 

        } else {
            decision = command.getStop();
        }

        return decision;

    } 
    public void faceIsland(){
        //called once in the beginning of the search
        if ((this.count-1) % 4 == 0) {
            decision.put("action", "heading");
            decision.put("parameters", parameters.put("direction", compass.turnRight().toString())); // "S"
            this.count = 3;
        } else if ((this.count-1) % 4 == 1) {
            decision.put("action", "heading");
            decision.put("parameters", parameters.put("direction", "N"));
            this.count = 3;
        } else {
            //this.changeHeading = false;
            //this.reachIslandMode = true;
            decision.clear();
            this.count = 0;
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 2 COMPLETE: changeHeading Mode");
        }
    }

    public boolean reachIsland(){
        //only repeated if in interlaceB interlaceC1 interlaceC2
        if (!(this.scanBiomes).equals("BEACH")) { // condition for finding land
            logger.info(this.count); // count for scan and fly
            if (this.count % 2 == 0) {
                decision.put("action", "scan");
            } else if (this.count % 2 == 1) {
                decision.put("action", "fly");
            }
            this.count++;
        } else {
            //this.reachIslandMode = false;
            //this.searchSite = true;
            decision.clear();
            this.count = 0; // reset counter
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 3 COMPLETE: reachIsland Mode");
        }
        return true;
    }

    public int searchSite(){
        //always happens after reachIsland is called
        if (!(this.scanBiomes).equals("OCEAN")) { // condition for finding land
            logger.info(this.count); // count for scan and fly
            if (this.count % 2 == 0) {
                decision.put("action", "scan");
            } else if (this.count % 2 == 1) {
                decision.put("action", "fly");
            }
            this.count++;
        } else {
            this.searchCount++;
            // this.found = null; // MIGHT NOT NEED THIS GOTTA DOUBLE CHECK THE LOGIC UGH
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 4 COMPLETE: searchSite Mode");
        }
        return 0;
    }

    public void intoPosition(){
        //always follows searchSite
        if (!(this.found).equals("OUT_OF_RANGE")) { // while we don't 'echo' find OUT_OF_RANGE 
            logger.info(this.count);
            if (this.count % 2 == 0) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "E")); // echo East
            } else if (this.count % 2 == 1) {
                decision.put("action", "fly");
            }
            this.count++;
        } else {
            if ((compass.getHeading().toString()).equals("S")) { // this.down is the initial direction of interlace turning
                this.down = true;
            }
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 5 COMPLETE: intoPosition Mode");
        }
    }

    public void interlaceA(){
        //always follows intoPosition
        if (this.count < 2) {
            if (this.down == true) {
                decision.put("action", "heading");
                decision.put("parameters", parameters.put("direction", compass.turnLeft().toString()));
            } else { // if (this.down == false) 
                decision.put("action", "heading");
                decision.put("parameters", parameters.put("direction", compass.turnRight().toString()));
            }
            this.count++;
        } else {
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceA");
        }
    }

    public void interlaceB(){
        //always happens after interlaceA, and after excecuted, depending on what echo observes we either go interlaceC1, interlaceC2, go back to reachIsland or we stop
        //if condtion is met at interlaceB we stop
        if (this.count == 0) {
            if (this.down == false) { // reversed bc this.down is direction before all of interlaceTurn
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "S"));
            } else if (this.down == true) {
                decision.put("action", "echo");
                decision.put("parameters", parameters.put("direction", "N"));
            }
            this.count++;
        } else {
            logger.info(this.searchCount);
            if ((this.found).equals("GROUND")) {
                logger.info("christmas");
                // decision.put("action", "stop");
                // this.reachIslandMode = true;
            } else {
                decision.put("action", "stop");
                /* 
                if (this.searchCount % 2 == 1) {
                    this.interlaceTurnC1 = true; // CASE 1
                } else {
                    this.interlaceTurnC2 = true; // CASE 2
                }
                */
            }
            this.count = 0; // reset counter
            logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII ROUND 6: interlaceB");
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

    public void executeGridSearch(Compass compass) {
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
        }
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
}

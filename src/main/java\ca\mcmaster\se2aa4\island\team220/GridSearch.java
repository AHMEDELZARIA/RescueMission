package ca.mcmaster.se2aa4.island.team220;

public class GridSearch implements ISearchAlgorithm {

    private CommandBook command = new CommandBook();
    private GridQueue queue = new GridQueue();
    public String mode = "checkStart"; // replace "findIsland" as start mode

    public Boolean down = true; // determines whether the drone is facing upwards or downwards when it exits the island for "intoPosition"
    public Boolean interlaceCheck = false;
    private Boolean start = false;
    public Integer count = 0; // for findIsland mode

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
        // condition for "loopAround" mode
        if (this.mode == "loopAround" && this.interlaceCheck) { 
            if (found.equals("OUT_OF_RANGE")) {
                loopExtra(compass);
            }
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
        // condition for "searchSite" mode
        if (this.mode == "searchSite" && biome.equals("OCEAN")) {
            if (found.equals("GROUND") && !range.equals(0)) {
                this.mode = "reachIsland";
            } else if (found.equals("OUT_OF_RANGE")) {
                found = "GROUND";
                this.mode = "intoPosition";
            }
        } else if (this.mode == "searchSite" && found.equals("OUT_OF_RANGE") && range < 3) {
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
            case "uTurn": // TURNING AROUND
                uTurn(compass);
                this.down = !this.down;
                break;
            case "loopAround": // INTERLACE LOOP
                loopAround(compass);
                this.interlaceCheck = true;
                break;
            case "stop":
                queue.enqueue(command.getStop());
                break;
        }
    }

    // find the Island
    public void findIsland(Compass compass) {
        this.count++;
        if (this.count % 3 == 0) {
            queue.enqueue(command.getFly());
        } else if (this.count % 3 == 1) {
            queue.enqueue(command.testEchoRight(compass));
        } else {
            queue.enqueue(command.testEchoLeft(compass));
        }
    }

    public void faceIsland(Compass compass) {
        if (this.count % 3 == 1) {
            queue.enqueue(command.getTurnRight(compass));
            this.down = true;
        } else if (this.count % 3 == 2) {
            queue.enqueue(command.getTurnLeft(compass));
            this.down = false;
        }
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
    }

    public void loopExtra(Compass compass) {
        if (this.down == true) { // To account for islands being of either odd or even length
            queue.enqueue(command.getTurnRight(compass));
            queue.enqueue(command.getFly());
            queue.enqueue(command.getFly());
            queue.enqueue(command.getTurnRight(compass));
            queue.enqueue(command.getTurnRight(compass));
            queue.enqueue(command.getTurnRight(compass));
        } else {
            queue.enqueue(command.getTurnLeft(compass));
            queue.enqueue(command.getFly());
            queue.enqueue(command.getFly());
            queue.enqueue(command.getTurnLeft(compass));
            queue.enqueue(command.getTurnLeft(compass));
            queue.enqueue(command.getTurnLeft(compass));
        }
    }

    // ------------------------------------------------------------------------------------------ DRONE START CASE
    public void refillStart(String found, Integer range, Compass compass) {
        switch(this.mode) {
            case "checkStart":
                checkStart(compass);
                if (found.equals("GROUND")) {
                    this.mode = "caseBPart1";
                } else if (found.equals("OUT_OF_RANGE") && range >= 46) {
                    this.start = true;
                    this.mode = "findIsland";
                } else if (found.equals("OUT_OF_RANGE")) {
                    this.mode = "caseAPart1";
                }
                break;
            case "caseAPart1":
                caseAPart1(range, compass);
                this.mode = "caseAPart2";
                break;
            case "caseAPart2":
                caseAPart2(range, compass);
                this.mode = "findIsland";
                this.start = true;
                found = "OUT_OF_RANGE";
                break;
            case "caseBPart1":
                caseBPart1(compass);
                this.mode = "caseBPart2";
                break;
            case "caseBPart2":
                caseBPart2(range, compass);
                this.mode = "findIsland";
                this.start = true;
                found = "OUT_OF_RANGE";
                break;
        }
    }

    public void checkStart(Compass compass) {
        queue.enqueue(command.testEchoForward(compass));
    }

    public void caseAPart1(Integer range, Compass compass) {
        for (int i = 0; i < (range - 1); i++) {
            queue.enqueue(command.getFly());
        }
        queue.enqueue(command.testEchoLeft(compass));
    }

    public void caseAPart2(Integer range, Compass compass) {
        if (range <= 2) {
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

    // -------------------------------------------------------------------- what do I do with this?

    //ADDED MARCH 22
    // public Integer getCount() {
    //     return count;
    // }

    // Getter method for the queue field
    public GridQueue getQueue() {
        return this.queue;
    }
}

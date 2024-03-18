package ca.mcmaster.se2aa4.island.team220;

import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONObject;

public class GridSearch implements IDecisionHandler {
    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();

    boolean islandFound = false;
    boolean halfComplete = false;
    Translator translator = new Translator();
    Queue<Runnable> actionQueue = new LinkedList<>(); // PLZ WORK

    @Override
    public void determineDecision(AreaMap map, Drone drone) {       

    //--------------------------------------------------------------------------------------------------------
    }

    public void findIsland(){
       //called once in the beginning of the search
    } 

    public void faceIsland(){
        //called once in the beginning of the search
    }

    public boolean reachIsland(){
        return true;
        //only repeated if in interlaceB interlaceC1 interlaceC2
    }

    public int searchSite(){
        return 0;
        //always happens after reachIsland is called
    }

    public void intoPosition(){
        //always follows searchSite
    }

    public void interlaceA(){
        //always follows intoPosition
    }

    public void interlaceB(){
        //always happens after interlaceA, and after excecuted, depending on what echo observes we either go interlaceC1, interlaceC2, go back to reachIsland or we stop
        //if condtion is met at interlaceB we stop
    }

    public boolean interlaceC1(){
        return true;
        //occurs if the number of searchSitesCount is odd
    }

    //either call interlaceC1 or interlaceC2
    public boolean interlaceC2(){
        return true;
        //occurs if the number of searchSitesCount is even
    }

    public void executeGridSearch(){
        while 
    }


    
}

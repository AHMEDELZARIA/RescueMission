package ca.mcmaster.se2aa4.island.team220;

import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONObject;

public class GridSearch implements IDecisionHandler {
    JSONObject decision = new JSONObject();
    JSONObject parameters = new JSONObject();

    boolean islandFound = false;
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
    }

    public int searchSite(){
        return 0;
    }

    public void intoPosition(){

    }

    public void interlaceA(){

    }

    public void interlaceB(){

    }

    public boolean interlaceC1(){
        return true;
    }

    //either call interlaceC1 or interlaceC2
    public boolean interlaceC2(){
        return true;
    }

    public void executeGridSearch(){

    }


    
}

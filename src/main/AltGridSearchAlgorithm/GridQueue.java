package ca.mcmaster.se2aa4.island.team220;

import java.util.LinkedList;
import java.util.Queue;

public class GridQueue {

    private Queue<String> queue;

    // private CommandBook commands;

    public GridQueue() {
        queue = new LinkedList<>();
    }

    public void enqueue(String action){
        queue.add(action); 
    }

    public String dequeue(){
        if (queue.isEmpty()){
            refillQueue();
        }
        return queue.poll();
    }

    public void refillQueue(){
    }
    
    public Boolean isEmpty() {
        if (queue.isEmpty() == true) {
            return true;
        } else {
            return false;
        }
    }
}

package ca.mcmaster.se2aa4.island.team220;

import java.util.LinkedList;
import java.util.Queue;

public class GridQueue {

    public Queue<String> queue;

    public GridQueue() {
        queue = new LinkedList<>();
    }
    public void enqueue(String action) { queue.add(action); }

    public String dequeue() { return queue.poll(); }

    public Boolean isEmpty() {
        if (queue.isEmpty() == true) {
            return true;
        } else {
            return false;
        }
    }

    // ADDED THIS RETURNS THE QUEUE (for testing)
    public Queue<String> getQueue() {
        return this.queue;
    }
}

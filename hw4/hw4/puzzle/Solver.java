package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;

public class Solver {
    private LinkedList<WorldState> states = new LinkedList<>();
    private int move;

    public Solver(WorldState initial) {
        move = 0;

        MinPQ<Node> pq = new MinPQ();
        pq.insert(new Node(initial, 0, null));

        Node curNode;
        while ( true ) {
            curNode = pq.delMin();
            if( curNode.ws.isGoal() ) {
                break;
            } else {
                WorldState prev;
                if(curNode.prev == null) {
                    prev = null;
                } else {
                    prev = curNode.prev.ws;
                }
                int newAccum = curNode.accum + 1;
                Iterable<WorldState> neighbors = curNode.ws.neighbors();
                for (WorldState nb : neighbors) {
                    if( prev == null || !prev.equals(nb) ) {
                        pq.insert(new Node(nb, newAccum, curNode));
                    }
                }
            }
        }
        while(curNode != null) {
            states.addFirst(curNode.ws);
            curNode = curNode.prev;
        }
    }

    public int moves() {
        return states.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return states;
    }
}

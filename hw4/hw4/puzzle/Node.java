package hw4.puzzle;

public class Node implements Comparable<Node>{
    public WorldState ws;
    public Node prev;
    public int accum;
    public int dist;

    public Node( WorldState ws, int accum, Node prev ) {
        this.ws = ws;
        this.prev = prev;
        this.accum = accum;
        dist = ws.estimatedDistanceToGoal();
    }

    @Override
    public int compareTo(Node o) {
        if (this.accum + this.dist > o.accum + o.dist) {
            return 1;
        } else if (this.accum + this.dist < o.accum + o.dist) {
            return -1;
        } else {
            return 0;
        }
    }
}

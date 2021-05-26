package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        edgeTo[maze.xyTo1D(1,1)] = maze.xyTo1D(1, 1);
        dfs(maze.xyTo1D(1, 1), 0);
    }

    // Helper methods go here
    private boolean dfs(int v, int dist) {
        if ( marked[v] ) {
            announce();
            return true;
        }

        marked[v] = true;
        distTo[v] = dist;
        announce();

        for(int w : maze.adj(v)) {
            if(w != edgeTo[v]) {
                edgeTo[w] = v;
                if( dfs(w, dist + 1) ) {
                    return true;
                }
            }
        }
        return false;
    }

}


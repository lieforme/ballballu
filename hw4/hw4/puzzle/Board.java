package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[] tiles1D;
    private int[] goal1D;
    private int[][] tiles2D;
    private int N;
    private static final int BLANK =  0; //相当于C中#define的宏变量

    public Board(int[][] tiles) {
        N = tiles[0].length;
        tiles1D = new int[N * N];
        tiles2D = new int[N][N];
        goal1D = new int[N * N];

        int t = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles1D[t] = tiles[i][j];
                tiles2D[i][j] = tiles[i][j];
                goal1D[t] = t + 1;
                t += 1;
            }
        }
        goal1D[N*N-1] = BLANK;
    }
    public int tileAt(int i, int j) {
        if(i < 0 || j < 0 || i > N - 1 || j > N - 1) {
            throw new IndexOutOfBoundsException("illegal i or j");
        }
        return tiles1D[translate(i, j)];
    }

    private int translate(int i, int j) {
        return i * N + j;
    }

    public int size() {
        return N;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int s = size();
        int blankX = -1;
        int blankY = -1;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                if(tileAt(i, j) == BLANK) {
                    blankX = i;
                    blankY = j;
                    break;
                }
            }
        }
        int [][] n = new int[s][s];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                n[i][j] = tileAt(i, j);
            }
        }

        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                if ( Math.abs(i - blankX) + Math.abs(j - blankY) - 1 == 0 ) {
                    n[blankX][blankY] = n[i][j];
                    n[i][j] = BLANK;
                    Board neighbor = new Board(n);
                    neighbors.enqueue(neighbor);
                    n[i][j] = n[blankX][blankY];
                    n[blankX][blankY] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int result = 0;
        for (int i = 0; i < N * N - 1; i++) {
            if(tiles1D[i] != goal1D[i]) {
                result += 1;
            }
        }
        return result;
    }

    public int manhattan() {
        int result = 0;
        for(int i = 0; i < N * N; i++) {
            if(tiles1D[i] != BLANK)
            {
                int expectR = tiles1D[i] / N ;
                int expectC = tiles1D[i] % N;
                result += Math.abs(expectR - i / N) + Math.abs(expectC - i % N);
            }
        }
        return result;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if ( y == null || y.getClass() != this.getClass() ) {
            return false;
        }
        if (this.N != ((Board)y).N) {
            return false;
        }
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j < N; j++) {
                if(this.tileAt(i, j) != ((Board)y).tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int result = tiles1D != null ? tiles1D.hashCode() : 0;
        result = 31 * result + (goal1D != null ? goal1D.hashCode() : 0);
        return result;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}

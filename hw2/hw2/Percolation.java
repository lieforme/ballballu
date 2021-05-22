package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Percolation {
    private WeightedQuickUnionUF percolation;
    private int length;
    private int open_blocks;
    private int[][] state;
    private boolean isPercolating;

    /** create N-by-N grid, with all sites initially blocked **/
    public Percolation(int N) {
        if(N <= 0) throw new IllegalArgumentException("N<=0");
        percolation = new WeightedQuickUnionUF(N * N);
        length = N;
        open_blocks = 0;
        isPercolating = false;

        state = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                state[i][j] = 1;
            }
        }
    }

    private void HandleError(int row, int col) {
        if(row >= length || col >= length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    private int translate(int row, int col) {
        return row*length + col;
    }

    /** open the site (row, col) if it is not open already **/
    public void open(int row, int col) {
        HandleError(row, col);
        if(isOpen(row, col)) return;
        state[row][col] = 0;
        openHelper(row, col);
        open_blocks += 1;
    }

    private void openHelper(int row, int col) {
        if(row > 0 && isOpen(row-1, col)) {
            percolation.union(translate(row-1,col), translate(row, col));
        }
        if(row < length-1 && isOpen(row+1, col)) {
            percolation.union(translate(row+1,col), translate(row, col));
        }
        if(col < length-1 && isOpen(row, col+1)) {
            percolation.union(translate(row,col+1), translate(row, col));
        }
        if(col > 0 && isOpen(row, col-1)) {
            percolation.union(translate(row,col-1), translate(row, col));
        }
    }

    /** is the site (row, col) open? **/
    public boolean isOpen(int row, int col) {
        HandleError(row, col);
        return state[row][col] == 0 || state[row][col] == 2;
    }

    /** is the site (row, col) full? **/
    public boolean isFull(int row, int col)  {
        HandleError(row, col);
        if(isOpen(row, col) == false) return false;
        if( state[row][col] == 2 ) return  true;
        for(int i = 0; i < length; i++) {
            if(isOpen(0,i) && percolation.connected(translate(0, i),translate(row ,col))) {
                state[row][col] = 2;
                return true;
            }
        }
        return false;
    }

    /** number of open sites **/
    public int numberOfOpenSites() {
        return open_blocks;
    }

    /** does the system percolate? **/
    public boolean percolates() {
        if(numberOfOpenSites() < length) {
            return false;
        }
        if(isPercolating) return true;
        for(int i = 0; i < length; i++) {
            if(isOpen(0, i)) {
                for(int j = 0; j < length; j++) {
                    if(isOpen(length - 1, j) && percolation.connected(translate(length-1,j),translate(0,i))) {
                        isPercolating = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

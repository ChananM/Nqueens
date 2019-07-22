package csp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {

    private int n;
    private int spaces;
    private int[] constraints;
    private List<Integer> queenSpaces;

    // =================================================================================================================

    public Board(int n) {
        this.n = n;
        this.spaces = n * n;

        // Indicates that all moves are possible at the beginning
        this.constraints = new int[this.spaces];

        // holds the final places of the queens
        this.queenSpaces = new ArrayList<>();
    }

    // =================================================================================================================

    public List<Integer> getPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();

        for (int i = 0; i < this.constraints.length; i++) {
            if (this.constraints[i] == 0) {
                possibleMoves.add(i);
            }
        }
        Collections.shuffle(possibleMoves);
        System.out.println(Arrays.toString(possibleMoves.toArray()));
        return possibleMoves;
    }

    // =================================================================================================================

    public void makeMove(int space) {

        // add the queen
        this.queenSpaces.add(space);

        // add the conflicts
        addConstraints(space);
    }

    // =================================================================================================================

    public void removeMove(int space) {

        // remove the queen
        this.queenSpaces.remove(this.queenSpaces.indexOf(space));

        // remove the dependent conflicts
        removeConstraints(space);
    }

    // =================================================================================================================
    // adds constraints along the row, col, and diags of a move

    public void addConstraints(int move) {

        int row = move / this.n;
        int col = move % this.n;
        int rdStartRow = row + col;
        int ldStartRow = row - col;

        for (int i = 0; i < this.n; i++)
        {
            // Row
            addConstraint(rcToSpace(row, i));

            // Col
            addConstraint(rcToSpace(i, col));


            // / diag
            if (rdStartRow > -1) {
                addConstraint(rcToSpace(rdStartRow, i));
                rdStartRow -= 1;
            }
            // \ diag
            if (ldStartRow < this.n) {
                addConstraint(rcToSpace(ldStartRow, i));
                ldStartRow += 1;
            }

        }
    }

    // =================================================================================================================

    public void removeConstraints(int move) {

        int row = move / this.n;
        int col = move % this.n;
        int rdStartRow = row + col;
        int ldStartRow = row - col;

        for (int i = 0; i < this.n; i++) {
            // Row
            removeConstraint(rcToSpace(row, i));

            // Col
            removeConstraint(rcToSpace(i, col));


            // / diag
            if (rdStartRow > -1) {
                removeConstraint(rcToSpace(rdStartRow, i));
                rdStartRow -= 1;
            }
            // \ diag
            if (ldStartRow < this.n) {
                removeConstraint(rcToSpace(ldStartRow, i));
                ldStartRow += 1;
            }

        }
    }

    // =================================================================================================================

    // add 1 to the constraint counter for a particular space
    public void addConstraint(int move) {
        if (move != -1)
            this.constraints[move] += 1;
    }

    // =================================================================================================================

    // remove 1 from the constraint counter for a particular space
    public void removeConstraint(int move) {
        if (move != -1)
            this.constraints[move] -= 1;
    }

    // =================================================================================================================
    // Utility Functions

    // returns the corresponding space # based on 0-indexed row and column
    // returns -1 if the space is not on the board
    // e.g.
    // rcToSpace(3,4) # the space at row 3, column 4
    // > 28           # the corresponding space number given an 8x8 board

    public int rcToSpace(int row, int col) {
        int space = row * this.n + col;
        if (space >= this.spaces || space < 0)
            return -1;
        else
            return space;
    }

    // =================================================================================================================

    public String print() {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < this.n; i++) {
            StringBuilder row = new StringBuilder();

            for (int j = 0; j < this.n; j++) {
                if (this.queenSpaces.contains(this.rcToSpace(i, j)))
                    row.append("Q");
                else
                    row.append("-");
                row.append("  ");
            }
//            System.out.println(row);
            res.append(row + "\n");
        }
        return res.toString();
    }

    // =================================================================================================================


    public int getN() {
        return n;
    }

    public int getSpaces() {
        return spaces;
    }

    public int[] getConstraints() {
        return constraints;
    }

    public List<Integer> getQueenSpaces() {
        return queenSpaces;
    }

    // =================================================================================================================

}

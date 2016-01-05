package de.seyfarth.sudokulve;

import de.seyfarth.sudokulve.exceptions.NoSolutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class containing the main method.
 */
public final class Sudoku {

    private static final Logger LOG = Logger.getLogger("Sudoku");

    private Sudoku() {
    }

    /**
     * The main method. It solves the hard-coded sudoku.
     * This is used to run the whole program for testing without having to type in a new
     * Sudioku every time.
     *
     * @param args needed parameter for main method, not used
     */
    public static void main(final String[] args) {
        final Matrix matrix = new Matrix(3, 3);

        matrix.setValue(0, 0, 4);
        matrix.setValue(0, 8, 9);
        matrix.setValue(1, 1, 2);
        matrix.setValue(1, 3, 1);
        matrix.setValue(1, 5, 8);
        matrix.setValue(1, 6, 7);
        matrix.setValue(1, 8, 6);
        matrix.setValue(2, 1, 1);
        matrix.setValue(2, 2, 5);
        matrix.setValue(2, 3, 2);
        matrix.setValue(2, 6, 4);
        matrix.setValue(2, 7, 8);
        matrix.setValue(3, 4, 1);
        matrix.setValue(3, 6, 9);
        matrix.setValue(4, 3, 8);
        matrix.setValue(4, 4, 5);
        matrix.setValue(4, 5, 7);
        matrix.setValue(4, 7, 4);
        matrix.setValue(5, 2, 3);
        matrix.setValue(5, 4, 6);
        matrix.setValue(5, 5, 2);
        matrix.setValue(6, 1, 3);
        matrix.setValue(6, 5, 6);
        matrix.setValue(6, 7, 2);
        matrix.setValue(7, 0, 2);
        matrix.setValue(7, 2, 6);
        matrix.setValue(7, 3, 5);
        matrix.setValue(7, 5, 4);
        matrix.setValue(7, 6, 1);
        matrix.setValue(7, 7, 3);
        matrix.setValue(8, 0, 8);
        matrix.setValue(8, 1, 7);
        matrix.setValue(8, 3, 3);
        matrix.setValue(8, 6, 6);
        matrix.setValue(8, 8, 5);
        final Solver solver = new Solver(matrix);
        try {
            solver.fillMatrix();
            solver.checkSectors();
        } catch (NoSolutionException e) {
            LOG.log(Level.SEVERE, "No solution found.", e);
        }
        for (int row = 0; row < matrix.getDimension(); row++) {
            final StringBuilder result = new StringBuilder();
            for (int column = 0; column < matrix.getDimension(); column++) {
                final int solution = matrix.getValue(row, column);
                if (solution != 0) {
                    result.append(solution);
                } else {
                    result.append(" ");
                }
                result.append(" ");
            }
            System.out.println(result.toString());
        }
    }
}

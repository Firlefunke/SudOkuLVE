package de.seyfarth.sudokulve;

import de.seyfarth.sudokulve.exceptions.NoSolutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Sudoku {

    private static final Logger LOG = Logger.getLogger("Sudoku");

    private Sudoku() {
    }

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
        final Solver loeser = new Solver(matrix);
        try {
            loeser.fillMatrix();
            loeser.checkSectors();
        } catch (NoSolutionException e) {
            LOG.log(Level.SEVERE, "Für das Sudoku wurde keine Loesung gefunden.", e);
        }
        for (int zeile = 0; zeile < matrix.getDimension(); zeile++) {
            final StringBuilder ergebnis = new StringBuilder();
            for (int spalte = 0; spalte < matrix.getDimension(); spalte++) {
                final int solution = matrix.getValue(zeile, spalte);
                if (solution != 0) {
                    ergebnis.append(solution);
                } else {
                    ergebnis.append(" ");
                }
                ergebnis.append(" ");
            }
            System.out.println(ergebnis.toString());
        }
    }
}

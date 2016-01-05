package de.seyfarth.sudokulve;

import de.seyfarth.sudokulve.exceptions.NoSolutionException;
import java.util.List;

/**
 * Provides algorithms to solve a Sudoku Matrix.
 */
public class Solver {

    private final Matrix sudoku;
    private List<Field> solvedFields;

    /**
     * Creates a Sudoku solver object for a specific matrix.
     * 
     * @param matrix Matrix filled with initial values
     */
    public Solver(final Matrix matrix) {
        this.sudoku = matrix;
    }

    /**
     * Fills each empty field of the Sudoku matrix with all possible numbers.
     * This method does not touch fields, that contain any number.
     */
    public void fillMatrix() {
        this.sudoku.fillEmptyFields();
    }

    /**
     * Cleans up the numbers in the fields of the Sudoku matrix.
     * Removes for each solved field the number of the field in all other fields of the same row,
     * the same column, and the same block.
     * If by deleting a number from a field the field gets a solution, the clean-up is done
     * for this field to.
     * If by deleting a number from a field the field gets empty, the NoSolutionException is
     * thrown.
     * 
     * @throws NoSolutionException if a field gets empty
     */
    public void checkSectors() throws NoSolutionException {
        this.solvedFields = this.sudoku.getSolvedFields();
        while (!this.solvedFields.isEmpty()) {
            final Field currentField = this.solvedFields.remove(0);
            deleteFromSectors(currentField);
        }
    }

    private void deleteFromSectors(final Field currentField) throws NoSolutionException {
        deleteFromRow(currentField);
        deleteFromColumn(currentField);
        deleteFromBlock(currentField);
    }

    private void deleteFromRow(final Field currentField) throws NoSolutionException {
        final int index = currentField.getRowIndex();
        final List<Field> row = this.sudoku.getRow(index);
        deleteFrom(row, currentField);
    }

    private void deleteFromColumn(final Field currentField) throws NoSolutionException {
        final int index = currentField.getColumnIndex();
        final List<Field> column = this.sudoku.getColumn(index);
        deleteFrom(column, currentField);
    }

    private void deleteFromBlock(final Field currentField) throws NoSolutionException {
        final int index = currentField.getBlockIndex();
        final List<Field> block = this.sudoku.getBlock(index);
        deleteFrom(block, currentField);
    }

    private void deleteFrom(final List<Field> sector, final Field currentField)
        throws NoSolutionException {

        final int number = currentField.getSolution();
        for (Field field : sector) {
            if (field != currentField) {
                removeNumber(field, number);
            }
        }
    }

    private void removeNumber(final Field field, final int number) throws NoSolutionException {
        final boolean isRemoved = field.remove(number);
        if ((field.hasSolution()) && isRemoved) {
            this.solvedFields.add(field);
        }
        if (field.isEmpty()) {
            throw new NoSolutionException();
        }
    }
}

package de.seyfarth.sudokulve;

import de.seyfarth.sudokulve.exceptions.NoSolutionException;
import java.util.List;

/**
 * Provides algorithms to solve a Sudoku Matrix.
 */
public class Solver {

    Matrix sudoku;
    List<Field> solvedFields;

    /**
     * Creates a Sudoku solver object for a specific matrix.
     * 
     * @param matrix Matrix filled with initial values
     */
    public Solver(Matrix matrix) {
        sudoku = matrix;
    }

    /**
     * Fills each empty field of the Sudoku matrix with all possible numbers.
     * This method does not touch fields, that contain any number.
     */
    public void fillMatrix() {
        sudoku.fillEmptyFields();
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
        solvedFields = sudoku.getSolvedFields();
        while (!solvedFields.isEmpty()) {
            Field currentField = solvedFields.remove(0);
            deleteFromSectors(currentField);
        }
    }

    private void deleteFromSectors(Field currentField) throws NoSolutionException {
        deleteFromRow(currentField);
        deleteFromColumn(currentField);
        deleteFromBlock(currentField);
    }

    private void deleteFromRow(Field currentField) throws NoSolutionException {
        int index = currentField.getRowIndex();
        List<Field> row = sudoku.getRow(index);
        deleteFrom(row, currentField);
    }

    private void deleteFromColumn(Field currentField) throws NoSolutionException {
        int index = currentField.getColumnIndex();
        List<Field> column = sudoku.getColumn(index);
        deleteFrom(column, currentField);
    }

    private void deleteFromBlock(Field currentField) throws NoSolutionException {
        int index = currentField.getBlockIndex();
        List<Field> block = sudoku.getBlock(index);
        deleteFrom(block, currentField);
    }

    private void deleteFrom(List<Field> sector, Field currentField) throws NoSolutionException {

        int number = currentField.getSolution();
        for (Field field : sector) {
            if (field != currentField) {
                removeNumber(field, number);
            }
        }
    }

    private void removeNumber(Field field, int number) throws NoSolutionException {
        boolean isRemoved = field.remove(number);
        if ((field.hasSolution()) && isRemoved) {
            solvedFields.add(field);
        }
        if (field.isEmpty()) {
            throw new NoSolutionException();
        }
    }
}

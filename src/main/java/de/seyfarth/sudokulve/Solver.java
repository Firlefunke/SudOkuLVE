package de.seyfarth.sudokulve;

import de.seyfarth.sudokulve.exceptions.NoSolutionException;
import java.util.List;

public class Solver {

    Matrix sudoku;
    List<Field> solvedFields;

    public Solver(Matrix matrix) {
        sudoku = matrix;
    }

    public void fillMatrix() {
        sudoku.fillEmptyFields();
    }

    /**
     *
     * @throws NoSolutionException
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

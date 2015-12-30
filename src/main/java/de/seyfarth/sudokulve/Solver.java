package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.*;
import java.util.List;

public class Solver {

    Matrix sudoku;
    List<Field> solvedFields;

    public Solver(Matrix matrix) {
        sudoku = matrix;
        solvedFields = new ArrayList<>();
    }

    void fillMatrix() throws NoSolutionException {
        for (Field[] row : sudoku.getMatrix()) {
            for (Field current : row) {
                if (current.hasSolution()) {
                    continue;
                }
                if (current.isEmpty()) {
                    current.fillWithAllNumbers();
                    removeFromSectors(current);
                }
                if (current.isEmpty()) {
                    throw new NoSolutionException();
                }
                if (current.hasSolution()) {
                    solvedFields.add(current);
                }
            }
        }
    }

    private void removeFromSectors(Field currentField) {
        removeFromRow(currentField);
        removeFromColumn(currentField);
        removeFromBlock(currentField);
    }

    private void removeFromRow(Field currentField) {
        int index = currentField.getRow();
        List<Field> row = sudoku.getRow(index);
        removeFrom(row, currentField);
    }

    private void removeFromColumn(Field currentField) {
        int index = currentField.getColumn();
        List<Field> column = sudoku.getColumn(index);
        removeFrom(column, currentField);
    }

    private void removeFromBlock(Field currentField) {
        int index = currentField.getBlock();
        List<Field> block = sudoku.getBlock(index);
        removeFrom(block, currentField);
    }

    private void removeFrom(List<Field> sector, Field currentField) {
        sector.stream()
                .filter((field) -> (field != currentField && field.hasSolution()))
                .forEach((field) -> currentField.remove(field.getSolution()));
    }

    void checkSectors() throws NoSolutionException {
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
        int index = currentField.getRow();
        List<Field> row = sudoku.getRow(index);
        deleteFrom(row, currentField);
    }

    private void deleteFromColumn(Field currentField) throws NoSolutionException {
        int index = currentField.getColumn();
        List<Field> column = sudoku.getColumn(index);
        deleteFrom(column, currentField);
    }

    private void deleteFromBlock(Field currentField) throws NoSolutionException {
        int index = currentField.getBlock();
        List<Field> block = sudoku.getBlock(index);
        deleteFrom(block, currentField);
    }

    private void deleteFrom(List<Field> sector, Field currentField) throws NoSolutionException {

        int number = currentField.getSolution();
        for (Field field : sector) {
            if (field == currentField) {
                continue;
            }
            boolean isRemoved = field.remove(number);
            if ((field.hasSolution()) && isRemoved) {
                solvedFields.add(field);
            }
            if (field.isEmpty()) {
                throw new NoSolutionException();
            }
        }
    }
}

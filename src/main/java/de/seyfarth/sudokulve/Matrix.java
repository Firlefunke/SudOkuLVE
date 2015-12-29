package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.NoSolutionException;

public class Matrix {

    private final int dimension;
    private final int numberRowsPerBlock;
    private final int numberColumnsPerBlock;
    private Field[] matrix;

    public Matrix(int dim, int blockRows, int blockColumns)
            throws NoSolutionException {
        if (blockRows * blockColumns != dim) {
            throw new NoSolutionException();
        }
        matrix = new Field[dim * dim];
        dimension = dim;
        numberRowsPerBlock = blockRows;
        numberColumnsPerBlock = blockColumns;

        int i = 0;
        for (int row = 1; row <= dimension; row++) {
            for (int column = 1; column <= dimension; column++) {
                int block = Matrix.this.getBlock(row, column);
                Field field = new Field(row, column, block, dimension);
                matrix[i] = field;
                i++;
            }
        }
    }

    private int getBlock(int row, int column) {
        int blockRow = ((row - 1) / numberRowsPerBlock) + 1; // x-Koordinate
        // des
        // Blocks
        int blockColumn = ((column - 1) / numberColumnsPerBlock) + 1; // y-Koordinate
        // des
        // Blocks
        int anzBloeckeInSpalte = (dimension / numberColumnsPerBlock); // Bloecke in
        // einer
        // Spalte
        int block = ((blockRow - 1) * anzBloeckeInSpalte)
                + blockColumn;
        return block;
    }

    public void setValue(int row, int column, int value) {
        int feldIndex = (row - 1) * dimension + column - 1;
        matrix[feldIndex].setSolution(value);
    }

    public int getValue(int row, int column) {
        int feldIndex = (row - 1) * dimension + column - 1;
        int inhalt = matrix[feldIndex].getSolution();
        return inhalt;
    }

    public int getDimension() {
        return dimension;
    }

    public ArrayList<Field> getRow(int index) {
        ArrayList<Field> zeilenFelder = new ArrayList<>();

        int erstesFeld = (index - 1) * dimension;
        for (int i = 0; i < dimension; i++) {
            zeilenFelder.add(matrix[erstesFeld + i]);
        }
        return zeilenFelder;
    }

    public ArrayList<Field> getColumn(int index) {
        ArrayList<Field> spaltenFelder = new ArrayList<>();

        int erstesFeld = index - 1;
        for (int i = 0; i < dimension; i++) {
            spaltenFelder.add(matrix[erstesFeld + i * dimension]);
        }
        return spaltenFelder;
    }

    public ArrayList<Field> getBlock(int index) {
        ArrayList<Field> block = new ArrayList<>();

        int blockZeile = (index - 1) / (dimension / numberRowsPerBlock);
        int blockSpalte = (index - 1) % (dimension / numberColumnsPerBlock);
        int firstBlockRow = blockZeile * numberRowsPerBlock;
        int firstBlockColumn = blockSpalte * numberColumnsPerBlock;
        for (int i = 0; i < numberRowsPerBlock; i++) {
            for (int j = 0; j < numberColumnsPerBlock; j++) {
                int feldIndex = (firstBlockRow + i) * dimension
                        + (firstBlockColumn + j);
                block.add(matrix[feldIndex]);
            }
        }
        return block;
    }

    public Field[] matrix() {
        return matrix;
    }
}

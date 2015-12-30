package de.seyfarth.sudokulve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Organizes all Sudoku fields in a matrix.
 * <br>
 * Each Sudoku matrix is structured into blocks. The dimension of the Sudoku matrix equals the
 * number of rows, the number of columns and the number of blocks of the matrix.
 * <br>
 * The block dimensions determine the dimensions of the Sudoku matrix. If a block has two rows
 * and three columns, the Sudoku matrix has the dimension 6 (6 rows and columns with 2x3 blocks).
 * <br>
 * A standard Sudoku has a matrix with 9 rows, 9 columns and 3x3 blocks.
 */
public class Matrix {

    private final int dimension;
    private final int numberRowsPerBlock;
    private final int numberColumnsPerBlock;
    private final Field[][] matrix;

    public Matrix(int blockRows, int blockColumns) {
        if (blockRows < 1 || blockColumns < 1) {
            throw new IllegalArgumentException("The block size must be greater than zero.");
        }

        try {
            dimension = Math.multiplyExact(blockRows, blockColumns);
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException("Java can not handle this dimension. Choose smaller values.");
        }

        matrix = new Field[dimension][dimension];
        numberRowsPerBlock = blockRows;
        numberColumnsPerBlock = blockColumns;

        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                int block = getBlockNumber(row, column);
                matrix[row][column] = new Field(row, column, block, dimension);
            }
        }
    }

    /**
     * Returns the number of the block, to which the field belongs, that is identified by row and
     * column in the Sudoku matrix.
     *
     * @param row number of row in Sudoku matrix. Numbering of rows starts with 0.
     * @param column number of column in Sudoku matrix. Numbering of columns starts with 0.
     * @return block number of the given Sudoku field. Numbering of blocks start with 0.
     */
    private int getBlockNumber(int row, int column) {
        int blockRow = row / numberRowsPerBlock; // x-coordinate of block
        int blockColumn = column / numberColumnsPerBlock; // y-coordinate of block
        return (blockRow * numberRowsPerBlock + blockColumn);
    }

    /**
     * Writes a solution into the given field of the Sudoku matrix. This function is mainly used
     * for the initialisation of the Matrix.
     *
     * @param row row number of field
     * @param column column number of field
     * @param value value of the solution of the given field
     * @throws IllegalArgumentException when a value is out of bounds
     */
    public void setValue(int row, int column, int value) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Matrix index must be greater or equal than 0.");
        }
        if (row >= dimension || column >= dimension) {
            throw new IllegalArgumentException("Matrix index must be smaller than "+ dimension + ".");
        }
        if (value <= 0 || value > dimension) {
            throw new IllegalArgumentException("The value may only be in the intervall [1, " + dimension + "].");
        }
        matrix[row][column].setSolution(value);
    }

    /**
     * Returns the value of the field identified by the given row and column number. If the field is
     * not solved or if it is empty, returns 0.
     *
     * @param row row number of field
     * @param column column number of field
     * @return solution or 0
     */
    public int getValue(int row, int column) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Matrix index must be greater or equal than 0.");
        }
        if (row >= dimension || column >= dimension) {
            throw new IllegalArgumentException("Matrix index must be smaller than "+ dimension + ".");
        }
        Field field = matrix[row][column];
        if (field.hasSolution()) {
            return field.getSolution();
        } else {
            return 0;
        }
    }

    public int getDimension() {
        return dimension;
    }

    public List<Field> getRow(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Row index must be greater or equal than 0.");
        }
        if (index >= dimension) {
            throw new IllegalArgumentException("Row index must be smaller than "+ dimension + ".");
        }
        return Arrays.asList(matrix[index]);
    }

    public List<Field> getColumn(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Column index must be greater or equal than 0.");
        }
        if (index >= dimension) {
            throw new IllegalArgumentException("Column index must be smaller than "+ dimension + ".");
        }

        List<Field> column = new ArrayList<>();
        for (int ii = 0; ii < dimension; ii++) {
            column.add(matrix[ii][index]);
        }
        return column;
    }

    public List<Field> getBlock(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Block index must be greater or equal than 0.");
        }
        if (index >= dimension) {
            throw new IllegalArgumentException("Block index must be smaller than "+ dimension + ".");
        }

        List<Field> block = new ArrayList<>();

        int firstBlockRow = (index / numberRowsPerBlock) * numberRowsPerBlock;
        int firstBlockColumn = (index % numberRowsPerBlock) * numberColumnsPerBlock;
        for (int ii = 0; ii < numberRowsPerBlock; ii++) {
            for (int jj = 0; jj < numberColumnsPerBlock; jj++) {
                block.add(matrix[firstBlockRow + ii][firstBlockColumn + jj]);
            }
        }
        return block;
    }

    public Field[][] getMatrix() {
        return matrix;
    }
}

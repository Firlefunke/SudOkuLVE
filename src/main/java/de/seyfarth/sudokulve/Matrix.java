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
 * The block dimensions determine the dimensions of the Sudoku matrix. If a block has two rows and
 * three columns, the Sudoku matrix has the dimension 6 (6 rows and columns with 2x3 blocks).
 * <br>
 * A standard Sudoku has a matrix with 9 rows, 9 columns and 3x3 blocks.
 */
public class Matrix {

    private final int dimension;
    private final int numberRowsPerBlock;
    private final int numberColumnsPerBlock;
    private final Field[][] matrix;

    /**
     * Constructs a new Matrix with the dimension {@code blockRows * blockColumns}.
     *
     * @param blockRows number of rows per block
     * @param blockColumns number of columns per block
     */
    public Matrix(final int blockRows, final int blockColumns) {
        if (blockRows < 1 || blockColumns < 1) {
            throw new IllegalArgumentException("The block size must be greater than zero.");
        }

        try {
            this.dimension = Math.multiplyExact(blockRows, blockColumns);
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException(
                "Java can not handle this dimension. Choose smaller values.");
        }

        this.matrix = new Field[this.dimension][this.dimension];
        this.numberRowsPerBlock = blockRows;
        this.numberColumnsPerBlock = blockColumns;

        for (int row = 0; row < this.dimension; row++) {
            for (int column = 0; column < this.dimension; column++) {
                final int block = getBlockNumber(row, column);
                this.matrix[row][column] = new Field(row, column, block, this.dimension);
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
    private int getBlockNumber(final int row, final int column) {
        final int blockRow = row / this.numberRowsPerBlock; // x-coordinate of block
        final int blockColumn = column / this.numberColumnsPerBlock; // y-coordinate of block
        return blockRow * this.numberRowsPerBlock + blockColumn;
    }

    /**
     * Writes a solution into the given field of the Sudoku matrix.
     * This function is mainly used for the initialisation of the Matrix.
     *
     * @param row row number of field
     * @param column column number of field
     * @param value value of the solution of the given field
     * @throws IllegalArgumentException when a value is out of bounds
     */
    public void setValue(final int row, final int column, final int value) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Matrix index must be greater or equal than 0.");
        }
        if (row >= this.dimension || column >= this.dimension) {
            throw new IllegalArgumentException("Matrix index must be smaller than "
                + this.dimension + ".");
        }
        if (value <= 0 || value > this.dimension) {
            throw new IllegalArgumentException("The value may only be in the intervall [1, "
                + this.dimension + "].");
        }
        this.matrix[row][column].setSolution(value);
    }

    /**
     * Fills all empty field of the matrix with all numbers.
     */
    public void fillEmptyFields() {
        Arrays.stream(this.matrix).forEach(row -> fillRowsEmptyFields(row));
    }

    /**
     * Fills all empty field of a row with all numbers.
     *
     * @param row Row of matrix to fill
     */
    private void fillRowsEmptyFields(final Field[] row) {
        Arrays.stream(row)
            .filter(field -> field.isEmpty())
            .forEach(field -> field.fillWithAllNumbers());
    }

    /**
     * Returns the value of the field identified by the given row and column number.
     * If the field is not solved or if it is empty, returns 0.
     *
     * @param row row number of field
     * @param column column number of field
     * @return solution or 0
     */
    public int getValue(final int row, final int column) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Matrix index must be greater or equal than 0.");
        }
        if (row >= this.dimension || column >= this.dimension) {
            throw new IllegalArgumentException("Matrix index must be smaller than "
                + this.dimension + ".");
        }
        final Field field = this.matrix[row][column];
        if (field.hasSolution()) {
            return field.getSolution();
        } else {
            return 0;
        }
    }

    /**
     * Get all fields from matrix, that have a solution.
     *
     * @return List with all solved fields in matrix
     */
    public List<Field> getSolvedFields() {
        final List<Field> solved = new ArrayList<>();
        Arrays.stream(this.matrix).forEach(row -> getRowsSolvedFields(row, solved));

        return solved;
    }

    /**
     * Get all fields from matrix row, that have a solution.
     *
     * @param row row of matrix
     * @param solved list with references to solved fields
     */
    private void getRowsSolvedFields(final Field[] row, final List<Field> solved) {
        Arrays.stream(row)
            .filter((field) -> field.hasSolution())
            .forEach(current -> solved.add(current));
    }

    /**
     * Get the dimension of the created sudoku matrix.
     *
     * @return dimension
     */
    public int getDimension() {
        return this.dimension;
    }

    /**
     * Constructs a list containing all elements of the given row.
     * This list is ascending ordered by column index.
     *
     * @param index row index<br>Range: [0, dimension - 1]
     * @return List with all Fields of row
     */
    public List<Field> getRow(final int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Row index must be greater or equal than 0.");
        }
        if (index >= this.dimension) {
            throw new IllegalArgumentException("Row index must be smaller than "
                + this.dimension + ".");
        }
        return Arrays.asList(this.matrix[index]);
    }

    /**
     * Constructs a list containing all elements of the given column.
     * This list is ascending ordered by row index.
     *
     * @param index column index<br>Range: [0, dimension - 1]
     * @return List with all Fields of column
     */
    public List<Field> getColumn(final int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Column index must be greater or equal than 0.");
        }
        if (index >= this.dimension) {
            throw new IllegalArgumentException("Column index must be smaller than "
                + this.dimension + ".");
        }

        final List<Field> column = new ArrayList<>();
        for (int ii = 0; ii < this.dimension; ii++) {
            column.add(this.matrix[ii][index]);
        }
        return column;
    }

    /**
     * Constructs a list containing all elements of the given block.
     * This list is ascending ordered primary by row index, then by column index.
     *
     * @param index block index<br>Range: [0, dimension - 1]
     * @return List with all Fields of block
     */
    public List<Field> getBlock(final int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Block index must be greater or equal than 0.");
        }
        if (index >= this.dimension) {
            throw new IllegalArgumentException("Block index must be smaller than "
                + this.dimension + ".");
        }

        final List<Field> block = new ArrayList<>();

        final int firstBlockRow = (index / this.numberRowsPerBlock) * this.numberRowsPerBlock;
        final int firstBlockColumn = (index % this.numberRowsPerBlock) * this.numberColumnsPerBlock;
        for (int ii = 0; ii < this.numberRowsPerBlock; ii++) {
            for (int jj = 0; jj < this.numberColumnsPerBlock; jj++) {
                block.add(this.matrix[firstBlockRow + ii][firstBlockColumn + jj]);
            }
        }
        return block;
    }
}

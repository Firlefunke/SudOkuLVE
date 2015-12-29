package de.seyfarth.sudokulve;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

/**
 * A Field object represents one field of a sudoku matrix.
 * The field contains its position in the matrix and
 * all possible values during the solving process of the sudoku.
 */
public class Field {

    private final int row;
    private final int column;
    private final int block;
    private final int dimension;

    private final TIntSet values;

    /**
     * Constructs an empty field on the given position with the given dimension.
     *
     * @param row row index of this field<br>Range: 0 to {@code dimension - 1}
     * @param column column index of this field<br>Range: 0 to {@code dimension - 1}
     * @param block block index of this field<br>Range: 0 to {@code dimension - 1}
     * @param dimension maximum value that can be part of this field and size of the matrix this
     * field is part of<br>Range: greater than zero
     */
    public Field(int row, int column, int block, int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Dimension greater than zero.");
        }
        if (row < 0 || block < 0 || column < 0) {
            throw new IllegalArgumentException("Position of field with negative index.");
        }
        if (row >= dimension || block >= dimension || column >= dimension) {
            throw new IllegalArgumentException("Position of field outside of matrix dimensions.");
        }
        this.row = row;
        this.column = column;
        this.block = block;
        this.dimension = dimension;
        values = new TIntHashSet();
    }

    /**
     * Adds all numbers from 1 (inclusive) to dimension (inclusive) to this {@link Field}.
     */
    public void fillWithAllNumbers() {
        for (int number = 1; number <= dimension; number++) {
            values.add(number);
        }
    }

    /**
     * Removes the given number from this {@link Field}.
     *
     * @param number number to be removed
     * @return true if this field contained the given number
     */
    public boolean remove(int number) {
        return values.remove(number);
    }

    /**
     * Get the only value of the field.
     * <br>
     * Precondition: Check {@link #hasSolution()} before calling this method.
     * 
     * @throws IllegalStateException if there is not exactly one solution
     * @return the solution value
     */
    public int getSolution() {
        if (values.size() > 1) {
            throw new IllegalStateException("There is more than one possible solution.");
        } else if (values.size() < 1) {
            throw new IllegalStateException("There is no possible solution.");
        }
        return values.iterator().next();
    }

    /**
     * Checks if there is no value in this {@link Field}.
     *
     * @return result of check
     */
    public boolean isEmpty() {
        return (values.isEmpty());
    }

    /**
     * Checks if there is exactly one value in this {@link Field}.
     *
     * @return result of check
     */
    public boolean hasSolution() {
        return (values.size() == 1);
    }

    /**
     * Removes the content of this {@link Field} and sets the given number as the only solution.
     * After calling this method {@link #hasSolution()} returns always {@code true}.
     *
     * @param number solution number
     */
    public void setSolution(int number) {
        if (number <= 0 || number > dimension) {
            throw new IllegalArgumentException("Given number was out of bounds: " + number);
        }
        values.clear();
        values.add(number);
    }

    /**
     * Row index of this {@link Field}.
     *
     * @return row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Column index of this {@link Field}.
     *
     * @return column index
     */
    public int getColumn() {
        return column;
    }

    /**
     * Block index of this {@link Field}.
     *
     * @return block index
     */
    public int getBlock() {
        return block;
    }

    /**
     * Maximum possible value of solution.
     * This value should be equal to {@link Matrix#dimension}.
     *
     * @return dimension
     */
    public int getDimension() {
        return dimension;
    }
}

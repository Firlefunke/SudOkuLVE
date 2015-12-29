package de.seyfarth.sudokulve;

import java.util.ArrayList;

public class Field {

    private final int row;
    private final int column;
    private final int block;
    private final int dimension;

    private final ArrayList<Integer> solution;

    public Field(int row, int column, int block, int dimension) {
        this.row = row;
        this.column = column;
        this.block = block;
        this.dimension = dimension;
        solution = new ArrayList<>();
    }

    void fillWithAllNumbers() {
        solution.clear();
        for (int number = 1; number <= dimension; number++) {
            solution.add(number);
        }
    }

    public boolean remove(int number) {
        int index = 0;
        while (solution.size() > index) {
            int currentNumber = solution.get(index);
            if (currentNumber == number) {
                solution.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    /**
     * Get the only value of the field.
     *
     * @precondition Check {@link #hasSolution()} before calling this method.
     * @throws IllegalStateException if there is not exactly one solution
     * @return the solution value
     */
    public int getSolution() {
        if (solution.size() > 1) {
            throw new IllegalStateException("There is more than one possible solution.");
        } else if (solution.size() < 1) {
            throw new IllegalStateException("There is no possible solution.");
        }
        return solution.get(0);
    }

    public boolean isEmpty() {
        return (solution.isEmpty());
    }

    public boolean hasSolution() {
        return (solution.size() == 1);
    }

    public void setSolution(int number) {
        solution.clear();
        solution.add(number);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getBlock() {
        return block;
    }
}

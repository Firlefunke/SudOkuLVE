/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.seyfarth.sudokulve;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author barbara
 */
public class MatrixTest {

    /**
     * Test or {@link de.seyfarth.sudokulve.Matrix#Matrix(int, int)}.
     */
    @Test
    public void validMatrix() {
        Matrix matrix = new Matrix(3, 2);
        assertEquals(6, matrix.getDimension());
    }

    /**
     * Test or {@link de.seyfarth.sudokulve.Matrix#Matrix(int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void matrixWithNegativeFirstArgument() {
        Matrix matrix = new Matrix(-1, 2);
    }

    /**
     * Test or {@link de.seyfarth.sudokulve.Matrix#Matrix(int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void matrixWithNegativeSecondArgument() {
        Matrix matrix = new Matrix(7, -3);
    }

    /**
     * Test or {@link de.seyfarth.sudokulve.Matrix#Matrix(int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void matrixDimensionOverflow() {
        Matrix matrix = new Matrix(Integer.MAX_VALUE, 2);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#setValue(int, int, int)} and
     * {@link de.seyfarth.sudokulve.Matrix#getValue(int, int)}.
     */
    @Test
    public void setAndReadValueInnerLowerBoundary() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(0, 0, 1);
        assertEquals(1, matrix.getValue(0, 0));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#setValue(int, int, int)} and
     * {@link de.seyfarth.sudokulve.Matrix#getValue(int, int)}.
     */
    @Test
    public void setAndReadValueInnerUpperBoundary() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(5, 5, 6);
        assertEquals(6, matrix.getValue(5, 5));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#setValue(int, int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void setValueOuterLowerBoundary1st() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(-1, 0, 1);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#setValue(int, int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void setValueOuterLowerBoundary2nd() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(0, -1, 1);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#setValue(int, int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void setValueOuterLowerBoundary3rd() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(0, 0, 0);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#setValue(int, int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void setValueOuterUpperBoundary1st() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(6, 5, 6);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#setValue(int, int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void setValueOuterUpperBoundary2nd() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(5, 6, 6);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#setValue(int, int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void setValueOuterUpperBoundary3rd() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(5, 5, 7);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getValue(int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getValueOuterLowerBoundary1st() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getValue(-1, 0);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getValue(int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getValueOuterLowerBoundary2nd() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getValue(0, -1);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getValue(int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getValueOuterUpperBoundary1st() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getValue(6, 5);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getValue(int, int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getValueOuterUpperBoundary2nd() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getValue(5, 6);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getValue(int, int)}.
     */
    @Test
    public void getValueFromEmptyField() {
        Matrix matrix = new Matrix(3, 2);
        assertEquals(0, matrix.getValue(3, 5));
    }
    
    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getDimension()}.
     */
    @Test
    public void validGetDimension() {
        Matrix matrix = new Matrix(13, 7);
        assertEquals(13 * 7, matrix.getDimension());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getRow(int)}.
     */
    @Test
    public void validGetRow() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(3, 0, 1);
        matrix.setValue(3, 1, 2);
        matrix.setValue(3, 2, 3);
        matrix.setValue(3, 3, 4);
        matrix.setValue(3, 4, 5);
        matrix.setValue(3, 5, 6);
        
        List<Field> row = matrix.getRow(3);
        assertEquals(6, row.size());
        int ii = 1;
        for(Field field : row) {
            assertTrue(field.hasSolution());
            assertEquals(ii, field.getSolution());
            assertEquals(3, field.getRow());
            ii++;
        }
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getRow(int)}.
     */
    @Test
    public void getRowLowerInnerBoundary() {
        Matrix matrix = new Matrix(3, 2);
        List<Field> row = matrix.getRow(0);
        assertEquals(6, row.size());
        row.forEach(field -> assertTrue(field.isEmpty()));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getRow(int)}.
     */
    @Test
    public void getRowUpperInnerBoundary() {
        Matrix matrix = new Matrix(3, 2);
        List<Field> row = matrix.getRow(5);
        assertEquals(6, row.size());
        row.forEach(field -> assertTrue(field.isEmpty()));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getRow(int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getRowLowerOuterBoundary() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getRow(-1);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getRow(int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getRowUpperOuterBoundary() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getRow(6);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getColumn(int)}.
     */
    @Test
    public void validGetColumn() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setValue(0, 2, 1);
        matrix.setValue(1, 2, 2);
        matrix.setValue(2, 2, 3);
        matrix.setValue(3, 2, 4);
        matrix.setValue(4, 2, 5);
        matrix.setValue(5, 2, 6);
        
        List<Field> column = matrix.getColumn(2);
        assertEquals(6, column.size());
        int ii = 1;
        for(Field field : column) {
            assertTrue(field.hasSolution());
            assertEquals(ii, field.getSolution());
            assertEquals(2, field.getColumn());
            ii++;
        }
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getColumn(int)}.
     */
    @Test
    public void getColumnLowerInnerBoundary() {
        Matrix matrix = new Matrix(3, 2);
        List<Field> column = matrix.getColumn(0);
        assertEquals(6, column.size());
        column.forEach(field -> assertTrue(field.isEmpty()));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getColumn(int)}.
     */
    @Test
    public void getColumnUpperInnerBoundary() {
        Matrix matrix = new Matrix(3, 2);
        List<Field> column = matrix.getColumn(5);
        assertEquals(6, column.size());
        column.forEach(field -> assertTrue(field.isEmpty()));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getColumn(int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getColumnLowerOuterBoundary() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getColumn(-1);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getColumn(int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getColumnUpperOuterBoundary() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getColumn(6);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getBlock(int)}.
     */
    @Test
    public void validGetBlock() {
        Matrix matrix = new Matrix(2, 3);
        matrix.setValue(2, 3, 1);
        matrix.setValue(2, 4, 2);
        matrix.setValue(2, 5, 3);
        matrix.setValue(3, 3, 4);
        matrix.setValue(3, 4, 5);
        matrix.setValue(3, 5, 6);
        
        List<Field> block = matrix.getBlock(3);
        assertEquals(6, block.size());
        int ii = 1;
        for(Field field : block) {
            assertTrue(field.hasSolution());
            assertEquals(ii, field.getSolution());
            assertEquals(3, field.getBlock());
            ii++;
        }
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getBlock(int)}.
     */
    @Test
    public void getBlockLowerInnerBoundary() {
        Matrix matrix = new Matrix(3, 2);
        List<Field> block = matrix.getBlock(0);
        assertEquals(6, block.size());
        block.forEach(field -> assertTrue(field.isEmpty()));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getBlock(int)}.
     */
    @Test
    public void getBlockUpperInnerBoundary() {
        Matrix matrix = new Matrix(3, 2);
        List<Field> block = matrix.getBlock(5);
        assertEquals(6, block.size());
        block.forEach(field -> assertTrue(field.isEmpty()));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getBlock(int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getBlockLowerOuterBoundary() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getBlock(-1);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Matrix#getBlock(int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void getBlockUpperOuterBoundary() {
        Matrix matrix = new Matrix(3, 2);
        matrix.getBlock(6);
    }
}

package de.seyfarth.sudokulve;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldTest {

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#Field(int, int, int, int)}.
     * Construct field with valid values.
     */
    @Test
    public final void validFieldConstructor() {
        Field field = new Field(2, 3, 2, 4);

        assertEquals(2, field.getRow());
        assertEquals(3, field.getColumn());
        assertEquals(2, field.getBlock());
        assertEquals(4, field.getDimension());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#Field(int, int, int, int)}.
     * Construct field with row index out of dimension bounds.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void illegalUpperRowArgument() {
        Field field = new Field(4, 3, 2, 4);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#Field(int, int, int, int)}.
     * Construct field with negative row index.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void illegalLowerRowArgument() {
        Field field = new Field(-1, 3, 2, 4);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#Field(int, int, int, int)}.
     * Construct field with column index out of dimension bounds.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void illegalUpperColumnArgument() {
        Field field = new Field(2, 4, 2, 4);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#Field(int, int, int, int)}.
     * Construct field with negative column index.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void illegalLowerColumnArgument() {
        Field field = new Field(2, -1, 2, 4);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#Field(int, int, int, int)}.
     * Construct field with block index out of dimension bounds.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void illegalUpperBlockArgument() {
        Field field = new Field(2, 3, 4, 4);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#Field(int, int, int, int)}.
     * Construct field with negative block index.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void illegalLowerBlockArgument() {
        Field field = new Field(2, 3, -1, 4);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#Field(int, int, int, int)}.
     * Construct field with dimension smaller than one.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void illegalDimensionArgument() {
        Field field = new Field(2, 3, 5, 0);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#isEmpty()}.
     */
    @Test
    public final void isEmpty() {
        Field field = new Field(2, 3, 2, 4);
        assertTrue(field.isEmpty());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#isEmpty()}.
     */
    @Test
    public final void fieldNotEmptyAllNumbers() {
        Field field = new Field(2, 3, 2, 4);
        field.fillWithAllNumbers();
        assertFalse(field.isEmpty());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#isEmpty()}.
     */
    @Test
    public final void fieldNotEmptySolution() {
        Field field = new Field(2, 3, 2, 4);
        field.setSolution(1);
        assertFalse(field.isEmpty());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#setSolution(int)} and
     * {@link de.seyfarth.sudokulve.Field#getSolution()}.
     */
    @Test
    public final void validSolution() {
        Field field = new Field(2, 3, 2, 4);
        field.setSolution(4);
        assertEquals(4, field.getSolution());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#setSolution(int)}.
     */
    @Test
    public final void setSolutionTwice() {
        Field field = new Field(2, 3, 2, 4);
        field.setSolution(4);
        assertEquals(4, field.getSolution());
        field.setSolution(2);
        assertEquals(2, field.getSolution());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#setSolution(int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void solutionZero() {
        Field field = new Field(2, 3, 2, 4);
        field.setSolution(0);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#setSolution(int)}.
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public final void solutionOutOfBounds() {
        Field field = new Field(2, 3, 2, 4);
        field.setSolution(5);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#fillWithAllNumbers()}.
     */
    @Test
    public final void validFillWithAllNumbers() {
        Field field = new Field(2, 3, 2, 4);
        field.fillWithAllNumbers();
        assertTrue(field.remove(1));
        assertTrue(field.remove(3));
        assertTrue(field.remove(2));
        assertTrue(field.hasSolution());
        assertTrue(field.remove(4));
        assertTrue(field.isEmpty());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#setSolution(int)} and
     * {@link de.seyfarth.sudokulve.Field#getSolution()}.
     */
    @Test
    public final void validHasSolution() {
        Field field = new Field(2, 3, 2, 4);
        field.setSolution(1);
        assertTrue(field.hasSolution());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#hasSolution()}.
     */
    @Test
    public final void solutionOfEmptyField() {
        Field field = new Field(2, 3, 2, 4);
        assertFalse(field.hasSolution());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#getSolution()}.
     */
    @Test(expected = java.lang.IllegalStateException.class)
    public final void getSolutionOfEmptyField() {
        Field field = new Field(2, 3, 2, 4);
        field.getSolution();
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#hasSolution()}.
     */
    @Test
    public final void solutionOfFilledField() {
        Field field = new Field(2, 3, 2, 4);
        field.fillWithAllNumbers();
        assertFalse(field.hasSolution());
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#getSolution()}.
     */
    @Test(expected = java.lang.IllegalStateException.class)
    public final void getSolutionOfFilledField() {
        Field field = new Field(2, 3, 2, 4);
        field.fillWithAllNumbers();
        field.getSolution();
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#remove(int)}.
     */
    @Test
    public final void removeTwice() {
        Field field = new Field(2, 3, 2, 4);
        field.setSolution(2);
        assertTrue(field.remove(2));
        assertFalse(field.remove(2));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#remove(int)}.
     */
    @Test
    public final void removeFromFilled() {
        Field field = new Field(2, 3, 2, 4);
        field.setSolution(1);
        assertFalse(field.remove(2));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Field#remove(int)}.
     */
    @Test
    public final void removeFromEmpty() {
        Field field = new Field(2, 3, 2, 4);
        assertFalse(field.remove(3));
    }
}

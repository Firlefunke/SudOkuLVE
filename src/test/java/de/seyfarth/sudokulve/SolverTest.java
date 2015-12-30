package de.seyfarth.sudokulve;

import de.seyfarth.sudokulve.exceptions.NoSolutionException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author barbara
 */
public class SolverTest {

    Matrix matrix;

    @Before
    public void setUp() {
        matrix = new Matrix(2, 2);
        matrix.setValue(0, 0, 1);
        matrix.setValue(1, 1, 2);
        matrix.setValue(2, 0, 3);
        matrix.setValue(2, 2, 2);
        matrix.setValue(3, 3, 1);
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Solver#fillMatrix()}.
     */
    @Test
    public void fillMatrix() {
        Solver solver = new Solver(matrix);
        solver.fillMatrix();

        List<Field> row;
        int dimension = matrix.getDimension();
        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
            row = matrix.getRow(rowIndex);
            row.stream().forEach(field -> assertFalse(field.isEmpty()));
        }
        row = matrix.getRow(0);
        assertTrue(row.get(0).hasSolution());
        row = matrix.getRow(2);
        assertTrue(row.get(0).hasSolution());
        row = matrix.getRow(1);
        assertTrue(row.get(1).hasSolution());

    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Solver#checkSectors()}.
     *
     * @throws de.seyfarth.sudokulve.exceptions.NoSolutionException when sudoku is not solvable
     */
    @Test
    public void checkSectorsWithSolvableMatrix() throws NoSolutionException {
        Solver solver = new Solver(matrix);
        solver.fillMatrix();
        solver.checkSectors();
        
        assertEquals(1, matrix.getValue(0, 0));
        assertEquals(3, matrix.getValue(0, 1));
        assertEquals(4, matrix.getValue(0, 2));
        assertEquals(2, matrix.getValue(0, 3));

        assertEquals(4, matrix.getValue(1, 0));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(1, matrix.getValue(1, 2));
        assertEquals(3, matrix.getValue(1, 3));
        
        assertEquals(3, matrix.getValue(2, 0));
        assertEquals(1, matrix.getValue(2, 1));
        assertEquals(2, matrix.getValue(2, 2));
        assertEquals(4, matrix.getValue(2, 3));

        assertEquals(2, matrix.getValue(3, 0));
        assertEquals(4, matrix.getValue(3, 1));
        assertEquals(3, matrix.getValue(3, 2));
        assertEquals(1, matrix.getValue(3, 3));
    }

    /**
     * Test for {@link de.seyfarth.sudokulve.Solver#checkSectors()}.
     *
     * @throws de.seyfarth.sudokulve.exceptions.NoSolutionException when sudoku is not solvable
     */
    @Test(expected = de.seyfarth.sudokulve.exceptions.NoSolutionException.class)
    public void checkSectorsWithUnsolvableMatrix() throws NoSolutionException {
        matrix.setValue(0, 1, 1);
        Solver solver = new Solver(matrix);
        solver.fillMatrix();
        solver.checkSectors();
    }
}

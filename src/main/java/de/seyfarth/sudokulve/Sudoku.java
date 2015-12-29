package de.seyfarth.sudokulve;

import de.seyfarth.sudokulve.exceptions.NoSolutionException;
import de.seyfarth.sudokulve.exceptions.NoNumberException;
import de.seyfarth.sudokulve.exceptions.MultipleNumbersException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sudoku {
	private static final Logger LOG = Logger.getLogger("Sudoku");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Matrix matrix;
		try {
			matrix = new Matrix(9, 3, 3);
		} catch (NoSolutionException e) {
			LOG.log(Level.SEVERE, "Ungueltige Dimensionen oder Block-Laengen bzw. Block-Hoehen angegeben.", e);
			return;
		}

		
		matrix.setValue(1, 1, 4);
		matrix.setValue(1, 9, 9);
		matrix.setValue(2, 2, 2);
		matrix.setValue(2, 4, 1);
		matrix.setValue(2, 6, 8);
		matrix.setValue(2, 7, 7);
		matrix.setValue(2, 9, 6);
		matrix.setValue(3, 2, 1);
		matrix.setValue(3, 3, 5);
		matrix.setValue(3, 4, 2);
		matrix.setValue(3, 7, 4);
		matrix.setValue(3, 8, 8);
		matrix.setValue(4, 5, 1);
		matrix.setValue(4, 7, 9);
		matrix.setValue(5, 4, 8);
		matrix.setValue(5, 5, 5);
		matrix.setValue(5, 6, 7);
		matrix.setValue(5, 8, 4);
		matrix.setValue(6, 3, 3);
		matrix.setValue(6, 5, 6);
		matrix.setValue(6, 6, 2);
		matrix.setValue(7, 2, 3);
		matrix.setValue(7, 6, 6);
		matrix.setValue(7, 8, 2);
		matrix.setValue(8, 1, 2);
		matrix.setValue(8, 3, 6);
		matrix.setValue(8, 4, 5);
		matrix.setValue(8, 6, 4);
		matrix.setValue(8, 7, 1);
		matrix.setValue(8, 8, 3);
		matrix.setValue(9, 1, 8);
		matrix.setValue(9, 2, 7);
		matrix.setValue(9, 4, 3);
		matrix.setValue(9, 7, 6);
		matrix.setValue(9, 9, 5);
		Solver loeser = new Solver(matrix);
		try {
			loeser.fillMatrix();
			loeser.checkSectors();
		} catch (NoSolutionException e) {
			LOG.log(Level.SEVERE, "Für das Sudoku wurde keine Loesung gefunden.", e);
		}
		for (int zeile = 1; zeile <= matrix.getDimension(); zeile++) {
			StringBuilder ergebnis = new StringBuilder();
			for (int spalte = 1; spalte <= matrix.getDimension(); spalte++) {
				try {
					ergebnis.append(matrix.getValue(zeile, spalte));
                                        ergebnis.append(" ");
				} catch (MultipleNumbersException e) {
					LOG.log(Level.WARNING, "Es gibt mehr als eine Loesung fuer das Sudoku!", e);
					return;
				} catch (NoNumberException e) {
					LOG.log(Level.WARNING, "Es gibt keine Loesung fuer das Sudoku!", e);
					return;
				}
			}
			System.out.println(ergebnis.toString());
		}
	}
}

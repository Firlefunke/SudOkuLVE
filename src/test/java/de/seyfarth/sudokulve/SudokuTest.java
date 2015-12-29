package de.seyfarth.sudokulve;

import org.junit.Test;

import de.seyfarth.sudokulve.exceptions.KeineLoesungException;
import de.seyfarth.sudokulve.exceptions.KeineZifferException;
import de.seyfarth.sudokulve.exceptions.MehrAlsEineZifferException;

public class SudokuTest {

	/**
	 * Test method for {@link de.seyfarth.sudokulve.Feld#Feld(int, int, int)}.
	 */
	@Test
	public final void testSudoku() {
		Matrix matrix;
		try {
			matrix = new Matrix(9, 3, 3);
		} catch (KeineLoesungException e) {
			e.printStackTrace();

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
		Loeser loeser = new Loeser(matrix);
		try {
			loeser.fuelleMatrix();
		} catch (KeineLoesungException e) {
			e.printStackTrace();
		}
		try {
			loeser.pruefeSektoren();
		} catch (KeineLoesungException e) {
			e.printStackTrace();
		}
		for (int zeile = 1; zeile <= Matrix.dimension; zeile++) {
			String ergebnis = "";
			for (int spalte = 1; spalte <= Matrix.dimension; spalte++) {
				try {
					ergebnis = ergebnis + matrix.getValue(zeile, spalte) + " ";
				} catch (MehrAlsEineZifferException e) {
					// Kann vorkommen, wenn es mehrere Loesungen fuer ein Sudoku
					// gibt
					e.printStackTrace();
					System.out
							.println("Es gibt mehr als eine Loesung fuer das" +
									" Sudoku oder das Sudoku konnte noch nicht" +
									" vollständig gelöst werden!!");
					return;
				} catch (KeineZifferException e) {
					// Kann vorkommen, wenn es keine Loesung fuer ein Sudoku
					// gibt
					e.printStackTrace();
					System.out
							.println("\nEs gibt keine Loesung fuer das Sudoku!!");
					return;
				}
			}
			System.out.println(ergebnis);
		}
	}
}

package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Solver {
	private static final Logger LOG = Logger.getLogger("Solver");
	
	Field currentField;
	Matrix sudoku;
	ArrayList<Field> solvedFields;

	public Solver(Matrix matrix) {
		sudoku = matrix;
		solvedFields = new ArrayList<>();
	}

	void fillMatrix() throws NoSolutionException {
		for (Field editedField : sudoku.matrix()) {
			if (editedField.hasSolution()) {
				continue;
			}
			if (editedField.isEmpty()) {
				editedField.fillWithAllNumbers(sudoku.getDimension());
				removeFromSectors(editedField);
			}
			if (editedField.isEmpty()) {
				throw new NoSolutionException();
			}
			if (editedField.hasSolution()) {
				solvedFields.add(editedField);
			}
		}
	}

	private void removeFromSectors(Field currentField) {
		removeFromRow(currentField);
		removeFromColumn(currentField);
		removeFromBlock(currentField);
	}

	private void removeFromRow(Field currentField) {
		int index = currentField.row;
		ArrayList<Field> row = sudoku.getRow(index);
		removeFrom(row, currentField);
	}

	private void removeFromColumn(Field currentField) {
		int index = currentField.column;
		ArrayList<Field> column = sudoku.getColumn(index);
		removeFrom(column, currentField);
	}

	private void removeFromBlock(Field currentField) {
		int index = currentField.block;
		ArrayList<Field> block = sudoku.getBlock(index);
		removeFrom(block, currentField);
	}

	private void removeFrom(ArrayList<Field> sector, Field currentField) {
		for (Field field : sector) {
			if (field == currentField) {
				continue;
			}
			if (field.hasSolution()) {

				int number;
				try {
					number = field.getSolution();
					currentField.remove(number);
				} catch (MultipleNumbersException e) {
					LOG.log(Level.SEVERE, "Trotz Ueberpruefung mehrere Ziffern enthalten.", e);
				} catch (NoNumberException e) {
					LOG.log(Level.SEVERE, "Trotz Ueberpruefung keine Ziffern enthalten.", e);
				}
			}
		}
	}

	void checkSectors() throws NoSolutionException {
		while (!solvedFields.isEmpty()) {
			currentField = solvedFields.remove(0);
			try {
				deleteFromSectors(currentField);
			} catch (MultipleNumbersException e) {
				LOG.log(Level.SEVERE, "Trotz Ueberpruefung mehrere Ziffern enthalten.", e);
			}
		}
	}

	private void deleteFromSectors(Field currentField) throws NoSolutionException,
			MultipleNumbersException {
		deleteFromRow(currentField);
		deleteFromColumn(currentField);
		deleteFromBlock(currentField);
	}

	private void deleteFromRow(Field currentField) throws NoSolutionException,
			MultipleNumbersException {
		int index = currentField.row;
		ArrayList<Field> row = sudoku.getRow(index);
		deleteFrom(row, currentField);
	}

	private void deleteFromColumn(Field currentField) throws NoSolutionException {
		int index = currentField.column;
		ArrayList<Field> column = sudoku.getColumn(index);
		deleteFrom(column, currentField);
	}

	private void deleteFromBlock(Field currentField) throws NoSolutionException {
		int index = currentField.block;
		ArrayList<Field> block = sudoku.getBlock(index);
		deleteFrom(block, currentField);
	}

	private void deleteFrom(ArrayList<Field> sector, Field currentField)
			throws NoSolutionException {

		int number;
		try {
			number = currentField.getSolution();
		} catch (MultipleNumbersException e) {
			LOG.log(Level.SEVERE, "Precondition von loescheAus() wurde nicht eingehalten: Zu viele Ziffern enthalten.", e);
			throw new ProgrammingException();
		} catch (NoNumberException e) {
			LOG.log(Level.SEVERE, "Precondition von loescheAus() wurde nicht eingehalten: Keine Ziffer enthalten.", e);
			throw new ProgrammingException();
		}
		for (Field field : sector) {
			if (field == currentField) {
				continue;
			}
			boolean isRemoved = field.remove(number);
			if ((field.hasSolution()) && isRemoved) {
				solvedFields.add(field);
			}
			if (field.isEmpty()) {
				throw new NoSolutionException();
			}
		}
	}
}

package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.KeineZifferException;
import de.seyfarth.sudokulve.exceptions.MehrAlsEineZifferException;

public class Field {

	int row;
	int column;
	int block;

	private final ArrayList<Integer> solution;
	private int dimension;

	public Field(int row, int column, int block) {
		this.row = row;
		this.column = column;
		this.block = block;
		this.dimension = 0;
		solution = new ArrayList<>();
	}

	void fillWithAllNumbers(int dimension) {
		this.dimension = dimension;
		solution.clear();
		for (int ziffer = 1; ziffer <= dimension; ziffer++) {
			solution.add(ziffer);
		}
	}

	public boolean remove(int number) {
		int index = 0;
		while (dimension > index) {
			int currentNumber = solution.get(index);
			if (currentNumber == number) {
				solution.remove(index);
				dimension--;
				return true;
			}
			index++;
		}
		return false;
	}

	public int getSolution() throws MehrAlsEineZifferException, KeineZifferException {
		if (dimension > 1) {
			throw new MehrAlsEineZifferException("Feld enthaelt mehr als einen Wert");
		} else if (dimension < 1) {
			throw new KeineZifferException("Kein Wert im Feld");
		}
		return solution.get(0);
	}

	public boolean isEmpty() {
		return (this.dimension == 0);
	}

	public boolean hasSolution() {
		return (this.dimension == 1);
	}

	public void setSolution(int number) {
		solution.clear();
		solution.add(number);
		dimension = 1;
	}
}

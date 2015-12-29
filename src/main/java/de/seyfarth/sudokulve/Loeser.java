package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loeser {
	private static final Logger log = Logger.getLogger("Loeser");
	
	Field aktFeld;
	Matrix sudoku;
	ArrayList<Field> geloesteFelder;

	public Loeser(Matrix matrix) {
		sudoku = matrix;
		geloesteFelder = new ArrayList<>();
	}

	void fuelleMatrix() throws KeineLoesungException {
		for (Field editedFeld : sudoku.matrix()) {
			if (editedFeld.hasSolution()) {
				continue;
			}
			if (editedFeld.isEmpty()) {
				editedFeld.fillWithAllNumbers(sudoku.getDimension());
				leereAusSektoren(editedFeld);
			}
			if (editedFeld.isEmpty()) {
				throw new KeineLoesungException();
			}
			if (editedFeld.hasSolution()) {
				geloesteFelder.add(editedFeld);
			}
		}
	}

	private void leereAusSektoren(Field aktFeld) {
		leereAusZeile(aktFeld);
		leereAusSpalte(aktFeld);
		leereAusBlock(aktFeld);
	}

	private void leereAusZeile(Field aktFeld) {
		int indexZeile = aktFeld.row;
		ArrayList<Field> zeile = sudoku.getRow(indexZeile);
		leereAus(zeile, aktFeld);
	}

	private void leereAusSpalte(Field aktFeld) {
		int indexSpalte = aktFeld.column;
		ArrayList<Field> spalte = sudoku.getColumn(indexSpalte);
		leereAus(spalte, aktFeld);
	}

	private void leereAusBlock(Field aktFeld) {
		int indexBlock = aktFeld.block;
		ArrayList<Field> block = sudoku.getBlock(indexBlock);
		leereAus(block, aktFeld);
	}

	private void leereAus(ArrayList<Field> sektor, Field aktFeld) {
		for (Field feld : sektor) {
			if (feld == aktFeld) {
				continue;
			}
			if (feld.hasSolution()) {

				int ziffer;
				try {
					ziffer = feld.getSolution();
					aktFeld.remove(ziffer);
				} catch (MehrAlsEineZifferException e) {
					log.log(Level.SEVERE, "Trotz Ueberpruefung mehrere Ziffern enthalten.", e);
				} catch (KeineZifferException e) {
					log.log(Level.SEVERE, "Trotz Ueberpruefung keine Ziffern enthalten.", e);
				}
			}
		}
	}

	void pruefeSektoren() throws KeineLoesungException {
		while (!geloesteFelder.isEmpty()) {
			aktFeld = geloesteFelder.remove(0);
			try {
				loescheAusSektoren(aktFeld);
			} catch (MehrAlsEineZifferException e) {
				log.log(Level.SEVERE, "Trotz Ueberpruefung mehrere Ziffern enthalten.", e);
			}
		}
	}

	private void loescheAusSektoren(Field aktFeld) throws KeineLoesungException,
			MehrAlsEineZifferException {
		loescheAusZeile(aktFeld);
		loescheAusSpalte(aktFeld);
		loescheAusBlock(aktFeld);
	}

	private void loescheAusZeile(Field aktFeld) throws KeineLoesungException,
			MehrAlsEineZifferException {
		int indexZeile = aktFeld.row;
		ArrayList<Field> zeile = sudoku.getRow(indexZeile);
		loescheAus(zeile, aktFeld);
	}

	private void loescheAusSpalte(Field aktFeld) throws KeineLoesungException {
		int indexSpalte = aktFeld.column;
		ArrayList<Field> spalte = sudoku.getColumn(indexSpalte);
		loescheAus(spalte, aktFeld);
	}

	private void loescheAusBlock(Field aktFeld) throws KeineLoesungException {
		int indexBlock = aktFeld.block;
		ArrayList<Field> block = sudoku.getBlock(indexBlock);
		loescheAus(block, aktFeld);
	}

	private void loescheAus(ArrayList<Field> sektor, Field aktFeld)
			throws KeineLoesungException {

		int ziffer;
		try {
			ziffer = aktFeld.getSolution();
		} catch (MehrAlsEineZifferException e) {
			log.log(Level.SEVERE, "Precondition von loescheAus() wurde nicht eingehalten: Zu viele Ziffern enthalten.", e);
			throw new ProgrammierException();
		} catch (KeineZifferException e) {
			log.log(Level.SEVERE, "Precondition von loescheAus() wurde nicht eingehalten: Keine Ziffer enthalten.", e);
			throw new ProgrammierException();
		}
		for (Field feld : sektor) {
			if (feld == aktFeld) {
				continue;
			}
			boolean wurdeEntfernt = feld.remove(ziffer);
			if ((feld.hasSolution()) && wurdeEntfernt) {
				geloesteFelder.add(feld);
			}
			if (feld.isEmpty()) {
				throw new KeineLoesungException();
			}
		}
	}
}

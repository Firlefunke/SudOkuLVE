package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loeser {
	private static final Logger log = Logger.getLogger("Loeser");
	
	Feld aktFeld;
	Matrix sudoku;
	ArrayList<Feld> geloesteFelder;

	public Loeser(Matrix matrix) {
		sudoku = matrix;
		geloesteFelder = new ArrayList<>();
	}

	void fuelleMatrix() throws KeineLoesungException {
		for (Feld editedFeld : sudoku.matrix()) {
			if (editedFeld.hatGenauEineZiffer()) {
				continue;
			}
			if (editedFeld.istLeer()) {
				editedFeld.fuelleMitAllenZiffern(sudoku.getDimension());
				leereAusSektoren(editedFeld);
			}
			if (editedFeld.istLeer()) {
				throw new KeineLoesungException();
			}
			if (editedFeld.hatGenauEineZiffer()) {
				geloesteFelder.add(editedFeld);
			}
		}
	}

	private void leereAusSektoren(Feld aktFeld) {
		leereAusZeile(aktFeld);
		leereAusSpalte(aktFeld);
		leereAusBlock(aktFeld);
	}

	private void leereAusZeile(Feld aktFeld) {
		int indexZeile = aktFeld.zeile;
		ArrayList<Feld> zeile = sudoku.zeile(indexZeile);
		leereAus(zeile, aktFeld);
	}

	private void leereAusSpalte(Feld aktFeld) {
		int indexSpalte = aktFeld.spalte;
		ArrayList<Feld> spalte = sudoku.spalte(indexSpalte);
		leereAus(spalte, aktFeld);
	}

	private void leereAusBlock(Feld aktFeld) {
		int indexBlock = aktFeld.block;
		ArrayList<Feld> block = sudoku.block(indexBlock);
		leereAus(block, aktFeld);
	}

	private void leereAus(ArrayList<Feld> sektor, Feld aktFeld) {
		for (Feld feld : sektor) {
			if (feld == aktFeld) {
				continue;
			}
			if (feld.hatGenauEineZiffer()) {

				int ziffer;
				try {
					ziffer = feld.holeEinzigenWert();
					aktFeld.entferne(ziffer);
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

	private void loescheAusSektoren(Feld aktFeld) throws KeineLoesungException,
			MehrAlsEineZifferException {
		loescheAusZeile(aktFeld);
		loescheAusSpalte(aktFeld);
		loescheAusBlock(aktFeld);
	}

	private void loescheAusZeile(Feld aktFeld) throws KeineLoesungException,
			MehrAlsEineZifferException {
		int indexZeile = aktFeld.zeile;
		ArrayList<Feld> zeile = sudoku.zeile(indexZeile);
		loescheAus(zeile, aktFeld);
	}

	private void loescheAusSpalte(Feld aktFeld) throws KeineLoesungException {
		int indexSpalte = aktFeld.spalte;
		ArrayList<Feld> spalte = sudoku.spalte(indexSpalte);
		loescheAus(spalte, aktFeld);
	}

	private void loescheAusBlock(Feld aktFeld) throws KeineLoesungException {
		int indexBlock = aktFeld.block;
		ArrayList<Feld> block = sudoku.block(indexBlock);
		loescheAus(block, aktFeld);
	}

	private void loescheAus(ArrayList<Feld> sektor, Feld aktFeld)
			throws KeineLoesungException {

		int ziffer;
		try {
			ziffer = aktFeld.holeEinzigenWert();
		} catch (MehrAlsEineZifferException e) {
			log.log(Level.SEVERE, "Precondition von loescheAus() wurde nicht eingehalten: Zu viele Ziffern enthalten.", e);
			throw new ProgrammierException();
		} catch (KeineZifferException e) {
			log.log(Level.SEVERE, "Precondition von loescheAus() wurde nicht eingehalten: Keine Ziffer enthalten.", e);
			throw new ProgrammierException();
		}
		for (Feld feld : sektor) {
			if (feld == aktFeld) {
				continue;
			}
			boolean wurdeEntfernt = feld.entferne(ziffer);
			if ((feld.hatGenauEineZiffer()) && wurdeEntfernt) {
				geloesteFelder.add(feld);
			}
			if (feld.istLeer()) {
				throw new KeineLoesungException();
			}
		}
	}
}

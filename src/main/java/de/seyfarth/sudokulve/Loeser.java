package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.*;
import java.util.Date;

public class Loeser {
	Feld aktFeld;
	Matrix sudoku;
	ArrayList<Feld> geloesteFelder;

	public Loeser(Matrix matrix) {
		sudoku = matrix;
		geloesteFelder = new ArrayList<Feld>();
	}

	void fuelleMatrix() throws KeineLoesungException {
		for (Feld aktFeld : sudoku.matrix()) {
			if (aktFeld.hatGenauEineZiffer()) {
				continue;
			}
			if (aktFeld.istLeer()) {
				aktFeld.fuelleMitAllenZiffern(sudoku.getDimension());
				leereAusSektoren(aktFeld);
			}
			if (aktFeld.istLeer()) {
				throw new KeineLoesungException();
			}
			if (aktFeld.hatGenauEineZiffer()) {
				geloesteFelder.add(aktFeld);
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
					// Sollte niemals auftreten, da vorher überprüft wurde, ob
					// eine L�sung drin ist
					e.printStackTrace();
				} catch (KeineZifferException e) {
					// Sollte niemals auftreten, da vorher überprüft wurde, ob
					// eine L�sung drin ist
					e.printStackTrace();
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
				// Sollte nie vorkommen, da in geloeste Felder nur Felder mit
				// genau einer Ziffer sind
				e.printStackTrace();
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
			// Misst - hier hat sich ein Programmierfehler eingeschlichen.
			// loescheAus() darf nur aufgerufen werden, wenn tats�chlich genau
			// ein Wert im aktuellen Feld ist
			e.printStackTrace();
			throw new ProgrammierException();
		} catch (KeineZifferException e) {
			// Misst - hier hat sich ein Programmierfehler eingeschlichen.
			// loescheAus() darf nur aufgerufen werden, wenn tats�chlich genau
			// ein Wert im aktuellen Feld ist
			e.printStackTrace();
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

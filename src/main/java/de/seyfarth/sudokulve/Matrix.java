package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.KeineLoesungException;
import de.seyfarth.sudokulve.exceptions.KeineZifferException;
import de.seyfarth.sudokulve.exceptions.MehrAlsEineZifferException;

public class Matrix {

	static int dimension;
	static int anzahlBlockZeilen;
	static int anzahlBlockSpalten;
	static Feld[] felder;

	public Matrix(int dim, int anzBlockZeilen, int anzBlockSpalten)
			throws KeineLoesungException {
		if (anzBlockZeilen * anzBlockSpalten != dim) {
			throw new KeineLoesungException();
		}
		felder = new Feld[dim * dim];
		dimension = dim;
		anzahlBlockZeilen = anzBlockZeilen;
		anzahlBlockSpalten = anzBlockSpalten;

		int i = 0;
		for (int zeile = 1; zeile <= dimension; zeile++) {
			for (int spalte = 1; spalte <= dimension; spalte++) {
				int block = getBlock(zeile, spalte);
				Feld feld = new Feld(zeile, spalte, block);
				felder[i] = feld;
				i++;
			}
		}
	}

	private int getBlock(int zeile, int spalte) {
		int zeileDesBlocks = ((zeile - 1) / anzahlBlockZeilen) + 1; // x-Koordinate
																	// des
																	// Blocks
		int spalteDesBlocks = ((spalte - 1) / anzahlBlockSpalten) + 1; // y-Koordinate
																		// des
																		// Blocks
		int anzBloeckeInSpalte = (dimension / anzahlBlockSpalten); // Bloecke in
																	// einer
																	// Spalte
		int block = ((zeileDesBlocks - 1) * anzBloeckeInSpalte)
				+ spalteDesBlocks;
		return block;
	}

	public void setValue(int zeile, int spalte, int wert) {
		int feldIndex = (zeile - 1) * dimension + spalte - 1;
		felder[feldIndex].setzeEinzigenWert(wert);
		}

	public int getValue(int zeile, int spalte)
			throws MehrAlsEineZifferException, KeineZifferException {
		int feldIndex = (zeile - 1) * dimension + spalte - 1;
		int inhalt = felder[feldIndex].holeEinzigenWert();
		return inhalt;
	}

	public int getDimension() {
		return dimension;
	}

	public ArrayList<Feld> zeile(int indexZeile) {
		ArrayList<Feld> zeilenFelder = new ArrayList<Feld>();

		int erstesFeld = (indexZeile - 1) * dimension;
		for (int i = 0; i < dimension; i++) {
			zeilenFelder.add(felder[erstesFeld + i]);
		}
		return zeilenFelder;
	}

	public ArrayList<Feld> spalte(int indexSpalte) {
		ArrayList<Feld> spaltenFelder = new ArrayList<Feld>();

		int erstesFeld = indexSpalte - 1;
		for (int i = 0; i < dimension; i++) {
			spaltenFelder.add(felder[erstesFeld + i * dimension]);
		}
		return spaltenFelder;
	}

	public ArrayList<Feld> block(int indexBlock) {
		ArrayList<Feld> blockFelder = new ArrayList<Feld>();

		int blockZeile = (indexBlock - 1) / (dimension / anzahlBlockZeilen);
		int blockSpalte = (indexBlock - 1) % (dimension / anzahlBlockSpalten);
		int indexErsteZeile = blockZeile * anzahlBlockZeilen;
		int indexErsteSpalte = blockSpalte * anzahlBlockSpalten;
		for (int i = 0; i < anzahlBlockZeilen; i++) {
			for (int j = 0; j < anzahlBlockSpalten; j++) {
				int feldIndex = (indexErsteZeile + i) * dimension
						+ (indexErsteSpalte + j);
				blockFelder.add(felder[feldIndex]);
			}
		}
		return blockFelder;
	}

	public Feld[] matrix() {
		return felder;
	}
}

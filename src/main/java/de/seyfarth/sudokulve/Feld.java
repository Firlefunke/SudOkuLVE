package de.seyfarth.sudokulve;

import java.util.ArrayList;
import de.seyfarth.sudokulve.exceptions.KeineZifferException;
import de.seyfarth.sudokulve.exceptions.MehrAlsEineZifferException;

public class Feld {
	int zeile;
	int spalte;
	int block;

	private final ArrayList<Integer> inhalt;
	private int laenge;

	public Feld(int zeile, int spalte, int block) {
		this.zeile = zeile;
		this.spalte = spalte;
		this.block = block;
		this.laenge = 0;
		inhalt = new ArrayList<Integer>();
	}

	void fuelleMitAllenZiffern(int dimension) {
		laenge = dimension;
		inhalt.clear();
		for (int ziffer = 1; ziffer <= dimension; ziffer++) {
			inhalt.add(ziffer);
		}
	}

	public boolean entferne(int ziffer) {
		int index = 0;
		while (laenge > index) {
			int aktZiffer = inhalt.get(index);
			if (aktZiffer == ziffer) {
				inhalt.remove(index);
				laenge--;
				return true;
			}
			index++;
		}
		return false;
	}

	public int holeEinzigenWert() throws MehrAlsEineZifferException,
			KeineZifferException {
		if (laenge > 1) {
			throw new MehrAlsEineZifferException(
					"Feld enthaelt mehr als einen Wert");
		} else if (laenge < 1) {
			throw new KeineZifferException("Kein Wert im Feld");
		}
		return inhalt.get(0);
	}

	public boolean istLeer() {
		return (this.laenge == 0);
	}

	public boolean hatGenauEineZiffer() {
		return (this.laenge == 1);
	}

	public void setzeEinzigenWert(int ziffer) {
		inhalt.clear();
		inhalt.add(ziffer);
		laenge = 1;
	}
}

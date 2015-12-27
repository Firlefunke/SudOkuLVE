/**
 * 
 */
package de.seyfarth.sudokulve;

import static org.junit.Assert.*;

import org.junit.Test;

import de.seyfarth.sudokulve.exceptions.KeineZifferException;
import de.seyfarth.sudokulve.exceptions.MehrAlsEineZifferException;

/**
 * @author barbara
 * 
 */
public class FeldTest {

	/**
	 * Test method for {@link sudokuloeser.Feld#Feld(int, int, int)}.
	 */
	@Test
	public final void testFeld() {
		// Erzeuge ein Feld, das in der 2. Spalte, 3. Zeile und Block 2 ist
		Feld test_feld = new Feld(2, 3, 2);

		// Check content of test_feld
		assertEquals("Zeilennummer", 2, test_feld.zeile);
		assertEquals("Spaltennummer", 3, test_feld.spalte);
		assertEquals("Blocknummer", 2, test_feld.block);
	}

	/**
	 * Test method for {@link sudokuloeser.Feld#fuelleMitAllenZiffern(int)}
	 * {@link sudokuloeser.Feld#entferne(int)}
	 * {@link sudokuloeser.Feld#istLeer(int)}
	 * {@link sudokuloeser.Feld#hatGenauEineZiffer(int)}
	 */
	@Test
	public final void testFuelleMitAllenZiffernUndLeereDann() {
		Feld test_feld = new Feld(2, 3, 2);
		boolean warEnthalten = false;

		// F�lle das Feld mit den Ziffern 1, 2, 3, 4
		test_feld.fuelleMitAllenZiffern(4);
		// Entferne eine Ziffer, die nie in der Liste war
		warEnthalten = test_feld.entferne(7);
		assertFalse(warEnthalten);
		// Und wieder f�llen... es sollten immer noch nur die Ziffern 1, 2, 3, 4
		// genau einmal enthalten
		test_feld.fuelleMitAllenZiffern(4);
		// Entferne Ziffer 3
		warEnthalten = test_feld.entferne(3);
		assertTrue(warEnthalten);
		// Feld hat mehr als eine Ziffer
		assertFalse(test_feld.istLeer());
		assertFalse(test_feld.hatGenauEineZiffer());
		// Entferne Ziffer 3, da die Ziffer schon entfernt wurde, wurde auch
		// nichts entfernt
		warEnthalten = test_feld.entferne(3);
		assertFalse(warEnthalten);
		// Entferne Ziffer 1
		warEnthalten = test_feld.entferne(1);
		assertTrue(warEnthalten);
		// Feld hat mehr als eine Ziffer
		assertFalse(test_feld.istLeer());
		assertFalse(test_feld.hatGenauEineZiffer());
		// Entferne Ziffer 4
		warEnthalten = test_feld.entferne(4);
		assertTrue(warEnthalten);
		// Feld hat mehr jetzt genau eine Ziffer, n�mlich die 2
		assertFalse(test_feld.istLeer());
		assertTrue(test_feld.hatGenauEineZiffer());
		// Entferne Ziffer 1, da die Ziffer schon entfernt wurde, wurde auch
		// nichts entfernt
		warEnthalten = test_feld.entferne(1);
		assertFalse(warEnthalten);
		// Entferne nun die letzte Ziffer, die 2
		warEnthalten = test_feld.entferne(2);
		assertTrue(warEnthalten);
		// Feld ist jetzt leer
		assertTrue(test_feld.istLeer());
		assertFalse(test_feld.hatGenauEineZiffer());
		// Entferne Ziffer 3, da die Ziffer schon entfernt wurde, wurde auch
		// nichts entfernt
		warEnthalten = test_feld.entferne(3);
		assertFalse(warEnthalten);
		// Feld ist weiterhin leer
		assertTrue(test_feld.istLeer());
		assertFalse(test_feld.hatGenauEineZiffer());
	}

	/**
	 * Test method for {@link sudokuloeser.Feld#holeEinzigenWert()}
	 */
	@Test
	public final void testHoleEinzigenWert() {
		Feld test_feld = new Feld(2, 3, 2);
		boolean warEnthalten = false;
		boolean richtigeExceptionAufgetreten = false;
		int ziffer = 0;

		// Erstmal f�llen wir das Feld mit den Ziffern 1, 2, 3, 4
		test_feld.fuelleMitAllenZiffern(4);
		// Dann entfernen wir zwei von den vier Ziffern
		warEnthalten = test_feld.entferne(4);
		assertTrue(warEnthalten);
		warEnthalten = test_feld.entferne(1);
		assertTrue(warEnthalten);
		// Jetzt versuchen wir mal, die einzige Ziffer zu holen, obwohl
		// eigentlich noch zwei Ziffern im Feld sind
		try {
			ziffer = test_feld.holeEinzigenWert();
			// Da hier eine Exception auftreten muss, darf das Programm dieses
			// assert nie ausf�hren!
			richtigeExceptionAufgetreten = false;
		} catch (MehrAlsEineZifferException e) {
			// Diese Exception muesste auftreten
			richtigeExceptionAufgetreten = true;
		} catch (KeineZifferException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		}
		assertTrue(richtigeExceptionAufgetreten);
		// Jetzt entfernen wir noch eine Ziffer, so dass nur noch eine Ziffer im
		// Feld ist
		warEnthalten = test_feld.entferne(2);
		assertTrue(warEnthalten);
		// Jetzt m�sste eigentlich noch genau eine Ziffer drin sein
		assertTrue(test_feld.hatGenauEineZiffer());
		// Und diese Ziffer ist eine 3
		try {
			ziffer = test_feld.holeEinzigenWert();
			// Da hier keine Exception auftreten darf, geht es einfach weiter
			richtigeExceptionAufgetreten = true;
		} catch (MehrAlsEineZifferException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		} catch (KeineZifferException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		}
		assertTrue(richtigeExceptionAufgetreten);
		assertTrue(ziffer == 3);
		// Jetzt entfernen wir auch noch die 3
		test_feld.entferne(3);
		// Das Feld muss leer sein
		assertTrue(test_feld.istLeer());
		// Das Lesen des Wertes muss eine Exception ausloesen
		try {
			ziffer = test_feld.holeEinzigenWert();
			// Da hier eine Exception auftreten muss, darf das Programm diese
			// Zuweisung nie ausfuehren!
			richtigeExceptionAufgetreten = false;
		} catch (MehrAlsEineZifferException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		} catch (KeineZifferException e) {
			// Diese Exception muss auftreten
			richtigeExceptionAufgetreten = true;
		}
		assertTrue(richtigeExceptionAufgetreten);
	}

	/**
	 * Test method for {@link sudokuloeser.Feld#holeEinzigenWert()}
	 * {@link sudokuloeser.Feld#setzeEinzigenWert(int)}
	 */
	@Test
	public final void testSetzeEinzigenWert() {
		Feld test_feld = new Feld(2, 3, 2);
		int ziffer = 0;
		boolean richtigeExceptionAufgetreten = false;

		// Als erstes ueberpruefen wir, ob das Feld leer ist
		assertTrue(test_feld.istLeer());
		// Wir schreiben einen Wert hinein
		test_feld.setzeEinzigenWert(234);
		// Nun muss genau eine Ziffer/Zahl drin stehen
		assertTrue(test_feld.hatGenauEineZiffer());
		// Und diese Zahl muss die 234 sein
		try {
			ziffer = test_feld.holeEinzigenWert();
			// Da hier keine Exception auftreten darf, geht es einfach weiter
			richtigeExceptionAufgetreten = true;
		} catch (MehrAlsEineZifferException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		} catch (KeineZifferException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		}
		assertTrue(richtigeExceptionAufgetreten);
		assertTrue(ziffer == 234);
		// Jetzt fuellen wir mal das Feld mir mehr Ziffern
		test_feld.fuelleMitAllenZiffern(17);
		// Pruefen, ob es auch wirklich mehr als eine Ziffer sind
		assertFalse(test_feld.istLeer());
		assertFalse(test_feld.hatGenauEineZiffer());
		// Und setzen jetzt wieder genau eine Ziffer
		test_feld.setzeEinzigenWert(3);
		// Nun muss genau eine Zifferdrin stehen
		assertTrue(test_feld.hatGenauEineZiffer());
		// Und diese Ziffer muss die 3 sein
		try {
			ziffer = test_feld.holeEinzigenWert();
			// Da hier keine Exception auftreten darf, geht es einfach weiter
			richtigeExceptionAufgetreten = true;
		} catch (MehrAlsEineZifferException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		} catch (KeineZifferException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		}
		assertTrue(richtigeExceptionAufgetreten);
		assertEquals(ziffer, 3);
	}
}

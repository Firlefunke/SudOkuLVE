/**
 * 
 */
package de.seyfarth.sudokulve;

import static org.junit.Assert.*;

import org.junit.Test;

import de.seyfarth.sudokulve.exceptions.NoNumberException;
import de.seyfarth.sudokulve.exceptions.MultipleNumbersException;

/**
 * @author barbara
 * 
 */
public class FieldTest {

	/**
	 * Test method for {@link de.seyfarth.sudokulve.Field#Field(int, int, int)}.
	 */
	@Test
	public final void testField() {
		// Erzeuge ein Field, das in der 2. Spalte, 3. Zeile und Block 2 ist
		Field test_feld = new Field(2, 3, 2);

		// Check content of test_feld
		assertEquals("Zeilennummer", 2, test_feld.row);
		assertEquals("Spaltennummer", 3, test_feld.column);
		assertEquals("Blocknummer", 2, test_feld.block);
	}

	/**
	 * Test method for {@link de.seyfarth.sudokulve.Field#fillWithAllNumbers(int)}
	 * {@link de.seyfarth.sudokulve.Field#remove(int)}
	 * {@link de.seyfarth.sudokulve.Field#isEmpty()}
	 * {@link de.seyfarth.sudokulve.Field#hasSolution()}
	 */
	@Test
	public final void testFuelleMitAllenZiffernUndLeereDann() {
		Field test_feld = new Field(2, 3, 2);
		boolean warEnthalten;

		// F�lle das Field mit den Ziffern 1, 2, 3, 4
		test_feld.fillWithAllNumbers(4);
		// Entferne eine Ziffer, die nie in der Liste war
		warEnthalten = test_feld.remove(7);
		assertFalse(warEnthalten);
		// Und wieder f�llen... es sollten immer noch nur die Ziffern 1, 2, 3, 4
		// genau einmal enthalten
		test_feld.fillWithAllNumbers(4);
		// Entferne Ziffer 3
		warEnthalten = test_feld.remove(3);
		assertTrue(warEnthalten);
		// Field hat mehr als eine Ziffer
		assertFalse(test_feld.isEmpty());
		assertFalse(test_feld.hasSolution());
		// Entferne Ziffer 3, da die Ziffer schon entfernt wurde, wurde auch
		// nichts entfernt
		warEnthalten = test_feld.remove(3);
		assertFalse(warEnthalten);
		// Entferne Ziffer 1
		warEnthalten = test_feld.remove(1);
		assertTrue(warEnthalten);
		// Field hat mehr als eine Ziffer
		assertFalse(test_feld.isEmpty());
		assertFalse(test_feld.hasSolution());
		// Entferne Ziffer 4
		warEnthalten = test_feld.remove(4);
		assertTrue(warEnthalten);
		// Field hat mehr jetzt genau eine Ziffer, n�mlich die 2
		assertFalse(test_feld.isEmpty());
		assertTrue(test_feld.hasSolution());
		// Entferne Ziffer 1, da die Ziffer schon entfernt wurde, wurde auch
		// nichts entfernt
		warEnthalten = test_feld.remove(1);
		assertFalse(warEnthalten);
		// Entferne nun die letzte Ziffer, die 2
		warEnthalten = test_feld.remove(2);
		assertTrue(warEnthalten);
		// Field ist jetzt leer
		assertTrue(test_feld.isEmpty());
		assertFalse(test_feld.hasSolution());
		// Entferne Ziffer 3, da die Ziffer schon entfernt wurde, wurde auch
		// nichts entfernt
		warEnthalten = test_feld.remove(3);
		assertFalse(warEnthalten);
		// Field ist weiterhin leer
		assertTrue(test_feld.isEmpty());
		assertFalse(test_feld.hasSolution());
	}

	/**
	 * Test method for {@link de.seyfarth.sudokulve.Field#getSolution()}
	 */
	@Test
	public final void testHoleEinzigenWert() {
		Field test_feld = new Field(2, 3, 2);
		boolean warEnthalten;
		boolean richtigeExceptionAufgetreten;
		int ziffer = 0;

		// Erstmal f�llen wir das Field mit den Ziffern 1, 2, 3, 4
		test_feld.fillWithAllNumbers(4);
		// Dann entfernen wir zwei von den vier Ziffern
		warEnthalten = test_feld.remove(4);
		assertTrue(warEnthalten);
		warEnthalten = test_feld.remove(1);
		assertTrue(warEnthalten);
		// Jetzt versuchen wir mal, die einzige Ziffer zu holen, obwohl
		// eigentlich noch zwei Ziffern im Field sind
		try {
			ziffer = test_feld.getSolution();
			// Da hier eine Exception auftreten muss, darf das Programm dieses
			// assert nie ausf�hren!
			richtigeExceptionAufgetreten = false;
		} catch (MultipleNumbersException e) {
			// Diese Exception muesste auftreten
			richtigeExceptionAufgetreten = true;
		} catch (NoNumberException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		}
		assertTrue(richtigeExceptionAufgetreten);
		// Jetzt entfernen wir noch eine Ziffer, so dass nur noch eine Ziffer im
		// Field ist
		warEnthalten = test_feld.remove(2);
		assertTrue(warEnthalten);
		// Jetzt m�sste eigentlich noch genau eine Ziffer drin sein
		assertTrue(test_feld.hasSolution());
		// Und diese Ziffer ist eine 3
		try {
			ziffer = test_feld.getSolution();
			// Da hier keine Exception auftreten darf, geht es einfach weiter
			richtigeExceptionAufgetreten = true;
		} catch (MultipleNumbersException | NoNumberException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		}
		assertTrue(richtigeExceptionAufgetreten);
		assertTrue(ziffer == 3);
		// Jetzt entfernen wir auch noch die 3
		test_feld.remove(3);
		// Das Field muss leer sein
		assertTrue(test_feld.isEmpty());
		// Das Lesen des Wertes muss eine Exception ausloesen
		try {
			test_feld.getSolution();
			// Da hier eine Exception auftreten muss, darf das Programm diese
			// Zuweisung nie ausfuehren!
			richtigeExceptionAufgetreten = false;
		} catch (MultipleNumbersException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		} catch (NoNumberException e) {
			// Diese Exception muss auftreten
			richtigeExceptionAufgetreten = true;
		}
		assertTrue(richtigeExceptionAufgetreten);
	}

	/**
	 * Test method for {@link de.seyfarth.sudokulve.Field#getSolution()}
	 * {@link de.seyfarth.sudokulve.Field#setSolution(int)}
	 */
	@Test
	public final void testSetzeEinzigenWert() {
		Field test_feld = new Field(2, 3, 2);
		int ziffer = 0;
		boolean richtigeExceptionAufgetreten;

		// Als erstes ueberpruefen wir, ob das Field leer ist
		assertTrue(test_feld.isEmpty());
		// Wir schreiben einen Wert hinein
		test_feld.setSolution(234);
		// Nun muss genau eine Ziffer/Zahl drin stehen
		assertTrue(test_feld.hasSolution());
		// Und diese Zahl muss die 234 sein
		try {
			ziffer = test_feld.getSolution();
			// Da hier keine Exception auftreten darf, geht es einfach weiter
			richtigeExceptionAufgetreten = true;
		} catch (MultipleNumbersException | NoNumberException e) {
			richtigeExceptionAufgetreten = false;
		}
		assertTrue(richtigeExceptionAufgetreten);
		assertTrue(ziffer == 234);
		// Jetzt fuellen wir mal das Field mir mehr Ziffern
		test_feld.fillWithAllNumbers(17);
		// Pruefen, ob es auch wirklich mehr als eine Ziffer sind
		assertFalse(test_feld.isEmpty());
		assertFalse(test_feld.hasSolution());
		// Und setzen jetzt wieder genau eine Ziffer
		test_feld.setSolution(3);
		// Nun muss genau eine Zifferdrin stehen
		assertTrue(test_feld.hasSolution());
		// Und diese Ziffer muss die 3 sein
		try {
			ziffer = test_feld.getSolution();
			// Da hier keine Exception auftreten darf, geht es einfach weiter
			richtigeExceptionAufgetreten = true;
		} catch (MultipleNumbersException | NoNumberException e) {
			// Diese Exception darf hier nicht auftreten
			richtigeExceptionAufgetreten = false;
		}
		assertTrue(richtigeExceptionAufgetreten);
		assertEquals(ziffer, 3);
	}
}

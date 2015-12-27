package de.seyfarth.sudokulve.exceptions;

@SuppressWarnings("serial")
/**
 * 
 * @author barbara
 * @brief Programmierfehler
 * 
 * Diese Exception wird in allen Zweigen geworfen,
 * die bei einer sauberen Programmierung nicht erreicht
 * werden dürfen.
 *
 */
public class ProgrammierException extends RuntimeException {
	public ProgrammierException() {
		super();
	}

	public ProgrammierException(String s) {
		super(s);
	}

}

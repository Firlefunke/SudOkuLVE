package de.seyfarth.sudokulve.exceptions;

@SuppressWarnings("serial")
public class NoSolutionException extends Exception {
	public NoSolutionException() {
		super();
	}

	public NoSolutionException(String s) {
		super(s);
	}

}

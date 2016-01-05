package de.seyfarth.sudokulve.exceptions;

/**
 * This Exception is thrown to indecate that a sudoku matrix has no correct
 * solution.
 */
public class NoSolutionException extends Exception {

    private static final long serialVersionUID = 3091815332603582985L;

    /**
     * Construct a NoSolutionException.
     */
    public NoSolutionException() {
        super();
    }

    /**
     * Construct an NoSolutionException with a custom message.
     *
     * @param message a message to describe the exceptions cause
     */
    public NoSolutionException(final String message) {
        super(message);
    }

}

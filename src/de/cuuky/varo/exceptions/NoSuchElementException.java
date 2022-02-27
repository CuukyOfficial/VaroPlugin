package de.cuuky.varo.exceptions;

public class NoSuchElementException extends NullPointerException {

    public NoSuchElementException() {
        super("Could not find player");
    }
}

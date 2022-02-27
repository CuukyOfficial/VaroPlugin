package de.cuuky.varo.exceptions;

public class NoSuchPlayerException extends NullPointerException {

    public NoSuchPlayerException() {
        super("Could not find player");
    }
}

package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     *
     * @param message the specified message.
     * @param cause the specified cause.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

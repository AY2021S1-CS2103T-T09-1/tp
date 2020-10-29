package seedu.gradpad.logic.commands;

import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Checks if the command requires confirmation from the user and needs to be stalled.
     *
     * @return generally returns false as most commands do not require confirmation from the user.
     */
    public boolean requiresStall() {
        return false;
    }
}

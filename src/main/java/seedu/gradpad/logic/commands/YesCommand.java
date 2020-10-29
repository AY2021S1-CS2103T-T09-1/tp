package seedu.gradpad.logic.commands;

import seedu.gradpad.model.Model;

/**
 * Confirms a command that requires confirmation.
 */
public class YesCommand extends Command {

    public static final String COMMAND_WORD = "yes";
    public static final String NO_CONFIRMATION_MESSAGE = "There is nothing to confirm!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(NO_CONFIRMATION_MESSAGE);
    }
}

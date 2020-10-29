package seedu.gradpad.logic.commands;

import seedu.gradpad.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {
    public static final String ADD_COMMAND = "add c/CS1231 cr/4 t/Core : Adds module 'CS1231'"
            + "with 4 credits and a 'Core' tag to it.";
    public static final String HELP_COMMAND = "help : Displays the help guide.";
    public static final String LIST_COMMAND = "list : Displays list of current modules stored";
    public static final String EDIT_COMMAND = "edit 1 c/CS1231 cr/4 :"
            + "Replaces the current module at index 1 with module 'CS1231' with 4 credits.";
    public static final String FIND_COMMAND = "find CS1231 : Searches for the 'CS1231' module.";
    public static final String DELETE_COMMAND = "delete 1 : Deletes the module at index 1.";
    public static final String CHECKMC_COMMAND = "checkmc : Checks the current total credits achieved by all modules.";
    public static final String EXIT_COMMAND = "exit : exits GradPad.";
    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = HELP_COMMAND + "\n\n" + ADD_COMMAND + "\n\n" + LIST_COMMAND
            + "\n\n" + EDIT_COMMAND + "\n\n" + DELETE_COMMAND + "\n\n" + FIND_COMMAND + "\n\n" + CHECKMC_COMMAND
            + "\n\n" + EXIT_COMMAND;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false);
    }
}

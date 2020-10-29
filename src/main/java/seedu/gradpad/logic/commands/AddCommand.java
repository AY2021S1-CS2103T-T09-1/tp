package seedu.gradpad.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.module.Module;

/**
 * Adds a Module to the GradPad.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to the GradPad. "
            + "Parameters: "
            + "MODULE CODE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS1231 "
            + PREFIX_TAG + "Foundation";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the GradPad";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

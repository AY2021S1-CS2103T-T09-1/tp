package seedu.gradpad.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.gradpad.commons.core.Messages;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;

/**
 * Deletes a Module identified using it's displayed index from the GradPad.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the module code used in the Completed Modules.\n"
            + "Parameters: MODULE CODE\n"
            + "Example: " + COMMAND_WORD + " cs2103t";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you wish to delete the following"
            + " module?\n\n";

    private final ModuleCode code;

    public DeleteCommand(ModuleCode code) {
        this.code = code;
    }

    /**
     * Retrieves the module to be deleted.
     *
     * @param model The Model which the command operates on.
     * @return The module to be deleted.
     * @throws CommandException if the module cannot be found in Completed Modules.
     */
    public Module getModuleToDelete(Model model) throws CommandException {
        List<Module> modules = model.getGradPad().getModuleList();

        Optional<Module> moduleToDelete = modules.stream()
                .filter(module -> module.getModuleCode().equals(code)).findFirst();
        if (moduleToDelete.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_MODULE, code.toString()));
        }
        return moduleToDelete.get();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module moduleToDelete = getModuleToDelete(model);
        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && code.equals(((DeleteCommand) other).code)); // state check
    }

    @Override
    public boolean requiresStall() {
        return true;
    }
}

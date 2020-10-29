package seedu.gradpad.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.gradpad.commons.core.Messages;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.module.Module;

/**
 * Finds and lists all modules in GradPad whose name or tag contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all modules whose names or tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2103T CS1101s CS2030";

    private final Predicate<Module> predicate;

    /**
     * Creates a FindCommand to filter module(s) based on the specified predicate.
     *
     * @param predicate the predicate used to filter relevant module(s).
     */
    public FindCommand(Predicate<Module> predicate) {
        assert(predicate != null);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredModuleList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, model.getFilteredModuleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

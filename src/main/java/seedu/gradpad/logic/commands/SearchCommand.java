package seedu.gradpad.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.gradpad.logic.ModuleInfoSearcher;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.model.Model;
import seedu.gradpad.nusmods.ModuleInfo;

/**
 * Search for a module to display its module details.
 * Module Code matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Search for a module with "
            + "the specified ModuleCode (case-insensitive) and display all the module details in the "
            + "Command Line Interface\n"
            + "Parameters: MODULE CODE\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    public static final String MESSAGE_SUCCESS = "Module Info for: %1$s \n"
            + "Modular Credits: %2$s\n"
            + "Module Title: %3$s \n\n" + "Module Description: \n%4$s \n\n"
            + "Preclusion: %5$s\n\n" + "Prerequisite: %6$s\n";

    private final String moduleCode;

    /**
     * Creates a SearchCommand to search for a module from the Computer Science
     * curriculum {@code Module}
     */
    public SearchCommand(String moduleCode) {
        requireNonNull(moduleCode);
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ModuleInfoSearcher moduleInfoSearcher = new ModuleInfoSearcher();
        ModuleInfo searchResult = moduleInfoSearcher.searchModule(moduleCode);

        // preclusions and prerequisites could be null
        String preclusion = Optional.ofNullable(searchResult.getPreclusion()).orElse("None");
        String prerequisite = Optional.ofNullable(searchResult.getPrerequisite()).orElse("None");

        String searchDisplay = String.format(MESSAGE_SUCCESS, searchResult.getModuleCode(),
                searchResult.getModuleCredit(), searchResult.getTitle(),
                searchResult.getDescription(), preclusion, prerequisite);
        return new CommandResult(searchDisplay);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && moduleCode.equals(((SearchCommand) other).moduleCode)); // state check
    }
}

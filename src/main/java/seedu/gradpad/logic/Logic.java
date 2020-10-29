package seedu.gradpad.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.gradpad.commons.core.GuiSettings;
import seedu.gradpad.logic.commands.CommandResult;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.logic.parser.exceptions.ParseException;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.module.Module;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the GradPad.
     *
     * @see seedu.gradpad.model.Model#getGradPad()
     */
    ReadOnlyGradPad getGradPad();

    /** Returns an unmodifiable view of the filtered list of modules */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Returns the user prefs' GradPad file path.
     */
    Path getGradPadFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}

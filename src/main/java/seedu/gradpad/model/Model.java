package seedu.gradpad.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.gradpad.commons.core.GuiSettings;
import seedu.gradpad.model.module.Module;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' GradPad file path.
     */
    Path getGradPadFilePath();

    /**
     * Sets the user prefs' GradPad file path.
     */
    void setGradPadFilePath(Path gradPadFilePath);

    /**
     * Replaces GradPad data with the data in {@code gradPad}.
     */
    void setGradPad(ReadOnlyGradPad gradPad);

    /** Returns the GradPad */
    ReadOnlyGradPad getGradPad();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the GradPad.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     * The module must exist in the GradPad.
     */
    void deleteModule(Module target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the GradPad.
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the GradPad.
     * The module identity of {@code editedModule} must not be the same as another existing module in the GradPad.
     */
    void setModule(Module target, Module editedModule);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);
}

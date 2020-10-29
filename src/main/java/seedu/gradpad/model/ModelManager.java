package seedu.gradpad.model;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.gradpad.commons.core.GuiSettings;
import seedu.gradpad.commons.core.LogsCenter;
import seedu.gradpad.model.module.Module;

/**
 * Represents the in-memory model of the GradPad data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final GradPad gradPad;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;


    /**
     * Initializes a ModelManager with the given GradPad and userPrefs.
     */
    public ModelManager(ReadOnlyGradPad gradPad, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(gradPad, userPrefs);

        logger.fine("Initializing with GradPad: " + gradPad + " and user prefs " + userPrefs);

        this.gradPad = new GradPad(gradPad);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModules = new FilteredList<>(this.gradPad.getModuleList());
    }

    public ModelManager() {
        this(new GradPad(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getGradPadFilePath() {
        return userPrefs.getGradPadFilePath();
    }

    @Override
    public void setGradPadFilePath(Path gradPadFilePath) {
        requireNonNull(gradPadFilePath);
        userPrefs.setGradPadFilePath(gradPadFilePath);
    }

    //=========== GradPad ================================================================================

    @Override
    public void setGradPad(ReadOnlyGradPad gradPad) {
        this.gradPad.resetData(gradPad);
    }

    @Override
    public ReadOnlyGradPad getGradPad() {
        return gradPad;
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return gradPad.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        assert target != null;
        gradPad.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        assert module != null;
        gradPad.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        gradPad.setModule(target, editedModule);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedGradPad}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return gradPad.equals(other.gradPad)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules);
    }

}

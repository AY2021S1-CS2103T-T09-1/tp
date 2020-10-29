package seedu.gradpad.model;

import javafx.collections.ObservableList;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.tag.UniqueTagMap;

/**
 * Unmodifiable view of a GradPad
 */
public interface ReadOnlyGradPad {

    /**
     * Returns an unmodifiable view of the module list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns the unique tags in the GradPad.
     */
    UniqueTagMap getTags();
}

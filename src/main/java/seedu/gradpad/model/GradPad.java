package seedu.gradpad.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.UniqueModuleList;
import seedu.gradpad.model.tag.Tag;
import seedu.gradpad.model.tag.UniqueTagMap;

/**
 * Wraps all data at the GradPad level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class GradPad implements ReadOnlyGradPad {

    private final UniqueModuleList modules;
    private final UniqueTagMap tags;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
        tags = new UniqueTagMap();
    }

    public GradPad() {}

    /**
     * Creates a GradPad using the Modules in the {@code toBeCopied}
     */
    public GradPad(ReadOnlyGradPad toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        assert modules != null;
        this.modules.setModules(modules);
    }

    /**
     * Replaces the current tags with {@code tags}.
     */
    public void setTags(UniqueTagMap tags) {
        assert tags != null;
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code GradPad} with {@code newData}.
     */
    public void resetData(ReadOnlyGradPad newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
        setTags(newData.getTags());
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the GradPad.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the GradPad.
     * The module must not already exist in the GradPad.
     */
    public void addModule(Module m) {
        assert m != null;

        // Reuse existing tags if possible
        Set<Tag> replacedTags = tags.checkAndReplaceTags(m.getTags());
        Module toAdd = new Module(m.getModuleCode(), m.getModuleTitle(), m.getModularCredits(), replacedTags);

        modules.add(toAdd);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the GradPad.
     * The module identity of {@code editedModule} must not be the same as another existing module in the GradPad.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        // "untag" all tags in the module we're going to edit
        tags.remove(target.getTags());
        // Get new tags and reuse existing tags if possible
        Set<Tag> replacedTags = tags.checkAndReplaceTags(editedModule.getTags());
        Module editedModuleToAdd =
                new Module(editedModule.getModuleCode(), editedModule.getModuleTitle(),
                           editedModule.getModularCredits(), replacedTags);

        modules.setModule(target, editedModuleToAdd);
    }

    /**
     * Removes {@code key} from this {@code GradPad}.
     * {@code key} must exist in the GradPad.
     */
    public void removeModule(Module key) {
        assert key != null;
        tags.remove(key.getTags());
        modules.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public UniqueTagMap getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GradPad // instanceof handles nulls
                && modules.equals(((GradPad) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}

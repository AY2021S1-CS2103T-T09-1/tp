package seedu.gradpad.model.module;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's module title in the GradPad.
 * * Guarantees: immutable.
 */
public class ModuleTitle {

    public static final String MESSAGE_CONSTRAINTS =
            "Module titles should only contain alphanumeric characters and spaces, and it should not be "
                    + "blank";

    public final String moduleTitle;

    /**
     * Constructs a {@code ModuleTitle}.
     *
     * @param moduleTitle A valid module title.
     */
    public ModuleTitle(String moduleTitle) {
        requireNonNull(moduleTitle);
        this.moduleTitle = moduleTitle;
    }

    @Override
    public String toString() {
        return moduleTitle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleTitle // instanceof handles nulls
                && moduleTitle.equals(((ModuleTitle) other).moduleTitle)); // state check
    }

    @Override
    public int hashCode() {
        return moduleTitle.hashCode();
    }
}

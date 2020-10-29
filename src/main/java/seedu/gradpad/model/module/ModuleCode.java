package seedu.gradpad.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's module code in the GradPad.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
    A module code must have 2 or more characters as its prefix followed by 1 or more digits as its numerical
    code. It can optionally end with 1 or more characters as a suffix. It is also case-insensitive.
    */
    public static final String VALIDATION_REGEX = "[\\p{Alpha}]{2,}\\d+[\\p{Alpha}]*";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A valid module code.
     *
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && moduleCode.equals(((ModuleCode) other).moduleCode)); // state check
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }
}

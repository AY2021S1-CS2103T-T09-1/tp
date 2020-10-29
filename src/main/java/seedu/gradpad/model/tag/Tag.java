package seedu.gradpad.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the GradPad.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;
    private int moduleCount;

    /**
     * Constructs a {@code Tag}. By default, a tag has a module count of 1 since is it constructed by a
     * module that contains it.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        moduleCount = 1;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the no. of modules that contain this tag.
     *
     * @return the module count
     */
    public int getModuleCount() {
        return moduleCount;
    }

    /**
     * Increments the no. of modules that contain this tag. This is used when:
     * 1. A new module with this tag is created
     * 2. An existing module is edited to contain this tag
     */
    public void incrementModuleCount() {
        moduleCount++;
    }

    /**
     * Decrements the no. of modules that contain this tag. This is used when:
     * 1. A module with this tag is deleted
     * 2. An existing module is edited and no longer contains this tag
     */
    public void decrementModuleCount() {
        moduleCount--;
    }

    /**
     * Checks if this tag is being used by any {@code Module} in GradPad.
     *
     * @return True if the tag is no longer used by any {@code Module}, false if otherwise.
     */
    public boolean isEmpty() {
        return moduleCount == 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}

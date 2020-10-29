package seedu.gradpad.model.module;

import static seedu.gradpad.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.gradpad.model.tag.Tag;

/**
 * Represents a Module in the GradPad.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    private final ModuleCode code;
    private final ModuleTitle title;
    private final ModularCredits credits;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode code, ModuleTitle title, ModularCredits credits, Set<Tag> tags) {
        requireAllNonNull(code, title, credits, tags);
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.tags.addAll(tags);
    }

    public ModuleCode getModuleCode() {
        return code;
    }

    public ModuleTitle getModuleTitle() {
        return title;
    }

    public ModularCredits getModularCredits() {
        return credits;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both modules of the same module code have at least one other field that is the same.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getModuleCode().equals(getModuleCode());
    }

    /**
     * Returns true if both modules have the same fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModularCredits().equals(getModularCredits())
                && otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getModuleTitle().equals(getModuleTitle())
                && otherModule.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, title, credits, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Module Code: ")
                .append(getModuleCode())
                .append("\nModule Title: ")
                .append(getModuleTitle())
                .append("\nModular Credits: ")
                .append(getModularCredits())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

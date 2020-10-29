package seedu.gradpad.model.module;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Module} contains a {@code Tag} matching any of the tag names given.
 */
public class ModuleContainsTagsPredicate implements Predicate<Module> {
    private final List<String> tagNames;

    /**
     * Creates a new ModuleContainsTagsPredicate to test whether a module contains any tag that
     * matches any of the tag names in the specified list.
     *
     * @param tagNames list of tag names.
     */
    public ModuleContainsTagsPredicate(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public boolean test(Module module) {
        return tagNames.stream().anyMatch(name ->
              module.getTags().stream().anyMatch(tag ->
                     tag.tagName.equalsIgnoreCase(name)));
    }
}

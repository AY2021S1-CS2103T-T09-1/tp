package seedu.gradpad.model.module;

import java.util.List;
import java.util.function.Predicate;

import seedu.gradpad.commons.util.StringUtil;

/**
 * Tests that a {@code Module}'s {@code module code} matches any of the keywords given.
 */
public class ModuleCodeContainsKeywordsPredicate implements Predicate<Module> {
    private final List<String> keywords;

    /**
     * Creates a new ModuleCodeContainsKeywordsPredicate to test whether a module code matches the contains
     * the keywords in the specified list.
     *
     * @param keywords list of keywords.
     */
    public ModuleCodeContainsKeywordsPredicate(List<String> keywords) {
        assert(keywords != null);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsCharSequenceIgnoreCase(module.getModuleCode().moduleCode,
                        keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCodeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ModuleCodeContainsKeywordsPredicate) other).keywords)); // state check
    }

}

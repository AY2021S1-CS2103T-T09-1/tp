package seedu.gradpad.model.module;

import java.util.List;
import java.util.function.Predicate;

public class CompoundFindPredicate implements Predicate<Module> {
    private final List<String> keywords;

    public CompoundFindPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        Predicate<Module> moduleCodePredicate = new ModuleCodeContainsKeywordsPredicate(keywords);
        Predicate<Module> tagsPredicate = new ModuleContainsTagsPredicate(keywords);
        // chain the predicates
        return moduleCodePredicate.or(tagsPredicate).test(module);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                       || (other instanceof CompoundFindPredicate // instanceof handles nulls
                                   && keywords.equals(((CompoundFindPredicate) other).keywords)); // state check
    }
}

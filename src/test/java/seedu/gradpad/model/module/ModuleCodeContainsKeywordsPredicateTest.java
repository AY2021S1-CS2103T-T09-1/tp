package seedu.gradpad.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.gradpad.testutil.ModuleBuilder;

public class ModuleCodeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("2100");
        List<String> secondPredicateKeywordList = Arrays.asList("2100", "3216");

        ModuleCodeContainsKeywordsPredicate firstPredicate = new ModuleCodeContainsKeywordsPredicate(
                firstPredicateKeywordList);
        ModuleCodeContainsKeywordsPredicate secondPredicate = new ModuleCodeContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ModuleCodeContainsKeywordsPredicate firstPredicateCopy = new ModuleCodeContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_moduleCodeContainsKeywords_returnsTrue() {
        // One keyword
        ModuleCodeContainsKeywordsPredicate predicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("CS2100"));
        assertTrue(predicate.test(new ModuleBuilder().withCode("CS2100").build()));

        // Multiple keywords
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("CS2100", "CS2103T"));
        assertTrue(predicate.test(new ModuleBuilder().withCode("CS2100").build()));

        // Mixed-case keywords
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("cS2100", "Cs2103T"));
        assertTrue(predicate.test(new ModuleBuilder().withCode("CS2103T").build()));
    }

    @Test
    public void test_moduleCodeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ModuleCodeContainsKeywordsPredicate predicate = new ModuleCodeContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new ModuleBuilder().withCode("CS2100").build()));

        // Non-matching keyword
        predicate = new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("2100"));
        assertFalse(predicate.test(new ModuleBuilder().withCode("CS2103T").build()));

        // Keywords match MCs and tags, but does not match code
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("4", "core"));
        assertFalse(predicate.test(new ModuleBuilder().withCode("CS2103T").withModularCredits("4")
                                           .withTags("core").build()));
    }
}

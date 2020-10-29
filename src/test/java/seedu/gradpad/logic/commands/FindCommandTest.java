package seedu.gradpad.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.commons.core.Messages.MESSAGE_MODULES_LISTED_OVERVIEW;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_CORE;
import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.gradpad.testutil.TypicalModules.CS2103T;
import static seedu.gradpad.testutil.TypicalModules.CS3216;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.UserPrefs;
import seedu.gradpad.model.module.CompoundFindPredicate;
import seedu.gradpad.model.module.ModuleCodeContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalGradPad(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalGradPad(), new UserPrefs());

    @Test
    public void equals() {
        ModuleCodeContainsKeywordsPredicate firstPredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("first"));
        ModuleCodeContainsKeywordsPredicate secondPredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different module -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noModuleFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 0);
        CompoundFindPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredModuleList());
    }

    @Test
    public void execute_multipleModuleCodes_multipleModulesFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 2);
        CompoundFindPredicate predicate = preparePredicate("CS2103T CS3216");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CS2103T, CS3216), model.getFilteredModuleList());
    }

    @Test
    public void execute_oneTagKeyword_oneModuleFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 1);
        CompoundFindPredicate predicate = preparePredicate(VALID_TAG_CORE);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CS2103T), model.getFilteredModuleList());
    }

    @Test
    public void execute_oneCapitalizedTagKeyword_oneModuleFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 1);
        CompoundFindPredicate predicate = preparePredicate(VALID_TAG_CORE.toUpperCase());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CS2103T), model.getFilteredModuleList());
    }

    @Test
    public void execute_tagAndModuleCodeKeywords_multipleModulesFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 2);
        CompoundFindPredicate predicate = preparePredicate(VALID_TAG_CORE + " CS3216");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CS2103T, CS3216), model.getFilteredModuleList());
    }

    /**
     * Parses {@code userInput} into a {@code ModuleCodeContainsKeywordsPredicate}.
     */
    private CompoundFindPredicate preparePredicate(String userInput) {
        return new CompoundFindPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

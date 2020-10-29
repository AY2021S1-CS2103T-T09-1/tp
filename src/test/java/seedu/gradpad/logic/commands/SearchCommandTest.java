package seedu.gradpad.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.util.JsonUtil;
import seedu.gradpad.logic.ModuleInfoSearcher;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.UserPrefs;
import seedu.gradpad.nusmods.ModuleInfo;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {

    private static final String CS1010X = "src/test/resources/NusmodsDataManagerTest/CS1010X.json";
    private Model model = new ModelManager(getTypicalGradPad(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalGradPad(), new UserPrefs());

    @Test
    public void equals() {
        String firstPredicate = "first";
        String secondPredicate = "second";

        SearchCommand searchFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand searchSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertEquals(searchFirstCommand, searchFirstCommand);

        // same values -> returns true
        SearchCommand searchFirstCommandCopy = new SearchCommand(firstPredicate);
        assertEquals(searchFirstCommand, searchFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, searchFirstCommand);

        // null -> returns false
        assertNotEquals(null, searchFirstCommand);

        // different module -> returns false
        assertNotEquals(searchFirstCommand, searchSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noModuleFound() {
        String expectedMessage = ModuleInfoSearcher.MESSAGE_EMPTY_SEARCH;
        String moduleCode = "";
        SearchCommand command = new SearchCommand(moduleCode);
        assertCommandFailure(command, model, expectedMessage);

    }

    @Test
    public void execute_searchCommand_success() throws DataConversionException {
        String moduleCode = "CS1010X";
        ModuleInfo cs1010x = JsonUtil.readJsonFile(Paths.get(CS1010X), ModuleInfo.class).get();
        SearchCommand command = new SearchCommand(moduleCode);

        String expectedPreclusion = Optional.ofNullable(cs1010x.getPreclusion()).orElse("None");
        String expectedPrerequisite = Optional.ofNullable(cs1010x.getPrerequisite()).orElse("None");

        String expectedMessage = String.format(SearchCommand.MESSAGE_SUCCESS,
                cs1010x.getModuleCode(), cs1010x.getModuleCredit(),
                cs1010x.getTitle(), cs1010x.getDescription(),
                expectedPreclusion, expectedPrerequisite);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}

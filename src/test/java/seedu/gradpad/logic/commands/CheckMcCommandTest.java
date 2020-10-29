package seedu.gradpad.logic.commands;

import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;
import static seedu.gradpad.testutil.TypicalModules.getTypicalTotalMc;

import org.junit.jupiter.api.Test;

import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.UserPrefs;

public class CheckMcCommandTest {
    private Model model = new ModelManager(getTypicalGradPad(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalGradPad(), new UserPrefs());

    @Test
    public void execute_checkmc_success() {
        assertCommandSuccess(new CheckMcCommand(), model, String.format(CheckMcCommand.MESSAGE_SUCCESS,
                getTypicalTotalMc()), expectedModel);
    }
}

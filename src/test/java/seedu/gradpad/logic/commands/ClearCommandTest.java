package seedu.gradpad.logic.commands;

import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import org.junit.jupiter.api.Test;

import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyGradPad_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyGradPad_success() {
        Model model = new ModelManager(getTypicalGradPad(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalGradPad(), new UserPrefs());
        expectedModel.setGradPad(new GradPad());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

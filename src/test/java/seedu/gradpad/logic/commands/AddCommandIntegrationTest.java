package seedu.gradpad.logic.commands;

import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.UserPrefs;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGradPad(), new UserPrefs());
    }

    @Test
    public void execute_newModule_success() {
        Module validModule = new ModuleBuilder().build();

        Model expectedModel = new ModelManager(model.getGradPad(), new UserPrefs());
        expectedModel.addModule(validModule);

        assertCommandSuccess(new AddCommand(validModule), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validModule), expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module moduleInList = model.getGradPad().getModuleList().get(0);
        assertCommandFailure(new AddCommand(moduleInList), model, AddCommand.MESSAGE_DUPLICATE_MODULE);
    }

}

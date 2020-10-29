package seedu.gradpad.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.logic.commands.CommandTestUtil.DESC_CS2103T;
import static seedu.gradpad.logic.commands.CommandTestUtil.DESC_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CODE_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CREDITS_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_CORE;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_NON_CORE;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TITLE_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.gradpad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.gradpad.logic.commands.CommandTestUtil.showModuleAtIndex;
import static seedu.gradpad.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.gradpad.testutil.TypicalModuleCodes.CODE_FIRST_MODULE;
import static seedu.gradpad.testutil.TypicalModuleCodes.CODE_SECOND_MODULE;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import org.junit.jupiter.api.Test;

import seedu.gradpad.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.UserPrefs;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.testutil.EditModuleDescriptorBuilder;
import seedu.gradpad.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalGradPad(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Module editedModule = new ModuleBuilder().build();
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditCommand editCommand = new EditCommand(CODE_FIRST_MODULE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new GradPad(model.getGradPad()), new UserPrefs());
        expectedModel.setModule(model.getFilteredModuleList().get(0), editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        ModuleCode moduleCodeLastModule = CODE_SECOND_MODULE;
        Module lastModule = model.getFilteredModuleList().stream()
            .filter(x -> x.getModuleCode().equals(moduleCodeLastModule)).findFirst().get();

        ModuleBuilder moduleInList = new ModuleBuilder(lastModule);
        Module editedModule = moduleInList.withTags(VALID_TAG_CORE).build();

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
            .withModuleCode(VALID_CODE_CS3216).withModuleTitle(VALID_TITLE_CS3216)
            .withModularCredits(VALID_CREDITS_CS3216).withTags(VALID_TAG_CORE).build();
        EditCommand editCommand = new EditCommand(moduleCodeLastModule, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new GradPad(model.getGradPad()), new UserPrefs());
        expectedModel.setModule(lastModule, editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(CODE_FIRST_MODULE, new EditModuleDescriptor());
        Module editedModule = model.getFilteredModuleList().stream()
            .filter(x -> x.getModuleCode().equals(CODE_FIRST_MODULE)).findFirst().get();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new GradPad(model.getGradPad()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Module moduleInFilteredList = model.getFilteredModuleList().stream()
            .filter(x -> x.getModuleCode().equals(CODE_FIRST_MODULE)).findFirst().get();
        Module editedModule = new ModuleBuilder(moduleInFilteredList)
            .withTags(VALID_TAG_CORE, VALID_TAG_NON_CORE).build();
        EditCommand editCommand = new EditCommand(CODE_FIRST_MODULE,
                new EditModuleDescriptorBuilder().withTags(VALID_TAG_CORE, VALID_TAG_NON_CORE).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new GradPad(model.getGradPad()), new UserPrefs());
        expectedModel.setModule(moduleInFilteredList, editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateModuleUnfilteredList_failure() {
        Module firstModule = model.getFilteredModuleList().stream()
            .filter(x -> x.getModuleCode().equals(CODE_FIRST_MODULE)).findFirst().get();
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(firstModule).build();
        EditCommand editCommand = new EditCommand(CODE_SECOND_MODULE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_duplicateModuleFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        // edit module in filtered list into a duplicate in GradPad
        Module moduleInList = model.getGradPad().getModuleList().stream()
            .filter(x -> x.getModuleCode().equals(CODE_SECOND_MODULE)).findFirst().get();
        EditCommand editCommand = new EditCommand(CODE_FIRST_MODULE,
                new EditModuleDescriptorBuilder(moduleInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_moduleNotYetAddedUnfilteredList_failure() {
        ModuleCode moduleNotYetAdded = new ModuleCode("CS2108");
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
            .withModuleCode(VALID_CODE_CS3216).build();
        EditCommand editCommand = new EditCommand(moduleNotYetAdded, descriptor);

        assertCommandFailure(editCommand, model, String.format(EditCommand.MESSAGE_MODULE_NOT_YET_ADDED,
            moduleNotYetAdded));
    }

    /**
     * Edit filtered list where module code does exists in NUSMods but not yet added into GradPad.
     */
    @Test
    public void execute_moduleNotYetAddedFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);
        ModuleCode moduleNotYetAdded = new ModuleCode("CS2108");

        EditCommand editCommand = new EditCommand(moduleNotYetAdded,
            new EditModuleDescriptorBuilder().withModuleCode(VALID_CODE_CS3216).build());

        assertCommandFailure(editCommand, model, String.format(EditCommand.MESSAGE_MODULE_NOT_YET_ADDED,
            moduleNotYetAdded));
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(CODE_FIRST_MODULE, DESC_CS2103T);

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleDescriptor(DESC_CS2103T);
        EditCommand commandWithSameValues = new EditCommand(CODE_FIRST_MODULE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different code -> returns false
        assertFalse(standardCommand.equals(new EditCommand(CODE_SECOND_MODULE, DESC_CS2103T)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(CODE_FIRST_MODULE, DESC_CS3216)));
    }

}

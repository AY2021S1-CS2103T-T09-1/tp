package seedu.gradpad.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.gradpad.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.gradpad.commons.core.index.Index;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCodeContainsKeywordsPredicate;
import seedu.gradpad.testutil.EditModuleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_CODE_CS2103T = "CS2103T";
    public static final String VALID_CODE_CS3216 = "CS3216";
    public static final String VALID_TITLE_CS2103T = "Software Engineering";
    public static final String VALID_TITLE_CS3216 = "Software Product Engineering for Digital Markets";
    public static final String VALID_CREDITS_CS2103T = "4";
    public static final String VALID_CREDITS_CS3216 = "5";
    public static final String VALID_TAG_NON_CORE = "nonCore";
    public static final String VALID_TAG_CORE = "core";

    public static final String CODE_DESC_CS2103T = " " + VALID_CODE_CS2103T;
    public static final String CODE_DESC_CS2103T_WITH_PREFIX = " " + PREFIX_CODE + VALID_CODE_CS2103T;
    public static final String CODE_DESC_CS3216 = " " + VALID_CODE_CS3216;
    public static final String CODE_DESC_CS3216_WITH_PREFIX = " " + PREFIX_CODE + VALID_CODE_CS3216;
    public static final String TAG_DESC_CORE = " " + PREFIX_TAG + VALID_TAG_CORE;
    public static final String TAG_DESC_NON_CORE = " " + PREFIX_TAG + VALID_TAG_NON_CORE;

    public static final String INVALID_CODE_DESC = " " + "CS1231&"; // '&' not allowed in codes
    public static final String INVALID_CODE_DESC_WITH_PREFIX = " " + PREFIX_CODE + "CS1231&";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "core*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";

    public static final EditCommand.EditModuleDescriptor DESC_CS2103T;
    public static final EditCommand.EditModuleDescriptor DESC_CS3216;

    static {
        DESC_CS2103T = new EditModuleDescriptorBuilder().withModuleCode(VALID_CODE_CS2103T)
            .withModuleTitle(VALID_TITLE_CS2103T).withModularCredits(VALID_CREDITS_CS2103T)
            .withTags(VALID_TAG_CORE).build();
        DESC_CS3216 = new EditModuleDescriptorBuilder().withModuleCode(VALID_CODE_CS3216)
            .withModuleTitle(VALID_TITLE_CS3216).withModularCredits(VALID_CREDITS_CS3216)
            .withTags(VALID_TAG_CORE).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the GradPad, filtered module list and selected module in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        GradPad expectedGradPad = new GradPad(actualModel.getGradPad());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedGradPad, actualModel.getGradPad());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the
     * {@code model}'s GradPad.
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleList().size());

        Module module = model.getFilteredModuleList().get(targetIndex.getZeroBased());
        final String[] splitName = module.getModuleCode().toString().split("\\s+");
        model.updateFilteredModuleList(new ModuleCodeContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredModuleList().size());
    }

}

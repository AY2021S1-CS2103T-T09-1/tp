package seedu.gradpad.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gradpad.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.gradpad.testutil.Assert.assertThrows;
import static seedu.gradpad.testutil.TypicalModuleCodes.CODE_FIRST_MODULE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.gradpad.logic.commands.AddCommand;
import seedu.gradpad.logic.commands.CheckMcCommand;
import seedu.gradpad.logic.commands.ClearCommand;
import seedu.gradpad.logic.commands.DeleteCommand;
import seedu.gradpad.logic.commands.EditCommand;
import seedu.gradpad.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.gradpad.logic.commands.ExitCommand;
import seedu.gradpad.logic.commands.FindCommand;
import seedu.gradpad.logic.commands.HelpCommand;
import seedu.gradpad.logic.commands.ListCommand;
import seedu.gradpad.logic.commands.TagsCommand;
import seedu.gradpad.logic.parser.exceptions.ParseException;
import seedu.gradpad.model.module.CompoundFindPredicate;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.testutil.EditModuleDescriptorBuilder;
import seedu.gradpad.testutil.ModuleBuilder;
import seedu.gradpad.testutil.ModuleUtil;

public class GradPadParserTest {

    private final GradPadParser parser = new GradPadParser();

    @Test
    public void parseCommand_add() throws Exception {
        Module module = new ModuleBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(ModuleUtil.getAddCommand(module));
        assertEquals(new AddCommand(module), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + CODE_FIRST_MODULE.toString());
        assertEquals(new DeleteCommand(CODE_FIRST_MODULE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Module module = new ModuleBuilder().build();
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(module).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + CODE_FIRST_MODULE + " " + ModuleUtil.getEditModuleDescriptorDetails(descriptor));
        assertEquals(new EditCommand(CODE_FIRST_MODULE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new CompoundFindPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_checkMc() throws Exception {
        assertTrue(parser.parseCommand(CheckMcCommand.COMMAND_WORD) instanceof CheckMcCommand);
        assertTrue(parser.parseCommand(CheckMcCommand.COMMAND_WORD + " 3") instanceof CheckMcCommand);
    }

    @Test
    public void parseCommand_tags() throws Exception {
        assertTrue(parser.parseCommand(TagsCommand.COMMAND_WORD) instanceof TagsCommand);
        assertTrue(parser.parseCommand(TagsCommand.COMMAND_WORD + " 3") instanceof TagsCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

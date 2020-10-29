package seedu.gradpad.logic.parser;

import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gradpad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.gradpad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.gradpad.logic.commands.FindCommand;
import seedu.gradpad.model.module.CompoundFindPredicate;
import seedu.gradpad.model.module.Module;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<String> keywords = Arrays.asList("CS2103T", "CS3216");
        Predicate<Module> expectedPredicate = new CompoundFindPredicate(keywords);

        FindCommand expectedFindCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, "CS2103T CS3216", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2103T \n \t CS3216  \t", expectedFindCommand);
    }

}

package seedu.gradpad.logic.parser;

import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gradpad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.gradpad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.gradpad.logic.commands.SearchCommand;



class SearchCommandParserTest {
    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand("CS2103T");
        assertParseSuccess(parser, "CS2103T", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2103T \n", expectedSearchCommand);
    }
}

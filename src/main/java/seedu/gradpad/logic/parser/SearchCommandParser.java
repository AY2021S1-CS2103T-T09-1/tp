package seedu.gradpad.logic.parser;

import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.gradpad.commons.util.StringUtil;
import seedu.gradpad.logic.commands.SearchCommand;
import seedu.gradpad.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        try {
            String moduleCode = StringUtil.ignoreCase(trimmedArgs);
            return new SearchCommand(moduleCode);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SearchCommand.MESSAGE_USAGE));
        }
    }
}

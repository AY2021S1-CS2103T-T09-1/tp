package seedu.gradpad.logic.parser;

import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.gradpad.logic.commands.FindCommand;
import seedu.gradpad.logic.parser.exceptions.ParseException;
import seedu.gradpad.model.module.CompoundFindPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> keywords = Arrays.asList(trimmedArgs.split("\\s+"));

        return new FindCommand(new CompoundFindPredicate(keywords));
    }

}

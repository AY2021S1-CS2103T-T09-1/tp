package seedu.gradpad.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.gradpad.logic.commands.DeleteCommand;
import seedu.gradpad.logic.parser.exceptions.ParseException;
import seedu.gradpad.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            ModuleCode code = ParserUtil.parseModuleCode(args);
            return new DeleteCommand(code);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}

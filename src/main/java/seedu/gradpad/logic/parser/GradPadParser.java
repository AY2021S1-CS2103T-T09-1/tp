package seedu.gradpad.logic.parser;

import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gradpad.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.gradpad.logic.commands.AddCommand;
import seedu.gradpad.logic.commands.CheckMcCommand;
import seedu.gradpad.logic.commands.ClearCommand;
import seedu.gradpad.logic.commands.Command;
import seedu.gradpad.logic.commands.DeleteCommand;
import seedu.gradpad.logic.commands.EditCommand;
import seedu.gradpad.logic.commands.ExitCommand;
import seedu.gradpad.logic.commands.FindCommand;
import seedu.gradpad.logic.commands.GemCommand;
import seedu.gradpad.logic.commands.HelpCommand;
import seedu.gradpad.logic.commands.ListCommand;
import seedu.gradpad.logic.commands.RequiredCommand;
import seedu.gradpad.logic.commands.ScienceCommand;
import seedu.gradpad.logic.commands.SearchCommand;
import seedu.gradpad.logic.commands.TagsCommand;
import seedu.gradpad.logic.commands.YesCommand;
import seedu.gradpad.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class GradPadParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case CheckMcCommand.COMMAND_WORD:
            return new CheckMcCommand();

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case RequiredCommand.COMMAND_WORD:
            return new RequiredCommand();

        case ScienceCommand.COMMAND_WORD:
            return new ScienceCommand();

        case YesCommand.COMMAND_WORD:
            return new YesCommand();

        case TagsCommand.COMMAND_WORD:
            return new TagsCommand();

        case GemCommand.COMMAND_WORD:
            return new GemCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

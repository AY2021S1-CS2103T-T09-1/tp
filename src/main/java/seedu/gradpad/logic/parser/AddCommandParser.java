package seedu.gradpad.logic.parser;

import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.gradpad.commons.util.StringUtil;
import seedu.gradpad.logic.ModuleInfoSearcher;
import seedu.gradpad.logic.commands.AddCommand;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.logic.parser.exceptions.ParseException;
import seedu.gradpad.model.module.ModularCredits;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.model.module.ModuleTitle;
import seedu.gradpad.model.tag.Tag;
import seedu.gradpad.nusmods.ModuleInfo;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    public static final String MESSAGE_INVALID_MODULE = "There is no such module in the CS curriculum!";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format or if the module does
     * not exist
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getPreamble());
        String moduleCodeText = StringUtil.ignoreCase(moduleCode.toString());
        ModuleInfoSearcher moduleInfoSearcher = new ModuleInfoSearcher();
        ModuleInfo moduleInfo;

        try {
            moduleInfo = moduleInfoSearcher.searchModule(moduleCodeText);
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }

        ModuleTitle moduleTitle = ParserUtil.parseModuleTitle(moduleInfo.getTitle());
        ModularCredits modularCredits = ParserUtil.parseModularCredits(moduleInfo.getModuleCredit());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Module module = new Module(moduleCode, moduleTitle, modularCredits, tagList);

        return new AddCommand(module);
    }
}

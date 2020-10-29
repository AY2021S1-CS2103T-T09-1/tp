package seedu.gradpad.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.gradpad.logic.ModuleInfoSearcher;
import seedu.gradpad.logic.commands.EditCommand;
import seedu.gradpad.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.logic.parser.exceptions.ParseException;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.model.tag.Tag;
import seedu.gradpad.nusmods.ModuleInfo;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "Module %1$s does not exist!";
    public static final String MESSAGE_MODULE_NOT_YET_ADDED = "Module %1$s has not been added yet!";

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_CODE, PREFIX_TAG);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE));
        }

        ModuleInfoSearcher moduleInfoSearcher = new ModuleInfoSearcher();
        ModuleCode code = ParserUtil.parseModuleCode(argMultimap.getPreamble());
        try {
            moduleInfoSearcher.searchModule(code.toString());
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }

        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();
        ModuleInfo moduleInfo;

        if (argMultimap.getValue(PREFIX_CODE).isPresent()) {
            ModuleCode newCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_CODE).get());
            try {
                moduleInfo = moduleInfoSearcher.searchModule(newCode.toString());
                editModuleDescriptor.setModuleCode(newCode);
                editModuleDescriptor.setModuleTitle(ParserUtil.parseModuleTitle(moduleInfo.getTitle()));
                editModuleDescriptor.setModularCredits(
                    ParserUtil.parseModularCredits(moduleInfo.getModuleCredit()));
            } catch (CommandException e) {
                throw new ParseException(e.getMessage());
            }
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editModuleDescriptor::setTags);

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(code, editModuleDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

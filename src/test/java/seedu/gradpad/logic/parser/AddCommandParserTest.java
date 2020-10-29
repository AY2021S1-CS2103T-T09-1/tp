package seedu.gradpad.logic.parser;

import static seedu.gradpad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gradpad.logic.commands.CommandTestUtil.CODE_DESC_CS2103T;
import static seedu.gradpad.logic.commands.CommandTestUtil.CODE_DESC_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static seedu.gradpad.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.gradpad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.gradpad.logic.commands.CommandTestUtil.TAG_DESC_CORE;
import static seedu.gradpad.logic.commands.CommandTestUtil.TAG_DESC_NON_CORE;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_CORE;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_NON_CORE;
import static seedu.gradpad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.gradpad.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.gradpad.testutil.TypicalModules.CS2103T;
import static seedu.gradpad.testutil.TypicalModules.CS3216;

import org.junit.jupiter.api.Test;

import seedu.gradpad.logic.commands.AddCommand;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.model.tag.Tag;
import seedu.gradpad.testutil.ModuleBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder(CS3216).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CODE_DESC_CS3216
                + TAG_DESC_NON_CORE, new AddCommand(expectedModule));

        // multiple tags - all accepted
        Module expectedModuleMultipleTags = new ModuleBuilder(CS3216)
                .withTags(VALID_TAG_CORE, VALID_TAG_NON_CORE)
                .build();
        assertParseSuccess(parser, CODE_DESC_CS3216
                + TAG_DESC_CORE + TAG_DESC_NON_CORE, new AddCommand(expectedModuleMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Module expectedModule = new ModuleBuilder(CS2103T).withTags().build();
        assertParseSuccess(parser, CODE_DESC_CS2103T, new AddCommand(expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing code
        assertParseFailure(parser, " ", expectedMessage);

        // missing code, with tags
        assertParseFailure(parser, TAG_DESC_CORE + TAG_DESC_NON_CORE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid code
        assertParseFailure(parser, INVALID_CODE_DESC + TAG_DESC_CORE + TAG_DESC_NON_CORE,
            ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, CODE_DESC_CS3216 + INVALID_TAG_DESC + VALID_TAG_NON_CORE,
            Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_CODE_DESC + INVALID_TAG_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS);
    }
}

package seedu.gradpad.logic.commands;

import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.UserPrefs;

public class TagsCommandTest {

    private Model modelWithTags;
    private Model modelWithoutTags;

    @BeforeEach
    public void setUp() {
        modelWithTags = new ModelManager(getTypicalGradPad(), new UserPrefs());
        modelWithoutTags = new ModelManager();
    }

    @Test
    public void execute_modelWithTags_printsTags() {
        List<String> tagNames = modelWithTags.getGradPad().getTags().getTagNames();
        String expectedMessage = TagsCommand.MESSAGE_SUCCESS + String.join("\n", tagNames);
        TagsCommand tagsCommand = new TagsCommand();

        // actual and expected model are the same as there should be no change
        CommandTestUtil.assertCommandSuccess(tagsCommand, modelWithTags, expectedMessage, modelWithTags);
    }

    @Test
    public void execute_modelWithoutTags_noTagsMessage() {
        String expectedMessage = TagsCommand.MESSAGE_NO_TAGS;
        TagsCommand tagsCommand = new TagsCommand();

        // actual and expected model are the same as there should be no change
        CommandTestUtil.assertCommandSuccess(tagsCommand, modelWithoutTags, expectedMessage, modelWithoutTags);
    }
}

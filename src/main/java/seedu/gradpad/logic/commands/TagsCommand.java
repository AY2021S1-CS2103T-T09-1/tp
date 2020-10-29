package seedu.gradpad.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.gradpad.model.Model;

/**
 * Lists all tags in GradPad.
 */
public class TagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";

    public static final String MESSAGE_SUCCESS = "Listed all tags:\n";
    public static final String MESSAGE_NO_TAGS = "There are no tags.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<String> tagNames = model.getGradPad().getTags().getTagNames();
        if (tagNames.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TAGS);
        }

        String tags = String.join("\n", tagNames);
        return new CommandResult(MESSAGE_SUCCESS + tags);
    }
}

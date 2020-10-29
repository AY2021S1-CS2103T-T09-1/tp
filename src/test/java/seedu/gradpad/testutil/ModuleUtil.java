package seedu.gradpad.testutil;

import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.gradpad.logic.commands.AddCommand;
import seedu.gradpad.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.tag.Tag;

/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddCommand(Module module) {
        return AddCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(module.getModuleCode().toString() + " ");
        module.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditModuleDescriptor}'s details.
     */
    public static String getEditModuleDescriptorDetails(EditModuleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getModuleCode().ifPresent(code -> sb.append(PREFIX_CODE).append(code.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

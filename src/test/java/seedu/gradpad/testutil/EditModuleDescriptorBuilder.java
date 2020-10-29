package seedu.gradpad.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.gradpad.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.gradpad.model.module.ModularCredits;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.model.module.ModuleTitle;
import seedu.gradpad.model.tag.Tag;

/**
 * A utility class to help with building EditModuleDescriptor objects.
 */
public class EditModuleDescriptorBuilder {

    private EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder() {
        descriptor = new EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder(EditModuleDescriptor descriptor) {
        this.descriptor = new EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code module}'s details
     */
    public EditModuleDescriptorBuilder(Module module) {
        descriptor = new EditModuleDescriptor();
        descriptor.setModuleCode(module.getModuleCode());
        descriptor.setModuleTitle(module.getModuleTitle());
        descriptor.setModularCredits(module.getModularCredits());
        descriptor.setTags(module.getTags());
    }

    /**
     * Sets the {@code Module Code} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withModuleCode(String code) {
        descriptor.setModuleCode(new ModuleCode(code));
        return this;
    }

    /**
     * Sets the {@code Modular Credits} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withModularCredits(String credits) {
        descriptor.setModularCredits(new ModularCredits(credits));
        return this;
    }

    /**
     * Sets the {@code Module Title} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withModuleTitle(String title) {
        descriptor.setModuleTitle(new ModuleTitle(title));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditModuleDescriptor}
     * that we are building.
     */
    public EditModuleDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditModuleDescriptor build() {
        return descriptor;
    }
}

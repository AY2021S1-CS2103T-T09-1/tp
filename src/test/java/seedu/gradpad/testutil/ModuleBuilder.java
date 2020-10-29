package seedu.gradpad.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.gradpad.model.module.ModularCredits;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.model.module.ModuleTitle;
import seedu.gradpad.model.tag.Tag;
import seedu.gradpad.model.util.SampleDataUtil;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_CODE = "CS1101S";
    public static final String DEFAULT_TITLE = "Programming Methodology";
    public static final String DEFAULT_MC = "4";

    private ModuleCode code;
    private ModuleTitle title;
    private ModularCredits credits;
    private Set<Tag> tags;

    /**
     * Creates a {@code ModuleBuilder} with the default details.
     */
    public ModuleBuilder() {
        code = new ModuleCode(DEFAULT_CODE);
        title = new ModuleTitle(DEFAULT_TITLE);
        credits = new ModularCredits(DEFAULT_MC);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        code = moduleToCopy.getModuleCode();
        title = moduleToCopy.getModuleTitle();
        credits = moduleToCopy.getModularCredits();
        tags = new HashSet<>(moduleToCopy.getTags());
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCode(String code) {
        this.code = new ModuleCode(code);
        return this;
    }

    /**
     * Sets the {@code ModuleTitle} of the {@code Module} that we are building.
     */
    public ModuleBuilder withTitle(String title) {
        this.title = new ModuleTitle(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Module} that we are building.
     */
    public ModuleBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ModularCredits} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModularCredits(String mc) {
        this.credits = new ModularCredits(mc);
        return this;
    }

    public Module build() {
        return new Module(code, title, credits, tags);
    }

}

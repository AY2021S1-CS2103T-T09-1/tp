package seedu.gradpad.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.model.module.ModularCredits;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.model.module.ModuleTitle;
import seedu.gradpad.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String code;
    private final String title;
    private final String credits;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String code,
                             @JsonProperty("moduleTitle") String title,
                             @JsonProperty("modularCredits") String credits,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        code = source.getModuleCode().toString();
        title = source.getModuleTitle().toString();
        credits = source.getModularCredits().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Tag> moduleTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            moduleTags.add(tag.toModelType());
        }

        if (code == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(code)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelCode = new ModuleCode(code);

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleTitle.class.getSimpleName()));
        }
        final ModuleTitle moduleTitle = new ModuleTitle(title);

        if (credits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModularCredits.class.getSimpleName()));
        }
        final ModularCredits modelCredits = new ModularCredits(credits);

        final Set<Tag> modelTags = new HashSet<>(moduleTags);
        return new Module(modelCode, moduleTitle, modelCredits, modelTags);
    }
}

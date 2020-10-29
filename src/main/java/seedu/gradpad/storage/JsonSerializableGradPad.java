package seedu.gradpad.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.module.Module;

/**
 * An Immutable GradPad that is serializable to JSON format.
 */
@JsonRootName(value = "gradpad")
public class JsonSerializableGradPad {

    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGradPad} with the given modules.
     */
    @JsonCreator
    public JsonSerializableGradPad(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyGradPad} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableGradPad}.
     */
    public JsonSerializableGradPad(ReadOnlyGradPad source) {
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this GradPad into the model's {@code GradPad} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GradPad toModelType() throws IllegalValueException {
        GradPad gradPad = new GradPad();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (gradPad.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            gradPad.addModule(module);
        }
        return gradPad;
    }

}

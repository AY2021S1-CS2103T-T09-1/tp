package seedu.gradpad.testutil;

import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.module.Module;

/**
 * A utility class to help with building GradPad objects.
 * Example usage: <br>
 *     {@code GradPad ab = new GradPadBuilder().withModule("John", "Doe").build();}
 */
public class GradPadBuilder {

    private GradPad gradPad;

    public GradPadBuilder() {
        gradPad = new GradPad();
    }

    public GradPadBuilder(GradPad gradPad) {
        this.gradPad = gradPad;
    }

    /**
     * Adds a new {@code Module} to the {@code GradPad} that we are building.
     */
    public GradPadBuilder withModule(Module module) {
        gradPad.addModule(module);
        return this;
    }

    public GradPad build() {
        return gradPad;
    }
}

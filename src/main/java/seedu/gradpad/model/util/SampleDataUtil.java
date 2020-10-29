package seedu.gradpad.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.module.ModularCredits;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.model.module.ModuleTitle;
import seedu.gradpad.model.tag.Tag;

/**
 * Contains utility methods for populating {@code GradPad} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new ModuleCode("CS2103T"), new ModuleTitle("Software Engineering"),
                new ModularCredits("4"), getTagSet("Foundation")),
            new Module(new ModuleCode("CS1010"), new ModuleTitle("Programming Methodology"),
                new ModularCredits("4"), getTagSet("Foundation")),
            new Module(new ModuleCode("CS2101"), new ModuleTitle("Effective Communication for Computing "
                + "Professionals"), new ModularCredits("4"), getTagSet("Foundation")),
            new Module(new ModuleCode("CS2105"), new ModuleTitle("Introduction to Computer Networks"),
                new ModularCredits("4"), getTagSet("Foundation")),
            new Module(new ModuleCode("MA1521"), new ModuleTitle("Calculus for Computing"),
                new ModularCredits("4"), getTagSet("MathSci")),
            new Module(new ModuleCode("CS2107"), new ModuleTitle("Introduction to Information Security"),
                new ModularCredits("4"), getTagSet("BreadthDepth"))
        };
    }

    public static ReadOnlyGradPad getSampleGradPad() {
        GradPad sampleAb = new GradPad();
        for (Module sampleModule : getSampleModules()) {
            sampleAb.addModule(sampleModule);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}

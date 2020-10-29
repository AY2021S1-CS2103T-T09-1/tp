package seedu.gradpad.testutil;

import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CODE_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CREDITS_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_NON_CORE;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TITLE_CS3216;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final Module CS2103T = new ModuleBuilder().withCode("CS2103T")
            .withTitle("Software Engineering")
            .withModularCredits("4")
            .withTags("core").build();

    public static final Module CS1231 = new ModuleBuilder().withCode("CS1231")
            .withTitle("Discrete Structures")
            .withModularCredits("4")
            .withTags("core").build();

    // Manually added - Module's details found in {@code CommandTestUtil}
    public static final Module CS3216 =
            new ModuleBuilder().withCode(VALID_CODE_CS3216).withModularCredits(VALID_CREDITS_CS3216)
                    .withTitle(VALID_TITLE_CS3216).withTags(VALID_TAG_NON_CORE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalModules() {} // prevents instantiation

    /**
     * Returns an {@code GradPad} with all the typical modules.
     */
    public static GradPad getTypicalGradPad() {
        GradPad ab = new GradPad();
        for (Module module: getTypicalModules()) {
            ab.addModule(module);
        }
        return ab;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS3216));
    }

    public static int getTypicalTotalMc() {
        int totalMc = 0;
        for (Module module: getTypicalModules()) {
            totalMc += Integer.parseInt(module.getModularCredits().toString());
        }
        return totalMc;
    }
}

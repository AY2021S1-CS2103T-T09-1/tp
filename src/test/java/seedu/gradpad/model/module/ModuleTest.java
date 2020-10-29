package seedu.gradpad.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CODE_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CREDITS_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_NON_CORE;
import static seedu.gradpad.testutil.Assert.assertThrows;
import static seedu.gradpad.testutil.TypicalModules.CS2103T;
import static seedu.gradpad.testutil.TypicalModules.CS3216;

import org.junit.jupiter.api.Test;

import seedu.gradpad.testutil.ModuleBuilder;

public class ModuleTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Module module = new ModuleBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> module.getTags().remove(0));
    }

    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(CS2103T.isSameModule(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.isSameModule(null));

        // different MCs -> returns true
        Module editedCS2103T = new ModuleBuilder(CS2103T).withModularCredits(VALID_CREDITS_CS3216).build();
        assertTrue(CS2103T.isSameModule(editedCS2103T));

        // different module code -> returns false
        editedCS2103T = new ModuleBuilder(CS2103T).withCode(VALID_CODE_CS3216).build();
        assertFalse(CS2103T.isSameModule(editedCS2103T));

        // same code, same MCs, different tags -> returns true
        editedCS2103T = new ModuleBuilder(CS2103T).withTags(VALID_TAG_NON_CORE).build();
        assertTrue(CS2103T.isSameModule(editedCS2103T));

        // same code, different MCs, different tags -> returns true
        editedCS2103T = new ModuleBuilder(CS2103T).withModularCredits(VALID_CREDITS_CS3216)
                              .withTags(VALID_TAG_NON_CORE).build();
        assertTrue(CS2103T.isSameModule(editedCS2103T));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module cs2103tCopy = new ModuleBuilder(CS2103T).build();
        assertTrue(CS2103T.equals(cs2103tCopy));

        // same object -> returns true
        assertTrue(CS2103T.equals(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.equals(null));

        // different type -> returns false
        assertFalse(CS2103T.equals(5));

        // different module -> returns false
        assertFalse(CS2103T.equals(CS3216));

        // different module code -> returns false
        Module editedCS2103T = new ModuleBuilder(CS2103T).withCode(VALID_CODE_CS3216).build();
        assertFalse(CS2103T.equals(editedCS2103T));

        // different MCs -> returns false
        editedCS2103T = new ModuleBuilder(CS2103T).withModularCredits(VALID_CREDITS_CS3216).build();
        assertFalse(CS2103T.equals(editedCS2103T));

        // different tags -> returns false
        editedCS2103T = new ModuleBuilder(CS2103T).withTags(VALID_TAG_NON_CORE).build();
        assertFalse(CS2103T.equals(editedCS2103T));
    }
}

package seedu.gradpad.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.logic.commands.CommandTestUtil.DESC_CS2103T;
import static seedu.gradpad.logic.commands.CommandTestUtil.DESC_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CODE_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CREDITS_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_NON_CORE;

import org.junit.jupiter.api.Test;

import seedu.gradpad.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.gradpad.testutil.EditModuleDescriptorBuilder;

public class EditModuleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditModuleDescriptor descriptorWithSameValues = new EditModuleDescriptor(DESC_CS2103T);
        assertTrue(DESC_CS2103T.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS2103T.equals(DESC_CS2103T));

        // null -> returns false
        assertFalse(DESC_CS2103T.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS2103T.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS2103T.equals(DESC_CS3216));

        // different code -> returns false
        EditModuleDescriptor editedCS2103T = new EditModuleDescriptorBuilder(DESC_CS2103T)
                .withModuleCode(VALID_CODE_CS3216).build();
        assertFalse(DESC_CS2103T.equals(editedCS2103T));

        // different credits -> returns false
        editedCS2103T = new EditModuleDescriptorBuilder(DESC_CS2103T).withModularCredits(VALID_CREDITS_CS3216).build();
        assertFalse(DESC_CS2103T.equals(editedCS2103T));

        // different tags -> returns false
        editedCS2103T = new EditModuleDescriptorBuilder(DESC_CS2103T).withTags(VALID_TAG_NON_CORE).build();
        assertFalse(DESC_CS2103T.equals(editedCS2103T));
    }
}

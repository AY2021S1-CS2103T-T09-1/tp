package seedu.gradpad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CODE_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_CREDITS_CS3216;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_NON_CORE;
import static seedu.gradpad.testutil.TypicalModules.CS2103T;
import static seedu.gradpad.testutil.TypicalModules.CS3216;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.exceptions.DuplicateModuleException;
import seedu.gradpad.model.tag.UniqueTagMap;
import seedu.gradpad.testutil.ModuleBuilder;

public class GradPadTest {

    private final GradPad gradPad = new GradPad();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), gradPad.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> gradPad.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGradPad_replacesData() {
        GradPad newData = getTypicalGradPad();
        gradPad.resetData(newData);
        assertEquals(newData, gradPad);
    }

    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two modules with the same identity fields
        Module editedCS3216 = new ModuleBuilder(CS3216).withCode(VALID_CODE_CS3216)
                .withModularCredits(VALID_CREDITS_CS3216).withTags(VALID_TAG_NON_CORE)
                .build();
        List<Module> newModules = Arrays.asList(CS3216, editedCS3216);
        GradPadStub newData = new GradPadStub(newModules);

        Assertions.assertThrows(DuplicateModuleException.class, () -> gradPad.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> gradPad.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInGradPad_returnsFalse() {
        assertFalse(gradPad.hasModule(CS2103T));
    }

    @Test
    public void hasModule_moduleInGradPad_returnsTrue() {
        gradPad.addModule(CS2103T);
        assertTrue(gradPad.hasModule(CS2103T));
    }

    @Test // Still needs changing
    public void hasModule_moduleWithSameIdentityFieldsInGradPad_returnsTrue() {
        gradPad.addModule(CS2103T);
        Module editedModule = new ModuleBuilder(CS2103T).withTags(VALID_TAG_NON_CORE).build();
        assertTrue(gradPad.hasModule(editedModule));
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> gradPad.getModuleList().remove(0));
    }

    /**
     * A stub ReadOnlyGradPad whose modules list can violate interface constraints.
     */
    private static class GradPadStub implements ReadOnlyGradPad {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        private final UniqueTagMap tags = new UniqueTagMap();

        GradPadStub(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }

        @Override
        public UniqueTagMap getTags() {
            return tags;
        }
    }

}

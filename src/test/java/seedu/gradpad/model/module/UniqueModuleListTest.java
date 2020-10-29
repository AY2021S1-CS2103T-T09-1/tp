package seedu.gradpad.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.logic.commands.CommandTestUtil.VALID_TAG_NON_CORE;
import static seedu.gradpad.testutil.Assert.assertThrows;
import static seedu.gradpad.testutil.TypicalModules.CS2103T;
import static seedu.gradpad.testutil.TypicalModules.CS3216;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.gradpad.model.module.exceptions.DuplicateModuleException;
import seedu.gradpad.model.module.exceptions.ModuleNotFoundException;
import seedu.gradpad.testutil.ModuleBuilder;

public class UniqueModuleListTest {

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void contains_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.contains(null));
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueModuleList.contains(CS2103T));
    }

    @Test
    public void contains_moduleInList_returnsTrue() {
        uniqueModuleList.add(CS2103T);
        assertTrue(uniqueModuleList.contains(CS2103T));
    }

    @Test
    public void contains_moduleWithSameFieldsExceptTagsInList_returnsTrue() {
        uniqueModuleList.add(CS2103T);
        Module editedCS2103T = new ModuleBuilder(CS2103T).withTags(VALID_TAG_NON_CORE)
                .build();
        assertTrue(uniqueModuleList.contains(editedCS2103T));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.add(null));
    }

    @Test
    public void add_duplicateModule_throwsDuplicateModuleException() {
        uniqueModuleList.add(CS2103T);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.add(CS2103T));
    }

    @Test
    public void setModule_nullTargetModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModule(null, CS2103T));
    }

    @Test
    public void setModule_nullEditedModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModule(CS2103T, null));
    }

    @Test
    public void setModule_targetModuleNotInList_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.setModule(CS2103T, CS2103T));
    }

    @Test
    public void setModule_editedModuleIsSameModule_success() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.setModule(CS2103T, CS2103T);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS2103T);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasSameIdentity_success() {
        uniqueModuleList.add(CS2103T);
        Module editedCS2103T = new ModuleBuilder(CS2103T).withTags(VALID_TAG_NON_CORE).build();
        uniqueModuleList.setModule(CS2103T, editedCS2103T);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(editedCS2103T);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasDifferentIdentity_success() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.setModule(CS2103T, CS3216);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS3216);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasNonUniqueIdentity_throwsDuplicateModuleException() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.add(CS3216);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.setModule(CS2103T, CS3216));
    }

    @Test
    public void remove_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.remove(null));
    }

    @Test
    public void remove_moduleDoesNotExist_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.remove(CS2103T));
    }

    @Test
    public void remove_existingModule_removesModule() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.remove(CS2103T);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullUniqueModuleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModules((UniqueModuleList) null));
    }

    @Test
    public void setModules_uniqueModuleList_replacesOwnListWithProvidedUniqueModuleList() {
        uniqueModuleList.add(CS2103T);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS3216);
        uniqueModuleList.setModules(expectedUniqueModuleList);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModules((List<Module>) null));
    }

    @Test
    public void setModules_list_replacesOwnListWithProvidedList() {
        uniqueModuleList.add(CS2103T);
        List<Module> moduleList = Collections.singletonList(CS3216);
        uniqueModuleList.setModules(moduleList);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS3216);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_listWithDuplicateModules_throwsDuplicateModuleException() {
        List<Module> listWithDuplicateModules = Arrays.asList(CS2103T, CS2103T);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.setModules(listWithDuplicateModules));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueModuleList.asUnmodifiableObservableList().remove(0));
    }
}

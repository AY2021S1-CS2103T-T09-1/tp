package seedu.gradpad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.gradpad.testutil.TypicalModules.CS2103T;
import static seedu.gradpad.testutil.TypicalModules.CS3216;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.gradpad.commons.core.GuiSettings;
import seedu.gradpad.model.module.ModuleCodeContainsKeywordsPredicate;
import seedu.gradpad.testutil.GradPadBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new GradPad(), new GradPad(modelManager.getGradPad()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGradPadFilePath(Paths.get("gradpad/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setGradPadFilePath(Paths.get("new/gradpad/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setGradPadFilePath_nullPath_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> modelManager.setGradPadFilePath(null));
    }

    @Test
    public void setGradPadFilePath_validPath_setsGradPadFilePath() {
        Path path = Paths.get("gradpad/file/path");
        modelManager.setGradPadFilePath(path);
        assertEquals(path, modelManager.getGradPadFilePath());
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> modelManager.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInGradPad_returnsFalse() {
        assertFalse(modelManager.hasModule(CS2103T));
    }

    @Test
    public void hasModule_moduleInGradPad_returnsTrue() {
        modelManager.addModule(CS2103T);
        assertTrue(modelManager.hasModule(CS2103T));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
                modelManager.getFilteredModuleList().remove(0));
    }

    @Test
    public void equals() {
        GradPad gradPad = new GradPadBuilder().withModule(CS2103T).withModule(CS3216).build();
        GradPad differentGradPad = new GradPad();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(gradPad, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(gradPad, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different gradPad -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentGradPad, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = { CS2103T.getModuleCode().moduleCode };
        modelManager.updateFilteredModuleList(new ModuleCodeContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(gradPad, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGradPadFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(gradPad, differentUserPrefs)));
    }
}

package seedu.gradpad.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.gradpad.commons.core.GuiSettings;
import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonGradPadStorage gradPadStorage = new JsonGradPadStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(gradPadStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void gradPadReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonGradPadStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonGradPadStorageTest} class.
         */
        GradPad original = getTypicalGradPad();
        storageManager.saveGradPad(original);
        ReadOnlyGradPad retrieved = storageManager.readGradPad().get();
        assertEquals(original, new GradPad(retrieved));
    }

    @Test
    public void getGradPadFilePath() {
        assertNotNull(storageManager.getGradPadFilePath());
    }

}

package seedu.gradpad.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.gradpad.testutil.Assert.assertThrows;
import static seedu.gradpad.testutil.TypicalModules.CS1231;
import static seedu.gradpad.testutil.TypicalModules.CS2103T;
import static seedu.gradpad.testutil.TypicalModules.getTypicalGradPad;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.ReadOnlyGradPad;

public class JsonGradPadStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonGradPadStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readGradPad_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readGradPad(null));
    }

    private java.util.Optional<ReadOnlyGradPad> readGradPad(String filePath) throws Exception {
        return new JsonGradPadStorage(Paths.get(filePath)).readGradPad(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readGradPad("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readGradPad("notJsonFormatGradPad.json"));
    }

    @Test
    public void readGradPad_invalidModuleGradPad_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readGradPad("invalidModuleGradPad.json"));
    }

    @Test
    public void readGradPad_invalidAndValidModuleGradPad_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readGradPad("invalidAndValidModuleGradPad.json"));
    }

    @Test
    public void readAndSaveGradPad_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempGradPad.json");
        GradPad original = getTypicalGradPad();
        JsonGradPadStorage jsonGradPadStorage = new JsonGradPadStorage(filePath);

        // Save in new file and read back
        jsonGradPadStorage.saveGradPad(original, filePath);
        ReadOnlyGradPad readBack = jsonGradPadStorage.readGradPad(filePath).get();
        assertEquals(original, new GradPad(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addModule(CS1231);
        original.removeModule(CS2103T);
        jsonGradPadStorage.saveGradPad(original, filePath);
        readBack = jsonGradPadStorage.readGradPad(filePath).get();
        assertEquals(original, new GradPad(readBack));

        // Save and read without specifying file path
        original.addModule(CS2103T);
        jsonGradPadStorage.saveGradPad(original); // file path not specified
        readBack = jsonGradPadStorage.readGradPad().get(); // file path not specified
        assertEquals(original, new GradPad(readBack));

    }

    @Test
    public void saveGradPad_nullGradPad_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveGradPad(null, "SomeFile.json"));
    }

    /**
     * Saves {@code gradPad} at the specified {@code filePath}.
     */
    private void saveGradPad(ReadOnlyGradPad gradPad, String filePath) {
        try {
            new JsonGradPadStorage(Paths.get(filePath))
                    .saveGradPad(gradPad, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveGradPad_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveGradPad(new GradPad(), null));
    }
}

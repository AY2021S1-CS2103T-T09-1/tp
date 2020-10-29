package seedu.gradpad.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.gradpad.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.commons.util.JsonUtil;
import seedu.gradpad.model.GradPad;
import seedu.gradpad.testutil.TypicalModules;

public class JsonSerializableGradPadTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableGradPadTest");
    private static final Path TYPICAL_MODULES_FILE = TEST_DATA_FOLDER.resolve("typicalModulesGradPad.json");
    private static final Path INVALID_MODULE_FILE = TEST_DATA_FOLDER.resolve("invalidModuleGradPad.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleGradPad.json");

    @Test
    public void toModelType_typicalModulesFile_success() throws Exception {
        JsonSerializableGradPad dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULES_FILE,
                JsonSerializableGradPad.class).get();
        GradPad gradPadFromFile = dataFromFile.toModelType();
        GradPad typicalModulesGradPad = TypicalModules.getTypicalGradPad();
        assertEquals(gradPadFromFile, typicalModulesGradPad);
    }

    @Test
    public void toModelType_invalidModuleFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGradPad dataFromFile = JsonUtil.readJsonFile(INVALID_MODULE_FILE,
                JsonSerializableGradPad.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        JsonSerializableGradPad dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableGradPad.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableGradPad.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

}

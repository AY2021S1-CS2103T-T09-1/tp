package seedu.gradpad.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.gradpad.logic.commands.GemCommand.MESSAGE_SUCCESS;
import static seedu.gradpad.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.storage.GemCommandStorage;
import seedu.gradpad.storage.JsonGradPadStorage;

public class GemCommandTest {

    public static final Path COMPILED_PATH_1 = Paths.get("src/test/data/GemCommandTest/compiledsem1GEmodules.json");
    public static final Path COMPILED_PATH_2 = Paths.get("src/test/data/GemCommandTest/compiledsem2GEmodules.json");
    public static final Path TEST_GEH_SEM1_PATH = Paths.get("src/main/resources/data/GEM/GEHs1.json");
    public static final Path TEST_GEH_SEM2_PATH = Paths.get("src/main/resources/data/GEM/GEHsem2.json");
    private Model model;
    private GemCommand gemCommand = new GemCommand();
    private ObservableList<Module> testModules;
    public void setUpTestModules(Path path) throws IOException, DataConversionException {
        JsonGradPadStorage storage = new JsonGradPadStorage(path);
        ReadOnlyGradPad gradPad = storage.readGradPad().get();
        testModules = gradPad.getModuleList();
    }
    @Test
    public void getSem1Storage_validTest() {
        GemCommandStorage actual = gemCommand.getSem1Storage();
        assertNull(actual);
    }
    @Test
    public void getSem2Storage_validTest() {
        GemCommandStorage actual = gemCommand.getSem2Storage();
        assertNull(actual);
    }
    @Test
    public void setSem1Storage_validTest() throws IOException, DataConversionException, IllegalValueException {
        setUpTestModules(TEST_GEH_SEM1_PATH);
        gemCommand.setSem1Storage();
        GemCommandStorage storage = gemCommand.getSem1Storage();
        ObservableList<Module> actual = storage.getGehModules();
        assertEquals(testModules, actual);
    }
    @Test
    public void setSem2Storage_validTest() throws IOException, DataConversionException, IllegalValueException {
        setUpTestModules(TEST_GEH_SEM2_PATH);
        gemCommand.setSem2Storage();
        GemCommandStorage storage = gemCommand.getSem2Storage();
        ObservableList<Module> actual = storage.getGehModules();
        assertEquals(testModules, actual);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> gemCommand.execute(model));
    }
    @Test
    public void execute_validTest() throws IOException, DataConversionException {
        model = new ModelManager();
        GemCommandStorage storage = new GemCommandStorage();
        setUpTestModules(COMPILED_PATH_1);
        String expectedMessage = MESSAGE_SUCCESS + "\n \n" + "SEMESTER 1:" + "\n";
        expectedMessage += storage.moduleExtractor(testModules);
        setUpTestModules(COMPILED_PATH_2);
        expectedMessage += "\n \n" + "SEMESTER 2:" + "\n";
        expectedMessage += storage.moduleExtractor(testModules);
        CommandResult expected = new CommandResult(expectedMessage);
        CommandResult actual = gemCommand.execute(model);
        assertEquals(expected, actual);
    }
}

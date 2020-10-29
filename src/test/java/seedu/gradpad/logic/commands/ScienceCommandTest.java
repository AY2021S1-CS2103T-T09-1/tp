package seedu.gradpad.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.gradpad.storage.RequiredCommandMessages.SCIENCE_PATH;
import static seedu.gradpad.storage.RequiredCommandStorageTest.TEST_SCIENCE_PATH;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.storage.JsonGradPadStorage;

public class ScienceCommandTest {
    public static final String INVALID_PATH = "data/science.json";
    public static final String MESSAGE_SUCCESS = "These are the Science Modules that you can take:";
    private Model model;
    private ScienceCommand scienceCommand = new ScienceCommand();
    private String moduleNames = "";
    public void setUp() throws IOException, DataConversionException {
        StringBuilder modulesToAdd = new StringBuilder();
        JsonGradPadStorage storage = new JsonGradPadStorage(Paths.get(TEST_SCIENCE_PATH));
        ObservableList<Module> modules = storage.readGradPad().get().getModuleList();
        for (Module module : modules) {
            String moduleToAdd = module.getModuleCode() + " (" + module.getModularCredits() + " MCs)";
            modulesToAdd.append("\n").append(moduleToAdd);
        }
        moduleNames += modulesToAdd;
    }
    @Test
    public void validGetScienceModulesTest() {
        ObservableList<Module> empty = scienceCommand.getScienceModules();
        assertNull(empty);
    }
    @Test
    public void nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scienceCommand.execute(model));
    }
    @Test
    public void nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scienceCommand.setScienceModules(null));
    }
    @Test
    public void validPathExecuteScienceCommand_success() throws IOException, DataConversionException {
        model = new ModelManager();
        setUp();
        CommandResult expected = new CommandResult(MESSAGE_SUCCESS + moduleNames);
        CommandResult actual = scienceCommand.execute(model);
        assertEquals(expected, actual);
    }

    @Test
    public void setScienceModulesInvalidPath_throwsIOexception() {
        assertThrows(IOException.class, () -> scienceCommand.setScienceModules(INVALID_PATH));
    }
    @Test
    public void setScienceModulesValidPath_returnsFilledOptional() throws IOException,
            DataConversionException, IllegalValueException {
        JsonGradPadStorage expectedJsonStorage = new JsonGradPadStorage(Paths.get(TEST_SCIENCE_PATH));
        Optional<ReadOnlyGradPad> expectedGradPad = expectedJsonStorage.readGradPad();
        ObservableList<Module> expected = expectedGradPad.get().getModuleList();
        scienceCommand.setScienceModules(SCIENCE_PATH);
        ObservableList<Module> actual = scienceCommand.getScienceModules();
        assertEquals(actual, expected);
    }
}

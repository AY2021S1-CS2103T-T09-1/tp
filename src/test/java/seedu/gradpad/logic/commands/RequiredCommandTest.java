package seedu.gradpad.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_FAILURE_GE_1;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_FAILURE_GE_2;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_FOUNDATION;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_INTERN_1;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_INTERN_2;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SCIENCE;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_FOUNDATION;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_GE;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_INTERN;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_ITPROF;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_MATH;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_SCIENCE;
import static seedu.gradpad.storage.RequiredCommandStorageTest.TEST_FOUNDATION_PATH;
import static seedu.gradpad.storage.RequiredCommandStorageTest.TEST_SCIENCE_PATH;
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
import seedu.gradpad.storage.JsonGradPadStorage;
import seedu.gradpad.storage.RequiredCommandStorage;

public class RequiredCommandTest {
    public static final Path COMPILED_PATH = Paths.get("src/test/data/RequiredCommandTest/compiledmodules.json");
    public static final Path INCOMPLETE_FOUNDATION_PATH =
            Paths.get("src/test/data/RequiredCommandTest/incompletefoundationmodules.json");
    public static final Path SINGLE_MODULE_PATH = Paths.get("src/test/data/RequiredCommandTest/singlemodule.json");
    public static final Path DOUBLE_MODULE_PATH = Paths.get("src/test/data/RequiredCommandTest/doublemodules.json");
    public static final String MISSING_MODULE_1 = "CS1101S (4 MCs)";
    public static final String MISSING_MODULE_2 = "CS1231S (4 MCs)";
    public static final String MESSAGE_INTERN_TEST = " You are currently at 4 MCs. ";
    private Model model;
    private RequiredCommand requiredCommand = new RequiredCommand();
    private ObservableList<Module> testModules;
    public void setUpTestModules(Path path) throws IOException, DataConversionException {
        JsonGradPadStorage storage = new JsonGradPadStorage(path);
        ReadOnlyGradPad gradPad = storage.readGradPad().get();
        testModules = gradPad.getModuleList();
    }

    public void setUp() throws IOException, DataConversionException {
        JsonGradPadStorage storage = new JsonGradPadStorage(COMPILED_PATH);
        ReadOnlyGradPad gradPad = storage.readGradPad().get();
        model = new ModelManager();
        model.setGradPad(gradPad);
    }
    @Test
    public void getLeftOverModules_validTest() {
        String actual = requiredCommand.getLeftOverModules();
        assertEquals("", actual);
    }
    @Test
    public void getStorage_validTest() {
        RequiredCommandStorage actual = requiredCommand.getStorage();
        assertNull(actual);
    }
    @Test
    public void setStorage_validTest() throws IOException, DataConversionException, IllegalValueException {
        setUpTestModules(Paths.get(TEST_FOUNDATION_PATH));
        requiredCommand.setStorage();
        RequiredCommandStorage storage = requiredCommand.getStorage();
        ObservableList<Module> actual = storage.getRequiredFoundation();
        assertEquals(testModules, actual);
    }
    @Test
    public void getCurrentModules_validTest() {
        ObservableList<Module> actual = requiredCommand.getCurrentModules();
        assertNull(actual);
    }
    @Test
    public void setCurrentModules_validTest() {
        requiredCommand.setCurrentModules(null);
        assertNull(requiredCommand.getCurrentModules());
    }
    @Test
    public void compareModules_validTest() throws IOException, DataConversionException {
        setUpTestModules(INCOMPLETE_FOUNDATION_PATH);
        requiredCommand.setCurrentModules(testModules);
        setUpTestModules(Paths.get(TEST_FOUNDATION_PATH));
        requiredCommand.compareModules(testModules, MESSAGE_FOUNDATION, MESSAGE_SUCCESS_FOUNDATION);
        String expected = MESSAGE_FOUNDATION + "\n" + MISSING_MODULE_1 + "\n" + "\n";
        String actual = requiredCommand.getLeftOverModules();
        assertEquals(expected, actual);
    }
    @Test
    public void compareScience_validTest() throws IOException, DataConversionException {
        setUpTestModules(INCOMPLETE_FOUNDATION_PATH);
        requiredCommand.setCurrentModules(testModules);
        setUpTestModules(Paths.get(TEST_SCIENCE_PATH));
        requiredCommand.compareScience(testModules);
        String actual = requiredCommand.getLeftOverModules();
        String expected = MESSAGE_SCIENCE + "\n" + "\n";
        assertEquals(expected, actual);
    }
    @Test
    public void compareInternship_validTest() throws IOException, DataConversionException {
        setUpTestModules(SINGLE_MODULE_PATH);
        requiredCommand.setCurrentModules(testModules);
        setUpTestModules(DOUBLE_MODULE_PATH);
        requiredCommand.compareInternship(testModules);
        String actual = requiredCommand.getLeftOverModules();
        String expected = MESSAGE_INTERN_1 + MESSAGE_INTERN_TEST + MESSAGE_INTERN_2 + "\n" + MISSING_MODULE_2;
        assertEquals(expected, actual);
    }

    @Test
    public void isGePresent_validTest() throws IOException, DataConversionException {
        setUpTestModules(SINGLE_MODULE_PATH);
        requiredCommand.setCurrentModules(testModules);
        assertFalse(requiredCommand.isGePresent("GEH"));
    }

    @Test
    public void compareAllGEs_validTest() throws IOException, DataConversionException {
        String expectedUncompletedGEs = "\n" + "GEH" + "\n" + "GEQ" + "\n"
                + "GER" + "\n" + "GES" + "\n" + "GET" + "\n";
        String expected = MESSAGE_FAILURE_GE_1 + expectedUncompletedGEs + MESSAGE_FAILURE_GE_2 + "\n" + "\n";
        setUpTestModules(SINGLE_MODULE_PATH);
        requiredCommand.setCurrentModules(testModules);
        requiredCommand.compareAllGEs();
        String actual = requiredCommand.getLeftOverModules();
        assertEquals(expected, actual);
    }

    @Test
    public void nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> requiredCommand.execute(model));
    }
    @Test
    public void execute_validTest() throws IOException, DataConversionException {
        setUp();
        String expectedMessage = "" + MESSAGE_SUCCESS_GE + "\n" + "\n" + MESSAGE_SUCCESS_FOUNDATION + "\n" + "\n"
                + MESSAGE_SUCCESS_ITPROF + "\n" + "\n" + MESSAGE_SUCCESS_MATH
                + "\n" + "\n" + MESSAGE_SUCCESS_SCIENCE + "\n" + "\n" + MESSAGE_SUCCESS_INTERN;
        CommandResult expected = new CommandResult(expectedMessage);
        CommandResult actual = requiredCommand.execute(model);
        assertEquals(expected, actual);
    }
}

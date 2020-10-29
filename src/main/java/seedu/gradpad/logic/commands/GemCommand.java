package seedu.gradpad.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.storage.GemCommandPaths.GEH_SEM1_PATH;
import static seedu.gradpad.storage.GemCommandPaths.GEH_SEM2_PATH;
import static seedu.gradpad.storage.GemCommandPaths.GEQ_PATH;
import static seedu.gradpad.storage.GemCommandPaths.GER_PATH;
import static seedu.gradpad.storage.GemCommandPaths.GES_SEM1_PATH;
import static seedu.gradpad.storage.GemCommandPaths.GES_SEM2_PATH;
import static seedu.gradpad.storage.GemCommandPaths.GET_SEM1_PATH;
import static seedu.gradpad.storage.GemCommandPaths.GET_SEM2_PATH;

import java.io.IOException;

import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.model.Model;
import seedu.gradpad.storage.GemCommandStorage;

public class GemCommand extends Command {
    public static final String COMMAND_WORD = "gem";
    public static final String MESSAGE_SUCCESS = "These are the GE Modules that you can take:";
    public static final String MESSAGE_FAILURE_GEM = "There was an error loading the required GE Modules :(";
    private GemCommandStorage sem1Storage;
    private GemCommandStorage sem2Storage;

    /**
     * Retrieves the attribute sem1Storage of a GemCommand object.
     * @return storage attribute of type GemCommandStorage.
     */
    public GemCommandStorage getSem1Storage() {
        return sem1Storage;
    }

    /**
     * Retrieves the attribute sem2Storage of a GemCommand object.
     * @return storage attribute of type GemCommandStorage.
     */
    public GemCommandStorage getSem2Storage() {
        return sem2Storage;
    }

    /**
     * Loads the sem1Storage attribute with Semester 1 GE Modules by using
     * the various set() methods from the GemCommandStorage class.
     * @throws IOException When the path in invalid.
     * @throws IllegalValueException When the data from the JSON file does not match the
     * specific field headers of the JsonAdaptedModule class (Eg.'moduleCode', 'modularCredits').
     */
    public void setSem1Storage() throws IOException, IllegalValueException {
        sem1Storage = new GemCommandStorage();
        sem1Storage.setGehModules(GEH_SEM1_PATH);
        sem1Storage.setGeqModules(GEQ_PATH);
        sem1Storage.setGerModules(GER_PATH);
        sem1Storage.setGesModules(GES_SEM1_PATH);
        sem1Storage.setGetModules(GET_SEM1_PATH);
    }

    /**
     * Loads the sem2Storage attribute with Semester 2 GE Modules by using
     * the various set() methods from the GemCommandStorage class.
     * @throws IOException When the path in invalid.
     * @throws IllegalValueException When the data from the JSON file does not match the
     * specific field headers of the JsonAdaptedModule class (Eg.'moduleCode', 'modularCredits').
     */
    public void setSem2Storage() throws IOException, IllegalValueException {
        sem2Storage = new GemCommandStorage();
        sem2Storage.setGehModules(GEH_SEM2_PATH);
        sem2Storage.setGeqModules(GEQ_PATH);
        sem2Storage.setGerModules(GER_PATH);
        sem2Storage.setGesModules(GES_SEM2_PATH);
        sem2Storage.setGetModules(GET_SEM2_PATH);
    }

    /**
     * Sets up the all the GE modules in the sem1Storage and sem2Storage and displays them
     * with the CommandResult object.
     * @param model {@code Model} which the command should operate on.
     * @return a CommandResult displaying all the available GE modules.
     */
    @Override
    public CommandResult execute(Model model) {
        try {
            requireNonNull(model);
            setSem1Storage();
            setSem2Storage();
            sem1Storage.setCompiledModules();
            sem2Storage.setCompiledModules();
            String modulesToAdd = "\n \n" + "SEMESTER 1:" + "\n";
            modulesToAdd += sem1Storage.getCompiledModules();
            modulesToAdd += "\n \n" + "SEMESTER 2:" + "\n";
            modulesToAdd += sem2Storage.getCompiledModules();
            return new CommandResult(MESSAGE_SUCCESS + modulesToAdd);
        } catch (IOException | IllegalValueException e) {
            return new CommandResult(MESSAGE_FAILURE_GEM);
        }
    }
}

package seedu.gradpad.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.storage.RequiredCommandMessages.FOUNDATION_PATH;
import static seedu.gradpad.storage.RequiredCommandMessages.INTERNSHIP_PATH;
import static seedu.gradpad.storage.RequiredCommandMessages.ITPROF_PATH;
import static seedu.gradpad.storage.RequiredCommandMessages.MATH_PATH;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_FAILURE;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_FAILURE_GE_1;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_FAILURE_GE_2;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_FOUNDATION;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_INTERN_1;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_INTERN_2;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_ITPROF;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_MATH;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SCIENCE;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_FOUNDATION;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_GE;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_INTERN;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_ITPROF;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_MATH;
import static seedu.gradpad.storage.RequiredCommandMessages.MESSAGE_SUCCESS_SCIENCE;
import static seedu.gradpad.storage.RequiredCommandMessages.SCIENCE_PATH;

import java.io.IOException;

import javafx.collections.ObservableList;
import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.storage.RequiredCommandStorage;

public class RequiredCommand extends Command {
    public static final String COMMAND_WORD = "required";

    private ObservableList<Module> currentModules;
    private String leftOverModules = "";
    private RequiredCommandStorage storage;

    /**
     * Retrieves the leftOverModules attribute of a RequiredCommand object.
     * @return leftOverModules of type String.
     */
    public String getLeftOverModules() {
        return leftOverModules;
    }

    /**
     * Retrieves the attribute storage of a RequiredCommand object.
     * @return storage attribute of type RequiredCommandStorage.
     */
    public RequiredCommandStorage getStorage() {
        return storage;
    }

    /**
     * Sets up the storage attribute with all the relevant modules from various fields.
     * @throws IOException When path is invalid.
     * @throws IllegalValueException When the data from the JSON file violates some constraints.
     */
    public void setStorage() throws IOException, IllegalValueException {
        storage = new RequiredCommandStorage();
        storage.setRequiredFoundation(FOUNDATION_PATH);
        storage.setRequiredITprof(ITPROF_PATH);
        storage.setRequiredMath(MATH_PATH);
        storage.setRequiredScience(SCIENCE_PATH);
        storage.setRequiredInternship(INTERNSHIP_PATH);
    }

    /**
     * Retrieves the currentModules attribute of an Required Command object.
     * @return currentModules attribute of type ObservableList<Module/>.
     */
    public ObservableList<Module> getCurrentModules() {
        return currentModules;
    }

    /**
     * Sets the argument modules as the attribute currentModules.
     * @param modules target argument of type ObservableList<Module/>.
     */
    public void setCurrentModules(ObservableList<Module> modules) {
        currentModules = modules;
    }

    /**
     * Checks if a Module already exists in a given Module List.
     * @param module Module that you wish to check.
     * @param modules Module List that you wish to check against.
     * @return True if the module already exists, false otherwise.
     */
    public boolean doesModuleAlreadyExist(Module module, ObservableList<Module> modules) {
        for (Module mod : modules) {
            if (module.isSameModule(mod)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cross references the user's current list of Modules against the given
     * modules argument and marks out any undone Modules. Displays a successMessage
     * if all modules are done, and a failMessage if there are left over modules.
     * @param modules List of Modules of a certain category (Eg. Foundation, IT Professsionalism)
     * @param failMessage Fail message for particular category of Modules.
     * @param successMessage Success message for particular category of Modules.
     */
    public void compareModules(ObservableList<Module> modules, String failMessage, String successMessage) {
        boolean areModulesCleared = true;
        StringBuilder modulesToAdd = new StringBuilder();
        for (Module module : modules) {
            if (!doesModuleAlreadyExist(module, currentModules)) {
                String moduleToAdd = module.getModuleCode() + " (" + module.getModularCredits() + " MCs)";
                modulesToAdd.append("\n").append(moduleToAdd);
                areModulesCleared = false;
            }
        } if (areModulesCleared) {
            leftOverModules += successMessage + "\n";
        } else {
            leftOverModules += failMessage + modulesToAdd + "\n";
        }
        leftOverModules += "\n";
    }

    /**
     * Cross references the user's current list of Modules and marks out
     * any undone Science Modules.
     */
    public void compareScience(ObservableList<Module> requiredScience) {
        boolean isScienceCleared = false;
        for (Module module : requiredScience) {
            if (doesModuleAlreadyExist(module, currentModules)) {
                leftOverModules += MESSAGE_SUCCESS_SCIENCE + "\n";
                isScienceCleared = true;
                break;
            }
        } if (!isScienceCleared) {
            leftOverModules += MESSAGE_SCIENCE + "\n";
        }
        leftOverModules += "\n";
    }

    /**
     * Cross references the user's current list of Modules and marks out
     * any undone Internship Modules. Also calculates current MC score
     * achieved from Internship Modules.
     */
    public void compareInternship(ObservableList<Module> requiredInternship) {
        int modularScore = 0;
        StringBuilder leftOverInternship = new StringBuilder();
        for (Module module : requiredInternship) {
            if (doesModuleAlreadyExist(module, currentModules)) {
                int modularCredits = Integer.parseInt(module.getModularCredits().toString());
                modularScore += modularCredits;
            } else {
                String moduleToAdd = module.getModuleCode() + " (" + module.getModularCredits() + " MCs)";
                leftOverInternship.append("\n").append(moduleToAdd);
            }
        } if (modularScore < 12) {
            String modScore = " You are currently at " + modularScore + " MCs. ";
            leftOverModules += MESSAGE_INTERN_1 + modScore + MESSAGE_INTERN_2 + leftOverInternship;
        } else {
            leftOverModules += MESSAGE_SUCCESS_INTERN;
        }
    }

    /**
     * Checks if particular GE field is cleared in the current GradPad.
     * @param ge The GE field that you wish to check (Eg. 'GEQ' or 'GEH').
     * @return True if the GE field is cleared, false otherwise.
     */
    public boolean isGePresent(String ge) {
        for (Module module : currentModules) {
            String moduleCode = module.getModuleCode().toString();
            if (moduleCode.startsWith(ge)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks which GE fields are not clear in the current GradPad and
     * adds to the attribute leftOverModules if that particular GE field
     * has not been cleared.
     */
    public void compareAllGEs() {
        String uncompletedGEs = "\n";
        boolean allGEsCleared = true;
        if (!isGePresent("GEH")) {
            uncompletedGEs += "GEH" + "\n";
            allGEsCleared = false;
        }
        if (!isGePresent("GEQ")) {
            uncompletedGEs += "GEQ" + "\n";
            allGEsCleared = false;
        }
        if (!isGePresent("GER")) {
            uncompletedGEs += "GER" + "\n";
            allGEsCleared = false;
        }
        if (!isGePresent("GES")) {
            uncompletedGEs += "GES" + "\n";
            allGEsCleared = false;
        }
        if (!isGePresent("GET")) {
            uncompletedGEs += "GET" + "\n";
            allGEsCleared = false;
        }
        if (allGEsCleared) {
            leftOverModules += MESSAGE_SUCCESS_GE + "\n";
        } else {
            leftOverModules += MESSAGE_FAILURE_GE_1 + uncompletedGEs + MESSAGE_FAILURE_GE_2 + "\n";
        }
        leftOverModules += "\n";
    }

    /**
     * Sets up the reference modules and the current modules in gradPad and compares all the modules.
     * @param model {@code Model} which the command should operate on.
     * @return a CommandResult displaying all the undone modules.
     */
    @Override
    public CommandResult execute(Model model) {
        try {
            requireNonNull(model);
            currentModules = model.getGradPad().getModuleList();
            setStorage();
            ObservableList<Module> requiredFoundation = storage.getRequiredFoundation();
            ObservableList<Module> requiredITprof = storage.getRequiredITprof();
            ObservableList<Module> requiredMath = storage.getRequiredMath();
            ObservableList<Module> requiredScience = storage.getRequiredScience();
            ObservableList<Module> requiredInternship = storage.getRequiredInternship();
            compareAllGEs();
            compareModules(requiredFoundation, MESSAGE_FOUNDATION, MESSAGE_SUCCESS_FOUNDATION);
            compareModules(requiredITprof, MESSAGE_ITPROF, MESSAGE_SUCCESS_ITPROF);
            compareModules(requiredMath, MESSAGE_MATH, MESSAGE_SUCCESS_MATH);
            compareScience(requiredScience);
            compareInternship(requiredInternship);
            return new CommandResult(leftOverModules);
        } catch (IOException | IllegalValueException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}

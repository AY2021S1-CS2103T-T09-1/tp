package seedu.gradpad.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javafx.collections.ObservableList;
import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.commons.util.JsonUtil;
import seedu.gradpad.model.module.Module;

public class GemCommandStorage {
    private ObservableList<Module> gehModules;
    private ObservableList<Module> geqModules;
    private ObservableList<Module> gerModules;
    private ObservableList<Module> gesModules;
    private ObservableList<Module> getModules;
    private String compiledModules;

    /**
     * Makes use of classLoaders to convert the original file path
     * into one that can be readable during runtime, such that it
     * can be used to retrieve the File's content.
     * @param filePath Original file path.
     * @return Converted file content of type String.
     * @throws IOException When the provided fileName is invalid.
     */
    private String getFileFromResource(String filePath) throws IOException {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IOException();
        } else {
            StringBuilder dataString = new StringBuilder();

            Reader reader = new BufferedReader(new InputStreamReader(inputStream,
                    Charset.forName(StandardCharsets.UTF_8.name())));
            int c = 0;
            while ((c = reader.read()) != -1) {
                dataString.append((char) c);
            }

            return dataString.toString();
        }
    }

    /**
     * Returns gehModules attribute of GemCommandStorage object.
     * @return gehModules attribute of type ObservableList<Module/>.
     */
    public ObservableList<Module> getGehModules() {
        return gehModules;
    }
    /**
     * Loads the gehModules attribute with GEH Modules.
     * @throws IOException When path is invalid.
     * @throws IllegalValueException When the data from the JSON file does not match the
     * specific field headers of the JsonAdaptedModule class (Eg.'moduleCode', 'modularCredits').
     */
    public void setGehModules(String path) throws IOException, IllegalValueException {
        String file = getFileFromResource(path);
        gehModules = JsonUtil.getModulesFromJsonFile(file);
    }

    /**
     * Returns geqModules attribute of GemCommandStorage object.
     * @return geqModules attribute of type ObservableList<Module/>.
     */
    public ObservableList<Module> getGeqModules() {
        return geqModules;
    }
    /**
     * Loads the geqModules attribute with GEQ Modules.
     * @throws IOException When path is invalid.
     * @throws IllegalValueException When the data from the JSON file does not match the
     * specific field headers of the JsonAdaptedModule class (Eg.'moduleCode', 'modularCredits').
     */
    public void setGeqModules(String path) throws IOException, IllegalValueException {
        String file = getFileFromResource(path);
        geqModules = JsonUtil.getModulesFromJsonFile(file);
    }

    /**
     * Returns gerModules attribute of GemCommandStorage object.
     * @return gerModules attribute of type ObservableList<Module/>.
     */
    public ObservableList<Module> getGerModules() {
        return gerModules;
    }
    /**
     * Loads the gerModules attribute with GER Modules.
     * @throws IOException When path is invalid.
     * @throws IllegalValueException When the data from the JSON file does not match the
     * specific field headers of the JsonAdaptedModule class (Eg.'moduleCode', 'modularCredits').
     */
    public void setGerModules(String path) throws IOException, IllegalValueException {
        String file = getFileFromResource(path);
        gerModules = JsonUtil.getModulesFromJsonFile(file);
    }

    /**
     * Returns gesModules attribute of GemCommandStorage object.
     * @return gesModules attribute of type ObservableList<Module/>.
     */
    public ObservableList<Module> getGesModules() {
        return gesModules;
    }
    /**
     * Loads the gesModules attribute with GES Modules.
     * @throws IOException When path is invalid.
     * @throws IllegalValueException When the data from the JSON file does not match the
     * specific field headers of the JsonAdaptedModule class (Eg.'moduleCode', 'modularCredits').
     */
    public void setGesModules(String path) throws IOException, IllegalValueException {
        String file = getFileFromResource(path);
        gesModules = JsonUtil.getModulesFromJsonFile(file);
    }

    /**
     * Returns getModules attribute of GemCommandStorage object.
     * @return getModules attribute of type ObservableList<Module/>.
     */
    public ObservableList<Module> getGetModules() {
        return getModules;
    }
    /**
     * Loads the getModules attribute with GET Modules.
     * @throws IOException When path is invalid.
     * @throws IllegalValueException When the data from the JSON file does not match the
     * specific field headers of the JsonAdaptedModule class (Eg.'moduleCode', 'modularCredits').
     */
    public void setGetModules(String path) throws IOException, IllegalValueException {
        String file = getFileFromResource(path);
        getModules = JsonUtil.getModulesFromJsonFile(file);
    }

    /**
     * Takes a List of Modules and extracts out their Module Code and Modular Credits.
     * @param modules List of Modules.
     * @return String of Module Codes and Modular Credits.
     */
    public StringBuilder moduleExtractor(ObservableList<Module> modules) {
        assert modules != null;
        StringBuilder modulesToAdd = new StringBuilder();
        for (Module module : modules) {
            String moduleToAdd = module.getModuleCode() + " (" + module.getModularCredits() + " MCs)";
            modulesToAdd.append("\n").append(moduleToAdd);
        }
        return modulesToAdd;
    }

    /**
     * Returns compiledModules attribute of GemCommandStorage object.
     * @return compiledModules attribute of type String.
     */
    public String getCompiledModules() {
        return compiledModules;
    }

    /**
     * Loads the compiledModules attribute with all the relevant GE modules in String form.
     */
    public void setCompiledModules() {
        compiledModules = "";
        compiledModules += moduleExtractor(gehModules);
        compiledModules += moduleExtractor(geqModules);
        compiledModules += moduleExtractor(gerModules);
        compiledModules += moduleExtractor(gesModules);
        compiledModules += moduleExtractor(getModules);
    }
}

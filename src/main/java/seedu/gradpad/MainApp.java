package seedu.gradpad;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.gradpad.commons.core.Config;
import seedu.gradpad.commons.core.LogsCenter;
import seedu.gradpad.commons.core.Version;
import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.util.ConfigUtil;
import seedu.gradpad.commons.util.StringUtil;
import seedu.gradpad.logic.Logic;
import seedu.gradpad.logic.LogicManager;
import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.ModelManager;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.ReadOnlyUserPrefs;
import seedu.gradpad.model.UserPrefs;
import seedu.gradpad.model.util.SampleDataUtil;
import seedu.gradpad.storage.GradPadStorage;
import seedu.gradpad.storage.JsonGradPadStorage;
import seedu.gradpad.storage.JsonUserPrefsStorage;
import seedu.gradpad.storage.Storage;
import seedu.gradpad.storage.StorageManager;
import seedu.gradpad.storage.UserPrefsStorage;
import seedu.gradpad.ui.Ui;
import seedu.gradpad.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing GradPad ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        GradPadStorage gradPadStorage = new JsonGradPadStorage(userPrefs.getGradPadFilePath());
        storage = new StorageManager(gradPadStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s GradPad and {@code userPrefs}. <br>
     * The data from the sample GradPad will be used instead if {@code storage}'s GradPad is not found,
     * or an empty GradPad will be used instead if errors occur when reading {@code storage}'s GradPad.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyGradPad> gradPadOptional;
        ReadOnlyGradPad initialData;
        try {
            gradPadOptional = storage.readGradPad();
            if (!gradPadOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample GradPad");
            }
            initialData = gradPadOptional.orElseGet(SampleDataUtil::getSampleGradPad);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty GradPad");
            initialData = new GradPad();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GradPad");
            initialData = new GradPad();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GradPad");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting GradPad " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping GradPad ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}

package seedu.gradpad.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.gradpad.commons.core.LogsCenter;
import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.ReadOnlyUserPrefs;
import seedu.gradpad.model.UserPrefs;

/**
 * Manages storage of GradPad data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GradPadStorage gradPadStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code GradPadStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(GradPadStorage gradPadStorage, UserPrefsStorage userPrefsStorage) {
        super();
        assert(gradPadStorage != null && userPrefsStorage != null);
        this.gradPadStorage = gradPadStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ GradPad methods ==============================

    @Override
    public Path getGradPadFilePath() {
        return gradPadStorage.getGradPadFilePath();
    }

    @Override
    public Optional<ReadOnlyGradPad> readGradPad() throws DataConversionException, IOException {
        return readGradPad(gradPadStorage.getGradPadFilePath());
    }

    @Override
    public Optional<ReadOnlyGradPad> readGradPad(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return gradPadStorage.readGradPad(filePath);
    }

    @Override
    public void saveGradPad(ReadOnlyGradPad gradPad) throws IOException {
        saveGradPad(gradPad, gradPadStorage.getGradPadFilePath());
    }

    @Override
    public void saveGradPad(ReadOnlyGradPad gradPad, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        gradPadStorage.saveGradPad(gradPad, filePath);
    }

}

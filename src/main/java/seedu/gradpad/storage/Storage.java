package seedu.gradpad.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.ReadOnlyUserPrefs;
import seedu.gradpad.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends GradPadStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getGradPadFilePath();

    @Override
    Optional<ReadOnlyGradPad> readGradPad() throws DataConversionException, IOException;

    @Override
    void saveGradPad(ReadOnlyGradPad gradPad) throws IOException;

}

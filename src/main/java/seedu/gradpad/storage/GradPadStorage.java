package seedu.gradpad.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.model.GradPad;
import seedu.gradpad.model.ReadOnlyGradPad;

/**
 * Represents a storage for {@link GradPad}.
 */
public interface GradPadStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGradPadFilePath();

    /**
     * Returns GradPad data as a {@link ReadOnlyGradPad}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGradPad> readGradPad() throws DataConversionException, IOException;

    /**
     * @see #getGradPadFilePath()
     */
    Optional<ReadOnlyGradPad> readGradPad(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGradPad} to the storage.
     * @param gradPad cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGradPad(ReadOnlyGradPad gradPad) throws IOException;

    /**
     * @see #saveGradPad(ReadOnlyGradPad)
     */
    void saveGradPad(ReadOnlyGradPad gradPad, Path filePath) throws IOException;

}

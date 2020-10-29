package seedu.gradpad.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.gradpad.commons.core.LogsCenter;
import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.exceptions.IllegalValueException;
import seedu.gradpad.commons.util.FileUtil;
import seedu.gradpad.commons.util.JsonUtil;
import seedu.gradpad.model.ReadOnlyGradPad;

/**
 * A class to access GradPad data stored as a json file on the hard disk.
 */
public class JsonGradPadStorage implements GradPadStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGradPadStorage.class);

    private Path filePath;

    public JsonGradPadStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGradPadFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGradPad> readGradPad() throws DataConversionException, IOException {
        return readGradPad(filePath);
    }

    /**
     * Similar to {@link #readGradPad()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyGradPad> readGradPad(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGradPad> jsonGradPad = JsonUtil.readJsonFile(
                filePath, JsonSerializableGradPad.class);
        if (!jsonGradPad.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGradPad.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveGradPad(ReadOnlyGradPad gradPad) throws IOException {
        saveGradPad(gradPad, filePath);
    }

    /**
     * Similar to {@link #saveGradPad(ReadOnlyGradPad)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGradPad(ReadOnlyGradPad gradPad, Path filePath) throws IOException {
        requireNonNull(gradPad);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGradPad(gradPad), filePath);
    }

}

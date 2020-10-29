package seedu.gradpad.nusmods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.gradpad.nusmods.exceptions.NusmodsException;

/**
 * Encapsulates operations to retrieve NUSMods module information.
 */
public class NusmodsDataManager implements NusmodsData {
    private final String filePath;
    private final DataFetcher dataFetcher;

    /**
     * Default public constructor to create an NusmodsDataManager object.
     */
    public NusmodsDataManager() {
        dataFetcher = new DataFetcherManager();
        filePath = DataFetcher.DATA_FILE_PATH;
    }

    /**
     * Constructor to change the DataFetcher class used. Mainly used for testing to provide stubs.
     *
     * @param dataFetcher The class used to invoke static methods in this class.
     */
    NusmodsDataManager(DataFetcher dataFetcher, String filePath) {
        assert(dataFetcher != null && !filePath.trim().isEmpty());

        this.dataFetcher = dataFetcher;
        this.filePath = filePath;
    }


    /**
     * Returns the module title of a module given its module code.
     *
     * @param moduleCode The module code to search for.
     * @return The module title or {@code Optional.empty()} if not found.
     * @throws NusmodsException if an error occurs while retrieving module info.
     */
    public Optional<String> getModuleTitle(String moduleCode) throws NusmodsException {
        assert(!moduleCode.trim().isEmpty());
        return getModuleInfo(moduleCode).map(ModuleInfo::getTitle);
    }

    /**
     * Returns the {@code ModuleInfo} of a module given its module code.
     * This method attempts to fetch the latest info from the NUSMods API if possible. Otherwise, it will
     * still return the info stored locally.
     *
     * @param moduleCode The module code to search for.
     * @return The {@code ModuleInfo} or {@code Optional.empty()} if not found.
     * @throws NusmodsException if an error occurs while retrieving module info.
     */
    public Optional<ModuleInfo> getModuleInfo(String moduleCode) throws NusmodsException {
        assert(!moduleCode.trim().isEmpty());

        try {
            return dataFetcher.fetchModuleInfo(moduleCode);
        } catch (NusmodsException ex) {
            return getModuleInfoFromFile(moduleCode);
        }
    }

    /**
     * Returns the {@code ModuleInfo} for a given module code by retrieving it locally.
     *
     * @param moduleCode The module coed to search for.
     * @return The {@code ModuleInfo} or {@code Optional.empty()} if not found.
     * @throws NusmodsException if an error occurs while retrieving module info.
     */
    private Optional<ModuleInfo> getModuleInfoFromFile(String moduleCode) throws NusmodsException {
        assert(!moduleCode.trim().isEmpty());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = getFileFromResource(getFilePath());
            TypeReference<HashMap<String, ModuleInfo>> targetType = new TypeReference<>(){};
            Map<String, ModuleInfo> moduleInfoMap = mapper.readValue(jsonString, targetType);

            return Optional.ofNullable(moduleInfoMap.get(moduleCode));
        } catch (IOException ex) {
            throw new NusmodsException(new IOException("Error reading module info from local file."));
        }
    }

    public String getFilePath() {
        return filePath;
    }

    private String getFileFromResource(String fileName) throws IOException {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

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
}

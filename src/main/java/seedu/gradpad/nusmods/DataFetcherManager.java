package seedu.gradpad.nusmods;

import static seedu.gradpad.nusmods.NusmodsKeywords.MODULE_FILTER_KEYWORDS;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.gradpad.commons.core.LogsCenter;
import seedu.gradpad.commons.util.HttpUtil;
import seedu.gradpad.commons.util.JsonUtil;
import seedu.gradpad.nusmods.exceptions.NusmodsException;

/**
 * Handles API requests made by GradPad to the NUSMods public API to retrieve module data.
 */
public class DataFetcherManager extends DataFetcher {
    private static final Logger logger = LogsCenter.getLogger(DataFetcher.class);
    private final HttpUtil httpUtil;
    private final String dataFilePath;

    DataFetcherManager() {
        httpUtil = HttpUtil.SINGLETON;
        dataFilePath = DataFetcher.SAVE_DATA_FILE_PATH;
    }

    DataFetcherManager(HttpUtil httpUtil) {
        assert(httpUtil != null);
        this.httpUtil = httpUtil;
        dataFilePath = DataFetcher.SAVE_DATA_FILE_PATH;
    }

    DataFetcherManager(HttpUtil httpUtil, String filePath) {
        assert(httpUtil != null && !filePath.trim().isEmpty());
        this.httpUtil = httpUtil;
        this.dataFilePath = filePath;
    }

    /**
     * Fetches a list of all NUS module summaries, then fetches module info for all CS modules (only) and
     * saves them to a JSON file locally.
     *
     * @throws NusmodsException if an error occurs while fetching data from NUSMods API or while saving data.
     */
    public void fetchAndSaveModules() throws NusmodsException {
        List<ModuleSummary> moduleSummaries = fetchModuleSummaryList();
        List<ModuleSummary> filteredModules = filterModuleSummaries(moduleSummaries, MODULE_FILTER_KEYWORDS);
        Map<String, ModuleInfo> moduleInfoMap = generateModuleInfoMap(filteredModules);

        try {
            JsonUtil.saveJsonFile(moduleInfoMap, Paths.get(getDataFilePath()));
        } catch (IOException ex) {
            throw new NusmodsException(ex);
        }
    }

    /**
     * Fetches the module info for a single module from the NUSMods API.
     *
     * @param moduleCode The module code of the module to fetch data for.
     * @return The fetched data in a {@code ModuleInfo} object or {@code Optional.empty()} if data not found.
     * @throws NusmodsException if an error occurs while fetching data or if data does not contain valid
     * {@code ModuleInfo}.
     */
    public Optional<ModuleInfo> fetchModuleInfo(String moduleCode) throws NusmodsException {
        assert(!moduleCode.trim().isEmpty());
        logger.info("Fetching module info for: " + moduleCode);

        String jsonResponse = httpUtil.makeGETRequest(String.format(MODULE_INFO_URL, moduleCode));
        try {
            return Optional.of(JsonUtil.fromJsonString(jsonResponse, ModuleInfo.class));
        } catch (IOException ex) {
            throw new NusmodsException(ex);
        }
    }

    /**
     * Fetches a list of all modules and their summaries from the NUSMods API.
     *
     * @return A list of {@code ModuleSummary} objects.
     * @throws NusmodsException if an error occurs while fetching data or if the data does not contain valid
     * {@code ModuleSummary}.
     */
    private List<ModuleSummary> fetchModuleSummaryList() throws NusmodsException {
        logger.info("Fetching list of module summaries from NUSMods API...");

        String jsonResponse = httpUtil.makeGETRequest(MODULE_SUMMARY_LIST_URL);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonResponse, new TypeReference<List<ModuleSummary>>(){});
        } catch (IOException ex) {
            throw new NusmodsException(ex);
        }
    }

    /**
     * Filters a list of {@code ModuleSummary} objects' module codes by a list of keywords.
     *
     * @param modules The list of ModuleSummary objects to filter.
     * @param keywords The keywords to match in each ModuleSummary object's module code.
     * @return The filtered list of ModuleSummary objects where every one of their module codes contains any
     * one (or more) of the keywords specified.
     */
    List<ModuleSummary> filterModuleSummaries(List<ModuleSummary> modules, String... keywords) {
        // checks if a module summary's module code contains ANY of the filter keywords
        Predicate<ModuleSummary> moduleCodePredicate = module -> Arrays.stream(keywords)
                 .anyMatch(keyword -> module.getModuleCode().contains(keyword));

        return modules.stream().filter(moduleCodePredicate).collect(Collectors.toList());
    }

    /**
     * Fetches module info for all modules given a list of {@code ModuleSummary} objects, then maps each
     * module's code to its {@code ModuleInfo} object.
     *
     * @param moduleSummaries The list of {@code ModuleSummary} objects to fetch info for.
     * @return A mapping of module codes to their respective {@code ModuleInfo}.
     */
    Map<String, ModuleInfo> generateModuleInfoMap(List<ModuleSummary> moduleSummaries) {
        logger.info("Fetching module info for all modules...");

        Map<String, ModuleInfo> moduleInfoMap = new HashMap<>();

        for (ModuleSummary summary : moduleSummaries) {
            String moduleCode = summary.getModuleCode();
            try {
                // delay each API call to adhere to rate limits
                Thread.sleep(150);
                Optional<ModuleInfo> info = fetchModuleInfo(moduleCode);
                info.ifPresent(moduleInfo -> moduleInfoMap.put(moduleCode, moduleInfo));
            } catch (NusmodsException | InterruptedException ex) {
                logger.warning("Module info not found for: " + moduleCode);
            }
        }

        return moduleInfoMap;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }
}

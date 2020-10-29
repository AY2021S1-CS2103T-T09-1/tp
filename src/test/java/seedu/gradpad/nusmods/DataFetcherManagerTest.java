package seedu.gradpad.nusmods;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.gradpad.testutil.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.util.FileUtil;
import seedu.gradpad.commons.util.HttpUtil;
import seedu.gradpad.commons.util.JsonUtil;
import seedu.gradpad.nusmods.exceptions.NusmodsException;

public class DataFetcherManagerTest {
    private static final String TEST_SAVE_FILE_PATH = "src/test/resources/NusmodsDataManagerTest/saveModulesTest.json";
    private static final String MODULE_TEST_FILE_PATH = "src/test/resources/NusmodsDataManagerTest/validModule.json";
    private static final String INVALID_MODULE_TEST_FILE_PATH = "src/test/resources/NusmodsDataManagerTest"
                                                                        + "/invalidModule.json";
    private static final String VALID_MODULE_SUMMARIES_TEST_FILE_PATH = "src/test/resources/NusmodsDataManagerTest"
                                                                                + "/validSummaries.json";
    private static final String CS1010X_MODULE_INFO = "src/test/resources/NusmodsDataManagerTest"
                                                                                + "/CS1010X.json";
    private static final String IS1103_MODULE_INFO = "src/test/resources/NusmodsDataManagerTest"
                                                                                + "/IS1103.json";

    private DataFetcherManager manager = new DataFetcherManager();

    @Test
    public void constructor_defaultWithNoArgs_defaultFilePathUsed() {
        assertEquals(DataFetcher.SAVE_DATA_FILE_PATH, manager.getDataFilePath());
    }

    @Test
    public void fetchModuleInfo_validModuleCode_validModuleInfo() throws NusmodsException, DataConversionException {
        manager = new DataFetcherManager(new ValidModuleFileHttpUtilStub());

        Optional<ModuleInfo> actualInfo = manager.fetchModuleInfo("CS2103T");
        Optional<ModuleInfo> expectedInfo = JsonUtil.readJsonFile(Paths.get(MODULE_TEST_FILE_PATH),
                                                                          ModuleInfo.class);
        if (actualInfo.isPresent() && expectedInfo.isPresent()) {
            assertEquals(expectedInfo.get(), actualInfo.get());
            return;
        }
        fail();
    }

    @Test
    public void fetchModuleInfo_invalidModuleInfoReceived_throwsNusmodsException() {
        manager = new DataFetcherManager(new InvalidModuleFileHttpUtilStub());
        assertThrows(NusmodsException.class, () -> manager.fetchModuleInfo("CS2103T"));
    }

    @Test
    public void filterModuleSummaries_filterByCs_removesIs1103Module() throws IOException {
        manager = new DataFetcherManager(new ValidModuleFileHttpUtilStub());

        List<ModuleSummary> summaryList = readModuleSummaryFromFile(VALID_MODULE_SUMMARIES_TEST_FILE_PATH);

        List<ModuleSummary> actualFilteredList = manager.filterModuleSummaries(summaryList, "CS");
        List<ModuleSummary> expectedFilteredList = summaryList.subList(1, 2);

        assertEquals(expectedFilteredList, actualFilteredList);
    }

    @Test
    public void filterModuleSummaries_filterByCsAndIs_noModulesRemoved() throws IOException {
        manager = new DataFetcherManager(new ValidModuleFileHttpUtilStub());

        List<ModuleSummary> summaryList = readModuleSummaryFromFile(VALID_MODULE_SUMMARIES_TEST_FILE_PATH);
        List<ModuleSummary> actualFilteredList = manager.filterModuleSummaries(summaryList, "CS", "IS1103");

        assertEquals(summaryList, actualFilteredList);
    }

    @Test
    public void filterModuleSummaries_noKeywordsGiven_returnEmptyList() throws IOException {
        manager = new DataFetcherManager(new ValidModuleFileHttpUtilStub());

        List<ModuleSummary> summaryList = readModuleSummaryFromFile(VALID_MODULE_SUMMARIES_TEST_FILE_PATH);
        List<ModuleSummary> actualFilteredList = manager.filterModuleSummaries(summaryList);

        assertTrue(actualFilteredList.isEmpty());
    }

    @Test
    public void generateModuleInfoMap_sampleModuleSummaryList_moduleInfoMap()
            throws IOException, DataConversionException {
        manager = new DataFetcherManager(new HttpUtilStub());

        List<ModuleSummary> summaryList = readModuleSummaryFromFile(VALID_MODULE_SUMMARIES_TEST_FILE_PATH);
        Map<String, ModuleInfo> actualModuleInfoMap = manager.generateModuleInfoMap(summaryList);

        Map<String, ModuleInfo> expectedModuleInfoMap = new HashMap<>();
        Optional<ModuleInfo> cs1010x = JsonUtil.readJsonFile(Paths.get(CS1010X_MODULE_INFO), ModuleInfo.class);
        Optional<ModuleInfo> is1103 = JsonUtil.readJsonFile(Paths.get(IS1103_MODULE_INFO), ModuleInfo.class);

        if (cs1010x.isPresent() && is1103.isPresent()) {
            expectedModuleInfoMap.put("CS1010X", cs1010x.get());
            expectedModuleInfoMap.put("IS1103", is1103.get());
            assertEquals(expectedModuleInfoMap, actualModuleInfoMap);
            return;
        }

        fail();
    }

    @Test
    public void fetchAndSaveModules_successfullySaveModuleInfoToFile()
            throws NusmodsException, IOException, DataConversionException {
        manager = new DataFetcherManager(new HttpUtilStub(), TEST_SAVE_FILE_PATH);

        manager.fetchAndSaveModules();
        Map<String, ModuleInfo> actualMap = readModuleInfoMapFromFile(TEST_SAVE_FILE_PATH);

        Optional<ModuleInfo> expectedCs1010x = JsonUtil.readJsonFile(Paths.get(CS1010X_MODULE_INFO), ModuleInfo.class);
        Optional<ModuleInfo> expectedIs1103 = JsonUtil.readJsonFile(Paths.get(IS1103_MODULE_INFO), ModuleInfo.class);

        // clean up test data file after we're done
        Files.deleteIfExists(Paths.get(TEST_SAVE_FILE_PATH));

        if (expectedCs1010x.isPresent() && expectedIs1103.isPresent()) {
            Map<String, ModuleInfo> expectedMap = new HashMap<>();
            expectedMap.put("CS1010X", expectedCs1010x.get());
            expectedMap.put("IS1103", expectedIs1103.get());
            assertEquals(expectedMap, actualMap);
            return;
        }

        fail();
    }

    //---------------- HttpUtil Stubs -----------------//

    private static class ValidModuleFileHttpUtilStub extends HttpUtil {
        @Override
        public String makeGETRequest(String urlString) throws NusmodsException {
            try {
                return FileUtil.readFromFile(Paths.get(MODULE_TEST_FILE_PATH));
            } catch (IOException ex) {
                throw new NusmodsException(ex);
            }
        }
    }

    private static class InvalidModuleFileHttpUtilStub extends HttpUtil {
        @Override
        public String makeGETRequest(String urlString) throws NusmodsException {
            try {
                return FileUtil.readFromFile(Paths.get(INVALID_MODULE_TEST_FILE_PATH));
            } catch (IOException ex) {
                throw new NusmodsException(ex);
            }
        }
    }

    private static class HttpUtilStub extends HttpUtil {
        // declare string constants here since string.format doesn't produce compile-time constants required
        // for the switch statement below
        public static final String CS1010X_FILE_PATH = "https://api.nusmods.com/v2/2020-2021/modules/CS1010X.json";
        public static final String IS1103_FILE_PATH = "https://api.nusmods.com/v2/2020-2021/modules/IS1103.json";

        @Override
        public String makeGETRequest(String urlString) throws NusmodsException {
            try {
                switch (urlString) {
                case DataFetcher.MODULE_SUMMARY_LIST_URL:
                    return FileUtil.readFromFile(Paths.get(VALID_MODULE_SUMMARIES_TEST_FILE_PATH));
                case CS1010X_FILE_PATH:
                    return FileUtil.readFromFile(Paths.get(CS1010X_MODULE_INFO));
                case IS1103_FILE_PATH:
                    return FileUtil.readFromFile(Paths.get(IS1103_MODULE_INFO));
                default:
                    return null;
                }
            } catch (IOException ex) {
                throw new NusmodsException(ex);
            }
        }
    }

    //---------------- Test utility fxns -----------------//

    private static List<ModuleSummary> readModuleSummaryFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                new File(filePath),
                new TypeReference<List<ModuleSummary>>(){});
    }

    private static Map<String, ModuleInfo> readModuleInfoMapFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                new File(filePath),
                new TypeReference<Map<String, ModuleInfo>>(){});
    }
}

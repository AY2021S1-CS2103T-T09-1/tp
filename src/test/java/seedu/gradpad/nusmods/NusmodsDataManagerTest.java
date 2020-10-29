package seedu.gradpad.nusmods;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.util.JsonUtil;
import seedu.gradpad.nusmods.exceptions.NusmodsException;

public class NusmodsDataManagerTest {
    private static final String VALID_MAP_TEST_FILE_PATH = "NusmodsDataManagerTest/validModuleMap.json";
    private static final String INVALID_MAP_TEST_FILE_PATH = "NusmodsDataManagerTest/invalidModuleMap"
                                                                     + ".json";
    private static final String MODULE_TEST_FILE_PATH = "src/test/resources/NusmodsDataManagerTest/validModule.json";
    private NusmodsDataManager manager = new NusmodsDataManager(new ReadFromFileDataFetcherStub(),
                                                                VALID_MAP_TEST_FILE_PATH);

    @Test
    public void constructor_defaultWithNoArgs_storesDefaultFilePath() {
        manager = new NusmodsDataManager();
        assertEquals(manager.getFilePath(), DataFetcher.DATA_FILE_PATH);
    }

    @Test
    public void getModuleInfo_validModuleInfoFile_returnsModuleInfoFromFile() throws NusmodsException,
                                                                                          DataConversionException {
        Optional<ModuleInfo> actualInfo = manager.getModuleInfo("CS2103T");
        Optional<ModuleInfo> expectedInfo = JsonUtil.readJsonFile(Paths.get(MODULE_TEST_FILE_PATH),
                                                                  ModuleInfo.class);

        if (actualInfo.isPresent() && expectedInfo.isPresent()) {
            assertEquals(actualInfo.get(), expectedInfo.get());
            return;
        }
        fail();
    }

    @Test
    public void getModuleInfo_invalidModuleInfoFileAndConnection_throwsNusmodsException() {
        manager = new NusmodsDataManager(new ReadFromFileDataFetcherStub(), INVALID_MAP_TEST_FILE_PATH);
        assertThrows(NusmodsException.class, () -> manager.getModuleInfo("CS2103T"));
    }

    @Test
    public void getModuleInfo_invalidFilePath_throwsNusmodsException() {
        manager = new NusmodsDataManager(new ReadFromFileDataFetcherStub(), "asdf.json");
        assertThrows(NusmodsException.class, () -> manager.getModuleInfo("CS2103T"));
    }

    @Test
    public void getModuleInfo_moduleNotInValidModuleFile_emptyOptional() throws NusmodsException {
        assertTrue(manager.getModuleInfo("CS2100").isEmpty());
    }

    @Test
    public void getModuleInfo_fetchFromApiSuccessful_moduleInfo() throws NusmodsException, DataConversionException {
        manager = new NusmodsDataManager(new FetchFromApiDataFetcherStub(), VALID_MAP_TEST_FILE_PATH);
        Optional<ModuleInfo> actualInfo = manager.getModuleInfo("CS2103T");
        Optional<ModuleInfo> expectedInfo = JsonUtil.readJsonFile(Paths.get(MODULE_TEST_FILE_PATH),
                                                                  ModuleInfo.class);
        if (actualInfo.isPresent() && expectedInfo.isPresent()) {
            assertEquals(actualInfo.get(), expectedInfo.get());
            return;
        }
        fail();
    }

    @Test
    public void getModuleTitle_moduleIsInData_moduleTitle() throws NusmodsException {
        Optional<String> actualTitle = manager.getModuleTitle("CS2103T");
        if (actualTitle.isPresent()) {
            assertEquals(actualTitle.get(), "Software Engineering");
            return;
        }
        fail();
    }

    @Test
    public void getModuleTitle_moduleNotInData_emptyOptional() throws NusmodsException {
        Optional<String> actualTitle = manager.getModuleTitle("CS2100");
        assertTrue(actualTitle.isEmpty());
    }

    private static class ReadFromFileDataFetcherStub extends DataFetcher {
        @Override
        void fetchAndSaveModules() {
        }

        @Override
        Optional<ModuleInfo> fetchModuleInfo(String moduleCode) throws NusmodsException {
            throw new NusmodsException(new IOException("stub exception"));
        }
    }

    private static class FetchFromApiDataFetcherStub extends DataFetcher {
        @Override
        void fetchAndSaveModules() {
        }

        @Override
        Optional<ModuleInfo> fetchModuleInfo(String moduleCode) throws NusmodsException {
            try {
                return JsonUtil.readJsonFile(Paths.get(MODULE_TEST_FILE_PATH), ModuleInfo.class);
            } catch (DataConversionException ex) {
                throw new NusmodsException(ex);
            }
        }
    }
}

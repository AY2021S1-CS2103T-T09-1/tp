package seedu.gradpad.nusmods;

import java.util.Optional;

import seedu.gradpad.nusmods.exceptions.NusmodsException;

public abstract class DataFetcher {
    public static final String DATA_FILE_PATH = "data/modules.json";
    public static final String SAVE_DATA_FILE_PATH = "./src/main/resources/data/modules.json";
    public static final String MODULE_SUMMARY_LIST_URL = "https://api.nusmods.com/v2/2020-2021/moduleList.json";
    public static final String MODULE_INFO_URL = "https://api.nusmods.com/v2/2020-2021/modules/%s.json";


    abstract void fetchAndSaveModules() throws NusmodsException;
    abstract Optional<ModuleInfo> fetchModuleInfo(String moduleCode) throws NusmodsException;
}

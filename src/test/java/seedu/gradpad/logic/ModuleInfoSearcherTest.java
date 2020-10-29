package seedu.gradpad.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.gradpad.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.util.JsonUtil;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.nusmods.ModuleInfo;

/**
 * Contains integration tests for {@code ModuleInfoSearcher}.
 */
class ModuleInfoSearcherTest {

    private static final String CS1010X = "src/test/resources/NusmodsDataManagerTest/CS1010X.json";


    private ModuleInfoSearcher moduleInfoSearcher = new ModuleInfoSearcher();

    @Test
    public void search_empty_module() {
        String emptyModule = "";
        assertThrows(CommandException.class, ModuleInfoSearcher.MESSAGE_EMPTY_SEARCH, ()
            -> moduleInfoSearcher.searchModule(emptyModule));
    }

    @Test
    public void search_false_module() {
        String falseModule = "AA0000";
        assertThrows(CommandException.class, ModuleInfoSearcher.MESSAGE_FAILED_TO_FIND_MODULE, ()
            -> moduleInfoSearcher.searchModule(falseModule));
    }

    @Test
    public void search_success() throws DataConversionException {
        ModuleInfo cs1010x = JsonUtil.readJsonFile(Paths.get(CS1010X), ModuleInfo.class).get();
        String moduleCode = "CS1010X";
        ModuleInfo actualModuleInfo = null;
        try {
            actualModuleInfo = moduleInfoSearcher.searchModule(moduleCode);
        } catch (CommandException e) {
            assert false;
        }
        assertEquals(actualModuleInfo, cs1010x);
    }

}

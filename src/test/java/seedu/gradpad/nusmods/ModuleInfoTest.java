package seedu.gradpad.nusmods;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.gradpad.commons.exceptions.DataConversionException;
import seedu.gradpad.commons.util.JsonUtil;

public class ModuleInfoTest {
    private static final String CS1010X = "src/test/resources/NusmodsDataManagerTest/CS1010X.json";
    private static final String IS1103 = "src/test/resources/NusmodsDataManagerTest/IS1103.json";

    @Test
    public void equals() throws DataConversionException {
        Optional<ModuleInfo> cs1010x = JsonUtil.readJsonFile(Paths.get(CS1010X), ModuleInfo.class);
        Optional<ModuleInfo> cs1010xDuplicate = JsonUtil.readJsonFile(Paths.get(CS1010X), ModuleInfo.class);
        Optional<ModuleInfo> is1103 = JsonUtil.readJsonFile(Paths.get(IS1103), ModuleInfo.class);

        if (cs1010x.isEmpty() || is1103.isEmpty()) {
            fail();
        }

        // null check
        assertFalse(cs1010x.get().equals(null));

        // same module --> true
        assertTrue(cs1010x.get().equals(cs1010x.get()));

        // different modules but same fields --> true
        assertTrue(cs1010x.get().equals(cs1010xDuplicate.get()));

        // different module --> false
        assertFalse(cs1010x.get().equals(is1103.get()));
    }

    @Test
    public void hash() throws DataConversionException {
        Optional<ModuleInfo> cs1010x = JsonUtil.readJsonFile(Paths.get(CS1010X), ModuleInfo.class);
        Optional<ModuleInfo> cs1010xDuplicate = JsonUtil.readJsonFile(Paths.get(CS1010X), ModuleInfo.class);
        Optional<ModuleInfo> is1103 = JsonUtil.readJsonFile(Paths.get(IS1103), ModuleInfo.class);

        if (cs1010x.isEmpty() || cs1010xDuplicate.isEmpty() || is1103.isEmpty()) {
            fail();
        }

        // same module --> same hash code
        assertEquals(cs1010x.get().hashCode(), cs1010x.get().hashCode());

        // different modules but same fields --> same hash code
        assertEquals(cs1010x.get().hashCode(), cs1010xDuplicate.get().hashCode());

        // different modules --> different hash code
        assertNotEquals(cs1010x.get().hashCode(), is1103.get().hashCode());

    }
}

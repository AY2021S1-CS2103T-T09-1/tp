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

public class ModuleSummaryTest {
    private static final String CS1010X_SUMMARY = "src/test/resources/NusmodsDataManagerTest/CS1010XSummary.json";
    private static final String IS1103_SUMMARY = "src/test/resources/NusmodsDataManagerTest/IS1103Summary.json";

    @Test
    public void equals() throws DataConversionException {
        Optional<ModuleSummary> cs1010xSummary = JsonUtil.readJsonFile(Paths.get(CS1010X_SUMMARY), ModuleSummary.class);
        Optional<ModuleSummary> cs1010xSummaryDuplicate = JsonUtil.readJsonFile(Paths.get(CS1010X_SUMMARY),
                                                                                ModuleSummary.class);
        Optional<ModuleSummary> is1103Summary = JsonUtil.readJsonFile(Paths.get(IS1103_SUMMARY), ModuleSummary.class);

        if (cs1010xSummary.isEmpty() || is1103Summary.isEmpty()) {
            fail();
        }

        // null check
        assertFalse(cs1010xSummary.get().equals(null));

        // same module --> true
        assertTrue(cs1010xSummary.get().equals(cs1010xSummary.get()));

        // different module object but same fields --> true
        assertTrue(cs1010xSummary.get().equals(cs1010xSummaryDuplicate.get()));

        // different module --> false
        assertFalse(cs1010xSummary.get().equals(is1103Summary.get()));
    }

    @Test
    public void hash() throws DataConversionException {
        Optional<ModuleSummary> cs1010xSummary = JsonUtil.readJsonFile(Paths.get(CS1010X_SUMMARY), ModuleSummary.class);
        Optional<ModuleSummary> cs1010xSummaryDuplicate = JsonUtil.readJsonFile(Paths.get(CS1010X_SUMMARY),
                                                                        ModuleSummary.class);
        Optional<ModuleSummary> is1103Summary = JsonUtil.readJsonFile(Paths.get(IS1103_SUMMARY), ModuleSummary.class);

        if (cs1010xSummary.isEmpty() || cs1010xSummaryDuplicate.isEmpty() || is1103Summary.isEmpty()) {
            fail();
        }

        // same module --> same hash code
        assertEquals(cs1010xSummary.get().hashCode(), cs1010xSummary.get().hashCode());

        // different module object but same fields --> same hash code
        assertEquals(cs1010xSummary.get().hashCode(), cs1010xSummaryDuplicate.get().hashCode());

        // different module --> different hash code
        assertNotEquals(cs1010xSummary.get().hashCode(), is1103Summary.get().hashCode());
    }
}

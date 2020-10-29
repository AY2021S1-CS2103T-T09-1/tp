package seedu.gradpad.model.module;

import static seedu.gradpad.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModularCreditsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModularCredits(null));
    }
}

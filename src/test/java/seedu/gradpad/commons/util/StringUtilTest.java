package seedu.gradpad.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gradpad.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsCharSequenceIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for ModuleCode: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsCharSequenceIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsCharSequenceIgnoreCase(
                "typical ModuleCode", null));
    }

    @Test
    public void containsCharSequenceIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "CharSequence parameter cannot be empty", () ->
                StringUtil.containsCharSequenceIgnoreCase("typical ModuleCode", "  "));
    }

    @Test
    public void containsCharSequenceIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "CharSequence parameter should be a single word", () ->
                StringUtil.containsCharSequenceIgnoreCase("typical ModuleCode", "aaa BBB"));
    }

    @Test
    public void containsCharSequenceIgnoreCase_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsCharSequenceIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for CharSequence:
     *   - any charSequence
     *   - charSequence containing symbols/numbers
     *   - charSequence with leading/trailing spaces
     *
     * Valid equivalence partitions for ModuleCode:
     *   - one moduleCode
     *   - ModuleCode with extra spaces
     *
     * Possible scenarios returning true:
     *   - charSequence matches any part of moduleCode
     *
     * Possible scenarios returning false:
     *   - ModuleCode does not contain charSequence
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsCharSequenceIgnoreCase_validInputs_correctResult() {

        // Empty ModuleCode
        assertFalse(StringUtil.containsCharSequenceIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsCharSequenceIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertTrue(StringUtil.containsCharSequenceIgnoreCase("bbb", "bb")); // ModuleCode word
        // bigger than CharSequence
        assertFalse(StringUtil.containsCharSequenceIgnoreCase("bbb", "bbbb")); // CharSequence bigger
        // than ModuleCode word

        // Matches word in the ModuleCode, different upper/lower case letters
        assertTrue(StringUtil.containsCharSequenceIgnoreCase("bBb", "Bbb")); // Different upper/lower case
        assertTrue(StringUtil.containsCharSequenceIgnoreCase("ccc@1", "CCc@1")); // With symbols/numbers
        assertTrue(StringUtil.containsCharSequenceIgnoreCase("  AAA   ", "aaa")); // ModuleCode has extra
        // spaces
        assertTrue(StringUtil.containsCharSequenceIgnoreCase("ccc", "  ccc  ")); // CharSequence has extra
        // spaces
    }

    //---------------- Tests for ignoreCase --------------------------------------

    @Test
    public void ignoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.ignoreCase(null));
    }

    @Test
    public void ignoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "CharSequence parameter cannot be empty", ()
            -> StringUtil.ignoreCase("  "));
    }

    @Test
    public void ignoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "CharSequence parameter should be a single word", ()
            -> StringUtil.ignoreCase("aaa BBB"));
    }

    @Test
    public void ignoreCase_validInputs_correctResult() {
        // Return all uppercase letters with different upper/lower case letters
        assertEquals(StringUtil.ignoreCase("abc"), "ABC");
        assertEquals(StringUtil.ignoreCase("abc1"), "ABC1");
        assertEquals(StringUtil.ignoreCase("ab1@c"), "AB1@C");
        assertEquals(StringUtil.ignoreCase("   abc  "), "ABC");
        assertEquals(StringUtil.ignoreCase("ABC"), "ABC");
        assertEquals(StringUtil.ignoreCase("cS2100"), "CS2100");
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
                .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

}

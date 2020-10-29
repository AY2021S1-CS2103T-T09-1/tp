package seedu.gradpad.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code moduleCode} contains the {@code charSequence}.
     *   Ignores case, a partial match is required.
     *   <br>examples:<pre>
     *       containsCharSequenceIgnoreCase("ABc", "abc") == true
     *       containsCharSequenceIgnoreCase("def", "DEF") == true
     *       containsCharSequenceIgnoreCase("ABc", "AB") == true // partial match
     *       </pre>
     * @param moduleCode cannot be null
     * @param charSequence cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsCharSequenceIgnoreCase(String moduleCode, String charSequence) {
        requireNonNull(moduleCode);
        requireNonNull(charSequence);

        String preppedModuleCode = moduleCode.trim().toUpperCase();
        String preppedCharSequence = charSequence.trim().toUpperCase();
        checkArgument(!preppedCharSequence.isEmpty(), "CharSequence parameter cannot be empty");
        checkArgument(preppedCharSequence.split("\\s+").length == 1, "CharSequence parameter should be a "
                + "single word");

        return preppedModuleCode.contains(preppedCharSequence);
    }

    /**
     * Ensures that the charSequence is in the correct String format as the module code.
     *
     * @param charSequence cannot be null, cannot be empty, must be a single word.
     * @return the upper case of the module code.
     */
    public static String ignoreCase(String charSequence) {
        String preppedCharSequence = charSequence.trim().toUpperCase();
        checkArgument(!preppedCharSequence.isEmpty(), "CharSequence parameter cannot be empty");
        checkArgument(preppedCharSequence.split("\\s+").length == 1, "CharSequence parameter should be a "
                + "single word");
        return preppedCharSequence;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}

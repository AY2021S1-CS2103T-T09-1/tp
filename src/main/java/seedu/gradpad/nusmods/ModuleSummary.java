package seedu.gradpad.nusmods;


import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents jackson-friendly module summaries from NUSMods.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleSummary {
    private String moduleCode;
    private String title;
    private List<Integer> semesters;

    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getSemesters() {
        return semesters;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModuleSummary)) {
            return false;
        }

        ModuleSummary otherModuleSummary = (ModuleSummary) other;
        return otherModuleSummary.getModuleCode().equals(getModuleCode())
                       && otherModuleSummary.getTitle().equals(getTitle())
                       && otherModuleSummary.getSemesters().equals(getSemesters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode, title, semesters);
    }

    @Override
    public String toString() {
        return getModuleCode() + " " + getTitle() + " " + getSemesters();
    }
}

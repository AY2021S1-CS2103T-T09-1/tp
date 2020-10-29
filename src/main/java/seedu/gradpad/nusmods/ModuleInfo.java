package seedu.gradpad.nusmods;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents jackson-friendly module information from NUSMods.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleInfo {
    private String moduleCode;
    private String title;
    private String moduleCredit;
    private String description;
    private String preclusion;
    private String prerequisite;

    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public String getModuleCredit() {
        return moduleCredit;
    }

    public String getDescription() {
        return description;
    }

    public String getPreclusion() {
        return preclusion;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModuleInfo)) {
            return false;
        }

        ModuleInfo otherModuleInfo = (ModuleInfo) other;
        return otherModuleInfo.getModuleCode().equals(getModuleCode())
                       && otherModuleInfo.getTitle().equals(getTitle())
                       && otherModuleInfo.getModuleCredit().equals(getModuleCredit())
                       && otherModuleInfo.getDescription().equals(getDescription())
                       && equalsIfNotNull(otherModuleInfo.getPreclusion(), getPreclusion())
                       && equalsIfNotNull(otherModuleInfo.getPrerequisite(), getPrerequisite());
    }

    private boolean equalsIfNotNull(Object o1, Object o2) {
        return o1 == null || o2 == null || o1.equals(o2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode, title, moduleCredit, description, preclusion, prerequisite);
    }

    @Override
    public String toString() {
        return getModuleCode() + " " + getTitle() + " " + getModuleCredit();
    }
}

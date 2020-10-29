package seedu.gradpad.nusmods;

import java.util.Optional;

import seedu.gradpad.nusmods.exceptions.NusmodsException;

public interface NusmodsData {
    Optional<String> getModuleTitle(String moduleCode) throws NusmodsException;
    Optional<ModuleInfo> getModuleInfo(String moduleCode) throws NusmodsException;
}

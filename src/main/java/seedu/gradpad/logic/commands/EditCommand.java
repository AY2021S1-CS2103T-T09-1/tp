package seedu.gradpad.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.gradpad.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.gradpad.model.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.gradpad.commons.util.CollectionUtil;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.module.ModularCredits;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.model.module.ModuleCode;
import seedu.gradpad.model.module.ModuleTitle;
import seedu.gradpad.model.tag.Tag;

/**
 * Edits the details of an existing module in the GradPad.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
        + "by its module code. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: MODULE CODE "
        + "[" + PREFIX_CODE + "MODULE CODE] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " cs2103t "
        + PREFIX_CODE + "cs2108 ";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the GradPad.";
    public static final String MESSAGE_MODULE_NOT_YET_ADDED = "Module %1$s has not been added yet!";

    private final ModuleCode code;
    private final EditModuleDescriptor editModuleDescriptor;

    /**
     * @param code of the module in GradPad to edit
     * @param editModuleDescriptor details to edit the module with
     */
    public EditCommand(ModuleCode code, EditModuleDescriptor editModuleDescriptor) {
        requireNonNull(code);
        requireNonNull(editModuleDescriptor);

        this.code = code;
        this.editModuleDescriptor = new EditModuleDescriptor(editModuleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> modules = model.getGradPad().getModuleList();

        Optional<Module> moduleToEdit = modules.stream()
            .filter(x -> x.getModuleCode().equals(code)).findFirst();
        if (moduleToEdit.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_YET_ADDED, code.toString()));
        }

        Module editedModule = createEditedModule(moduleToEdit.get(), editModuleDescriptor);
        if (!moduleToEdit.get().isSameModule(editedModule) && model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.setModule(moduleToEdit.get(), editedModule);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit, EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        ModuleCode updatedCode = editModuleDescriptor.getModuleCode().orElse(moduleToEdit.getModuleCode());
        ModuleTitle updatedTitle = editModuleDescriptor.getModuleTitle()
            .orElse(moduleToEdit.getModuleTitle());
        ModularCredits updatedCredits = editModuleDescriptor.getModularCredits()
            .orElse(moduleToEdit.getModularCredits());
        Set<Tag> updatedTags = editModuleDescriptor.getTags().orElse(moduleToEdit.getTags());

        return new Module(updatedCode, updatedTitle, updatedCredits, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return code.equals(e.code)
            && editModuleDescriptor.equals(e.editModuleDescriptor);
    }

    /**
     * Stores the details to edit the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditModuleDescriptor {
        private ModuleCode code;
        private ModuleTitle title;
        private ModularCredits credits;
        private Set<Tag> tags;

        public EditModuleDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditModuleDescriptor(EditModuleDescriptor toCopy) {
            setModuleCode(toCopy.code);
            setModuleTitle(toCopy.title);
            setModularCredits(toCopy.credits);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(code, title, credits, tags);
        }

        public void setModuleCode(ModuleCode code) {
            this.code = code;
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable(code);
        }

        public void setModuleTitle(ModuleTitle title) {
            this.title = title;
        }

        public Optional<ModuleTitle> getModuleTitle() {
            return Optional.ofNullable(title);
        }

        public void setModularCredits(ModularCredits credits) {
            this.credits = credits;
        }

        public Optional<ModularCredits> getModularCredits() {
            return Optional.ofNullable(credits);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditModuleDescriptor)) {
                return false;
            }

            // state check
            EditModuleDescriptor e = (EditModuleDescriptor) other;

            return getModuleCode().equals(e.getModuleCode())
                && getModuleTitle().equals(e.getModuleTitle())
                && getModularCredits().equals(e.getModularCredits())
                && getTags().equals(e.getTags());
        }
    }
}

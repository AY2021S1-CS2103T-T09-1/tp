package seedu.gradpad.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.gradpad.commons.core.GuiSettings;
import seedu.gradpad.commons.core.LogsCenter;
import seedu.gradpad.logic.commands.ClearCommand;
import seedu.gradpad.logic.commands.Command;
import seedu.gradpad.logic.commands.CommandResult;
import seedu.gradpad.logic.commands.DeleteCommand;
import seedu.gradpad.logic.commands.YesCommand;
import seedu.gradpad.logic.commands.exceptions.CommandException;
import seedu.gradpad.logic.parser.GradPadParser;
import seedu.gradpad.logic.parser.exceptions.ParseException;
import seedu.gradpad.model.Model;
import seedu.gradpad.model.ReadOnlyGradPad;
import seedu.gradpad.model.module.Module;
import seedu.gradpad.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String MESSAGE_CONFIRMATION_SYNTAX = "\n\nType:\tyes<Enter>\tto confirm "
            + "OR\nType:\tno<Enter>\t\tto cancel";
    public static final String MESSAGE_CONFIRMATION_CANCEL = "Command aborted - ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final GradPadParser gradPadParser;

    private Command stalledCommand;
    private String stalledCommandText;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        assert(model != null && storage != null);
        this.model = model;
        this.storage = storage;
        gradPadParser = new GradPadParser();
    }

    private CommandResult handleStall(Command command, String commandText) throws CommandException {
        if (command instanceof ClearCommand) {
            assignStalledComponents(command, commandText);
            return new CommandResult(ClearCommand.MESSAGE_CONFIRMATION + MESSAGE_CONFIRMATION_SYNTAX);
        } else {
            Module moduleToBeDeleted = ((DeleteCommand) command).getModuleToDelete(model);
            assignStalledComponents(command, commandText);
            return new CommandResult(DeleteCommand.MESSAGE_CONFIRMATION + moduleToBeDeleted
                    + MESSAGE_CONFIRMATION_SYNTAX);
        }
    }

    private void assignStalledComponents(Command command, String commandText) {
        stalledCommand = command;
        stalledCommandText = commandText;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        boolean isCancel = stalledCommand != null && !commandText.equalsIgnoreCase("yes");

        if (isCancel) {
            stalledCommand = null;
            return new CommandResult(MESSAGE_CONFIRMATION_CANCEL
                    + String.format("\"%s\"", stalledCommandText));
        }

        CommandResult commandResult;
        Command command = gradPadParser.parseCommand(commandText);
        boolean isConfirmation = command instanceof YesCommand && stalledCommand != null;

        if (command.requiresStall()) {
            return handleStall(command, commandText);
        } else if (isConfirmation) {
            command = stalledCommand;
            stalledCommand = null;
        }

        commandResult = command.execute(model);

        try {
            storage.saveGradPad(model.getGradPad());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyGradPad getGradPad() {
        return model.getGradPad();
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public Path getGradPadFilePath() {
        return model.getGradPadFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}

package seedu.gradpad.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for displaying feedbacks to user commands.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Displays the specified feedback to the user via the result display.
     *
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Hides the result display.
     */
    public void hide() {
        getRoot().setVisible(false);
    }

    /**
     * Shows the result display.
     */
    public void show() {
        getRoot().setVisible(true);
    }

}

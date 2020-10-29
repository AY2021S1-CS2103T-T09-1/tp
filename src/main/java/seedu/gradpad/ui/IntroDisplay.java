package seedu.gradpad.ui;

import javafx.scene.layout.Region;

/**
 * A ui component for displaying the opening page of GradPad.
 */
public class IntroDisplay extends UiPart<Region> {

    private static final String FXML = "IntroDisplay.fxml";

    public IntroDisplay() {
        super(FXML);
    }

    /**
     * Returns true if the intro display is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isVisible();
    }

    /**
     * Hides the intro display.
     */
    public void hide() {
        getRoot().setVisible(false);
    }

    /**
     * Shows the intro display.
     */
    public void show() {
        getRoot().setVisible(true);
    }
}

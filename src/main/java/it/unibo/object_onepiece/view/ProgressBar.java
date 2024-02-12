package it.unibo.object_onepiece.view;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
/**
 * ProgressBar that allows for update of progress and color customization
 */
public interface ProgressBar {
    /**
     * Vertical Box with vertical progress bar and text under it
     * @return topmost container which holds all children in it
     */
    VBox getContainer();
    /**
     * Update progress and text
     * @param progress
     */
    void update(int progress);
    /**
     * Update progress in proportion to maxProgress and text
     * @param progress
     * @param maxProgress
     */
    void update(int progress, int maxProgress);
    /**
     * Changes background color and front (progress) color
     * @param back
     * @param front
     */
    void setColor(Color back, Color front);
}
